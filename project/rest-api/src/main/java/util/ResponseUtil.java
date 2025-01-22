package util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import template.ResponseData;
import template.ResponseErrData;

public class ResponseUtil {

    private ResponseUtil() {
    }

    /**
     * 성공 응답
     */
    public static <T> ResponseEntity<ResponseData<T>> ok(T data) {
        ResponseData<T> responseData = new ResponseData<>(data);
        return new ResponseEntity<>(responseData, HttpStatus.valueOf(responseData.getStatus()));
    }

    /**
     * 실패 응답
     */
    public static ResponseEntity<ResponseErrData> err(int status, String message) {
        return err(status, false, message);
    }

    public static ResponseEntity<ResponseErrData> err(int status, boolean showMessage, String message) {
        ResponseErrData responseErrData = new ResponseErrData(status, showMessage, message);
        return new ResponseEntity<>(responseErrData, HttpStatus.valueOf(responseErrData.getStatus()));
    }
}
