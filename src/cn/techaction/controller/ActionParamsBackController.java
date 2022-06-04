package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamsService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionParamVo;

@Controller
@RequestMapping("/mgr/param")
public class ActionParamsBackController {
	@Autowired
	private ActionParamsService actionParamsService;
	@Autowired
	private UserService userService;
	
	/**
	 * 新增类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/saveparam.do")
	@ResponseBody
	public SverResponse<String> saveParam(HttpSession session, ActionParam actionParam){
		//1.判断用户是否登录
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response= userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法:新增类型
			return actionParamsService.addParam(actionParam);
		}
		return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"无操作权限！");
	}
	
	/**
	 * 修改类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/updateparam.do")
	@ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam actionParam){
		//1.判断用户是否登录
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response= userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法:修改类型
			return actionParamsService.updateParam(actionParam);
		}
		return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"无操作权限！");
	}
	
	/**
	 * 删除商品类型
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/delparam.do")
	@ResponseBody
	public SverResponse<String> delParam(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user = (User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"请登录后再进行操作！");
		}
		//2.用户是否是管理员
		SverResponse<String> response= userService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用Service中的方法:删除类型
			return actionParamsService.delParam(id);
		}
		return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"无操作权限！");
	}
	
	
	/**
	 * 查找下一级子节点(根类型)
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findchildren.do")
	@ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue="0")Integer id){
		//1.判断用户是否登录
				User user = (User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"请登录后再进行操作！");
				}
				//2.用户是否是管理员
				SverResponse<String> response= userService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用Service中的方法:获得子类型
					return actionParamsService.findParamChildren(id);
				}
				return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(),"无操作权限！");
	}
	
	@RequestMapping({"/findptype.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getParamByParentId(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.actionParamsService.findProdutTypeParams() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findpartstype.do"})
    @ResponseBody
    public SverResponse<List<ActionParamVo>> getPartsParam(HttpSession session, Integer productTypeId) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.actionParamsService.findPartsTypeParamsByProductTypeId(productTypeId) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findpathparam.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getPathParam(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.actionParamsService.findAllPathParams() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }
}
