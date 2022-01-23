package com.bootdo.service;

import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryListReq;
import com.bootdo.mapper.MedCaseMapper;
import com.bootdo.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class MedCaseService extends BaseService<MedCaseMapper, MedCase> {

}
