package com.bootdo.controller;

import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.utils.ShiroUtils;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

	public SysUser getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}