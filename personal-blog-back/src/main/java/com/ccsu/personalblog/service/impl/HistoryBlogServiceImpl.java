package com.ccsu.personalblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccsu.personalblog.entity.BlogArticle;
import com.ccsu.personalblog.entity.HistoryBlog;
import com.ccsu.personalblog.mapper.HistoryBlogMapper;
import com.ccsu.personalblog.service.HistoryBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryBlogServiceImpl extends ServiceImpl<HistoryBlogMapper, HistoryBlog> implements HistoryBlogService {

    @Autowired
    private HistoryBlogMapper historyBlogMapper;
    @Override
    public List<HistoryBlog> selectPageByUserId(String userId, int offset, int pageSize) {
        return historyBlogMapper.selectPageByUserId(userId, offset, pageSize);
    }
}
