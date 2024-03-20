package com.lottetour.web.dto;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.lottetour.web.domain.Criteria;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* @package com.lottetour.web.dto
* @class   페이징 처리를 위한 데이터를 생성 및 저장하기 위한 DTO
* @author  강준혁
* @since   2023.11.08.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                 수정자                 수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 08.    강준혁                 최초 생성
* </pre>
*/


//페이지 번호 출력하는 클래스  startPage(시작페이지), endPage(끝 페이지), prev(이전 버튼 활성화 여부), next(다음 버튼 활성화 여부)
@Getter
@Setter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private Criteria cri;

	public PageDTO(Criteria _criteria, int _total) {
		this.cri = _criteria;
		this.total = _total;
		this.endPage = (int) (Math.ceil(_criteria.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		int realEnd = (int) (Math.ceil((_total * 1.0) / _criteria.getAmount()));
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
