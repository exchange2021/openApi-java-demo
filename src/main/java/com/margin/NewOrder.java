package com.margin;

import com.alibaba.fastjson.JSON;
import com.utils.Signature;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

public class NewOrder {
    /**
     * Post请求方式/创建杠杆订单
     * POST Request method/New Order
     */

    private static String apiKey = "Please enter your Apikey";
    private static  String secret = "Please enter your Secret";

    public static void main (String args[]) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("symbol", "btcusdt");//REQUIRED
        param.put("volume", "1");//REQUIRED
        param.put("side", "SELL");//REQUIRED
        param.put("type", "LIMIT");//REQUIRED
        param.put("price", "1");//OPTIONAL

        String timestemp = String.valueOf(System.currentTimeMillis());
        String sign  = Signature.toSign(timestemp, "POST", "/sapi/v1/margin/order", "", JSON.toJSONString(param), secret);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(param));
        Request request = new Request.Builder()
                .url("https://openapi.xxx.com/sapi/v1/margin/order")
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
