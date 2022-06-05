package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.*;
import cn.techaction.pojo.*;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.DateUtils;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionAddressVo;
import cn.techaction.vo.ActionOrderItemVo;
import cn.techaction.vo.ActionOrderVo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class ActionOrderServiceImpl implements ActionOrderService {
    @Autowired
    private ActionOrderDao actionOrderDao;
    @Autowired
    private ActionAddressDao actionAddressDao;
    @Autowired
    private ActionOrderItemDao actionOrderItemDao;
    @Autowired
    private ActionCartDao aCartDao;
    @Autowired
    private ActionProductDao aProductDao;

    //查询订单信息
    @Override
    public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo) {
        List<ActionOrder> orders=actionOrderDao.searchOrders(orderNo);

        List<ActionOrderVo> vos= Lists.newArrayList();
        for (ActionOrder temp:orders){
            //转换成vo
            vos.add(this.createOrderVo(temp,true));
        }
        return null;
    }


    //将order转换成vo对象
    private ActionOrderVo createOrderVo(ActionOrder order,boolean hasAdress){
        ActionOrderVo orderVo=new ActionOrderVo();
        //设置普通属性
        this.setNormalProperty(order,orderVo);
        //设置地址
        this.setAddressProperty(order,orderVo,hasAdress);
        //设置订单详情
        
        List<ActionOrderItem> orderItems=actionOrderItemDao.getItemsByOrderNo(order.getOrderNo());
        List<ActionOrderItemVo> vos=Lists.newArrayList();
        for (ActionOrderItem item:orderItems){
            vos.add(this.createOrderItemVo(item));
        }
        orderVo.setOrderItems(vos);
        return orderVo;
    }

    /*封装vo*/
    private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
        ActionOrderVo orderVo = new ActionOrderVo();
        setNormalProperty(order,orderVo);
        setAddressProperty(order,orderVo,hasAddress);
        //设置订单项
        List<ActionOrderItem> orderItems = actionOrderItemDao.getItemsByOrderNo(order.getOrderNo());
        setOrderItemProperty(orderItems,orderVo);
        return orderVo;
    }
    //封装订单Vo
    private ActionOrderVo createOrderVo2(ActionOrder order, List<ActionOrderItem> orderItems) {
        ActionOrderVo orderVo = new ActionOrderVo();
        setNormalProperty(order,orderVo);
        setAddressProperty(order,orderVo,true);
        //设置订单项
        setOrderItemProperty(orderItems,orderVo);
        return orderVo;
    }

    //转换成普通属性
    private void setNormalProperty(ActionOrder order,ActionOrderVo orderVo){
    	orderVo.setOrderNo(order.getOrderNo());
        
        orderVo.setAmount(order.getAmmount());
        orderVo.setType(order.getType());
        orderVo.setTypeDesc(ConstUtil.PaymentType.getTypeDesc(order.getType()));
        orderVo.setFreight(order.getFreight());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
        orderVo.setAddrId(order.getAddrid());
        //时间
        orderVo.setPaymentTime(DateUtils.date2Str(order.getPayment_time()));
        orderVo.setDeliveryName(DateUtils.date2Str(order.getDelivery_time()));
        orderVo.setFinishTime(DateUtils.date2Str(order.getFinish_time()));
        orderVo.setCloseTime(DateUtils.date2Str(order.getClose_time()));
        orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
    }

    private void setAddressProperty(ActionOrder order,ActionOrderVo orderVo,boolean hasAddress){
        //根据地址id获得地址对象
        ActionAddress address=actionAddressDao.findAddrById(order.getAddrid());
        if (address!=null){
            orderVo.setDeliveryName(address.getName());
            if (hasAddress){
                orderVo.setAddress(this.createAddressVo(address));
            }else {
                orderVo.setAddress(null);
            }
        }
    }
    //将地址转换成vo
    private ActionAddressVo createAddressVo(ActionAddress addr){
        ActionAddressVo vo=new ActionAddressVo();
        vo.setAddr(addr.getAddr());
        vo.setCity(addr.getCity());
        vo.setDistrict(addr.getDistrict());
        vo.setMobile(addr.getMobile());
        vo.setName(addr.getName());
        vo.setPhone(addr.getPhone());
        vo.setProvince(addr.getProvince());
        vo.setZip(addr.getZip());
        return vo;
    }
    //将orderItem转换成orderItemVo
    private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem){
        ActionOrderItemVo vo=new ActionOrderItemVo();
        vo.setOrderNo(orderItem.getOrder_no());
        vo.setGoodsId(orderItem.getGoods_id());
        vo.setGoodsName(orderItem.getGoods_name());
        vo.setIconUrl(orderItem.getIcon_url());
        vo.setCurPrice(orderItem.getPrice());
        vo.setTotalPrice(orderItem.getTotal_price());
        vo.setQuantity(orderItem.getQuantity());
        return vo;
    }

    @Override
    public SverResponse<ActionOrderVo> mgrDetail(Long orderNo) {
        //订单号判断
        if (orderNo==null){
            return SverResponse.createByErrorMessage("参数错误！");
        }
        //调用dao层方法，根据订单号获得对象ActionOrder
        ActionOrder order=actionOrderDao.findOrderDetailByNo(orderNo);
        if (order==null){
            return SverResponse.createByErrorMessage("该用户的订单不存在！");
        }

        //将order对象转换成orderVo对象
        
        ActionOrderVo orderVo=this.createOrderVo(order,true);
        return SverResponse.createRespBySuccess(orderVo);
    }

    @Override
    public SverResponse<PageBean<ActionOrderVo>> finOrders(Integer uid, Integer status, int pageNum, int pageSize) {
        //判断uid是否为空
        if(uid == null){
            return SverResponse.createByErrorMessage("参数错误！");
        }
        //查找符合条件的总记录数
        int totalRecord = actionOrderDao.getTotalRecord(uid,status);

        //创建分页封装对象
        PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum,pageSize,totalRecord);

        //读取数据
        List<ActionOrder> orders =actionOrderDao.findOrders(uid,status,pageBean.getStartIndex(),pageSize);

        //封装vo
        List<ActionOrderVo> voList = Lists.newArrayList();
        for (ActionOrder order:orders){
            voList.add(createOrderVo1(order,false));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }

    /**
     * 封装订单项属性
     * @param orderItems
     * @param orderVo
     */
    private void setOrderItemProperty(List<ActionOrderItem> orderItems, ActionOrderVo orderVo) {
        List<ActionOrderItemVo> items = Lists.newArrayList();
        for(ActionOrderItem orderItem:orderItems){
            items.add(createOrderItemVo(orderItem));
        }
        orderVo.setOrderItems(items);
    }

    @Override
    public SverResponse<String> cancelOrder(Integer uid, Long orderNo) {
        //查询订单
        ActionOrder order = actionOrderDao.findOrderByUserAndOrderNo(uid,orderNo);
        //判断订单是否存在
        if (order==null){
            return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
        }
        //判断订单是否付款
        if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID){
            return SverResponse.createByErrorMessage("该订单已经付款，无法取消！");
        }
        //判断状态修改订单信息
        ActionOrder updateOrder =new ActionOrder();
        updateOrder.setId(order.getId());
        updateOrder.setUpdated(new Date());
        if (order.getStatus() == 1){
            updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
            int row = actionOrderDao.updateOrder(updateOrder);
            if (row>0){
                return SverResponse.createRespBySuccessMessage("订单已经取消！");
            }
        }
        if (order.getStatus() == 3){
            updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_SUCCESS);
            int row = actionOrderDao.updateOrder(updateOrder);
            if (row>0){
                return SverResponse.createRespBySuccessMessage("订单已经确认收货！");
            }
        }
        return SverResponse.createByErrorMessage("失败！");
    }

    @Override
    public SverResponse<ActionOrderVo> findOrderDetails(Integer uid, Long orderNo) {
        //判断参数是否正确
        if (uid ==null || orderNo ==null){
            return SverResponse.createByErrorMessage("参数错误！");
        }
        //查找订单,封装
        ActionOrder order = actionOrderDao.findOrderByUserAndOrderNo(uid,orderNo);
        if (order==null){
            return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
        }
        ActionOrderVo orderVo = createOrderVo1(order,true);
        return SverResponse.createRespBySuccess(orderVo);
    }

    @Override
    public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId) {
        //提取购物车中商品信息
        List<ActionCart> carts = aCartDao.findCartByUser(uid);
        //计算购物车中每件商品总价格并生成订单项
        SverResponse resp = this.cart2OrderItem(uid,carts);
        if (!resp.isSuccess()){
            return resp;
        }

        //取出订单项中的价格计算订单总价格
        List<ActionOrderItem> orderItems = (List<ActionOrderItem>) resp.getData();
        BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);

        //生成订单，插入数据
        ActionOrder order = saveOrder(uid,addrId,totalPrice);
        if (order ==null){
            return SverResponse.createByErrorMessage("订单产生错误，请检查后重新提交！");
        }
        if (CollectionUtils.isEmpty(orderItems)){
            return SverResponse.createByErrorMessage("订单项为空，请选择要购买的商品！");
        }

        //批量插入订单项
        for (ActionOrderItem orderItem:orderItems){
            //为订单项设置订单主键
            orderItem.setOrder_no(order.getOrderNo());
        }
        actionOrderItemDao.batchInsert(orderItems);
        //减少商品表中库存
        for (ActionOrderItem orderItem:orderItems){
            ActionProduct product = aProductDao.findProductById(orderItem.getGoods_id());
            //减少库存
            product.setStock(product.getStock() - orderItem.getQuantity());
            product.setUpdated(new Date());
            //更新库存
            aProductDao.updateProduct(product);
        }
        //清空购物车
        aProductDao.deleteCartProduct(uid);
        //封装返回前端
        ActionOrderVo orderVo = createOrderVo2(order,orderItems);
        return SverResponse.createRespBySuccess(orderVo);
    }

    /**
     * 保存订单
     * @param uid
     * @param addrId
     * @param totalPrice
     * @return
     */
    private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
        ActionOrder order =new ActionOrder();
        //生成订单号
        long currentTime = System.currentTimeMillis();
        Long orderNo = currentTime + new Random().nextInt(100);
        order.setOrderNo(orderNo);
        order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY);//默认未付款
        order.setType(ConstUtil.PaymentType.PAY_ON_LINE);//在线支付
        order.setFreight(0);
        order.setAmmount(totalPrice);
        order.setAddrid(addrId);
        order.setUid(uid);
        order.setUpdated(new Date());
        order.setCreated(new Date());
        //插入订单
        int rs = actionOrderDao.insertOrder(order);
        if (rs > 0 ){
            return order;
        }
        return null;
    }

    /**
     * 计算订单总价格
     * @param orderItems
     * @return
     */
    private BigDecimal calcOrderTotalPrice(List<ActionOrderItem> orderItems) {
        BigDecimal totalPrice = new BigDecimal("0");
        for (ActionOrderItem item:orderItems){
            totalPrice = CalcUtil.add(totalPrice.doubleValue(),item.getTotal_price().doubleValue());
        }
        return totalPrice;
    }

    /**
     * 将购物车中商品封装为订单项
     * @param uid
     * @param carts
     * @return
     */
    private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
        List<ActionOrderItem> items = Lists.newArrayList();
        //判断购物车是否为空
        if (CollectionUtils.isEmpty(carts)){
            return SverResponse.createByErrorMessage("购物车为空，请选择要购买的商品");
        }
        for (ActionCart cart:carts){
            //查看购物车的商品状态
            ActionProduct product = aProductDao.findProductById(cart.getProductId());
            //查看商品状态
            if(ConstUtil.ProductStatus.STATUS_ON_SALE != product.getStatus()){
                //如果商品不是上架在售，则返回提示信息
                return SverResponse.createByErrorMessage("商品" + product.getName() + "已经下架，不能在线购买！");
            }
            //查看库存
            if (cart.getQuantity() >product.getStock()){
                return SverResponse.createByErrorMessage("商品" + product.getName() + "库存不足！");
            }
            //封装订单项
            ActionOrderItem orderItem = new ActionOrderItem();
            orderItem.setUid(uid);
            orderItem.setGoods_name(product.getName());
            orderItem.setGoods_id(product.getId());
            orderItem.setIcon_url(product.getIconUrl());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotal_price(CalcUtil.mul(orderItem.getPrice().doubleValue(),orderItem.getQuantity().doubleValue()));
            orderItem.setCreated(new Date());
            orderItem.setUpdated(new Date());
            items.add(orderItem);
        }
        return SverResponse.createRespBySuccess(items);
    }
    
    @Override
    public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum, int pageSize) {
        int totalRecord = this.actionOrderDao.mgrTotalRecords();
        PageBean<ActionOrderVo> pageBean = new PageBean(pageNum, pageSize, totalRecord);
        List<ActionOrder> orders = this.actionOrderDao.mgrOrders(pageBean.getStartIndex(), pageSize);
        List<ActionOrderVo> voList = Lists.newArrayList();
        Iterator var8 = orders.iterator();

        while(var8.hasNext()) {
            ActionOrder order = (ActionOrder)var8.next();
            voList.add(this.createOrderVo1(order, false));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }
    @Override
    public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo) {
        List<ActionOrder> orders = this.actionOrderDao.searchOrders(orderNo);
        List<ActionOrderVo> voList = Lists.newArrayList();
        Iterator var5 = orders.iterator();

        while(var5.hasNext()) {
            ActionOrder order = (ActionOrder)var5.next();
            voList.add(this.createOrderVo1(order, false));
        }

        return SverResponse.createRespBySuccess(voList);
    }
 
    @Override
    public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo, int pageNum, int pageSize) {
        if (orderNo == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionOrder order = this.actionOrderDao.findOrderDetailByNo(orderNo);
            if (order == null) {
                return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
            } else {
                PageBean<ActionOrderVo> pageBean = new PageBean(pageNum, pageSize, 1);
                ActionOrderVo orderVo = this.createOrderVo1(order, true);
                List<ActionOrderVo> orders = Lists.newArrayList();
                orders.add(orderVo);
                pageBean.setData(orders);
                return SverResponse.createRespBySuccess(pageBean);
            }
        }
    }
    @Override
    public SverResponse<String> deliverGoods(Long orderNo) {
        if (orderNo == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionOrder order = this.actionOrderDao.findOrderDetailByNo(orderNo);
            if (order != null) {
                if (order.getStatus() == 2) {
                    order.setStatus(3);
                    order.setDelivery_time(new Date());
                    order.setUpdated(new Date());
                    this.actionOrderDao.updateOrder(order);
                    return SverResponse.createRespBySuccessMessage("该订单发货成功！");
                } else {
                    return SverResponse.createRespBySuccessMessage("该订单尚未付款，不能发货！");
                }
            } else {
                return SverResponse.createByErrorMessage("该订单不存在");
            }
        }
    }
}
