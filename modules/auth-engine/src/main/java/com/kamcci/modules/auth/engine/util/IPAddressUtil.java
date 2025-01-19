package com.kamcci.modules.auth.engine.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class IPAddressUtil {
    // 프록시 서버를 거쳤을 때 헤더 값을 순회하면서 IP 주소를 반환하고, 없으면 req.getRemoteAddr() 반환
    public static String getIPAddress(HttpServletRequest req) {
        return Arrays.stream(ProxyIPHeaderType.values()).map(header -> req.getHeader(header.getAttrName()))
                .filter(value -> value != null && !value.isEmpty()).findFirst().orElse(req.getRemoteAddr());
    }

    // IP 주소 목록에서 공용 IP 주소(첫 번째 값)를 추출하여 반환
    public static String getPublicIPAddress(HttpServletRequest req) {
        String ip = getIPAddress(req);
        return ip.split(",")[0].trim();
    }

    public enum ProxyIPHeaderType {
        X_FORWARDED_FOR("X-Forwarded-For"), PROXY_CLIENT_IP("Proxy-Client-IP"), WL_PROXY_CLIENT_IP("WL-Proxy-Client" +
                "-IP"), HTTP_CLIENT_IP("HTTP_CLIENT_IP"), HTTP_X_FORWARDED_FOR("HTTP_X_FORWARDED_FOR"),
        HTTP_X_FORWARDED("HTTP_X_FORWARDED"), HTTP_X_CLUSTER_CLIENT_IP("HTTP_X_CLUSTER_CLIENT_IP"),
        HTTP_FORWARDED_FOR("HTTP_FORWARDED_FOR"), HTTP_FORWARDED("HTTP_FORWARDED"), HTTP_VIA("HTTP_VIA"),
        REMOTE_ADDR("REMOTE_ADDR");
        private final String attrName;

        ProxyIPHeaderType(String attrName) {
            this.attrName = attrName;
        }

        public String getAttrName() {
            return attrName;
        }
    }
}

