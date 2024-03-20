package com.lottetour.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lottetour.web.domain.BoardVO;
import com.lottetour.web.domain.Criteria;
import com.lottetour.web.dto.PasswordDTO;
import com.lottetour.web.dto.ResponseDTO;
import com.lottetour.web.dto.SaveBoardDTO;
import com.lottetour.web.dto.UpdateBoardDTO;
import com.lottetour.web.persistence.BoardDAO;
import com.lottetour.web.util.Encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @package com.lottetour.web.service
* @class   게시물 CRUD를 위한 Service
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                 수정자                수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03     강준혁               최초 생성
*   2023. 11. 06          강준혁 	          글 작성 -addBoard(), 글 삭제 -deleteBoard(), 글 목록 조회: readBoardList() 생성.
*   2023. 11. 07          강준혁 	          글 수정:update() 생성. 조회 수 증가:updateViewCnt() 생성.
*   2023. 11. 08          강준혁 	          페이징 읽어오기: getList(), 총 게시물 수 구하기: getTotalCount() 생성.
*   2023. 11. 22     강준혁               id로 게시글 조회 메소드 추가 (패키지/프로시저 활용) : getBoardByProc(int id)
*   2023. 11. 23     강준혁               삭제 스케줄러 실행 메소드 추가: executeDeleteSchedule()
* </pre>
*/


@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardDAO boardDAO;
	private final Encrypt encrypt;
	
	//게시물 등록
	public void addBoard(BoardVO boardVO, String pwd) throws Exception {
		//비밀번호 인코딩
		String salt = encrypt.getSalt();
		String encodedPasswd = encrypt.getEncrypt(pwd,salt);
		boardVO.registerSalt(salt);
		boardVO.registerPassword(encodedPasswd);
		boardDAO.add(boardVO);
	}
	
	//게시물 목록 조회
	public List<BoardVO> readBoardList() throws Exception {
		return boardDAO.readList();
	}
	
	//게시물 상세보기.
	public BoardVO readBoardDetail(int id) throws Exception {
		return boardDAO.readDetail(id);
	}
	
	//게시물 삭제.
	public void deleteBoard(int id) throws Exception {
		boardDAO.deleteById(id);
	}
	
	//게시물 수정
	public ResponseDTO<?> update(int id, UpdateBoardDTO updateBoardDto) throws Exception {
		BoardVO boardVO = boardDAO.readDetail(id);

		//저장된 암호화된 비밀번호와 비교
		if (checkPasswd(id, updateBoardDto.getPasswd())) {
			boardVO.updateBoard(updateBoardDto.getTitle(), updateBoardDto.getContent(), 
					updateBoardDto.getUserId(), updateBoardDto.getViewCount(), updateBoardDto.getDeleteDate());
			boardDAO.updateBoard(boardVO);
			return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
		} else {
			return new ResponseDTO<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 비밀번호입니다.");
		}
	}
	
	//게시물 삭제
	public ResponseDTO<?> deleteById(int id, PasswordDTO password) throws Exception {

		//저장된 암호화된 비밀번호와 비교
		if (checkPasswd(id, password.getPasswd())) {
			boardDAO.deleteById(id);
			return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
		} else {
			return new ResponseDTO<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 비밀번호입니다.");
		}
	}

	//비밀번호 일치 확인
	public Boolean checkPasswd(int id, String pwd) throws Exception {
		BoardVO boardVO = boardDAO.readDetail(id);
		String storedSalt = boardVO.getSalt();        //저장된 salt 불러오기.
		String storedPasswd = boardVO.getPasswd();   //저장된 암호화 비밀번호 불러오기.
		String inputEncodedPasswd = encrypt.getEncrypt(pwd, storedSalt);   //입력 폼에 입력한 비밀번호를 암호화.

        return storedPasswd.equals(inputEncodedPasswd);   //저장된 비밀번호와 일치-> true, 불일치 -> false
	}
	
	//조회 수 증가
	public void updateViewCnt(int id) throws Exception {
		boardDAO.updateViewCount(id);
	}
	
	//페이징 읽어오기.
	public List<BoardVO> getList(Criteria cri) {
	    return boardDAO.getListWithPaging(cri);
	}
	
	//전체 게시글 수 읽어오기
	public int getTotalCount(Criteria cri) {
		return boardDAO.getTotalCount(cri);
	}
	
	//id로 게시글 조회 (패키지/프로시저 활용)
	public ArrayList<BoardVO> getBoardByProc(int id){
		Map<String, Object> param  = boardDAO.getBoardByProc(id);
		@SuppressWarnings("unchecked")
		ArrayList<BoardVO> boardlist = (ArrayList<BoardVO>) param.get("p_result");
		return boardlist;
	}
	
	//삭제 스케줄러 실행
    public void executeDeleteSchedule() {
        boardDAO.executeDeleteSchedule();
    }
	
}
