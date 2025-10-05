package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Article;

public interface ArticleService extends IService<Article> {
    boolean add(Article article);
}
