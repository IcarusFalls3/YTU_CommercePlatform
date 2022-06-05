//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.controller;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.service.UserService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/mgr/order"})
public class ActionOrderBackController {
    @Autowired
    private ActionOrderService aOrderService;
    @Autowired
    private UserService userService;

    public ActionOrderBackController() {
    }

    @RequestMapping({"/findorders.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionOrderVo>> getOrders(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);                    
            return response.isSuccess() ? this.aOrderService.mgrOrders(pageNum, pageSize) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findorders_nopages.do"})
    @ResponseBody
    public SverResponse<List<ActionOrderVo>> findOrders(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aOrderService.findOrdersForNoPages(orderNo) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/getdetail.do"})
    @ResponseBody
    public SverResponse<ActionOrderVo> getDetail(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aOrderService.mgrDetail(orderNo) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/search.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionOrderVo>> searchOrder(HttpSession session, Long orderNo, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aOrderService.searchOrderByNo(orderNo, pageNum, pageSize) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/delivergoods.do"})
    @ResponseBody
    public SverResponse<String> delivergoods(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请管理员登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aOrderService.deliverGoods(orderNo) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }
}
