package cn.techaction.service;


import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface ActionUserService {
	/**
	 * 用户登录
	 * @param account
	 * @param password
	 * @return
	 */
	public SverResponse<User> doLogin(String account,String password);
	//判断是不是管理员
//	public SverResponse<String> isAdmin(User user);
//	//获得所有的用户信息
//	public SverResponse<List<ActionUserVo>> findUserList();
//	//根据用户id获得用户对象
//	public SverResponse<ActionUserVo> findUser(Integer id);
	/**
	 * 更新用户信息
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	//删除用户
//	public SverResponse<String> delUsers(Integer id);
	//用户注册
	public SverResponse<String> doRegister(User user);
	/**
	 * 信息校验验证
	 * @param str
	 * @param type
	 * @return
	 */
	public SverResponse<String> checkValidation(String str,String type);
	/**
	 * 根据用户名获得用户对象
	 * @param account
	 * @return
	 */
	public SverResponse<User> findUserByAccount(String account);
	/**
	 * 校验用户问题答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	public SverResponse<String> checkUserAnswer(String account, String question, String asw);
	/**
	 * 重置密码
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	public SverResponse<String> resetPassword(Integer userId, String password);
	/**
	 * 重设密码
	 * @param user
	 * @param newpwd
	 * @param oldpwd
	 * @return
	 */
	public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword);
}
