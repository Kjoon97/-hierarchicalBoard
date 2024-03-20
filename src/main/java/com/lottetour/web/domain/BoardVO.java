package com.lottetour.web.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
* @package com.lottetour.web.domain
* @class   게시물 데이터를 저장하기 위한 VO
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                  수정자               수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03    강준혁                최초 생성
*   2023. 11. 06        강준혁 	         글 작성 -save(), 글 삭제 -deleteById() 생성.
*   2023. 11. 13    강준혁                게시물 SHA256 암호화: passwd, salt 필드 추가.
* </pre>
*/



@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardVO {

	//게시판 번호
	private int id;
	
	//게시판 제목
	private String title;
	
	//게시판 내용
	private String content;
	
	//등록 날짜
	private Date regDate;
	
	//수정 날짜
	private Date updateDate;
	
	//삭제 날짜
	private Date deleteDate;
	
	//등록자 계정
	private String userId;
	
	//조회 수 
	private int viewCount;
	
	//삭제 유무 
	private int isDeleted;
	
	//비밀 번호.
	private String passwd;
	
	//salt
	private String salt;
	
	public void updateBoard(String title, String content, String userId, int viewCount, Date deleteDate) {
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.viewCount = viewCount;
		this.deleteDate = deleteDate;
	}
	
	public void registerPassword(String passwd) {
		this.passwd = passwd;
	}
	
	public void registerSalt(String salt) {
		this.salt = salt;
	}
	
	public void updateIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

}
