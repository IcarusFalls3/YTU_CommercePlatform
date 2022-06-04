package cn.techaction.dao;

import cn.techaction.pojo.ActionOrder;

import java.util.List;

public interface ActionOrderDao {
    //根据用户id获得订单信息
    public List<ActionOrder> findOrderByUid(Integer uid);
    //根据订单号查询订单信息
    public List<ActionOrder> searchOrders(Long orderNo);
    //根据订单号获得订单对象
    public ActionOrder findOrderDetailByNo(Long orderNo);

    /**
     * 获取用户订单总数（各种状态下的）
     * @param uid
     * @param status
     * @return
     */
    public int getTotalRecord(Integer uid, Integer status);

    /**
     * 获取用户订单分页列表
     * @param uid
     * @param status
     * @param startIndex
     * @param pageSize
     * @return
     */
    public List<ActionOrder> findOrders(Integer uid, Integer status, int startIndex, int pageSize);

    /**
     * 根据用户和订单编号查询订单信息
     * @param uid
     * @param orderNo
     * @return
     */
    public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo);

    /**
     * 更新订单信息
     * @param updateOrder
     * @return
     */
    public int updateOrder(ActionOrder updateOrder);

    /**
     * 保存订单信息
     * @param order
     * @return
     */

 
    public int insertOrder(ActionOrder order);
	public int mgrTotalRecords();
	public List<ActionOrder> mgrOrders(int startIndex, int pageSize);
}
