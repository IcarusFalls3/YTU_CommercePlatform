package cn.techaction.service;


import cn.techaction.common.SverResponse;
//import cn.techaction.pojo.User;
//import cn.techaction.vo.ActionUserVo;
import cn.techaction.pojo.User;

public interface ActionUserService {
	public SverResponse<User> doLogin(String account,String password);
}
