package com.lottetour.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @package com.lottetour.web.dto
* @class   비밀번호를 body로 받기 위한 DTO
* @author  강준혁
* @since   2023.11.13.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*      수정일                   수정자                 수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 08.    강준혁                 최초 생성
* </pre>
*/



@Data
@NoArgsConstructor
@Getter
public class PasswordDTO {
	
	String passwd;

}
