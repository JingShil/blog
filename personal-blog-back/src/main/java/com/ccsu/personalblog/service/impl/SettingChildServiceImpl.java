package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.SettingChild;
import com.ccsu.personalblog.mapper.SettingChildMapper;
import com.ccsu.personalblog.service.SettingChildService;
import org.springframework.stereotype.Service;

@Service
public class SettingChildServiceImpl extends ServiceImpl<SettingChildMapper , SettingChild> implements SettingChildService {
}
