package com.ccsu.personalblog.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.CollectBlog;
import com.ccsu.personalblog.mapper.CollectBlogMapper;
import com.ccsu.personalblog.service.CollectBlogService;
import org.springframework.stereotype.Service;

@Service
public class CollectBlogServiceImpl extends ServiceImpl<CollectBlogMapper, CollectBlog> implements CollectBlogService {
}
