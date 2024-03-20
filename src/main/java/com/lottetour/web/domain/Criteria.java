package com.lottetour.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
* @package com.lottetour.web.domain
* @class   페이징과 검색 데이터를 묶어주는 객체.
* @author  강준혁
* @since   2023.11.08.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                  수정자               수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 08.    강준혁               최초 생성
* </pre>
*/


@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria(){
		this(1,5);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
