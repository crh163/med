package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.constant.AdminEnum;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.Response;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryCaseListReq;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.service.MedCaseService;
import com.bootdo.service.SysRoleService;
import com.bootdo.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequestMapping("/sys/user")
@Controller
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysRoleService sysRoleService;

	@GetMapping("/list")
	public String list(Model model) {
		return "system/user/user";
	}

	@PostMapping("/list")
	@ResponseBody
	public ManPage list(@RequestBody QueryCaseListReq req) {
		QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
		return sysUserService.selectList(req.getPage(), req.getPageSize(), wrapper);
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("roles", sysRoleService.list());
		return "system/user/add";
	}

	@PostMapping("/add")
	@ResponseBody
	public Response insert(SysUser sysUser) throws BasicException {
		sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
		return ResponseUtil.getResult(sysUserService.save(sysUser));
	}

	@GetMapping("/edit")
	public String edit(Model model,String id) {
		model.addAttribute("user", sysUserService.getById(id));
		model.addAttribute("roles", sysRoleService.list());
		return "system/user/edit";
	}

	@PostMapping("/update")
	@ResponseBody
	public Response update(SysUser sysUser) throws BasicException {
		return ResponseUtil.getResult(sysUserService.updateById(sysUser));
	}

	@PostMapping("/deleteByIds")
	@ResponseBody
	public Response deleteByIds(String ids) throws BasicException {
		List<Long> idList = Arrays.stream(ids.split(","))
				.map(Long::parseLong).collect(Collectors.toList());
		for(Long id : idList){
			if (AdminEnum.USER_ADMIN.getId().equals(id)) {
				return ResponseUtil.getFail("不能删除admin用户");
			}
			SysUser sysUser = sysUserService.getById(id);
			if(Objects.isNull(sysUser)){
				return ResponseUtil.getFail("id对应用户为空");
			}
		}
		sysUserService.removeByIds(idList);
		return ResponseUtil.getSuccess();
	}

}
