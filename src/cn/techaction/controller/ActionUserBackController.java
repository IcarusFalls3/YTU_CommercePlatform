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
		//1.����Sevice��¼����
		SverResponse<User> response=userService.doLogin(account, password);
		//2.�жϷ��ؽ��
		if(response.isSuccess()){
			//3.�ܵ�¼���ж��ǲ��ǹ���Ա���ǹ���Ա�����session�������ʾ������Ϣ
			User user=response.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN){
				session.setAttribute(ConstUtil.CUR_USER, user);
				//����session.setAttribute(ConstUtil.CUR_USER, response.getData());
				return response;
			}
			return SverResponse.createByErrorMessage("���ǹ���Ա���޷���¼��");
		}
		return response;

	}

	//����id����û���Ϣ
	@RequestMapping("/finduser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id ){
	//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���!");
		}
		//2.�Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
			//3.����Service�ķ��������û�id����û���Ϣ
			return userService.findUser(id);
		}
	return SverResponse.createByErrorMessage("����Ȩ�޲�����");
	}

	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���!");
		}
		//2.�Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
				//3.����servise�еķ���������е��û���Ϣ
			return userService.findUserList();
		}
	
		return SverResponse.createByErrorMessage("���޲���Ȩ��");
	}
	
	/**
	 * �޸��û�����
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/updateuser.do")
	@ResponseBody
	public SverResponse<User> updateUser(HttpSession session,ActionUserVo userVo){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���!");
		}
		//2.�Ƿ��ǹ���Ա
		SverResponse<String> response=userService.isAdmin(user);
		if(response.isSuccess()){
				//3.����Service�ķ����������û�
				return userService.updateUserInfo(userVo);
		}
		return SverResponse.createByErrorMessage("����Ȩ�޲�����");
	}



	//ɾ���û�
	@RequestMapping("/deleteusers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null){
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "���¼���ٽ��в���!");
		}
		//2.�Ƿ��ǹ���Ա
				SverResponse<String> response=userService.isAdmin(user);
				if(response.isSuccess()){
						//3.����Service�ķ�����ɾ������
						return userService.delUser(id);
				}
				return SverResponse.createByErrorMessage("����Ȩ�޲�����");
	}
}