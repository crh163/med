package com.bootdo.common.domain.entity;

import com.bootdo.common.aspect.MedCaseAnn;
import com.bootdo.common.domain.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class MedCase extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病人病情的流水号
     */
    @MedCaseAnn("COURSE_FLOW")
    private String courseFlow;

    /**
     * 病人的id
     */
    @MedCaseAnn("IN_PATIENT_FLOW")
    private String inPatientFlow;

    /**
     * 核磁共振的id
     */
    @MedCaseAnn("MR_ID")
    private String mrId;

    /**
     * 记录人员的id
     */
    @MedCaseAnn("RECORDOWN_ID")
    private String recordownId;

    /**
     * 记录人员的姓名
     */
    @MedCaseAnn("RECORDOWN_NAME")
    private String recordownName;

    /**
     * 修改记录人员的id
     */
    @MedCaseAnn("RECORDMODIFY_ID")
    private String recordmodifyId;

    /**
     * 修改记录人员的姓名
     */
    @MedCaseAnn("RECORDMODIFY_NAME")
    private String recordmodifyName;

    /**
     * 修改记录时间
     */
    @MedCaseAnn("RECORDMODIFY_TIME")
    private String recordmodifyTime;

    /**
     * 纪律的时间
     */
    @MedCaseAnn("RECORD_TIME")
    private String recordTime;

    /**
     * 行为的id
     */
    @MedCaseAnn("COURSE_TYPEID")
    private String courseTypeid;

    /**
     * 行为
     */
    @MedCaseAnn("COURSE_TYPE")
    private String courseType;

    /**
     * 医生的姓名
     */
    @MedCaseAnn("DOCTOR_NAME")
    private String doctorName;

    /**
     *
     */
    @MedCaseAnn("COURSE_CONTENT")
    private String courseContent;

    /**
     * 科室id
     */
    @MedCaseAnn("DEPT_ID")
    private String deptId;

    /**
     * 科室名称
     */
    @MedCaseAnn("DEPT_NAME")
    private String deptName;

    /**
     * 病室id
     */
    @MedCaseAnn("AREA_ID")
    private String areaId;

    /**
     * 病室名称
     */
    @MedCaseAnn("AREA_NAME")
    private String areaName;

    /**
     * 没啥用
     */
    @MedCaseAnn("SUBMIT_FLAG")
    private String submitFlag;

    /**
     * 对内容md5加密的返回的hash
     */
    @MedCaseAnn("MD5_CONTENT")
    private String md5Content;

    /**
     * 没啥用
     */
    @MedCaseAnn("LOCK_FLAG")
    private String lockFlag;

    /**
     * 没啥用
     */
    @MedCaseAnn("NEWPAGE")
    private String newpage;

}
