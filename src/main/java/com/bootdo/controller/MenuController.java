package com.bootdo.controller;

import com.bootdo.common.domain.entity.SysMenu;
import com.bootdo.common.domain.model.Tree;
import com.bootdo.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
	String prefix = "system/menu";

	@Autowired
	private SysMenuService menuService;

	@GetMapping()
	String menu(Model model) {
		return prefix+"/menu";
	}

	@RequestMapping("/list")
	@ResponseBody
	List<SysMenu> list() {
		List<SysMenu> menus = menuService.list();
		return menus;
	}

	@GetMapping("/tree")
	@ResponseBody
	Tree<SysMenu> tree() {
		Tree<SysMenu>  tree = menuService.getTree(null);
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	Tree<SysMenu> tree(@PathVariable("roleId") Long roleId) {
		Tree<SysMenu> tree = menuService.getTree(roleId);
		return tree;
	}
}
