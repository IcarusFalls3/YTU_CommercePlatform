package cn.techaction.controller;


import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionProductService;
import cn.techaction.service.UserService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductListVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionProductService;
import cn.techaction.service.UserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/mgr/product")
public class ActionProductBackController {
	@Autowired
	private ActionProductService actionProductService;
	@Autowired
	private UserService userService;

	/*
	 * @RequestMapping("/find_product.do")
	 * 
	 * @ResponseBody public SverResponse<PageBean<ActionProduct>>
	 * findProducts(Integer productId,Integer partsId ,Integer pageNum,Integer
	 * pageSize){ //调用Service层的方法去分页查询 return
	 * actionProductService.findProduct(productId, partsId, pageNum, pageSize);
	 * }
	 */
	
	/**
	 * 查询商品信息
	 * 
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/productlist.do")
	@ResponseBody
	public SverResponse<List<ActionProductListVo>> findProducts(
			HttpSession session, ActionProduct product) {
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorCodeMessage(
					ResponseCode.UNLOGIN.getCode(), "请登陆后，再进行操作！");
		}
		// 2.判断登录的用户是哪种类型
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用service的方法去查询产品的信息
			return actionProductService.findProducts(product);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
	}
	/**
	 * 新增商品
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/saveproduct.do")
	@ResponseBody
	public SverResponse<String> saveProduct(HttpSession session,
			ActionProduct product) {
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorCodeMessage(
					ResponseCode.UNLOGIN.getCode(), "请登陆后，再进行操作！");
		}
		// 2.判断登录的用户是哪种类型
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用service的方法:保存商品信息
			return actionProductService.saveOrupdateProduct(product);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
	}
	

	/**
	 * 修改商品状态：上下架和热销
	 * 
	 * @param session
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	@RequestMapping("/setstatus.do")
	@ResponseBody
	public SverResponse<String> modifyStatus(HttpSession session,
			Integer productId, Integer status, Integer hot) {
		// 1.判断用户是否登录
		User user = (User) session.getAttribute(ConstUtil.CUR_USER);
		if (user == null) {
			return SverResponse.createByErrorCodeMessage(
					ResponseCode.UNLOGIN.getCode(), "请登陆后，再进行操作！");
		}
		// 2.判断登录的用户是哪种类型
		SverResponse<String> response = userService.isAdmin(user);
		if (response.isSuccess()) {
			// 3.调用service的方法:更新状态信息
			return actionProductService.updateStatus(productId, status, hot);
		}
		return SverResponse.createByErrorMessage("无操作权限！");
	}
    @RequestMapping("/upload.do")     
        @ResponseBody
        public SverResponse<Map<String, String>> uploadFiles(HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) {
            User user = (User)session.getAttribute(ConstUtil.CUR_USER);
            if (user == null) {
                return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
            } else {
                SverResponse<String> response = this.userService.isAdmin(user);
                if (response.isSuccess()) {
                    String path = request.getSession().getServletContext().getRealPath("/upload/");
                    return actionProductService.uploadFile(file, path);
                } else {
                    return SverResponse.createByErrorMessage("无操作权限！");
                }
            }
        }
    
    @RequestMapping(value = "/pic_upload.do")
        @ResponseBody
        public Map<String, String> editorUploadFiles(HttpSession session, @RequestParam(value = "files",required = false) MultipartFile file, HttpServletRequest request) {
            User user = (User)session.getAttribute(ConstUtil.CUR_USER);
            if (user == null) {
                return null;
            } else {
                SverResponse<String> response = this.userService.isAdmin(user);
                Map<String, String> result = new HashMap();
                result.put("success", "false");
                if (response.isSuccess()) {
                    String path = request.getSession().getServletContext().getRealPath("/upload/");
                    SverResponse<Map<String, String>> resp = this.actionProductService.uploadFile(file, path);
                    if (resp.isSuccess()) {
                        result.put("success", "true");
                        result.put("file_path", request.getContextPath() + (String)((Map)resp.getData()).get("url"));
                    }
                }

                return result;
            }
           
    }
    
    
    
    @RequestMapping({"/searchproducts.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionProductListVo>> searchProducts(HttpSession session, ActionProduct product, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.actionProductService.findProductsByCondition(product, pageNum, pageSize) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    
    
    @RequestMapping({"/getdetail.do"})
    @ResponseBody
    public SverResponse<ActionProduct> getProductDetail(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.actionProductService.findProductDetailById(productId) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

}
