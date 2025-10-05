package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
