package com.ccsu.personalblog.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.ccsu.personalblog.api.IfasrApi;
import com.ccsu.personalblog.common.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/voice/text")
public class IfasrController {

    @PostMapping("/upload")
    public R<String> uploadAudio(@RequestParam("audio") MultipartFile audioFile) {
        try {
            // 生成一个唯一的文件名
            String fileName = UUID.randomUUID().toString() + ".wav";
            // 构建文件保存路径
            String uploadPath = "D:/java实训项目/个人博客/blog/personal-blog-back/src/main/resources/audio";
            String filePath = uploadPath + "/" + fileName;
//            String filePath = uploadPath + fileName;

            // 将MultipartFile对象保存为文件
            File file = new File(filePath);
            audioFile.transferTo(file);

            IfasrApi ifasrApi = new IfasrApi();

//            // 处理完毕后删除文件
//            file.delete();
            String text = ifasrApi.getText(fileName);


            String pattern = "\"w\\\\\":\\\\\".*?\\\\\"";

            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(text);

            StringBuilder sb = new StringBuilder();
            while (m.find()) {
                String match = m.group();
                sb.append(match.substring(7, match.length() - 2));
            }

            String result = sb.toString();
            System.out.println(result);

            return R.success(result);
        } catch (Exception e) {
            System.out.println(e);
            return R.error("错误");
        }
    }
}
