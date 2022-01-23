package com.bootdo.common.domain.req;

import lombok.Data;

@Data
public class QueryCaseListReq extends QueryListReq {

    private String doctorName;

    private String deptName;

    private String areaName;

    private Long userId;

}
