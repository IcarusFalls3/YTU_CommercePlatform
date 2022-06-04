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
import cn.techaction.vo.ActionUserVo;

@Service
public class ActionUserServiceImpl implements ActionUserService{

	@Autowired
	private ActionUserDao actionUserDao;
	@Override
	public SverResponse<User> doLogin(String account, String password) {
		// TODO Auto-generated method stub
		//1.�ж��û����Ƿ����
		int rs=actionUserDao.checkUserByAccount(account);
		if(rs==0){
			return SverResponse.createByErrorMessage("�û������ڣ�");
		}
		//2�����û�������������û�
//		String md5pwd=MD5Util.MD5Encode(password, "utf-8", false);
		User user=actionUserDao.findUserByAccountAndPassword(account, password);
//		User user=actionUserDao.findUserByAccountAndPassword(account, md5pwd);
		//3.�жϲ��ҵ��û��Ƿ����
		if(user==null){
			return SverResponse.createByErrorMessage("�������");
		}
		//�ÿ�����
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��¼�ɹ�",user);
	}
	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<String> doRegister(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<String> checkValidation(String str, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<User> findUserByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<String> resetPassword(Integer userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
