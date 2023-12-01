package com.ccsu.personalblog.api;

import com.ccsu.personalblog.common.WebSocket;
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

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ChatGptApi {

    @Resource
    private WebSocket webSocket;


    public ChatResponseData sendChatGpt(ChatRequestData chatRequestData) {
        try {
            //创建URL对象，使用HTTP POST方法请求JSON格式的数据

            HttpURLConnection cn = getHttpURLConnection();
            //设置要发送的JSON数据
//            String postDada = getPostDada(chatRequestData);
            String postDada = new JSONObject(chatRequestData).toString();
            //将请求发给网站
            OutputStream outputStream = cn.getOutputStream();
            outputStream.write(postDada.getBytes());
            outputStream.flush();
            outputStream.close();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cn.getInputStream()));
            String input;
            StringBuilder buffer = new StringBuilder();

            ObjectMapper objectMapper = new ObjectMapper() ;
            if(chatRequestData.isStream()) {
                while ((input = bufferedReader.readLine()) != null) {
                    input = input.replaceAll("^data:\\s*", "");
                    System.out.println(input);
                    try {
                        ChatResponseData re = objectMapper.readValue(input, ChatResponseData.class);
                        webSocket.sendMessage(input);
                        System.out.println(re);
                    } catch (JsonProcessingException e) {
//                        System.out.println("Invalid input format: " + input);
                        System.out.println("数据解析错误: " + e);
                        continue;
                        // 可以选择跳过该数据或进行其他处理
                    }
                }
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                while ((input = bufferedReader.readLine()) != null) {
                    stringBuilder.append(input);
                }
                ChatResponseData re = objectMapper.readValue(stringBuilder.toString(), ChatResponseData.class);
                return re;
            }
            bufferedReader.close();
            cn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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
     * 设置请求头
     * @param chatRequestData
     * @return
     */
    private static String getPostDada(ChatRequestData chatRequestData) {
//        JSONObject test = new JSONObject(chatRequestData);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", chatRequestData.getModel());
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(chatRequestData.getMessages());
        jsonObject.put("messages", chatRequestData.getMessages());
        jsonObject.put("max_tokens", 200);
        jsonObject.put("stream", chatRequestData.isStream());

        String postDada = jsonObject.toString();
        return postDada;
    }





}
