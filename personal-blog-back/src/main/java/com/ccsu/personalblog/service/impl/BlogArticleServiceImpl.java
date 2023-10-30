package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.BlogArticle;
import com.ccsu.personalblog.mapper.BlogArticleMapper;
import com.ccsu.personalblog.service.BlogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public List<BlogArticle> selectPageByUserId(String userId, int offset, int pageSize) {
        return blogArticleMapper.selectPageByUserId(userId, offset, pageSize);
    }
}
