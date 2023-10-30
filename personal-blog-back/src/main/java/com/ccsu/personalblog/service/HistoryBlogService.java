package com.ccsu.personalblog.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ccsu.personalblog.entity.BlogArticle;
import com.ccsu.personalblog.entity.HistoryBlog;

import java.util.List;

public interface HistoryBlogService extends IService<HistoryBlog> {
    List<HistoryBlog> selectPageByUserId(String userId, int offset, int pageSize);

}
