package com.wisezone.crm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wisezone.crm.service.UserService;
import com.wisezone.crm.util.LoginUserUtil;
import com.wisezone.crm.vo.LoginUserInfo;

@Controller
@RequestMapping("")
public class IndexController
{
	@Autowired
	private UserService userService;
	
	@RequestMapping("index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("main")
	public String main(Model model,HttpServletRequest request){
		model.addAttribute("ctx", request.getContextPath());
		Integer userId = LoginUserUtil.loadUserIdFromCookie(request);
		LoginUserInfo loginUserInfo = userService.findLoginUser(userId);
		model.addAttribute("currentUser", loginUserInfo);
		return "main";
	}
}
