package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.User;

public interface ActionUserDao {
		/**
		 * 根据用户名找到用户
		 * @param acount
		 * @return
		 */
		public int checkUserByAccount(String acount);
		/**
		 * 根据用户名和密码找到用户
		 * @param account
		 * @param password
		 * @return
		 */
		public User findUserByAccountAndPassword(String account,String password);
		
//		public List<User> findAllUsers();
//		
//		public User findUserById(Integer id);
//		
//		public int updateUserInfo(User user);
//		
//		public int checkUserByEmail(String email);
//		
//		public int checkUserByPhone(String phone);
//		
//		public int insertUser(User user);
//		
//		public User findUserByAccount(String account);
//		
//		public int checkUserAnswer(String account, String question, String asw);
//		
//		public int checkPassword(String account, String password);
}
