package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;

public interface UserService {
	public SverResponse<User> doLogin(String account,String password );
	
	/**
	 * 判断是否为管理员
	 * @param user
	 * @return
	 */
	public SverResponse<String> isAdmin(User user);
	
	/**
	 * 获得所有用户的信息
	 * @return
	 */
	public SverResponse<List<ActionUserVo>> findUserList();
	/**
	 * 根据用户id获取用户对象
	 * @param id
	 * @return
	 */
	public SverResponse<ActionUserVo> findUser(Integer id);
	/**
	 * 更新用户信息
	 * @param userVo
	 * @return
	 */
	public SverResponse<User> updateUserInfo(ActionUserVo userVo);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public SverResponse<String> delUser(Integer id);
	
}
