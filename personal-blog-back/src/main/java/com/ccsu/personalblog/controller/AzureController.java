package com.ccsu.personalblog.controller;

import com.ccsu.personalblog.api.AzureTtsApi;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.AzureTtsRequest;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/azure")
public class AzureController {

    @PostMapping("/tts")
    public R<String> AzureTts(@RequestBody AzureTtsRequest azureTtsRequest){
        AzureTtsApi azureTtsApi = new AzureTtsApi();
//        azureTtsRequest.setSpeed(1.5f);
//        azureTtsRequest.setVoiceName("zh-CN-XiaoxiaoNeural");
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
        return R.success(base64Audio);
    }

}
