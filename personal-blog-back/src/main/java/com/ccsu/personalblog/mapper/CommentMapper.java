package com.ccsu.personalblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.personalblog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
