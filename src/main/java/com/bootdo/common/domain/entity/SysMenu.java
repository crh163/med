package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMenu extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long parentId;

    private String name;

    private String url;

    private String icon;

    private Integer type;

    private Integer orderNum;

}
