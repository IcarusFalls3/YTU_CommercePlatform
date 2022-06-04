package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface UserService {
	public SverResponse<User> doLogin(String account,String password );
	
	/**
	 * �ж��Ƿ�Ϊ����Ա
	 * @param user
	 * @return
	 */
	public SverResponse<String> isAdmin(User user);
	
	/**
	 * ��������û�����Ϣ
	 * @return
	 */
	public SverResponse<List<ActionUserVo>> findUserList();
	/**
	 * �����û�id��ȡ�û�����
	 * @param id
	 * @return
	 */
	public SverResponse<ActionUserVo> findUser(Integer id);
	/**
	 * �����û���Ϣ
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	/**
	 * ɾ���û�
	 * @param id
	 * @return
	 */
	public SverResponse<String> delUser(Integer id);
	
}
