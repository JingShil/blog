package com.ccsu.personalblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.personalblog.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
