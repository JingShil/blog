package com.ccsu.personalblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.personalblog.api.ChatGptApi;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.ChatRequestData;
import com.ccsu.personalblog.entity.User;
import com.ccsu.personalblog.global.ChatGlobal;
import com.ccsu.personalblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {

    private Map<String, SseEmitter> sseEmitterMap = new HashMap<>();


    @Autowired
    private UserService userService;


    @PostMapping("/link")
    public SseEmitter link(@RequestBody Map<String, String> ids){
        String id = ids.get("id");
        SseEmitter sseEmitter1 = sseEmitterMap.get(id);
        if(sseEmitter1 == null) {
            SseEmitter sseEmitter = new SseEmitter(10000L);

            sseEmitterMap.put(id, sseEmitter);
            sseEmitter.onCompletion(() -> {
                sseEmitterMap.remove(id);
            });
            return sseEmitter;
        }else {
//            return R.error("错误");
            return null;
        }
    }

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody Map<String, String> object) {
        String id = object.get("id");
        String question = object.get("question");
        SseEmitter sseEmitter = sseEmitterMap.get(id);
        if(sseEmitter == null) {
            return R.error("错误");
        }
        ChatGptApi chatGptApi = new ChatGptApi();
        ChatRequestData chatRequestData = new ChatRequestData();
        chatRequestData.setStream(true);
        chatRequestData.setMessages(question);
        chatRequestData.setSseEmitter(sseEmitter);
//        chatGptApi.send("你好");
        chatGptApi.sendChatGpt(chatRequestData);
        return R.success("发送成功");
    }
}
