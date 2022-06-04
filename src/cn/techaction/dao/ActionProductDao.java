package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 * ����������Ʒ
	 * @param num
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	
	/**
	 * ���ݲ�Ʒ���Ͳ��ѯ��Ʒ��Ϣ
	 * @param typeHntjx
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);
	
	/**
	 * ������Ʒ��Ų�ѯ��Ʒ��Ϣ
	 * @param productId
	 * @return
	 */
	public ActionProduct findProductById(Integer id);
	
	/**
	 * ����������ѯ�ܼ�¼��
	 * @param product
	 * @return
	 */
	public Integer getTotalCount(ActionProduct product);
	
	/**
	 * ����������ҳ��ѯ
	 * @param product
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize);
	
	//����������ò�ѯ��Ʒ������
	public Integer getTotalCount(Integer productId,Integer partsId);

	public List<ActionProduct> findProductByInfo(Integer productId,Integer partsId,
		Integer startIndex, Integer pageSize);
	/**
	 * ��������ѯ��Ʒ��Ϣ
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct condition);

	/**
	 * �޸���Ʒ
	 * @param product
	 * @return
	 */
	public int updateProduct(ActionProduct product);
	
	/**
	 * ������Ʒ
	 * @param product
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	
	/**
	 * ����������Ͳ�����Ʒ��Ϣ
	 * @param partsId
	 * @return
	 */
	public List<ActionProduct> findProductsByPartsId(Integer partsId);
	
	 /**
     * ɾ��ĳ���û����ﳵ��������Ʒ
     * @param uid
     * @return
     */
    public int deleteCartProduct(Integer uid);
}
