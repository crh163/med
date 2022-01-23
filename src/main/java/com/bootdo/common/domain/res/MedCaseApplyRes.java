package com.bootdo.common.domain.res;

import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MedCaseApplyRes implements Serializable {

    private Long id;

    private Long createId;

    private Date createDate;

    private Long userId;

    private String username;

    private Long caseId;

    private String caseUsername;

    private String caseName;

    private Integer status;

    /**
     * 当前亲属是否操作过
     */
    private Integer ownStatus;

}
