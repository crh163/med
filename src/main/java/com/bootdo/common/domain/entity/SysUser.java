package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUser extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String name;

    private String password;

    private Integer status;

    private Long roleId;

    private Long relativesId;

}
