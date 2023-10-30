package com.ccsu.personalblog.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccsu.personalblog.common.R;
import com.ccsu.personalblog.entity.BlogArticle;
import com.ccsu.personalblog.entity.CollectBlog;
import com.ccsu.personalblog.entity.HistoryBlog;
import com.ccsu.personalblog.entity.LikeBlog;
import com.ccsu.personalblog.mapper.BlogArticleMapper;
import com.ccsu.personalblog.service.BlogArticleService;
import com.ccsu.personalblog.service.CollectBlogService;
import com.ccsu.personalblog.service.HistoryBlogService;
import com.ccsu.personalblog.service.LikeBlogService;
import com.ccsu.personalblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("blog")
public class BlogArticleController {

    MD5Utils md5Utils = new MD5Utils();

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private CollectBlogService collectBlogService;

    @Autowired
    private LikeBlogService likeBlogService;

    @Autowired
    private HistoryBlogService historyBlogService;


    @PostMapping("/add")
    public R<String> add(@RequestBody BlogArticle blogArticle) {
        String id = new MD5Utils().encryptMD5(blogArticle.getUserId() + blogArticle.getText());
        LocalDateTime localDateTime = LocalDateTime.now();
        blogArticle.setId(id);
        blogArticle.setCreateTime(localDateTime);
        blogArticle.setUpdateTime(localDateTime);
        blogArticleService.save(blogArticle);
        return R.success("发表成功");
    }


    @PostMapping("delete")
    public R<String> delete(List<Integer> ids) {
        blogArticleService.removeBatchByIds(ids);
        return R.success("删除成功");
    }

    @PostMapping("update")
    public R<String> update(@RequestBody BlogArticle blogArticle) {
        blogArticleService.updateById(blogArticle);
        return R.success("修改成功");
    }

    @PostMapping("listAll")
    public R<List<BlogArticle>> listAll(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        List<BlogArticle> blogArticles = blogArticleService.selectPageByUserId(null, offset, pageSize);
        return R.success(blogArticles);
    }

    @PostMapping("list")
    public R<List<BlogArticle>> list(int pageNumber, int pageSize, String id) {
        int offset = (pageNumber - 1) * pageSize;
        List<BlogArticle> blogArticles = blogArticleService.selectPageByUserId(id, offset, pageSize);
        return R.success(blogArticles);
    }

    @PostMapping("/collect")
    public R<String> collect(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        DateTime dateTime = DateTime.now();
        String id = md5Utils.encryptMD5(userId + blogId);
        CollectBlog collectBlog = new CollectBlog();
        collectBlog.setId(id);
        collectBlog.setBlogId(blogId);
        collectBlog.setUserId(userId);
        collectBlog.setCreateTime(dateTime);
        collectBlogService.save(collectBlog);
        return R.success("收藏成功");
    }

    @PostMapping("/collect/cancel")
    public R<String> collectCancel(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        LambdaQueryWrapper<CollectBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CollectBlog::getBlogId, blogId).eq(CollectBlog::getUserId, userId);
        collectBlogService.remove(queryWrapper);
        return R.success("取消成功");
    }

    @PostMapping("/collect/count")
    public R<Long> collectCount(@RequestBody Map<String, String> ids) {
        String blogId = ids.get("blogId");
        LambdaQueryWrapper<CollectBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CollectBlog::getBlogId, blogId);
        long count = collectBlogService.count(queryWrapper);
        return R.success(count);
    }

    @PostMapping("/like")
    public R<String> like(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        DateTime dateTime = DateTime.now();
        String id = md5Utils.encryptMD5(userId + blogId);
        LikeBlog likeBlog = new LikeBlog();
        likeBlog.setId(id);
        likeBlog.setBlogId(blogId);
        likeBlog.setUserId(userId);
        likeBlog.setCreateTime(dateTime);
        likeBlogService.save(likeBlog);
        return R.success("点赞成功");
    }

    @PostMapping("/like/cancel")
    public R<String> likeCancel(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        LambdaQueryWrapper<LikeBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeBlog::getBlogId, blogId).eq(LikeBlog::getUserId, userId);
        likeBlogService.remove(queryWrapper);
        return R.success("取消成功");
    }

    @PostMapping("/like/count")
    public R<Long> liketCount(@RequestBody Map<String, String> ids) {
        String blogId = ids.get("blogId");
        LambdaQueryWrapper<LikeBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeBlog::getBlogId, blogId);
        long count = likeBlogService.count(queryWrapper);
        return R.success(count);
    }

    @PostMapping("/history/add")
    public R<String> historyAdd(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        DateTime dateTime = DateTime.now();
        String id = md5Utils.encryptMD5(userId + blogId);
        HistoryBlog historyBlog = new HistoryBlog();
        historyBlog.setId(id);
        historyBlog.setBlogId(blogId);
        historyBlog.setUserId(userId);
        historyBlog.setUpdateTime(dateTime);
        LambdaQueryWrapper<HistoryBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryBlog::getUserId, userId).orderByAsc(HistoryBlog::getUpdateTime);
        List<HistoryBlog> list = historyBlogService.list(queryWrapper);
        if(list.size() >= 50) {
            List<HistoryBlog> historyBlogs = new ArrayList<>();
            Boolean flag = true;
            for(HistoryBlog historyBlog1 : list) {
                if(flag) {
                    flag = false;
                    continue;
                }
                historyBlogs.add(historyBlog1);
            }
            historyBlogs.add(historyBlog);
            list = historyBlogs;
        }else {
            list.add(historyBlog);
        }
        historyBlogService.save(historyBlog);
        return R.success("成功");
    }

    @PostMapping("/history/update")
    public R<String> historyUpdate(@RequestBody Map<String, String> ids) {
        String userId = ids.get("userId");
        String blogId = ids.get("blogId");
        DateTime dateTime = DateTime.now();
        LambdaQueryWrapper<HistoryBlog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HistoryBlog::getBlogId, blogId).eq(HistoryBlog::getUserId, userId);
        HistoryBlog one = historyBlogService.getOne(queryWrapper);
        one.setUpdateTime(dateTime);
        historyBlogService.updateById(one);
        return R.success("更新成功");
    }

    @PostMapping("/history/list")
    public R<List<HistoryBlog>> historyList(int pageNumber, int pageSize, String id) {
        int offset = (pageNumber - 1) * pageSize;
        List<HistoryBlog> historyBlogs = historyBlogService.selectPageByUserId(id, offset, pageSize);
        return R.success(historyBlogs);
    }

}
