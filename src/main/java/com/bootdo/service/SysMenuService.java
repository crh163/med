package com.bootdo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.domain.entity.SysMenu;
import com.bootdo.common.domain.entity.SysRoleMenu;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.model.BuildTree;
import com.bootdo.common.domain.model.Tree;
import com.bootdo.mapper.SysMenuMapper;
import com.bootdo.mapper.SysRoleMenuMapper;
import com.bootdo.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rory.chen
 * @date 2021-01-21 18:06
 */
@Service
@Slf4j
public class SysMenuService extends BaseService<SysMenuMapper, SysMenu> {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    
    public Tree<SysMenu> getTree(Long roleId) {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> menus = null;
        if (roleId == null) {
            menus = list();
        } else {
            menus = sysRoleMenuMapper.selectByRoleId(roleId);
        }
        for (SysMenu sysMenuDO : menus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    public List<Tree<SysMenu>> listMenuTree(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            return null;
        }
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> menus = null;
        if (sysUser.getRoleId() == null) {
            menus = list();
        } else {
            menus = sysRoleMenuMapper.selectByRoleId(sysUser.getRoleId());
        }
        for (SysMenu sysMenuDO : menus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.buildList(trees, "0");
    }
}
