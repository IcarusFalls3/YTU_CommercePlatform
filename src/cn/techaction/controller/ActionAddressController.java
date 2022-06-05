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
		 * ������ַ
		 * @param session
		 * @param addr
		 * @return
		 */
		@RequestMapping(value="/saveaddr.do",method=RequestMethod.POST)
		@ResponseBody
		public SverResponse<List<ActionAddress>> saveAddress(HttpSession session,ActionAddress addr){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("���¼����в���");
			}
			addr.setUid(user.getId());
			//�ж����޸Ļ������
			SverResponse<String> result=null;
			if(addr.getId()==null){
				result=aAddrService.addAddress(addr);
			}else{
				result =aAddrService.updateAddress(addr);
			}
			//��ӳɹ������ص�ǰ�û����е�ַ
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		
		/**
		 * ɾ����ַ
		 * @param session
		 * @param id
		 * @return
		 */
		@RequestMapping("/deladdr.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> deleteAddress(HttpSession session,Integer id){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("���¼����в���");
			}
			//����ɾ����ַ
			SverResponse<String> result=aAddrService.delAddress(user.getId(),id);
			//ɾ���ɹ��󷵻ص�ǰ�û����е�ַ
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		
		/**
		 * ����Ĭ�ϵ�ַ
		 * @param session
		 * @param id
		 * @return
		 */
		@RequestMapping("/setdefault.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> setDefault(HttpSession session,Integer id){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("���¼����в���");
			}
			SverResponse<String> result= aAddrService.updateAddrDedaultStatus(user.getId(),id);
			if(result.isSuccess()){
				return aAddrService.findAddrsByUserId(user.getId());
			}
			return SverResponse.createByErrorMessage(result.getMsg());
		}
		/**
		 * ���ҵ�¼�û�
		 * @param session
		 * @return
		 */
		@RequestMapping("/findaddrs.do")
		@ResponseBody
		public SverResponse<List<ActionAddress>> findAddrs(HttpSession session){
			User user=(User) session.getAttribute(ConstUtil.CUR_USER);
			if(user==null){
				return SverResponse.createByErrorMessage("���¼����в���");
			}
			return aAddrService.findAddrsByUserId(user.getId());
		}
		
		@RequestMapping("/findAddressById.do")
		@ResponseBody
		public SverResponse<ActionAddress> findAddressById(HttpSession session,Integer id){
		    User user = (User)session.getAttribute(ConstUtil.CUR_USER);
	        return user == null ? SverResponse.createByErrorMessage("���¼���ڽ��в�����") : this.aAddrService.getAddressById(id);
	    }
}
