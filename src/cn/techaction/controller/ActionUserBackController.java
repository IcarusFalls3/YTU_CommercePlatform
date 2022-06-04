package cn.techaction.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/mgr/user")
public class ActionUserBackController {
	@Resource
	private UserService userService;

	@RequestMapping("/login.do")
	@ResponseBody
	public  SverResponse<User> doLogin(HttpSession session,String account,String password) {
		//1.调用Sevice登录方法
		SverResponse<User> response=userService.doLogin(account, password);
		//2.判断返回结果
		if(response.isSuccess()){
			//3.能登录则判断是不是管理员，是管理员存放在session里，否则提示错误信息
			User user=response.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN){
				session.setAttribute(ConstUtil.CUR_USER, user);
				//男生session.setAttribute(ConstUtil.CUR_USER, response.getData());
				return response;
			}
			return SverResponse.createByErrorMessage("不是管理员，无法登录！");
		}
		return response;

	}

	//根据id获得用户信息
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id ){
	//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作!");
		}
		//2.是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
			//3.调用Service的方法根据用户id获得用户信息
			return userService.findUser(id);
		}
	return SverResponse.createByErrorMessage("您无权限操作！");
	}

	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作!");
		}
		//2.是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
				//3.调用servise中的方法获得所有的用户信息
			return userService.findUserList();
		}
	
		return SverResponse.createByErrorMessage("您无操作权限");
	}
	
	/**
	 * 修改用户个人
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/updateuser.do")
	@ResponseBody
	public SverResponse<User> updateUser(HttpSession session,ActionUserVo userVo){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作!");
		}
		//2.是否是管理员
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
				//3.调用Service的方法：更新用户
				return userService.updateUserInfo(userVo);
		}
		return SverResponse.createByErrorMessage("您无权限操作！");
	}



	//删除用户
	@RequestMapping("/deleteusers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后再进行操作!");
		}
		//2.是否是管理员
				SverResponse<String> response=userService.isAdmin(user);
				if(response.isSuccess()){
						//3.调用Service的方法：删除方法
						return userService.delUser(id);
				}
				return SverResponse.createByErrorMessage("您无权限操作！");
	}
}