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

    //��ѯ������Ϣ
    @Override
    public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo) {
        List<ActionOrder> orders=actionOrderDao.searchOrders(orderNo);

        List<ActionOrderVo> vos= Lists.newArrayList();
        for (ActionOrder temp:orders){
            //ת����vo
            vos.add(this.createOrderVo(temp,true));
        }
        return null;
    }


    //��orderת����vo����
    private ActionOrderVo createOrderVo(ActionOrder order,boolean hasAdress){
        ActionOrderVo orderVo=new ActionOrderVo();
        //������ͨ����
        this.setNormalProperty(order,orderVo);
        //���õ�ַ
        this.setAddressProperty(order,orderVo,hasAdress);
        //���ö�������
        
        List<ActionOrderItem> orderItems=actionOrderItemDao.getItemsByOrderNo(order.getOrderNo());
        List<ActionOrderItemVo> vos=Lists.newArrayList();
        for (ActionOrderItem item:orderItems){
            vos.add(this.createOrderItemVo(item));
        }
        orderVo.setOrderItems(vos);
        return orderVo;
    }

    /*��װvo*/
    private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
        ActionOrderVo orderVo = new ActionOrderVo();
        setNormalProperty(order,orderVo);
        setAddressProperty(order,orderVo,hasAddress);
        //���ö�����
        List<ActionOrderItem> orderItems = actionOrderItemDao.getItemsByOrderNo(order.getOrderNo());
        setOrderItemProperty(orderItems,orderVo);
        return orderVo;
    }
    //��װ����Vo
    private ActionOrderVo createOrderVo2(ActionOrder order, List<ActionOrderItem> orderItems) {
        ActionOrderVo orderVo = new ActionOrderVo();
        setNormalProperty(order,orderVo);
        setAddressProperty(order,orderVo,true);
        //���ö�����
        setOrderItemProperty(orderItems,orderVo);
        return orderVo;
    }

    //ת������ͨ����
    private void setNormalProperty(ActionOrder order,ActionOrderVo orderVo){
    	orderVo.setOrderNo(order.getOrderNo());
        
        orderVo.setAmount(order.getAmmount());
        orderVo.setType(order.getType());
        orderVo.setTypeDesc(ConstUtil.PaymentType.getTypeDesc(order.getType()));
        orderVo.setFreight(order.getFreight());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
        orderVo.setAddrId(order.getAddrid());
        //ʱ��
        orderVo.setPaymentTime(DateUtils.date2Str(order.getPayment_time()));
        orderVo.setDeliveryName(DateUtils.date2Str(order.getDelivery_time()));
        orderVo.setFinishTime(DateUtils.date2Str(order.getFinish_time()));
        orderVo.setCloseTime(DateUtils.date2Str(order.getClose_time()));
        orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
    }

    private void setAddressProperty(ActionOrder order,ActionOrderVo orderVo,boolean hasAddress){
        //���ݵ�ַid��õ�ַ����
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
    //����ַת����vo
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
    //��orderItemת����orderItemVo
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
        //�������ж�
        if (orderNo==null){
            return SverResponse.createByErrorMessage("��������");
        }
        //����dao�㷽�������ݶ����Ż�ö���ActionOrder
        ActionOrder order=actionOrderDao.findOrderDetailByNo(orderNo);
        if (order==null){
            return SverResponse.createByErrorMessage("���û��Ķ��������ڣ�");
        }

        //��order����ת����orderVo����
        
        ActionOrderVo orderVo=this.createOrderVo(order,true);
        return SverResponse.createRespBySuccess(orderVo);
    }

    @Override
    public SverResponse<PageBean<ActionOrderVo>> finOrders(Integer uid, Integer status, int pageNum, int pageSize) {
        //�ж�uid�Ƿ�Ϊ��
        if(uid == null){
            return SverResponse.createByErrorMessage("��������");
        }
        //���ҷ����������ܼ�¼��
        int totalRecord = actionOrderDao.getTotalRecord(uid,status);

        //������ҳ��װ����
        PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum,pageSize,totalRecord);

        //��ȡ����
        List<ActionOrder> orders =actionOrderDao.findOrders(uid,status,pageBean.getStartIndex(),pageSize);

        //��װvo
        List<ActionOrderVo> voList = Lists.newArrayList();
        for (ActionOrder order:orders){
            voList.add(createOrderVo1(order,false));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }

    /**
     * ��װ����������
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
        //��ѯ����
        ActionOrder order = actionOrderDao.findOrderByUserAndOrderNo(uid,orderNo);
        //�ж϶����Ƿ����
        if (order==null){
            return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
        }
        //�ж϶����Ƿ񸶿�
        if (order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID){
            return SverResponse.createByErrorMessage("�ö����Ѿ�����޷�ȡ����");
        }
        //�ж�״̬�޸Ķ�����Ϣ
        ActionOrder updateOrder =new ActionOrder();
        updateOrder.setId(order.getId());
        updateOrder.setUpdated(new Date());
        if (order.getStatus() == 1){
            updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
            int row = actionOrderDao.updateOrder(updateOrder);
            if (row>0){
                return SverResponse.createRespBySuccessMessage("�����Ѿ�ȡ����");
            }
        }
        if (order.getStatus() == 3){
            updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_SUCCESS);
            int row = actionOrderDao.updateOrder(updateOrder);
            if (row>0){
                return SverResponse.createRespBySuccessMessage("�����Ѿ�ȷ���ջ���");
            }
        }
        return SverResponse.createByErrorMessage("ʧ�ܣ�");
    }

    @Override
    public SverResponse<ActionOrderVo> findOrderDetails(Integer uid, Long orderNo) {
        //�жϲ����Ƿ���ȷ
        if (uid ==null || orderNo ==null){
            return SverResponse.createByErrorMessage("��������");
        }
        //���Ҷ���,��װ
        ActionOrder order = actionOrderDao.findOrderByUserAndOrderNo(uid,orderNo);
        if (order==null){
            return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
        }
        ActionOrderVo orderVo = createOrderVo1(order,true);
        return SverResponse.createRespBySuccess(orderVo);
    }

    @Override
    public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId) {
        //��ȡ���ﳵ����Ʒ��Ϣ
        List<ActionCart> carts = aCartDao.findCartByUser(uid);
        //���㹺�ﳵ��ÿ����Ʒ�ܼ۸����ɶ�����
        SverResponse resp = this.cart2OrderItem(uid,carts);
        if (!resp.isSuccess()){
            return resp;
        }

        //ȡ���������еļ۸���㶩���ܼ۸�
        List<ActionOrderItem> orderItems = (List<ActionOrderItem>) resp.getData();
        BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);

        //���ɶ�������������
        ActionOrder order = saveOrder(uid,addrId,totalPrice);
        if (order ==null){
            return SverResponse.createByErrorMessage("����������������������ύ��");
        }
        if (CollectionUtils.isEmpty(orderItems)){
            return SverResponse.createByErrorMessage("������Ϊ�գ���ѡ��Ҫ�������Ʒ��");
        }

        //�������붩����
        for (ActionOrderItem orderItem:orderItems){
            //Ϊ���������ö�������
            orderItem.setOrder_no(order.getOrderNo());
        }
        actionOrderItemDao.batchInsert(orderItems);
        //������Ʒ���п��
        for (ActionOrderItem orderItem:orderItems){
            ActionProduct product = aProductDao.findProductById(orderItem.getGoods_id());
            //���ٿ��
            product.setStock(product.getStock() - orderItem.getQuantity());
            product.setUpdated(new Date());
            //���¿��
            aProductDao.updateProduct(product);
        }
        //��չ��ﳵ
        aProductDao.deleteCartProduct(uid);
        //��װ����ǰ��
        ActionOrderVo orderVo = createOrderVo2(order,orderItems);
        return SverResponse.createRespBySuccess(orderVo);
    }

    /**
     * ���涩��
     * @param uid
     * @param addrId
     * @param totalPrice
     * @return
     */
    private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
        ActionOrder order =new ActionOrder();
        //���ɶ�����
        long currentTime = System.currentTimeMillis();
        Long orderNo = currentTime + new Random().nextInt(100);
        order.setOrderNo(orderNo);
        order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY);//Ĭ��δ����
        order.setType(ConstUtil.PaymentType.PAY_ON_LINE);//����֧��
        order.setFreight(0);
        order.setAmmount(totalPrice);
        order.setAddrid(addrId);
        order.setUid(uid);
        order.setUpdated(new Date());
        order.setCreated(new Date());
        //���붩��
        int rs = actionOrderDao.insertOrder(order);
        if (rs > 0 ){
            return order;
        }
        return null;
    }

    /**
     * ���㶩���ܼ۸�
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
     * �����ﳵ����Ʒ��װΪ������
     * @param uid
     * @param carts
     * @return
     */
    private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
        List<ActionOrderItem> items = Lists.newArrayList();
        //�жϹ��ﳵ�Ƿ�Ϊ��
        if (CollectionUtils.isEmpty(carts)){
            return SverResponse.createByErrorMessage("���ﳵΪ�գ���ѡ��Ҫ�������Ʒ");
        }
        for (ActionCart cart:carts){
            //�鿴���ﳵ����Ʒ״̬
            ActionProduct product = aProductDao.findProductById(cart.getProductId());
            //�鿴��Ʒ״̬
            if(ConstUtil.ProductStatus.STATUS_ON_SALE != product.getStatus()){
                //�����Ʒ�����ϼ����ۣ��򷵻���ʾ��Ϣ
                return SverResponse.createByErrorMessage("��Ʒ" + product.getName() + "�Ѿ��¼ܣ��������߹���");
            }
            //�鿴���
            if (cart.getQuantity() >product.getStock()){
                return SverResponse.createByErrorMessage("��Ʒ" + product.getName() + "��治�㣡");
            }
            //��װ������
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
            return SverResponse.createByErrorMessage("��������");
        } else {
            ActionOrder order = this.actionOrderDao.findOrderDetailByNo(orderNo);
            if (order == null) {
                return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
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
            return SverResponse.createByErrorMessage("��������");
        } else {
            ActionOrder order = this.actionOrderDao.findOrderDetailByNo(orderNo);
            if (order != null) {
                if (order.getStatus() == 2) {
                    order.setStatus(3);
                    order.setDelivery_time(new Date());
                    order.setUpdated(new Date());
                    this.actionOrderDao.updateOrder(order);
                    return SverResponse.createRespBySuccessMessage("�ö��������ɹ���");
                } else {
                    return SverResponse.createRespBySuccessMessage("�ö�����δ������ܷ�����");
                }
            } else {
                return SverResponse.createByErrorMessage("�ö���������");
            }
        }
    }
}
