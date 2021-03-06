package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAddrService;
import cn.techaction.utils.ConstUtil;

@Controller
@RequestMapping("/addr")
public class ActionAddressController {
	@Autowired
		private ActionAddrService aAddrService;
		/**
		 * 新增地址
		 * @param session
		 * @param addr
		 * @return
		 */
		@RequestMapping(value="/saveaddr.do",method=RequestMethod.POST)
		@ResponseBody
		public SverResponse<List<ActionAddress>> saveAddress(HttpSession session,ActionAddress addr){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("请登录后进行操作");
			}
			addr.setUid(user.getId());
			//判断是修改还是添加
			SverResponse<String> result=null;
			if(addr.getId()==null){
				result=aAddrService.addAddress(addr);
			}else{
				result =aAddrService.updateAddress(addr);
			}
			//添加成功，返回当前用户所有地址
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		
		/**
		 * 删除地址
		 * @param session
		 * @param id
		 * @return
		 */
		@RequestMapping("/deladdr.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> deleteAddress(HttpSession session,Integer id){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("请登录后进行操作");
			}
			//隐形删除地址
			SverResponse<String> result=aAddrService.delAddress(user.getId(),id);
			//删除成功后返回当前用户所有地址
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		
		/**
		 * 设置默认地址
		 * @param session
		 * @param id
		 * @return
		 */
		@RequestMapping("/setdefault.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> setDefault(HttpSession session,Integer id){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("请登录后进行操作");
			}
			SverResponse<String> result= aAddrService.updateAddrDedaultStatus(user.getId(),id);
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		/**
		 * 查找登录用户
		 * @param session
		 * @return
		 */
		@RequestMapping("/findaddrs.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> findAddrs(HttpSession session){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("请登录后进行操作");
			}
			return aAddrService.findAddrsByUserId(user.getId());
		}
		
		@RequestMapping("/findAddressById.do")
		@ResponseBody
		public SverResponse<ActionAddress> findAddressById(HttpSession session,Integer id){
		    User user = (User)session.getAttribute(ConstUtil.CUR_USER);
	        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aAddrService.getAddressById(id);
	    }
}
