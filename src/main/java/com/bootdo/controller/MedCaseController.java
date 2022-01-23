package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.SysMenu;
import com.bootdo.common.domain.model.Tree;
import com.bootdo.common.domain.page.ManPage;
import com.bootdo.common.domain.req.QueryCaseListReq;
import com.bootdo.common.domain.req.QueryListReq;
import com.bootdo.common.properties.FileProperties;
import com.bootdo.service.MedCaseService;
import com.bootdo.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/med/case")
@Controller
public class MedCaseController extends BaseController {

	@Autowired
	private MedCaseService medCaseService;

	@GetMapping("/list")
	public String list(String userId, Model model) {
		model.addAttribute("userId", userId);
		return "med/case/list";
	}

	@PostMapping("/list")
	@ResponseBody
	public ManPage list(@RequestBody QueryCaseListReq req) {
		QueryWrapper<MedCase> wrapper = new QueryWrapper<>();
		wrapper.eq("userId", req.getUserId() == null ? getUserId() : req.getUserId());
		if (StringUtils.isNotBlank(req.getDoctorName())) {
			wrapper.like("doctorName", req.getDoctorName());
		}
		return medCaseService.selectList(req.getPage(), req.getPageSize(), wrapper);
	}

	@GetMapping("/info")
	public String info(String id, Model model) {
		MedCase medCase = medCaseService.getById(id);
		model.addAttribute("medCase", medCase);
		return "med/case/info";
	}
}
