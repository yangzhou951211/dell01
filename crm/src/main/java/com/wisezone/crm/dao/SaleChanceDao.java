package com.wisezone.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.wisezone.crm.model.SaleChance;
import com.wisezone.crm.query.SaleChanceQuery;

public interface SaleChanceDao
{

	/**
	 * 分页查询
	 * @param query
	 * @param pageBounds
	 * @return
	 * List<SaleChance>
	 */
	public List<SaleChance> selectForPage(SaleChanceQuery query, PageBounds pageBounds);

	/**
	 * 插入数据
	 * @param saleChance
	 */
	public int insert(SaleChance saleChance);

	/**
	 * 修改数据
	 * @param saleChance
	 */
	public int update(SaleChance saleChance);

	/**
	 * 批量删除数据
	 * @param ids
	 */
	public int deleteBatch(@Param(value="ids")String ids);

}
