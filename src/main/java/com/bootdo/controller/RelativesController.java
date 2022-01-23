package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.constant.ColumnConsts;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.Response;
import com.bootdo.common.domain.entity.SysRole;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryCaseListReq;
import com.bootdo.common.domain.req.QueryListReq;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.service.MedCaseService;
import com.bootdo.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/med/relatives")
@Controller
public class RelativesController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("user", getUser());
		return "med/relatives/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public ManPage list(@RequestBody QueryListReq req) {
		QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
		//病人
		if (getUser().getRoleId() == 3) {
			wrapper.eq("id", getUserId()).or().eq("relatives_id", getUserId());
		}
		//普通人（亲属）
		if (getUser().getRoleId() == 4) {
			Long relativesId = getUser().getRelativesId();
			if (relativesId == null || relativesId == 0L) {
				return new ManPage(new ArrayList(), 0);
			}
			wrapper.eq("id", relativesId).or().eq("relatives_id", relativesId);
		}
		return sysUserService.selectList(req.getPage(), req.getPageSize(), wrapper);
	}

	@PostMapping("/insert")
	@ResponseBody
	public Response insert(String username) throws BasicException {
		SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));
		if (user == null) {
			return ResponseUtil.getFail(ResponseCodeEnum.USER_NOT_EXIST);
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(user.getId());
		sysUser.setRelativesId(getUserId());
		sysUserService.updateById(sysUser);
		return ResponseUtil.getSuccess();
	}

	@PostMapping("/delete")
	@ResponseBody
	public Response deleteByIds(Long id) throws BasicException {
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setRelativesId(0L);
		sysUserService.updateById(sysUser);
		return ResponseUtil.getSuccess();
	}
}
