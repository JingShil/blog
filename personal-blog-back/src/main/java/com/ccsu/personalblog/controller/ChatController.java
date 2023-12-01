package com.ccsu.personalblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.personalblog.api.AzureTtsApi;
import com.ccsu.personalblog.api.ChatGptApi;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.common.WebSocket;
import com.ccsu.personalblog.entity.*;
import com.ccsu.personalblog.global.ChatGlobal;
import com.ccsu.personalblog.mapper.ChatHistoryMapper;
import com.ccsu.personalblog.mapper.ChatgptRoleSettingMapper;
import com.ccsu.personalblog.mapper.UserChatgptRoleSettingMapper;
import com.ccsu.personalblog.service.UserService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/chat")
public class ChatController {

    private Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    @Resource
    private WebSocket webSocket;

    @Autowired
    private UserService userService;

    @Autowired
    private UserChatgptRoleSettingMapper userChatgptRoleSettingMapper;

    @Autowired
    private ChatgptRoleSettingMapper chatgptRoleSettingMapper;

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;


    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody MessageDto messageDto) {

        List<Message> messageList = new ArrayList<>();
        List<Message> messages = messageDto.getMessages();
        for(int i=0;i < messages.size()-1;i++) {
            messageList.add(messages.get(i));
        }
        ChatGptApi chatGptApi = new ChatGptApi();
        ChatRequestData chatRequestData = new ChatRequestData();
        chatRequestData.setStream(true);
        chatRequestData.setMessages(messageList);
        chatRequestData.setModel(messageDto.getModel());
//        chatGptApi.send("你好");
        chatGptApi.sendChatGpt(chatRequestData);
        return R.success("发送成功");
    }

    @PostMapping("/sendMsg/voice")
    public R<Map<String, String>> sendMsgVoice(@RequestBody MessageDto messageDto) {

//        List<Message> messageList = new ArrayList<>();
//        for(int i=0;i < messages.size()-1;i++) {
//            messageList.add(messages.get(i));
//        }
        List<Message> messages = messageDto.getMessages();
        ChatGptApi chatGptApi = new ChatGptApi();
        ChatRequestData chatRequestData = new ChatRequestData();
        chatRequestData.setStream(false);
        chatRequestData.setMessages(messages);
        chatRequestData.setModel(messageDto.getModel());
//        chatGptApi.send("你好");
        ChatResponseData chatResponseData = chatGptApi.sendChatGpt(chatRequestData);
        //文字转语言
        AzureTtsApi azureTtsApi = new AzureTtsApi();
        AzureTtsRequest azureTtsRequest = new AzureTtsRequest();
        List<ChatResponseData.Choice> choices = chatResponseData.getChoices();
        Message message = choices.get(0).getMessage();
        String content = message.getContent();
        azureTtsRequest.setText(content);
//        azureTtsRequest.setSpeed(1f);
//        azureTtsRequest.setVoiceName("zh-CN-XiaoxiaoNeural");
        azureTtsRequest.setSpeed(Float.parseFloat(messageDto.getSpeedVoice()));
        azureTtsRequest.setVoiceName(messageDto.getLanguageVoice());
        azureTtsRequest.setModeration_stop(true);
        byte[] tts = azureTtsApi.getTts(azureTtsRequest);
        String filePath = "D:/java实训项目/个人博客/blog/personal-blog-back/src/main/resources/audio/file.wav";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(tts); // 将字节数组写入文件
            System.out.println("音频文件写入成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String base64Audio = new String(Base64.getEncoder().encode(tts), StandardCharsets.UTF_8);
        String base64Audio = Base64.getEncoder().encodeToString(tts);
        Map<String, String> map = new HashMap<>();
        map.put("audioBase64", base64Audio);
        map.put("content", content);
        return R.success(map);
    }

    @PostMapping("/setting/find")
    public R<List<ChatgptRoleSetting>> settingFind(@RequestBody Map<String,String> object) {
        String userSettingFlag = object.get("userSettingFlag");
        List<ChatgptRoleSetting> setting = new ArrayList<>();
        if(userSettingFlag.equals(0)){
            List<ChatgptRoleSetting> all = chatgptRoleSettingMapper.findAll();
            for(ChatgptRoleSetting one:all){
                setting.add(one);
            }
        }else{
            String userId = object.get("userId");
            LambdaQueryWrapper<UserChatgptRoleSetting> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserChatgptRoleSetting::getUserId, userId);
            List<UserChatgptRoleSetting> userChatgptRoleSettings =
                    userChatgptRoleSettingMapper.selectList(lambdaQueryWrapper);
            for(UserChatgptRoleSetting userChatgptRoleSetting: userChatgptRoleSettings){
                ChatgptRoleSetting chatgptRoleSetting = new ChatgptRoleSetting();
                chatgptRoleSetting.setId(userChatgptRoleSetting.getId());
                chatgptRoleSetting.setText(userChatgptRoleSetting.getText());
                setting.add(chatgptRoleSetting);
            }

        }
        return R.success(setting);
    }

    @PostMapping("/setting/add")
    public R<String> settingAdd(@RequestBody Map<String, String> object){
        String text = object.get("text");
        String userId = object.get("userId");
        LocalDateTime now = LocalDateTime.now();
        String id = new MD5Utils().encryptMD5(text + userId + now);
        UserChatgptRoleSetting userChatgptRoleSetting = new UserChatgptRoleSetting();
        userChatgptRoleSetting.setUserId(userId);
        userChatgptRoleSetting.setText(text);
        userChatgptRoleSetting.setId(id);
        userChatgptRoleSetting.setUpdateTime(now);
        userChatgptRoleSettingMapper.insert(userChatgptRoleSetting);
        return R.success("添加成功");
    }

    @PostMapping("/setting/update")
    public R<String> settingUpdate(@RequestBody Map<String, String> object){
        String id = object.get("id");
        String text = object.get("text");
        LocalDateTime now = LocalDateTime.now();
        UserChatgptRoleSetting userChatgptRoleSetting = userChatgptRoleSettingMapper.selectById(id);
        userChatgptRoleSetting.setUpdateTime(now);
        userChatgptRoleSetting.setText(text);
        userChatgptRoleSettingMapper.updateById(userChatgptRoleSetting);
        return R.success("修改成功");
    }

    @PostMapping("/setting/delete")
    public R<String> settingDelete(@RequestBody Map<String, String> object){
        String id = object.get("id");
        userChatgptRoleSettingMapper.deleteById(id);
        return R.success("删除成功");
    }

    @PostMapping("/history/add")
    public R<String> historyAdd(@RequestBody Map<String, String> object){
        String userId = object.get("userId");
        String text = object.get("text");
        LocalDateTime now = LocalDateTime.now();
        String id = new MD5Utils().encryptMD5(userId + text + now);
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setId(id);
        chatHistory.setText(text);
        chatHistory.setUpdateTime(now);
        chatHistory.setUserId(userId);
        chatHistoryMapper.insert(chatHistory);
        return R.success("保存成功");
    }

    @PostMapping("/history/delete")
    public R<String> historyDelete(@RequestBody Map<String, String> object){
        String id = object.get("id");
        chatHistoryMapper.deleteById(id);
        return R.success("删除成功");
    }

    @PostMapping("/history/list")
    public R<List<ChatHistory>> historyList(@RequestBody Map<String, String> object){
        Integer pageNumber = Integer.parseInt(object.get("pageNumber"));
        Integer pageSize = Integer.parseInt(object.get("pageSize"));
        String userId = object.get("userId");
        int offset = (pageNumber - 1) * pageSize;
        List<ChatHistory> chatHistories = chatHistoryMapper.selectPageByUserId(userId, offset, pageSize);
        return R.success(chatHistories);
    }
}
