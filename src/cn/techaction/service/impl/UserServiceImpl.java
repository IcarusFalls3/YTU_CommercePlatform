package cn.techaction.service.impl;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
//import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.*;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.MD5Util;
import cn.techaction.vo.ActionUserVo;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private ActionUserDao userDao;
	@Autowired
	private ActionOrderDao orderDao;
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		// TODO Auto-generated method stub
		//1、判断用户名是否存在
		int rs=userDao.checkUserByAccount(account);
		if(rs==0){
			return SverResponse.createByErrorMessage("用户名不存在！");
		}
		//2、根据用户名和密码去查找用户
		String md5pwd=MD5Util.MD5Encode(password, "utf-8", false);
		User user=userDao.findUserByAccountAndPassword(account, md5pwd);
		
		//3、判断用户是否存在
		if(user==null){
			return SverResponse.createByErrorMessage("密码错误");
		}
		//置空密码
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("登陆成功", user);
	}
	@Override
	public SverResponse<String> isAdmin(User user) {
		// TODO Auto-generated method stub
		if(user.getRole()==ConstUtil.Role.ROLE_ADMIN){
			return SverResponse.createRespBySuccess();
		}
		return SverResponse.createRespByError();
	}
	@Override
	public SverResponse<List<ActionUserVo>> findUserList() {
		// TODO Auto-generated method stub
		List<ActionUserVo> vos=Lists.newArrayList();
		//1.去调用Dao层的方法
		List<User> users=userDao.findAllUsers();
		//2.处理 将User对象转化成ActionUserVO对象
		for(User u:users){
			vos.add(setNormalProperty(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * 将user转换成actionuservo
	 * @param user
	 * @return
	 */
	private ActionUserVo setNormalProperty(User user){
		ActionUserVo vo=new ActionUserVo();
		vo.setAccount(user.getAccount());
		vo.setAge(user.getAge());
		vo.setEmail(user.getEmail());
		vo.setId(user.getId());
		vo.setName(user.getName());
		vo.setPhone(user.getPhone());
		if(user.getSex()==1){
			vo.setSex("男");
		}else {
			vo.setSex("女");
		}
		return vo;
	}
	@Override
	public SverResponse<ActionUserVo> findUser(Integer id) {
		// TODO Auto-generated method stub
		//1.调用dao层的方法
		User user=userDao.findUserById(id);
		//2.将User转换层action user vo
		ActionUserVo vo=setNormalProperty(user);
		return SverResponse.createRespBySuccess(vo);
	}
	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// TODO Auto-generated method stub
		//1.根据id获得User对象
		User user =userDao.findUserById(userVo.getId());
		//2.把userVo里修改的属性值赋值给user对象
		user.setAccount(userVo.getAccount());
		user.setAge(userVo.getAge());
		user.setEmail(userVo.getEmail());
		user.setName(userVo.getName());
		user.setPhone(userVo.getPhone());
		if (userVo.getSex().equals("男")) {
			user.setSex(1);
		}else {
			user.setSex(0);
		} 
		user.setUpdate_time(new Date());//更新要改变时间
		//3.调用dao层方法
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("用户信息修改成功！",user);
		}
		return SverResponse.createByErrorMessage("用户信息修改失败！");
	}
	
	@Override
	public SverResponse<String> delUser(Integer id) {
		// TODO Auto-generated method stub
		//1.判断用户是否有订单，如果没有订单就可以删除
		if(orderDao.findOrderByUid(id).size()>0){
			return SverResponse.createRespBySuccessMessage("用户存在关联订单，无法删除！");
		}
		//2.删除用户，实际是修改用户的del的值
		User user=userDao.findUserById(id);
		user.setDel(1);
		user.setUpdate_time(new Date());
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("用户删除成功！");
		}
		return SverResponse.createByErrorMessage("用户删除失败！");
	}

}
