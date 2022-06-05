package cn.techaction.dao;

import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import com.google.common.collect.Lists;

import java.util.List;

public interface ActionOrderItemDao {
    //���ݶ����Ż�ö�������
    public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);

    /**
     * �������붩����
     * @param orderItems
     * @return
     */
    public int[] batchInsert(List<ActionOrderItem> orderItems);
}
