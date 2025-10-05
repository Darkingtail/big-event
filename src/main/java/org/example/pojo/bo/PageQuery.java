package org.example.pojo.bo;

import lombok.Data;

@Data
public class PageQuery {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private Boolean asc;
}
