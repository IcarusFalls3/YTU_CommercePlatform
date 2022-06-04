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
	 * 门户：查找热门商品
	 * @param num 查找的数量
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);
	/**
	 * 门户：获得首页所有楼层数据
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();
	/**
	 * 门户：根据商品编号查找商品信息
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId);
	/**
	 * 门户：根据产品类型和配件类型查找商品信息（模糊查找）
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
	 * 多条件查询商品信息
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product);
	/**
	 * 更新商品状态：上下架，热销
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer productId,Integer status,Integer hot);
	/**
	 * 保存商品信息（新增or修改）
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrupdateProduct(ActionProduct product);
	
	/**
	 * 上传
	 * @param file
	 * @param path
	 * @return
	 */
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path);
	
	
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize);
	
	
	public SverResponse<ActionProduct> findProductDetailById(Integer productId);
}
