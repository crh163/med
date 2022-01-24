package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.MedCaseApply;
import com.bootdo.common.domain.entity.Response;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryCaseListReq;
import com.bootdo.common.exception.BasicException;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.service.MedCaseApplyService;
import com.bootdo.service.MedCaseService;
import com.bootdo.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/med/caseApply")
@Controller
public class MedCaseApplyController extends BaseController {

	@Autowired
	private MedCaseApplyService medCaseApplyService;

	@Autowired
	private MedCaseService medCaseService;

	@Autowired
	private SysUserService sysUserService;

	@GetMapping("/list")
	public String list() {
		return "med/apply/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public ManPage list(@RequestBody QueryCaseListReq req) {
		QueryWrapper<MedCaseApply> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("create_date");
		return medCaseApplyService.selectList(req.getPage(), req.getPageSize(), wrapper);
	}

	@PostMapping("/insert")
	@ResponseBody
	public Response insert(String username) {
		SysUser user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));
		if (user == null) {
			return ResponseUtil.getFail(ResponseCodeEnum.USER_NOT_EXIST);
		} else if (user.getRoleId() != 3) {
			return ResponseUtil.getFail(ResponseCodeEnum.CASE_APPLY_NO_ROLE3);
		}
		int count = medCaseService.count(new QueryWrapper<MedCase>()
				.eq("user_id", user.getId()));
		if (count == 0) {
			return ResponseUtil.getFail(ResponseCodeEnum.USER_NO_EXIST_CASE);
		}
		//查询状态为非不同意的，可再次发起申请
		List<MedCaseApply> list = medCaseApplyService.list(new QueryWrapper<MedCaseApply>()
				.eq("user_id", getUserId())
				.eq("case_username", username)
				.last(" and status!=1 "));
		if (!CollectionUtils.isEmpty(list)) {
			return ResponseUtil.getFail(ResponseCodeEnum.CASE_APPLY_ERROR);
		}
		MedCaseApply medCaseApply = new MedCaseApply();
		medCaseApply.setUserId(getUserId());
		medCaseApply.setUsername(getUser().getUsername());
		medCaseApply.setName(getUser().getName());
		medCaseApply.setCaseId(user.getId());
		medCaseApply.setCaseUsername(user.getUsername());
		medCaseApply.setCaseName(user.getName());
		medCaseApply.setStatus(0);
		medCaseApplyService.save(medCaseApply);
		return ResponseUtil.getSuccess();
	}


}
