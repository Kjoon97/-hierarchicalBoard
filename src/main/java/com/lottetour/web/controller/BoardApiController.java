package com.lottetour.web.controller;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lottetour.web.domain.BoardVO;
import com.lottetour.web.dto.PasswordDTO;
import com.lottetour.web.dto.ResponseDTO;
import com.lottetour.web.dto.SaveBoardDTO;
import com.lottetour.web.dto.UpdateBoardDTO;
import com.lottetour.web.service.BoardService;
import com.lottetour.web.util.Encrypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @package com.lottetour.web.Controller
* @class   rest통신을 위한 Controller
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*      수정일                   수정자                                수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03     강준혁               최초 생성
*   2023. 11. 06          강준혁 	          글 작성 -save(), 글 삭제 -deleteById() 생성.
*   2023. 11. 07          강준혁 	          글 수정 -update() 생성.
*   2023. 11. 22     강준혁               에러 페이지 생성(프로시저 게시글 조회)
* </pre>
*/

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApiController {
	
	private final BoardService boardService;
	
    //글 작성
    @PostMapping("/api/board")
    public ResponseDTO<Integer> save(@RequestBody SaveBoardDTO saveBoardDto) throws Exception{
    
    	BoardVO boardVO = saveBoardDto.toEntity();
        boardService.addBoard(boardVO, saveBoardDto.getPasswd());
  
    	return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
    //글 삭제(삭제 컬럼 0->1 업데이트)
    @PatchMapping("/api/board/{id}")
    public ResponseDTO<?> deleteById(@PathVariable int id, @RequestBody PasswordDTO password) throws Exception{
    	ResponseDTO<?> response = boardService.deleteById(id, password);
    	return response;
    }
    
    //글 수정하기
    @PutMapping("/api/board/{id}")
    public ResponseDTO<?> update(@PathVariable int id, @RequestBody UpdateBoardDTO updateBoardDto) throws Exception{
    	ResponseDTO<?> response = boardService.update(id, updateBoardDto);
        return response;
    }
    
    //에러 페이지 호출
    @GetMapping("/error/page")
    public String errorPage(@ModelAttribute("errorMessage") String errorMessage) {
    	return "<h1>" + errorMessage + "</h1>";
    }

}
