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
     * 응답 메시지 전송(200 제외)
     */
    public static ResponseEntity<Map<String, Object>> response(Boolean showMessage, String message, HttpStatusCode httpStatusCode) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("showMessage", showMessage);
        resultMap.put("message", message);
        return new ResponseEntity(resultMap, httpStatusCode);
    }

    public static ResponseEntity<Map<String, Object>> response(String message, HttpStatusCode httpStatusCode) {
        return response(true, message, httpStatusCode);
    }
}
