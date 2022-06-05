package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

import java.security.PublicKey;
import java.util.List;

public interface ActionOrderService {
    //查询订单信息
    public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo);

    public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);


    /**
     * 订单分页列表
     * @param uid
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    public SverResponse<PageBean<ActionOrderVo>>  finOrders(Integer uid, Integer status, int pageNum, int pageSize);

    /**
     * 取消订单
     * @param uid
     * @param orderNo
     * @return
     */
    public SverResponse<String> cancelOrder(Integer uid, Long orderNo);

    /**
     * 跟剧编号获取订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    public SverResponse<ActionOrderVo> findOrderDetails(Integer uid, Long orderNo);

    /**
     * 生成订单
     * @param uid
     * @param addrId
     * @return
     */
    public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId);
    SverResponse<PageBean<ActionOrderVo>> mgrOrders(int var1, int var2);

    SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long var1);

   

    SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long var1, int var2, int var3);

    SverResponse<String> deliverGoods(Long var1);

}
