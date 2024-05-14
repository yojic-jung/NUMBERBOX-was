package util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import template.ResponseData;

public class ResponseUtil {
    /**
     * 성공 응답
     */
    public static <T> ResponseEntity<ResponseData<T>> ok(T data) {
        ResponseData<T> responseData = new ResponseData<>(data);
        return new ResponseEntity<>(responseData, HttpStatus.valueOf(responseData.getStatus()));
    }
}
