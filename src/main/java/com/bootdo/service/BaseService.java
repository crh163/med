package com.bootdo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bootdo.common.domain.model.BaseModel;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.utils.ShiroUtils;
import java.util.Date;

/**
 * @author rory.chen
 * @date 2021-01-22 14:39
 */
public class BaseService<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> {

    public ManPage selectList(int page, int pageSize, QueryWrapper<T> wrapper) {
        page = page == 0 ? 1 : page;
        ManPage manPage = new ManPage();
        manPage.setTotal(count(wrapper));
        wrapper.last(" LIMIT " + (page-1)* pageSize + "," + pageSize);
        manPage.setRows(list(wrapper));
        return manPage;
    }

    @Override
    public boolean save(T entity) {
        entity.setCreateId(ShiroUtils.getUserId());
        entity.setCreateDate(new Date());
        return super.save(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        entity.setUpdateId(ShiroUtils.getUserId());
        entity.setUpdateDate(new Date());
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateById(T entity) {
        entity.setUpdateId(ShiroUtils.getUserId());
        entity.setUpdateDate(new Date());
        return super.updateById(entity);
    }

}
