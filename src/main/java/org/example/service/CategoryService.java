package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void add(Category data);

    List<Category> listByUser();

    Category getDetailByCategoryName(String categoryName);

    Category getDetailById(Integer id);
}