package com.bootdo.common.domain.model;

import com.bootdo.common.constant.CommonConsts;
import lombok.Data;

import java.util.List;

/**
 * 分页查询模板
 *
 * @author rory.chen
 * @date 2021-01-22 14:25
 */
@Data
public class QueryModel {

    private Integer page;

    private Integer pageSize;

    private String searchKey;

    private String searchValue;

    private String orderField;

    private String orderSeq = "asc";

    private List<Long> ids;

    private Long id;

    public boolean isAsc() {
        return CommonConsts.ASC.equals(orderSeq);
    }

}
