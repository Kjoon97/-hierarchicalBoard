package com.lottetour.web.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;


/**
* @package com.lottetour.web.util
* @class   SHA-256 암호화를 위한 클래스
* @author  강준혁
* @since   2023.11.14.
* @see
*
* <pre>
* << 개정이력(Modification Information) >> *
*       수정일                 수정자                수정내용
*  ------------    ---------    ---------------------------
*   2023. 11. 14     강준혁               최초 생성
* </pre>
*/

@Component
public class Encrypt {
	// 무작위 문자열 Salt
    public String getSalt() {
        //1. Random, salt 생성
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[20];
        //2. 난수 생성
        sr.nextBytes(salt);
        //3. byte To String (10진수 문자열로 변경)
        StringBuffer sb = new StringBuffer();
        for(byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
  	// SHA-256 알고리즘 적용
    public String getEncrypt(String pwd, String salt) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            System.out.println("PWD + SALT 적용 전 : " + pwd + salt);
            md.update((pwd + salt).getBytes());
            byte[] pwdSalt = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
//            System.out.println("PWD + SALT 적용 후 : " + result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
