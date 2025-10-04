package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public void add(Category data) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        LocalDateTime now = LocalDateTime.now();
        data.setUpdateTime(now);
        data.setCreateTime(now);
        data.setCreateUser(userId);
        this.save(data);
    }

    @Override
    public List<Category> listByUser() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        return this.list(new QueryWrapper<Category>().eq("create_user", userId));
    }

    @Override
    public Category getDetailByCategoryName(String categoryName) {
        return this.getOne(new QueryWrapper<Category>().like("category_name", categoryName));
    }

    @Override
    public Category getDetailById(Integer id) {
        return this.lambdaQuery()
                .eq(Category::getId, id)
                .one();
    }
}