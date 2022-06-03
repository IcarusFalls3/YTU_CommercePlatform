package cn.techaction.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
//import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.MD5Util;

@Service
public class ActionUserServiceImpl implements ActionUserService{

	@Autowired
	private ActionUserDao actionUserDao;
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		// TODO Auto-generated method stub
		//1.判断用户名是否存在
		int rs=actionUserDao.checkUserByAccount(account);
		if(rs==0){
			return SverResponse.createByErrorMessage("用户不存在！");
		}
		//2根据用户名和密码查找用户
//		String md5pwd=MD5Util.MD5Encode(password, "utf-8", false);
		User user=actionUserDao.findUserByAccountAndPassword(account, password);
//		User user=actionUserDao.findUserByAccountAndPassword(account, md5pwd);
		//3.判断查找的用户是否存在
		if(user==null){
			return SverResponse.createByErrorMessage("密码错误");
		}
		//置空密码
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登录成功",user);
	}
	
}
