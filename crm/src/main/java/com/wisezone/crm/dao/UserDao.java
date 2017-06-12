package com.wisezone.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisezone.crm.model.User;

public interface UserDao
{
	public List<User> listAll();

	/**
	 * 获取登录账号
	 * @param userName	用户名
	 * @param password	密码
	 * @param roleName	角色名称
	 * @return
	 * User
	 */
	public User findUserByUserNamePwdRole(@Param(value="userName")String userName, 
			@Param(value="password")String password, 
			@Param(value="roleName")String roleName);

	/**
	 * 通过userID获取用户信息
	 * @param userId
	 * @return
	 * User
	 */
	public User loadById(Integer userId);

	/**
	 * 更新密码
	 * @param userId	
	 * @param md5Method
	 * @return
	 * int
	 */
	public int updatePassword(@Param(value="userId")Integer userId, 
			@Param(value="password")String password);

	/**
	 * 根据角色名称获取用户信息
	 * @param string
	 * @return
	 * List<User>
	 */
	public List<User> findByRoleName(@Param(value="roleName")String roleName);

}
