package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.domain.entity.MedCaseApply;
import com.bootdo.common.domain.entity.MedCaseApplyAgree;
import com.bootdo.common.domain.entity.Response;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryCaseListReq;
import com.bootdo.common.domain.res.MedCaseApplyRes;
import com.bootdo.common.utils.DataUtils;
import com.bootdo.common.utils.DateUtils;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.service.MedCaseApplyAgreeService;
import com.bootdo.service.MedCaseApplyService;
import com.bootdo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/med/caseAgree")
@Controller
public class MedCaseAgreeController extends BaseController {

	@Autowired
	private MedCaseApplyService medCaseApplyService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private MedCaseApplyAgreeService medCaseApplyAgreeService;

	@GetMapping("/list")
	public String list() {
		return "med/agree/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public ManPage list(@RequestBody QueryCaseListReq req) {
		Long selectUserId = getUserId();
		if (getUser().getRoleId() != 3) {
			SysUser user = sysUserService.getById(getUser().getRelativesId());
			selectUserId = user.getId();
		}
		QueryWrapper<MedCaseApply> wrapper = new QueryWrapper<>();
		wrapper.eq("case_id", selectUserId);
		ManPage manPage = medCaseApplyService.selectList(req.getPage(), req.getPageSize(), wrapper);
		List<MedCaseApplyRes> list = new ArrayList<>();
		for (Object object : manPage.getRows()) {
			MedCaseApply medCaseApply = (MedCaseApply) object;
			MedCaseApplyRes medCaseApplyRes = DataUtils.coverData(medCaseApply, MedCaseApplyRes.class);
			Integer agreeCount = medCaseApplyAgreeService.count(new QueryWrapper<MedCaseApplyAgree>()
					.eq("apply_id", medCaseApply.getId())
					.eq("user_id", getUserId()));
			medCaseApplyRes.setOwnStatus(agreeCount > 0 ? 1 : 0);
			list.add(medCaseApplyRes);
		}
		manPage.setRows(list);
		return manPage;
	}

	@PostMapping("/agree")
	@ResponseBody
	public Response agree(Long id, String type) {
		medCaseApplyAgreeService.agree(id, type, getUser());
		return ResponseUtil.getSuccess();
	}


}
