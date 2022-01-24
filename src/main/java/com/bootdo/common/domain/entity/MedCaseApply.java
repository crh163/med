package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MedCaseApply extends BaseModel implements Serializable {

    private Long userId;

    private String username;

    private String name;

    private Long caseId;

    private String caseUsername;

    private String caseName;

    private Integer status;

}
