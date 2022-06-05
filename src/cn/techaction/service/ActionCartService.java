package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {
    /**
      * ������Ʒ��Ϣ�����ﳵ
     * @param id
     * @param productId
     * @param count
     * @return
     */
	SverResponse<String> saveOrUpdate(Integer id, Integer productId, Integer count);
	/**
	 * ��ѯ�û����ﳵ����Ʒ��Ϣ
	 * @param id
	 * @return
	 */

	SverResponse<ActionCartVo> findAllCarts(Integer  userId);
	/**
	 * ��չ��ﳵ
	 * @param id
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	/**
	 * ���¹��ﳵ�е���Ʒ����
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId,Integer productId,Integer count,Integer checked);
	/**
	 * ɾ�����ﳵ���е���Ʒ��Ϣ
	 * @param userId
	 * @param producId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId,Integer productId);
	/**
	 * ��ȡ��¼�û����ﳵ����Ʒ�ĸ���
	 * @param id
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);

}
