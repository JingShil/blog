package com.ccsu.personalblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.Code;
import com.ccsu.personalblog.entity.User;
import com.ccsu.personalblog.mapper.CodeMapper;
import com.ccsu.personalblog.service.UserService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CodeMapper codeMapper;



    @PostMapping("/code")
    public R<String> sendCode(@RequestBody Map<String, String> data) {
        String phone = data.get("phone");
        Integer loginFlag = Integer.valueOf(data.get("loginFlag"));
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone, phone);
        User one = userService.getOne(lambdaQueryWrapper);

        LambdaQueryWrapper<Code> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Code::getId, phone);
        Code code = codeMapper.selectOne(queryWrapper);
        if(code != null) {
            LocalDateTime createTime = code.getCreateTime();
            LocalDateTime now = LocalDateTime.now();
            long seconds = ChronoUnit.SECONDS.between(createTime, now);
            if (seconds <= 60) {
                return R.error("验证码还在时间内");
            } else {
                codeMapper.deleteById(code);
            }
        }
        code = new Code();
        code.setCode("1111");
        code.setId(phone);
        code.setCreateTime(LocalDateTime.now());
        if(loginFlag == 1) {
            if(one == null) {
                return R.error("请注册");
            }else {
                codeMapper.insert(code);
                return R.success("发送验证码成功");
            }
        }else {
            if(one != null) {
                return R.error("已注册");
            }else {
                codeMapper.insert(code);
                return R.success("发送验证码成功");
            }
        }
    }

    public String getCode(String phone) {
        LambdaQueryWrapper<Code> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Code::getId, phone);
        Code code = codeMapper.selectOne(queryWrapper);
        if(code != null) {
            LocalDateTime createTime = code.getCreateTime();
            LocalDateTime now = LocalDateTime.now();
            long seconds = ChronoUnit.SECONDS.between(createTime, now);
            if (seconds <= 60) {
                return code.getCode();
            } else {
                codeMapper.deleteById(code);
                return null;
            }
        }
        return null;
    }



    @PostMapping("/login")
    public R<String> login(@RequestBody Map<String, String> user) {
        String phone = user.get("phone");
        String password = user.get("password");
        if(phone==null) {
            return R.error("数据异常");
        }
        if(StringUtils.isEmpty(password)) {
            String code = user.get("code");
            if(code==null) {
                return R.error("数据异常");
            }
            Object codeSession = getCode(phone);
            if(codeSession == null) {
                return R.error("请先发送验证码");
            }
            if(code.equals(codeSession)) {
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(User::getPhone, phone);
                User one = userService.getOne(queryWrapper);
                return R.success("登陆成功", one.getId());
            }else {
                return R.error("验证码错误");
            }
        }else {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User one = userService.getOne(queryWrapper);
            if(one != null) {
                if(password.equals(one.getPassword())){
                    return R.success("登陆成功", one.getId());
                }else {
                    return R.error("密码错误");
                }
            }else {
                return R.error("手机号未注册");
            }
        }

    }

    @PostMapping("/register")
    public R<String> register(@RequestBody Map<String, String> user) {

        String phone = user.get("phone");
        String code = user.get("code");
        String name = user.get("name");
        String password = user.get("password");
        if(phone==null||code==null||name==null||password==null) {
            return R.error("数据异常");
        }
        Object codeSession = getCode(phone);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User one = userService.getOne(queryWrapper);
        if(one != null) {
            return R.error("手机号已注册");
        }
        if(codeSession == null) {
            return R.error("请先发送验证码");
        }else {
            if(code.equals(codeSession)) {
                LocalDateTime now = LocalDateTime.now();
                String id = new MD5Utils().encryptMD5(phone + now.toString());
                User user2 = new User();
                user2.setId(id);
                user2.setName(user.get("name"));
                user2.setPassword(user.get("password"));
                user2.setPhone(phone);
                user2.setCreateTime(now);
                userService.saveOrUpdate(user2);
                return R.success("注册成功");
            }else {
                return R.error("验证码错误");
            }
        }

    }

}
