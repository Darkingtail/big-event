package org.example.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private List<T> list;
}
