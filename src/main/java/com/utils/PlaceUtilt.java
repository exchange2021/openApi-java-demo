package com.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

public class PlaceUtilt {
    /**
     * Post请求方式
     */

    private static String apiKey = "";
    private static  String secret = "";

    public static void main (String args[]) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        Map<String, Object> param = new HashMap<>();
        param.put("symbol", "xfnhusdt");
        param.put("volume", "1");
        param.put("side", "SELL");
        param.put("type", "LIMIT");
        param.put("price", "1");

        String timestemp = String.valueOf(System.currentTimeMillis());
        String sign  = com.spot.utils.Signature.toSign(timestemp, "POST", "/sapi/v1/order", "", JSON.toJSONString(param), secret);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(param));
        Request request = new Request.Builder()
                .url("https://openapi.xxx.com/sapi/v1/order")
                .method("POST", body)
                .addHeader("X-CH-APIKEY", apiKey)
                .addHeader("X-CH-TS", timestemp)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-CH-SIGN", sign)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.code());
        System.out.println(response.body().string());
        System.out.println("签名串"+sign);

    }


}
