package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.example.pojo.dto.CategoryAddGroup;
import org.example.pojo.dto.CategoryUpdateGroup;

import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category {
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = CategoryAddGroup.class)
    @NotNull(groups = CategoryUpdateGroup.class)
    private Integer id;//主键ID
    @NotEmpty(groups = {CategoryAddGroup.class, CategoryUpdateGroup.class})
    private String categoryName;//分类名称
    @NotEmpty(groups = {CategoryAddGroup.class, CategoryUpdateGroup.class})
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;//更新时间
}