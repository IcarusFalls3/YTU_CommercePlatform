package cn.techaction.controller;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class ActionOrderController {
    @Autowired
    private ActionOrderService aOrderService;

    /**
     * ��ȡ�����б�
     * @param session
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getlist.do")
    @ResponseBody
    public SverResponse<PageBean<ActionOrderVo>> list(HttpSession session, Integer status,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //�ж��û��Ƿ��¼
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        if (user==null){
            return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
        }
        return aOrderService.finOrders(user.getId(),status,pageNum,pageSize);
    }

    /**
     * ȡ������
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/cancelorder.do")
    @ResponseBody
    public SverResponse<String> cancelOrder(HttpSession session,Long orderNo){
        //�ж��û��Ƿ��¼
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        if (user==null){
            return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
        }
        return aOrderService.cancelOrder(user.getId(),orderNo);
    }

    /**
     * ȷ���ջ�
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/confirmreceipt.do")
    @ResponseBody
    public SverResponse<String> confirmReceipt(HttpSession session,Long orderNo){
        //�ж��û��Ƿ��¼
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        if (user==null){
            return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
        }
        return aOrderService.cancelOrder(user.getId(),orderNo);
    }

    /**
     * ��ȡ������ϸ��Ϣ
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/getdetails.do")
    @ResponseBody
    public SverResponse<ActionOrderVo> getDetails(HttpSession session,Long orderNo){
        //�ж��û��Ƿ��¼
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        if (user==null){
            return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
        }
        return aOrderService.findOrderDetails(user.getId(),orderNo);
    }

    /**
     * ��������
     * @param session
     * @param addrId
     * @return
     */
    @RequestMapping(value = "/createorder.do",method = RequestMethod.POST)
    @ResponseBody
    public SverResponse<ActionOrderVo> createOrder(HttpSession session,Integer addrId){
        //�ж��û��Ƿ��¼
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        if (user==null){
            return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
        }
        return aOrderService.generateOrder(user.getId(),addrId);
    }

}
