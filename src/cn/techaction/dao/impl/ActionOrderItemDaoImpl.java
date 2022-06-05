package cn.techaction.dao.impl;

import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionOrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
@Repository
public class ActionOrderItemDaoImpl implements ActionOrderItemDao {
    @Autowired
    private QueryRunner queryRunner;
    @Override
    public List<ActionOrderItem> getItemsByOrderNo(Long orderNo) {
        String sql="select * from action_order_items where order_no=?";
        try{
            return queryRunner.query(sql,new BeanListHandler<ActionOrderItem>(ActionOrderItem.class),orderNo);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int[] batchInsert(List<ActionOrderItem> orderItems) {
        String sql = "insert into action_order_items(uid,order_no,goods_id," +
                "goods_name,icon_url,price,quantity,total_price,created,updated) values(?,?,?,?,?,?,?,?,?,?)";
        Object[][] params = new Object[orderItems.size()][];

        for (int i=0;i<orderItems.size();i++){
            ActionOrderItem item =orderItems.get(i);
            params[i] = new Object[]{
                    item.getUid(),item.getOrder_no(),item.getGoods_id(),item.getGoods_name(),
                    item.getIcon_url(),item.getPrice(),item.getQuantity(),item.getTotal_price(),
                    item.getCreated(),item.getUpdated()
            };
        }
        try {
            return queryRunner.batch(sql,params);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
