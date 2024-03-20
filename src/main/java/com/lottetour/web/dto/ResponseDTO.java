package com.lottetour.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @package com.lottetour.web.dto
* @class   rest api 통신 응답을 위한 DTO
* @author  강준혁
* @since   2023.11.03.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                 수정자                  수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 03.    강준혁                  최초 생성
* </pre>
*/


@Data
@NoArgsConstructor
@Getter
public class ResponseDTO<T> {
    int statusCode;
    T data;

    //상태 코드만 리턴하고 싶을 때 사용.
    public ResponseDTO(int statusCode){
        super();
        this.statusCode = statusCode;
    }

    //상태 코드, 데이터를 리턴 하고 싶을 때 사용.
    public ResponseDTO(int statusCode, T data){
        super();
        this.statusCode =statusCode;
        this.data =data;
    }
}