package cn.techaction.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	/**
	 * �Ż�������������Ʒ
	 * @param num ���ҵ�����
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * �Ż��������ҳ����¥������
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
	/**
	 * �Ż���������Ʒ��Ų�����Ʒ��Ϣ
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * �Ż������ݲ�Ʒ���ͺ�������Ͳ�����Ʒ��Ϣ��ģ�����ң�
	 * @param productTyoeId
	 * @param partsId
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProductListVo>> findProductsForprotal(Integer productTypeId, Integer partsId,
			String name, int pageNum, int pageSize);
	
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId,Integer partsId,
			Integer pageNum,Integer pageSize);
	/**
	 * ��������ѯ��Ʒ��Ϣ
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product);
	/**
	 * ������Ʒ״̬�����¼ܣ�����
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status,Integer hot);
	/**
	 * ������Ʒ��Ϣ������or�޸ģ�
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrupdateProduct(ActionProduct product);
	
	/**
	 * �ϴ�
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path);
	
	
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize);
	
	
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
}
