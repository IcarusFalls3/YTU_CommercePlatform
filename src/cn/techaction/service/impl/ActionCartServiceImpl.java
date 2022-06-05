package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.vo.ActionCartVo;
import cn.techaction.vo.ActionCartsListVo;

@Service
public class ActionCartServiceImpl implements ActionCartService {
	@Autowired
	private ActionCartDao aCartDao;
	@Autowired
	private ActionProductDao aProductDao;

	@Override
	public SverResponse<String> saveOrUpdate(Integer userId, Integer productId, Integer count) {
		// ��֤�����Ƿ���ȷ
		if (userId == null || productId == null || count == null) {
			return SverResponse.createByErrorMessage("��������!");
		}
		// �鿴�û��Ĺ��ﳵ���Ƿ������Ʒ
		ActionCart actionCart = aCartDao.findCartByUserAndProductId(userId, productId);
		if (actionCart == null) {
			// ������������
			ActionCart cart = new ActionCart();
			cart.setUserId(userId);
			cart.setProductId(productId);
			cart.setQuantity(count);
			cart.setCreated(new Date());
			cart.setUpdated(new Date());
			aCartDao.insertCart(cart);
		} else {
			// ��������������
			int cartCount = actionCart.getQuantity() + count;
			actionCart.setQuantity(cartCount);
			aCartDao.updateCartById(actionCart);
		}

		return SverResponse.createRespBySuccess("��Ʒ�ѳɹ����뵽���ﳵ��");
	}

	@Override
	public SverResponse<ActionCartVo> findAllCarts(Integer userId) {
		if (userId == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		// ���Ҹ��û����ﳵ�е���Ʒ
		List<ActionCart> list = aCartDao.findCartByUser(userId);
		// ��װactioncartvo����
		ActionCartVo cartVo = createCartVo(list);
		return SverResponse.createRespBySuccess(cartVo);
	}

/**
 * ��װ���ﳵVo����
 * @param list
 * @return
 */
	private ActionCartVo createCartVo(List<ActionCart> carts) {
		ActionCartVo cartVo =new ActionCartVo();
		List<ActionCartsListVo> list =Lists.newArrayList();
		//���ﳵ��Ʒ�ܼ۸�
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(CollectionUtils.isNotEmpty(carts)) {
			for(ActionCart cart:carts ) {
				//ת������
				ActionCartsListVo listVo=new ActionCartsListVo();
				listVo.setId(cart.getId());
				listVo.setUserId(cart.getUserId());
				listVo.setProductId(cart.getProductId());
				listVo.setChecked(cart.getChecked());
				//��װ��Ʒ��Ϣ
				ActionProduct product = aProductDao.findProductById(listVo.getProductId());
				if(product!=null) {
					listVo.setName(product.getName());
					listVo.setStatus(product.getStatus());
					listVo.setPrice(product.getPrice());
					listVo.setStock(product.getStock());
					listVo.setIconUrl(product.getIconUrl());
					//�жϿ��
					int buyCount=0;
					if(product.getStock()>=cart.getQuantity()) {
						buyCount=cart.getQuantity();
					}else {
						buyCount = product.getStock();
						//���¹��ﳵ��Ʒ����
						ActionCart updateCart = new ActionCart();
						updateCart.setId(cart.getId());
						updateCart.setQuantity(buyCount);
						//����ѡ��״̬
						updateCart.setChecked(cart.getChecked());
						aCartDao.updateCartById(updateCart);
					}
					listVo.setQuantity(buyCount);
					//���㹺�ﳵ��ĳ��Ʒ�ܼ۸�
					BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
					listVo.setTotalPrice(totalPrice);
					if(cart.getChecked()==1) {
						//���㹺�ﳵѡ��״̬��Ʒ���ܼ۸�
						cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
					}
				}
				list.add(listVo);
			}
		}
		cartVo.setLists(list);
		cartVo.setTotalPrice(cartTotalPrice);
		return cartVo;
	}
	@Override
	public SverResponse<String> clearCart(Integer userId){
		//�жϲ�����ȷ
		if(userId==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//��չ��ﳵ,�ж���ȷ
		int rs=aCartDao.deleteCartByUserId(userId);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("�ɹ���չ��ﳵ��");
		}
		
		return SverResponse.createByErrorMessage("��չ��ﳵʧ�ܣ�");
	}


	@Override
	public SverResponse<ActionCartVo> updateCart(Integer userId, Integer productId, Integer count,
			Integer checked) {
		//�жϲ���
		if(userId==null || productId==null || count==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���¹��ﳵ��Ϣ
		ActionCart actionCart=new ActionCart();
		actionCart.setUserId(userId);
		actionCart.setProductId(productId);
		actionCart.setQuantity(count);
		actionCart.setChecked(checked);
		aCartDao.updateCartByUserIdAndProductId(actionCart);
		//�������й��ﳵ��Ϣ
		return findAllCarts(userId);
	}


	@Override
	public SverResponse<ActionCartVo> deleteCart(Integer userId, Integer productId) {
		//�жϲ���
		if(userId == null || productId == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//ɾ����Ʒ
		int rs=aCartDao.deleteCarts(userId,productId);
		if(rs>0) {
			//�������й��ﳵ��Ϣ
			return this.findAllCarts(userId);
		}
		return SverResponse.createByErrorMessage("ɾ����Ʒʧ�ܣ�");
	}


	@Override
	public SverResponse<Integer> getCartCount(Integer userId) {
		//�жϲ���
		if(userId == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		int count = aCartDao.getCartCountByUserId(userId);
		return SverResponse.createRespBySuccess(Integer.valueOf(count));
	}
}
