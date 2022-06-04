package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {
	 /**
	  * �����˻������û�
	  * @param acount
	  * @return
	  */
		public int checkUserByAccount(String acount);
		//�����û�������������û�
		public User findUserByAccountAndPassword(String account,String password);
		//��������û���Ϣ
		public List<User> findAllUsers();
		//�����û�id���user����
		public User findUserById(Integer id);
		//�����û���Ϣ
		public int updateUserInfo(User user);
		/**
		 * ��֤���������Ƿ��Ѿ���ע��
		 * @param str
		 * @return
		 */
		public int checkUserByEmail(String email);
		/**
		 * ��֤�ֻ��Ƿ��Ѿ���ע��
		 * @param str
		 * @return
		 */
		public int checkUserByPhone(String phone);
		/**
		 * �����û�
		 * @param user
		 * @return
		 */
		public int insertUser(User user);
		/**
		 * �����û��������û�
		 * @param account
		 * @return
		 */
		public User findUserByAccount(String account);
		/**
		 * ����û���������Ĵ�
		 * @param account
		 * @param question
		 * @param asw
		 * @return
		 */
		public int checkUserAnswer(String account, String question, String asw);
		/**
		 * ��֤�û������Ƿ��Ѿ�����
		 * @param account
		 * @param oldPassword
		 * @return
		 */
		public int checkPassword(String account, String password);
}
