package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
//import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
//import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/ht/user")
public class ActionUserController {
	@Autowired
	private ActionUserService actionUserService;

	@RequestMapping("/login.do")
	@ResponseBody
	public  SverResponse<User> doLogin(HttpSession session,String account,String password) {
		//1.调用Sevice登录方法
		SverResponse<User> response=actionUserService.doLogin(account, password);
		//2.判断返回结果
		if(response.isSuccess()){
			User user=response.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return response;
			}
			return SverResponse.createByErrorMessage("不是管理员，无法登录！");
		}
		if(response.isSuccess()){
				return response;
		}
		return response;
	}
}