package com.spot;

import com.alibaba.fastjson.JSON;
import com.utils.Signature;
import okhttp3.*;

import java.util.HashMap;
import java.util.Map;

public class CancelOrder {
    /**
     * Post请求方式/撤销订单
     * POST Request method/Cancel Order
     */

    private static String apiKey = "Please enter your Apikey";
    private static  String secret = "Please enter your Secret";

    public static void main (String args[]) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("symbol", "btcusdt");//REQUIRED
        param.put("orderId", "123456");//REQUIRED

        String timestemp = String.valueOf(System.currentTimeMillis());
        String sign  = Signature.toSign(timestemp, "POST", "/sapi/v1/cancel", "", JSON.toJSONString(param), secret);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(param));
        Request request = new Request.Builder()
                .url("https://openapi.xxx.com/sapi/v1/cancel")
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
