package com.bootdo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.domain.entity.MedCaseApply;
import com.bootdo.common.domain.entity.MedCaseApplyAgree;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.mapper.MedCaseApplyAgreeMapper;
import com.bootdo.mapper.MedCaseApplyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class MedCaseApplyAgreeService extends BaseService<MedCaseApplyAgreeMapper, MedCaseApplyAgree> {

    @Autowired
    private MedCaseApplyService medCaseApplyService;

    @Autowired
    private SysUserService sysUserService;

    @Transactional
    public void agree(Long id, String type, SysUser sysUser) {
        MedCaseApplyAgree medCaseApplyAgree = new MedCaseApplyAgree();
        medCaseApplyAgree.setApplyId(id);
        medCaseApplyAgree.setUserId(sysUser.getId());
        medCaseApplyAgree.setType(type);
        save(medCaseApplyAgree);
        boolean update = true;
        Integer status = "1".equals(type) ? 2 : 1;
        if (sysUser.getRoleId() != 3) {
            //计算是否有一半以上同意
            int relativesCount = sysUserService.count(new QueryWrapper<SysUser>()
                    .eq("relatives_id", sysUser.getRelativesId()));
            int agreeCount = count(new QueryWrapper<MedCaseApplyAgree>()
                    .eq("apply_id", id)
                    .eq("type", type));
            int x = relativesCount % 2>0?1:0;
            if (relativesCount/2+x > agreeCount) {
                update = false;
            } else {
                status = 2;
            }
        }
        if (update) {
            MedCaseApply apply = new MedCaseApply();
            apply.setId(id);
            apply.setStatus(status);
            medCaseApplyService.updateById(apply);
        }
    }

}
