package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenu extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String menuId;

}
