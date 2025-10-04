package org.example.controller;

import org.example.pojo.Category;
import org.example.pojo.Result;
import org.example.pojo.dto.CategoryAddGroup;
import org.example.pojo.dto.CategoryUpdateGroup;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/detailByCategoryName")
    public Result getByCategoryName(@RequestParam String categoryName) {
        if (!StringUtils.hasLength(categoryName)) {
            return Result.error("参数错误");
        }
        Category category = categoryService.getDetailByCategoryName(categoryName);
        return category == null ? Result.error("没有该Category") : Result.success(category);
    }

    @GetMapping("/detailById")
    public Result getById(@RequestParam Integer id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        Category category = categoryService.getDetailById(id);
        return category == null ? Result.error("没有该Category") : Result.success(category);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(CategoryAddGroup.class) Category data) {
        if (!StringUtils.hasLength(data.getCategoryName()) || !StringUtils.hasLength(data.getCategoryAlias())) {
            return Result.error("参数错误");
        }
        if (categoryService.getDetailByCategoryName(data.getCategoryName()) != null) {
            return Result.error("该分类已存在");
        }
        categoryService.add(data);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody @Validated(CategoryUpdateGroup.class) Category data) {
        // TODO implement update flow
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        return Result.success(categoryService.listByUser());
    }
}