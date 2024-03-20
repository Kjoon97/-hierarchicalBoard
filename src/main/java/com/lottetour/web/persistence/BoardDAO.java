package com.lottetour.web.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.lottetour.web.domain.BoardVO;
import com.lottetour.web.domain.Criteria;

import lombok.RequiredArgsConstructor;

/**
* @package com.lottetour.web.persistence
* @class   게시물 테이블에 접근하기 위한 객체 DAO
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*   수정일         수정자           수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03     강준혁             최초 생성
*   2023. 11. 06          강준혁 	        글 작성:add(), 글 삭제:deleteById(), 글 목록 조회:readList() 생성.
*   2023. 11. 07          강준혁 	        글 수정:updateBoard() 생성. 조회 수 증가:updateViewCount() 생성.
*   2023. 11. 08          강준혁 	        페이징: getListWithPaging(), 총 게시물 수 구하기: getTotalCount() 생성.
*   2023. 11. 22     강준혁             id로 게시글 조회 메소드 추가 (패키지/프로시저 활용) : getBoardByProc(int p_id)
*   2023. 11. 23     강준혁             삭제 스케줄러 실행 메소드 추가: executeDeleteSchedule()
* </pre>
*/

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardDAO {
	
	private final SqlSession sqlSession;
	
	private static final String namespace = "com.lottetour.web.mapper.BoardMapper";
	
	//글 등록.
	public void add(BoardVO board) throws Exception {
		sqlSession.insert(namespace + ".insert", board);
	}
	
	//글 목록 읽기
	public List<BoardVO> readList() throws Exception {
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		Map<String, String> map = new HashMap<String, String>();
		boardList = sqlSession.selectList(namespace + ".selectAll");
		return boardList;
	}
	
	//상세보기
	public BoardVO readDetail(int id) throws Exception {
		BoardVO boardvo = sqlSession.selectOne(namespace+".selectByid", id);
		return boardvo;
	}
	
	//글 삭제
	public void deleteById(int id) throws Exception {
		//sqlSession.delete(namespace + ".delete", id);
		sqlSession.update(namespace+ ".changeIsDeleted", id);
	}
	
	//글 수정
	public void updateBoard(BoardVO board) throws Exception {
		sqlSession.update(namespace + ".update", board);
	}
	
	//조회수 증가
	public void updateViewCount(int id) throws Exception {
		sqlSession.update(namespace + ".updateviewcount", id); 
	};
	
	//페이징
	public List<BoardVO> getListWithPaging(Criteria cri){
		return sqlSession.selectList(namespace + ".getListWithPaging", cri);
	}
	
	//전체 수
	public int getTotalCount(Criteria cri) {
		return sqlSession.selectOne(namespace+ ".getTotalCount", cri);
	}
	
	//해당 날짜에 게시물 삭제 컬럼 update
	public void DeleteListByDate(String todayDate ){
		sqlSession.update(namespace + ".deleteListBySchedule", todayDate);
	}
	
	
    //id로 게시글 조회 (패키지/프로시저 활용)
    public Map<String, Object> getBoardByProc(int p_id) {
    	Map<String, Object> param = new HashMap<>();
    	param.put("p_id", p_id);
    	sqlSession.selectOne(namespace+".getBoardByProc", param);
		log.info(param.toString());
        return param;
    }
    
    //삭제 스케줄러 실행
    public void executeDeleteSchedule() {
    	sqlSession.update(namespace + ".executeDeleteBoardJob");
    }
}
