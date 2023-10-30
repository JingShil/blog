package com.ccsu.personalblog.api;

import com.ccsu.personalblog.entity.ChatRequestData;
import com.ccsu.personalblog.entity.ChatResponseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ChatGptApi {
    private int sum;
    public void sendChatGpt(ChatRequestData chatRequestData) {
        SseEmitter sseEmitter = chatRequestData.getSseEmitter();
        try {
            //创建URL对象，使用HTTP POST方法请求JSON格式的数据
            System.out.println(sseEmitter);
            HttpURLConnection cn = getHttpURLConnection();
            //设置要发送的JSON数据
            String postDada = getPostDada(chatRequestData);

            //将请求发给网站
            OutputStream outputStream = cn.getOutputStream();
            outputStream.write(postDada.getBytes());
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cn.getInputStream()));
            String input;
            StringBuilder buffer = new StringBuilder();
            sum = 0;
            ObjectMapper objectMapper = new ObjectMapper() ;
            while((input = bufferedReader.readLine()) != null) {
                input = input.replaceAll("^data:\\s*", "");
                System.out.println(input);
                try {
                    ChatResponseData re = objectMapper.readValue(input, ChatResponseData.class);
                    sseSend(sseEmitter, re);
                    System.out.println(re);
                } catch (JsonProcessingException e) {
                    System.out.println("Invalid input format: " + input);
                    continue;
                    // 可以选择跳过该数据或进行其他处理
                }
            }
            bufferedReader.close();
            cn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        sseEmitter.complete();
    }


    /**
     * 设置请求路径，格式和方式
     * @return
     * @throws IOException
     */
    private static HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL("https://openai.api2d.net/v1/chat/completions");
        HttpURLConnection cn = (HttpURLConnection) url.openConnection();
        cn.setRequestMethod("POST");
        cn.setRequestProperty("Content-Type", "application/json");
        cn.setRequestProperty("Authorization", "Bearer fk194545-QXswLRl4g8ejPXSfxxpnlve3Tn6b78pp");//设置Forward Key, Bearer 要保留
        cn.setDoOutput(true);
        return cn;
    }

    /**
     * 设置需要发送的数据
     * @param sseEmitter
     * @param input
     * @throws IOException
     */


    /**
     * 将消息发送给前端
     * @param sseEmitter
     * @param chatResponseData
     */
    private static void sseSend(SseEmitter sseEmitter, ChatResponseData chatResponseData) {
        try {
            sseEmitter.send(SseEmitter.event().name("message").data(chatResponseData));
        } catch (IOException e) {
            sseEmitter.complete();
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置请求头
     * @param chatRequestData
     * @return
     */
    private static String getPostDada(ChatRequestData chatRequestData) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", chatRequestData.getModel());
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("role", chatRequestData.getRole());
        jsonObject1.put("content", chatRequestData.getContent());
        jsonArray.put(jsonObject1);
        jsonObject.put("messages", jsonArray);
        jsonObject.put("max_tokens", 200);
        jsonObject.put("stream", chatRequestData.isStream());

        String postDada = jsonObject.toString();
        return postDada;
    }





}
