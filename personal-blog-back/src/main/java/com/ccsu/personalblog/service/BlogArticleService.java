package com.ccsu.personalblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccsu.personalblog.entity.BlogArticle;

import java.util.List;

public interface BlogArticleService extends IService<BlogArticle> {
    List<BlogArticle> selectPageByUserId(String userId, int offset, int pageSize);

}
