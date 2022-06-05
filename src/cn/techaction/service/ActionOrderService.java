package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

import java.security.PublicKey;
import java.util.List;

public interface ActionOrderService {
    //��ѯ������Ϣ
    public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo);

    public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);


    /**
     * ������ҳ�б�
     * @param uid
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    public SverResponse<PageBean<ActionOrderVo>>  finOrders(Integer uid, Integer status, int pageNum, int pageSize);

    /**
     * ȡ������
     * @param uid
     * @param orderNo
     * @return
     */
    public SverResponse<String> cancelOrder(Integer uid, Long orderNo);

    /**
     * �����Ż�ȡ��������
     * @param uid
     * @param orderNo
     * @return
     */
    public SverResponse<ActionOrderVo> findOrderDetails(Integer uid, Long orderNo);

    /**
     * ���ɶ���
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
