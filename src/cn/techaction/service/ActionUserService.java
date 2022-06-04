package cn.techaction.service;


import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface ActionUserService {
	/**
	 * �û���¼
	 * @param account
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String account,String password);
	//�ж��ǲ��ǹ���Ա
//	public SverResponse<String> isAdmin(User user);
//	//������е��û���Ϣ
//	public SverResponse<List<ActionUserVo>> findUserList();
//	//�����û�id����û�����
//	public SverResponse<ActionUserVo> findUser(Integer id);
	/**
	 * �����û���Ϣ
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	//ɾ���û�
//	public SverResponse<String> delUsers(Integer id);
	//�û�ע��
	public SverResponse<String> doRegister(User user);
	/**
	 * ��ϢУ����֤
	 * @param str
	 * @param type
	 * @return
	 */
	public SverResponse<String> checkValidation(String str,String type);
	/**
	 * �����û�������û�����
	 * @param account
	 * @return
	 */
	public SverResponse<User> findUserByAccount(String account);
	/**
	 * У���û������
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public SverResponse<String> checkUserAnswer(String account, String question, String asw);
	/**
	 * ��������
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	public SverResponse<String> resetPassword(Integer userId, String password);
	/**
	 * ��������
	 * @param user
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword);
}
