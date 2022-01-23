package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MedCaseApplyAgree extends BaseModel implements Serializable {

    private Long applyId;

    private Long userId;

    //0不同意 1已同意
    private String type;

}
