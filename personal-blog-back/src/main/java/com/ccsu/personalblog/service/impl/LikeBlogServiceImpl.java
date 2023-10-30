package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.LikeBlog;
import com.ccsu.personalblog.mapper.LikeBlogMapper;
import com.ccsu.personalblog.service.LikeBlogService;
import org.springframework.stereotype.Service;

@Service
public class LikeBlogServiceImpl extends ServiceImpl<LikeBlogMapper, LikeBlog> implements LikeBlogService {
}
