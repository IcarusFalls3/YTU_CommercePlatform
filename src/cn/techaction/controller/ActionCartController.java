package cn.techaction.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionCartVo;

@Controller
@RequestMapping("/cart")

public class ActionCartController {
    @Autowired
	private ActionCartService aCartService;
    /**
      * ���빺�ﳵ
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping(value="/savecart.do",method=RequestMethod.POST)
    @ResponseBody
    public SverResponse<String> addProductCart(HttpSession session,Integer productId,Integer count){
    	User user= (User) session.getAttribute(ConstUtil.CUR_USER);
    	if(user==null)
    	{
    		return SverResponse.createByErrorMessage("���¼���ڲ鿴���ﳵ��");
    	}
    	return aCartService.saveOrUpdate(user.getId(),productId,count);
    }
    /**
      * �鿴���ﳵ
     * @param session
     * @return
     */
    @RequestMapping(value="/findallcarts.do")
    @ResponseBody
  public SverResponse<ActionCartVo> findAllCarts(HttpSession session ){
    	User user= (User) session.getAttribute(ConstUtil.CUR_USER);
    	if(user==null)
    	{
    		return SverResponse.createByErrorMessage("���¼���ڲ鿴���ﳵ��");
    	}
    	return aCartService.findAllCarts(user.getId());
  }
    
    /**
	 * ��չ��ﳵ
	 * @param session
	 * @return
	 */
	@RequestMapping("/clearcarts.do")
	@ResponseBody
	public SverResponse<String> clearCarts(HttpSession session){
		User user=(User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		//��չ��ﳵ
		return aCartService.clearCart(user.getId());
	}  
	/**
	 * ���¹��ﳵ
	 * @return
	 */
	@RequestMapping("/updatecarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> updateCarts(HttpSession session,Integer productId,Integer count,Integer checked){
		User user=(User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return aCartService.updateCart(user.getId(),productId,count,checked);
	}
	/**
	 * ɾ����Ʒ
	 * @param session
	 * @param productId
	 * @return
	 */
	@RequestMapping("/deletecarts.do")
	@ResponseBody
	public SverResponse<ActionCartVo> deleteCart(HttpSession session,Integer productId){
		User user=(User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return aCartService.deleteCart(user.getId(),productId);
	}
	/**
	 * ���ﳵ����
	 * @param session
	 * @return
	 */
	@RequestMapping("/getcartcount.do")
	@ResponseBody
	public SverResponse<Integer> getCartsCount(HttpSession session){
		User user=(User) session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorMessage("���¼���ٲ鿴���ﳵ��");
		}
		return aCartService.getCartCount(user.getId());
	}

}
