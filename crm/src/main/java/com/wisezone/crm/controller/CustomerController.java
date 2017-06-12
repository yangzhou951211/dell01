package com.wisezone.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisezone.crm.service.CustomerService;
import com.wisezone.crm.vo.CustomerVo;

@Controller
@RequestMapping("customer")
public class CustomerController
{
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("find_all")
	@ResponseBody
	public List<CustomerVo> findAll(){
		return customerService.findAll();
	}
}
