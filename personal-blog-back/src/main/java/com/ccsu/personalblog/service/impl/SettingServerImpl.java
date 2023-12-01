package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.Setting;
import com.ccsu.personalblog.mapper.SettingMapper;
import com.ccsu.personalblog.service.SettingServer;
import org.springframework.stereotype.Service;

@Service
public class SettingServerImpl extends ServiceImpl<SettingMapper, Setting> implements SettingServer {
}
