package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleName;

    private String remark;

}
