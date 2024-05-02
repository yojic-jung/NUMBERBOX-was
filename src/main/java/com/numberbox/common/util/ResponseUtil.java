package com.numberbox.common.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 응답 메시지 전송
 */
public class ResponseUtil {

    /**
     * 예외 메시지 전송
     */
    public static ResponseEntity<Map<String, Object>> makeErrMsg(Boolean showMessage, String message, HttpStatusCode httpStatusCode) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("showMessage", showMessage);
        resultMap.put("message", message);
        return new ResponseEntity(resultMap, httpStatusCode);
    }

    public static ResponseEntity<Map<String, Object>> makeErrMsg(String message, HttpStatusCode httpStatusCode) {
        return makeErrMsg(true, message, httpStatusCode);
    }
}
