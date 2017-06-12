package com.wisezone.crm.service;

import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisezone.base.exception.LoginBizException;
import com.wisezone.base.exception.ParamException;
import com.wisezone.crm.constant.Constant;
import com.wisezone.crm.dao.UserDao;
import com.wisezone.crm.model.User;
import com.wisezone.crm.util.MD5Util;
import com.wisezone.crm.util.UserIDBase64;
import com.wisezone.crm.vo.LoginUserInfo;
import com.wisezone.crm.vo.UserLoginIdentity;

@Service
public class UserService
{
	@Autowired
	private UserDao userDao;
	
	public List<User> listAll(){
		List<User> users = userDao.listAll();
		return users;
	}

	/**
	 * 用户登录
	 * @param userName	用户名
	 * @param password	密码
	 * @param roleName	角色名称
	 * @return 
	 */
	public UserLoginIdentity login(String userName, String password, String roleName)
	{
		//1、基本参数验证
		if (StringUtils.isBlank(userName))
		{
			throw new ParamException("请输入用户名");
		}
		if (StringUtils.isBlank(password))
		{
			throw new ParamException("请输入密码");
		}
		if (StringUtils.isBlank(roleName))
		{
			throw new ParamException("请选择用户类型");
		}
		//2、用户是否存在
		password = MD5Util.md5Method(password);//md5加密，不可逆
		User user = userDao.findUserByUserNamePwdRole(userName.trim(),
				password,roleName.trim());
		if (user == null)
		{
			throw new ParamException("用户名或密码错误");
		}
		//3、封装返回对象
		UserLoginIdentity userLoginIdentity = new UserLoginIdentity();
		userLoginIdentity.setUserIdString(UserIDBase64.encoderUserID(user.getId()));
		userLoginIdentity.setUserName(userName);
		return userLoginIdentity;
	}

	/**
	 * 获取登录用户的信息
	 * @param userId
	 * @return
	 * LoginUserInfo
	 */
	public LoginUserInfo findLoginUser(Integer userId)
	{
//		if (userId == null || userId < 1)
//		{
//			throw new LoginBizException(Constant.LOGIN_FIRST);
//		}
//		User user = userDao.loadById(userId);
//		if (user == null)
//		{
//			throw new LoginBizException(Constant.LOGIN_FIRST);
//		}
		User user = findUserById(userId);//封装的上面的
		LoginUserInfo loginUserInfo = new LoginUserInfo();
		loginUserInfo.setRealName(user.getTrueName());
		loginUserInfo.setRoleName(user.getRoleName());
		loginUserInfo.setUserName(user.getUserName());
		
		return loginUserInfo;
	}

	/**
	 * 更改密码
	 * @param oldPassword	旧密码
	 * @param newPassword	新密码
	 * @param confirmPassword	确认密码
	 * @param userId
	 * void
	 */
	public void updatePassword(String oldPassword, String newPassword, String confirmPassword, Integer userId)
	{
		//1、基本参数验证
		checkUptPwdParams(oldPassword, newPassword, confirmPassword);
		
		//2、通过用户id获取用户信息
		User user = findUserById(userId);
		
		//3、校验旧密码是否正确
		if (!MD5Util.md5Method(oldPassword).equals(user.getPassword()))
		{
			throw new ParamException("旧密码输入错误，请重新输入");
		}
		
		//4、更新新密码
		int uptcount = userDao.updatePassword(userId,MD5Util.md5Method(newPassword));
		if (uptcount == 0)
		{
			throw new ParamException(Constant.OPT_FAILURE);
		}
	}
	
	/**
	 * 获取客户经理
	 * @return
	 * List<User>
	 */
	public List<User> findCustomerManagers()
	{
		List<User> customerManagers = userDao.findByRoleName("客户经理");
        return customerManagers;
	}
	
	
	/**
	 * 封装的----通过userId获取用户信息
	 * @param userId
	 * @return
	 * User
	 */
	@SuppressWarnings("unused")
	private User findUserById(Integer userId){
		if (userId == null || userId < 1)
		{
			throw new LoginBizException(Constant.LOGIN_FIRST);
		}
		User user = userDao.loadById(userId);
		if (user == null)
		{
			throw new LoginBizException(Constant.LOGIN_FIRST);
		}
		return user;
	}
	
	/**
	 * 封装---更新密码的参数验证
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmPassword
	 * void
	 */
	@SuppressWarnings("unused")
	private static void checkUptPwdParams(String oldPassword, 
			String newPassword, String confirmPassword){
		if (StringUtils.isBlank(oldPassword))
		{
			throw new ParamException("请输入旧密码");
		}
		if (StringUtils.isBlank(newPassword))
		{
			throw new ParamException("请输入新密码");
		}
		if (StringUtils.isBlank(confirmPassword))
		{
			throw new ParamException("请输入确认密码");
		}
		if (!confirmPassword.equals(newPassword))
		{
			throw new ParamException("确认密码不一致");
		}
	}

	
}
