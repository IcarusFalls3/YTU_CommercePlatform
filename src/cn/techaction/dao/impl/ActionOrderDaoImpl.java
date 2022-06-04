package cn.techaction.dao.impl;

import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.ActionOrder;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Repository
public class ActionOrderDaoImpl implements ActionOrderDao {

	@Resource
    private QueryRunner queryRunner;

    private String alias="id,order_no as orderNo,uid,addr_id as addrId,amount as ammount,type,"
            + "freight,status,payment_time as paymentTime,delivery_time as deliveryTime,finish_time as "
            + "finishTime,close_time as closeTime,updated,created";
    @Override
    public List<ActionOrder> findOrderByUid(Integer uid) {
//        String sql="select * from action_orders where uid=?";
        String sql="select " + this.alias +" from action_orders where uid=?";
        try {
            return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class),uid);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ActionOrder> searchOrders(Long orderNo) {
        String sql="select " + this.alias +" from action_orders where 1=1";

        try{
            if(orderNo!=null){
                sql+=" and order_no=? order by created";
                return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class),orderNo);
            }
            return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActionOrder findOrderDetailByNo(Long orderNo) {
    	 String sql = "select " + this.alias +" from action_orders where order_no = ?";
        try{
            return queryRunner.query(sql,new BeanHandler<ActionOrder>(ActionOrder.class),orderNo);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getTotalRecord(Integer uid, Integer status) {
        String sql = "select count(id) as num from action_orders where uid = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        if (status != 0){
            sql+=" and status = ?";
            params.add(status);
        }
        try {
            return queryRunner.query(sql,new ColumnListHandler<Long>("num"),params.toArray()).get(0).intValue();
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ActionOrder> findOrders(Integer uid, Integer status, int startIndex, int pageSize) {
        String sql = "select " + this.alias +" from action_orders where uid = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        if (status != 0){
            sql+=" and status = ?";
            params.add(status);
        }
        sql+=" limit ?,?";
        params.add(startIndex);
        params.add(pageSize);

        try {
            return queryRunner.query(sql,new BeanListHandler<ActionOrder>(ActionOrder.class),params.toArray());
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo) {
        String sql = "select " + this.alias + " from action_orders where uid = ? and order_no = ?";
        try {
            return queryRunner.query(sql,new BeanHandler<ActionOrder>(ActionOrder.class),uid,orderNo);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateOrder(ActionOrder updateOrder) {
        String sql = "update action_orders set updated = ?";
        List<Object> params = new ArrayList<>();
        params.add(updateOrder.getUpdated());

        if (updateOrder.getStatus() != null){
            sql+=" ,status = ?";
            params.add(updateOrder.getStatus());
        }
        if (updateOrder.getPayment_time() != null){
            sql+=" ,payment_time = ?";
            params.add(updateOrder.getPayment_time());
        }
        if (updateOrder.getDelivery_time() != null){
            sql+=" ,delivery_time = ?";
            params.add(updateOrder.getDelivery_time());
        }
        if (updateOrder.getFinish_time() != null){
            sql+=" ,finish_time = ?";
            params.add(updateOrder.getFinish_time());
        }
        if (updateOrder.getClose_time() != null){
            sql+=" ,close_time = ?";
            params.add(updateOrder.getClose_time());
        }
        sql+=" where id = ?";
        params.add(updateOrder.getId());

        try {
            return queryRunner.update(sql,params.toArray());
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int insertOrder(ActionOrder order) {
        String sql = "insert into action_orders(order_no,uid,addr_id,amount,type,freight,status" +
                " ,payment_time,delivery_time,finish_time,close_time,updated,created)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {order.getOrderNo(),order.getUid(),order.getAddrid(),order.getAmmount(),order.getType(),
                order.getFreight(),order.getStatus(),order.getPayment_time(),order.getDelivery_time(),
                order.getFinish_time(),order.getClose_time(),order.getUpdated(),order.getCreated()};
        try {
            return queryRunner.update(sql,params);
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public int mgrTotalRecords() {
        String sql = "select count(id) as num from action_orders ";

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"))).get(0)).intValue();
        } catch (SQLException var3) {
            var3.printStackTrace();
            return 0;
        }
    }
	@Override
	 public List<ActionOrder> mgrOrders(int offset, int pageSize) {
	        String sql = "select " + this.alias + " from action_orders  limit ?,?";

	        try {
	            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class), new Object[]{offset, pageSize});
	        } catch (SQLException var5) {
	            var5.printStackTrace();
	            return null;
	        }
	    

}
}
