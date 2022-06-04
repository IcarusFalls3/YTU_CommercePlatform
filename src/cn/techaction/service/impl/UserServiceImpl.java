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
		//1���ж��û����Ƿ����
		int rs=userDao.checkUserByAccount(account);
		if(rs==0){
			return SverResponse.createByErrorMessage("�û��������ڣ�");
		}
		//2�������û���������ȥ�����û�
		String md5pwd=MD5Util.MD5Encode(password, "utf-8", false);
		User user=userDao.findUserByAccountAndPassword(account, md5pwd);
		
		//3���ж��û��Ƿ����
		if(user==null){
			return SverResponse.createByErrorMessage("�������");
		}
		//�ÿ�����
		user.setPassword(StringUtils.EMPTY);
		return SverResponse.createRespBySuccess("��½�ɹ�", user);
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
		//1.ȥ����Dao��ķ���
		List<User> users=userDao.findAllUsers();
		//2.���� ��User����ת����ActionUserVO����
		for(User u:users){
			vos.add(setNormalProperty(u));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * ��userת����actionuservo
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
			vo.setSex("��");
		}else {
			vo.setSex("Ů");
		}
		return vo;
	}
	@Override
	public SverResponse<ActionUserVo> findUser(Integer id) {
		// TODO Auto-generated method stub
		//1.����dao��ķ���
		User user=userDao.findUserById(id);
		//2.��Userת����action user vo
		ActionUserVo vo=setNormalProperty(user);
		return SverResponse.createRespBySuccess(vo);
	}
	@Override
	public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
		// TODO Auto-generated method stub
		//1.����id���User����
		User user =userDao.findUserById(userVo.getId());
		//2.��userVo���޸ĵ�����ֵ��ֵ��user����
		user.setAccount(userVo.getAccount());
		user.setAge(userVo.getAge());
		user.setEmail(userVo.getEmail());
		user.setName(userVo.getName());
		user.setPhone(userVo.getPhone());
		if (userVo.getSex().equals("��")) {
			user.setSex(1);
		}else {
			user.setSex(0);
		} 
		user.setUpdate_time(new Date());//����Ҫ�ı�ʱ��
		//3.����dao�㷽��
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("�û���Ϣ�޸ĳɹ���",user);
		}
		return SverResponse.createByErrorMessage("�û���Ϣ�޸�ʧ�ܣ�");
	}
	
	@Override
	public SverResponse<String> delUser(Integer id) {
		// TODO Auto-generated method stub
		//1.�ж��û��Ƿ��ж��������û�ж����Ϳ���ɾ��
		if(orderDao.findOrderByUid(id).size()>0){
			return SverResponse.createRespBySuccessMessage("�û����ڹ����������޷�ɾ����");
		}
		//2.ɾ���û���ʵ�����޸��û���del��ֵ
		User user=userDao.findUserById(id);
		user.setDel(1);
		user.setUpdate_time(new Date());
		int rs=userDao.updateUserInfo(user);
		if(rs>0){
			return SverResponse.createRespBySuccess("�û�ɾ���ɹ���");
		}
		return SverResponse.createByErrorMessage("�û�ɾ��ʧ�ܣ�");
	}

}
