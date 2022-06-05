package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionCart;

public interface ActionCartDao {
    /**
      * �����û����ﳵ�е���Ʒ��Ϣ
     * @param uid
     * @return
     */
	public List<ActionCart> findCartByUser(Integer uid);
	/**
	 * �����û�id�Ͳ�Ʒid��ѯ���ﳵ
	 * @param userId
	 * @param productId
	 * @return
	 */
	public ActionCart findCartByUserAndProductId(Integer userId,Integer productId);
	/**
	 * ���湺�ﳵ
	 * @param cart
	 */
	public int insertCart(ActionCart cart);
	/**
	 * ���¹��ﳵ�е���Ʒ����
	 * @param actionCart
	 * @return
	 */
	public int updateCartById(ActionCart actionCart);
	/**
	 * ɾ��ĳ���û����ﳵ���е�������Ʒ
	 * @param userId
	 * @return
	 */
	public int deleteCartByUserId(Integer userId);
	/**
	 * ���¹��ﳵ���е���Ʒ����
	 * @param actionCart
	 * @return
	 */
	public int updateCartByUserIdAndProductId(ActionCart actionCart);
	/**
	 * ɾ�����ﳵ�е���Ʒ��Ϣ
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int deleteCarts(Integer userId,Integer productId);
	/**
	 * ��ȡ��ǰ�û����ﳵ�е���Ʒ����
	 * @param userId
	 * @return
	 */
	public int getCartCountByUserId(Integer userId);

}
