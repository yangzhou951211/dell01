package com.wisezone.crm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisezone.base.ResultInfo;
import com.wisezone.base.exception.ParamException;
import com.wisezone.crm.constant.Constant;
import com.wisezone.crm.model.User;
import com.wisezone.crm.service.UserService;
import com.wisezone.crm.util.LoginUserUtil;
import com.wisezone.crm.vo.UserLoginIdentity;

@Controller
@RequestMapping("user")
public class UserController
{
	@Autowired
	private UserService userService;
	
	@RequestMapping("list")
	public String listAll(Model model){
		List<User> users = userService.listAll();
		model.addAttribute("users",users);
		return "user_list";
	}
	
	@RequestMapping(value="login")
	@ResponseBody
	public ResultInfo login(String userName,String password,String roleName){
		try
		{
			UserLoginIdentity result = userService.login(userName,password,roleName);
			return new ResultInfo(result);
		} catch (ParamException e)
		{
			return new ResultInfo(Constant.RESULT_ERROR,e.getMessage());
		}
	}
	
	
	@RequestMapping(value="update_password")
	@ResponseBody
	public ResultInfo updatePassword(String oldPassword,String newPassword,
			String confirmPassword,HttpServletRequest request){
		try
		{
			Integer userId = LoginUserUtil.loadUserIdFromCookie(request);
			userService.updatePassword(oldPassword,newPassword,
					confirmPassword,userId);
			return new ResultInfo(Constant.OPT_SUCCESS);
		} catch (ParamException e)
		{
			return new ResultInfo(Constant.RESULT_ERROR,e.getMessage());
		}
	}
	
	
	@RequestMapping("find_customer_manager")
	@ResponseBody
	public List<User> findCustomerManager(){
		List<User> customerManagers = userService.findCustomerManagers();
		return customerManagers;
	}
}
