package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {
	 /**
	  * 根据账户查找用户
	  * @param acount
	  * @return
	  */
		public int checkUserByAccount(String acount);
		//根据用户名和密码查找用户
		public User findUserByAccountAndPassword(String account,String password);
		//获得所有用户信息
		public List<User> findAllUsers();
		//根据用户id获得user对象
		public User findUserById(Integer id);
		//更新用户信息
		public int updateUserInfo(User user);
		/**
		 * 验证电子邮箱是否已经被注册
		 * @param str
		 * @return
		 */
		public int checkUserByEmail(String email);
		/**
		 * 验证手机是否已经被注册
		 * @param str
		 * @return
		 */
		public int checkUserByPhone(String phone);
		/**
		 * 新增用户
		 * @param user
		 * @return
		 */
		public int insertUser(User user);
		/**
		 * 根据用户名查找用户
		 * @param account
		 * @return
		 */
		public User findUserByAccount(String account);
		/**
		 * 检查用户密码问题的答案
		 * @param account
		 * @param question
		 * @param asw
		 * @return
		 */
		public int checkUserAnswer(String account, String question, String asw);
		/**
		 * 验证用户密码是否已经存在
		 * @param account
		 * @param oldPassword
		 * @return
		 */
		public int checkPassword(String account, String password);
}
