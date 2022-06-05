package cn.techaction.dao;

import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import com.google.common.collect.Lists;

import java.util.List;

public interface ActionOrderItemDao {
    //根据订单号获得订单详情
    public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);

    /**
     * 批量插入订单项
     * @param orderItems
     * @return
     */
    public int[] batchInsert(List<ActionOrderItem> orderItems);
}
