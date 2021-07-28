package com.utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;
import java.security.NoSuchAlgorithmException;


public class Signature {
    public static final String HMAC_SHA256 = "HmacSHA256";
    public static Mac MAC;
    static {
        try {
            MAC = Mac.getInstance(HMAC_SHA256);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeErrorException(new Error("Can't get Mac's instance."));
        }
    }
    /**
     * 生成sign
     **/
    public static String toSign(String timestamp, String method, String requestPath,
                          String queryString, String body, String secretKey) throws Exception {
        // 签名串
        String preHash = preHash(timestamp, method, requestPath, queryString, body);
        byte[] secretKeyBytes = secretKey.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        Mac mac = (Mac) MAC.clone();

        mac.init(secretKeySpec);
        return Hex.encodeHexString(mac.doFinal(preHash.getBytes("UTF-8")));
    }

    /**
     * 签名串
     **/
    private static String preHash(String timestamp, String method, String requestPath, String queryString, String body) {

        StringBuilder preHash = new StringBuilder();
        preHash.append(timestamp);
        preHash.append(method.toUpperCase());
        preHash.append(requestPath);
        if (!StringUtils.isEmpty(queryString)) {
            preHash.append("?").append(queryString);
        }
        if (!StringUtils.isEmpty(body)) {
            preHash.append(body);
        }
        return preHash.toString();
    }

    /**
     * queryString
     **/
    private String queryString(ServerHttpRequest request) {
        String url = request.getURI().toString();
        String queryString = "";
        if (url.contains("?")) {
            queryString = url.substring(url.lastIndexOf("?") + 1);
        }
        return queryString;
    }



}
