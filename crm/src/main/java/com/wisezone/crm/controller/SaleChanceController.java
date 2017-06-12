package com.wisezone.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisezone.base.ResultInfo;
import com.wisezone.crm.constant.Constant;
import com.wisezone.crm.model.SaleChance;
import com.wisezone.crm.query.SaleChanceQuery;
import com.wisezone.crm.service.SaleChanceService;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController
{
	@Autowired
	private SaleChanceService saleChanceService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,Model model){
		model.addAttribute("ctx", request.getContextPath());
		return "sale_chance";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> selectForPage(SaleChanceQuery query){
		Map<String, Object> result = saleChanceService.selectForPage(query);
		return result;
	}
	
	
	@RequestMapping("add_update")
	@ResponseBody
	public ResultInfo addOrUpdate(SaleChance saleChance){
		try
		{
			saleChanceService.addOrUpdate(saleChance);
			return new ResultInfo(Constant.OPT_SUCCESS);
		} catch (Exception e)
		{
			return new ResultInfo(e.getMessage());
		}
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public ResultInfo delete(String ids){
		try
		{
			saleChanceService.delete(ids);
			return new ResultInfo(Constant.OPT_SUCCESS);
		} catch (Exception e)
		{
			return new ResultInfo(e.getMessage());
		}
	}
}
