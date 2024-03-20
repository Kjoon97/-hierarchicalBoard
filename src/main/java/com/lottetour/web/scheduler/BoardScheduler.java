package com.lottetour.web.scheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lottetour.web.persistence.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* @package com.lottetour.web.scheduler
* @class   게시물 삭제 스케줄을 위한 스케줄러
* @author  강준혁
* @since   2023.11.14
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*   수정일                          수정자                 수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 14     강준혁                   최초 생성
*  
* </pre>
*/


@Slf4j
@Component
@RequiredArgsConstructor
public class BoardScheduler {
	
	private final BoardDAO boardDAO;

	//오늘 날짜 
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String todayDate = LocalDate.now().format(formatter);
	
	//삭제 스케줄러.
	@Scheduled(cron = "0 0 11 * * ?")  //매일 오전 11시
	public void DeleteBoard() throws Exception {
		boardDAO.DeleteListByDate(todayDate);
	}
}
