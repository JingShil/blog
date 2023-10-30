package com.ccsu.personalblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccsu.personalblog.entity.BlogArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    @Select("<script>" +
            "SELECT * FROM blog_article" +
            "<if test='userId != null'> WHERE user_id = #{userId}</if>" +
            " LIMIT #{offset}, #{pageSize}" +
            "</script>")
    List<BlogArticle> selectPageByUserId(@Param("userId") String userId,
                                         @Param("offset") int offset,
                                         @Param("pageSize") int pageSize);
}
