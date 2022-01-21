package com.bootdo.common.domain.entity;

import com.bootdo.common.domain.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MedCase extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("COURSE_FLOW")
    private String courseFlow;

    @JsonProperty("IN_PATIENT_FLOW")
    private String inPatientFlow;

    @JsonProperty("MR_ID")
    private String mrId;

    @JsonProperty("RECORDOWN_ID")
    private String recordownId;

    @JsonProperty("RECORDOWN_NAME")
    private String recordownName;

    @JsonProperty("RECORDMODIFY_ID")
    private String recordmodifyId;

    @JsonProperty("RECORDMODIFY_NAME")
    private String recprdModifyName;

    @JsonProperty("RECORDMODIFY_TIME")
    private String recordmodifyTime;

    @JsonProperty("RECORD_TIME")
    private String recordTime;

    @JsonProperty("COURSE_TYPEID")
    private String courseTypeid;

    @JsonProperty("COURSE_TYPE")
    private String courseType;

    @JsonProperty("DOCTOR_NAME")
    private String doctorName;

    @JsonProperty("COURSE_CONTENT")
    private String courseContent;

    @JsonProperty("DEPT_ID")
    private String deptId;

    @JsonProperty("DEPT_NAME")
    private String deptName;

    @JsonProperty("AREA_ID")
    private String areaId;

    @JsonProperty("AREA_NAME")
    private String areaName;

    @JsonProperty("SUBMIT_FLAG")
    private String submitFlag;

    @JsonProperty("MD5_CONTENT")
    private String md5Content;

    @JsonProperty("LOCK_FLAG")
    private String lockFlag;

    @JsonProperty("NEWPAGE")
    private String newpage;

}
