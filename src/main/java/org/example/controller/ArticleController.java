package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.pojo.Article;
import org.example.pojo.Result;
import org.example.pojo.bo.PageQuery;
import org.example.pojo.dto.PageDTO;
import org.example.service.ArticleService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @PostMapping("/list")
//    public Result<String> list() {
//        return Result.success("article list");
//    }

    @PostMapping
    public Result<String> add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success("article add");
    }

    @GetMapping("/list")
    public Result<PageDTO<Article>> list(PageQuery pageQuery, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state) {
        // 设置默认分页参数
        if (pageQuery.getPageNo() == null || pageQuery.getPageNo() <= 0) {
            pageQuery.setPageNo(1);
        }
        if (pageQuery.getPageSize() == null || pageQuery.getPageSize() <= 0) {
            pageQuery.setPageSize(10);
        }
        Map<String, Object> o = ThreadLocalUtil.get();
        Integer userId = (Integer) o.get("id");
        // 构建查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .like(categoryId != null, Article::getCategoryId, categoryId)
                .eq(state != null && !state.isEmpty(), Article::getState, state)
                .eq(userId != null, Article::getId, userId)
                .orderByAsc(Article::getId);

        // 执行分页查询
        Page<Article> page = articleService.page(new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize()), queryWrapper);

        // 打印分页信息
        System.out.println("/article/list ======> 当前页: " + page.getCurrent() + ", 每页数量: " + page.getSize() + ", 总数: " + page.getTotal() + ", 总页数: " + page.getPages());
        System.out.println("/article/list ======> 数据列表: " + page.getRecords());

        // 使用自定义PageDTO封装返回结果
        PageDTO<Article> pageDTO = new PageDTO<>();
        pageDTO.setPageNo(pageQuery.getPageNo());
        pageDTO.setPageSize(pageQuery.getPageSize());
        pageDTO.setList(page.getRecords());

        return Result.success(pageDTO);
    }
}