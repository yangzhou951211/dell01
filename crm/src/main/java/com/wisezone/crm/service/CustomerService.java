package com.wisezone.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisezone.crm.dao.CustomerDao;
import com.wisezone.crm.vo.CustomerVo;

@Service
public class CustomerService
{
	@Autowired
	private CustomerDao customerDao;

	/**
	 * 获取所有的客户
	 * @return
	 * List<CustomerVo>
	 */
	public List<CustomerVo> findAll()
	{
		List<CustomerVo> customerVos = customerDao.listAll();
		return customerVos;
	}

}
