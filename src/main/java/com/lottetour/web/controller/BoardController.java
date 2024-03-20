package com.lottetour.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottetour.web.domain.BoardVO;
import com.lottetour.web.domain.Criteria;
import com.lottetour.web.dto.PageDTO;
import com.lottetour.web.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.sql.DATE;

/**
* @package com.lottetour.web.Controller
* @class   게시판 조회,등록, 삭제하기 위한 Controller
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*      수정일                    수정자                                수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03     강준혁               최초 생성
*   2023. 11. 06          강준혁 	          글 작성 -save(), 글 삭제 -deleteById() 생성.
*   2023. 11. 07          강준혁 	          상세보기 조회: detailBoard(), 수정 페이지 조회: modifyBoard() 생성.
*   2023. 11. 07          강준혁 	          상세보기: detailBoard()에서 조회 수 증가 updateViewCnt() 호출.
*   2023. 11. 08          강준혁 	          페이징, 검색을 위해 home()에서 Criteria 매개변수 추가.
*   2023. 11. 15          강준혁 	          불필요한 주석 삭제
*   2023. 11. 22     강준혁               게시글 조회 메소드(프로시저 활용 버전) 생성: detailByPrc()
* </pre>
*/

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService bs;
	
	
	//게시물 리스트
	@GetMapping(value="/")
	public String home(Criteria cri, Model model) throws Exception {
		
		List<BoardVO> boards = bs.getList(cri);   
		model.addAttribute("boards",boards);
		model.addAttribute("pageMaker", new PageDTO(cri, bs.getTotalCount(cri))); 
		
		return "board/list";
	}
	
	//게시물 등록
	@GetMapping(value="/board")
	public String registerBoard() throws Exception {
		return "board/register";
	}
	
	//게시물 상세보기
	@GetMapping(value="/board/detail/{id}")
	public String detailBoard(@PathVariable int id, Model model) throws Exception {
		
		BoardVO board = bs.readBoardDetail(id);
		bs.updateViewCnt(id);
		model.addAttribute("board",board);
		bs.readBoardDetail(id);
		return "board/detail";
	}
	
	//게시물 수정페이지
	@GetMapping(value="/board/{id}")
	public String modifyBoard(@PathVariable int id, Model model) throws Exception {
		
		BoardVO board = bs.readBoardDetail(id);
		Date dd = board.getDeleteDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String deleteDate  = sdf.format(dd);
		model.addAttribute("deleteDate", deleteDate);
		model.addAttribute("board",board);
		
		return "board/modify";
	}
	
	//게시글 조회 (프로시저 활용 버전)
	@GetMapping(value="/board/detail/proc/{id}")
	public String detailByPrc(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
		ArrayList<BoardVO> boardlist = bs.getBoardByProc(id);
		
		//예외처리
		if (boardlist.isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", id + "번 게시물이 존재하지 않습니다.");
			return "redirect:/error/page";
		}
		BoardVO board = boardlist.get(0);
		model.addAttribute("board", board);
		
		return "board/proc";
	}

}
