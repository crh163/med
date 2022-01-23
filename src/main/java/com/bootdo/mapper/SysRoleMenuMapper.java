package com.bootdo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bootdo.common.domain.entity.SysMenu;
import com.bootdo.common.domain.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rory.chen
 * @date 2021-01-12 18:32
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysMenu> selectByRoleId(@Param("roleId") Long roleId);

}
