package com.margin;

import com.utils.Signature;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class QueryOrder {
    /**
     * GET请求方式/杠杆订单查询
     * GET Request method/Query Order
     */

    private static String apiKey = "Please enter your Apikey";
    private static  String secret = "Please enter your Secret";

    public static void main(String[] args) throws Exception {
        String timestemp = String.valueOf(System.currentTimeMillis());
        String sign  = Signature.toSign(timestemp, "GET", "/sapi/v1/margin/order", "", "", secret);
        String url = "https://openapi.xxx.com/sapi/v1/margin/order?symbol=btcusdt&orderId=123456";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //默认值我GET
        con.setRequestMethod("GET");

        //添加请求头
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("X-CH-APIKEY",apiKey);
        con.setRequestProperty("X-CH-TS",timestemp);
        con.setRequestProperty("X-CH-SIGN",sign);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //打印结果
        System.out.println(response.toString());
    }
}
