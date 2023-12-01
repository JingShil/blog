package com.ccsu.personalblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.personalblog.entity.ChatgptRoleSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatgptRoleSettingMapper extends BaseMapper<ChatgptRoleSetting> {


    @Select("SELECT * FROM chatgpt_role_setting")
    List<ChatgptRoleSetting> findAll();
}
