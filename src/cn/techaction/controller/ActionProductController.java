package cn.techaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/product")
public class ActionProductController {
	@Autowired
	private ActionProductService aProductService;
	
	@RequestMapping("/find_product.do")
	@ResponseBody
	public SverResponse<PageBean<ActionProduct>> findProducts(Integer productId,Integer partsId ,
			Integer pageNum,Integer pageSize){ //调用Service层的方法去分页查询 return
	 return aProductService.findProduct(productId, partsId, pageNum, pageSize); 
	 }
//	/**
//	 * 查询热销商品
//	 * 
//	 * @param num
//	 * @return
//	 */
//	@RequestMapping("/findhotproducts.do")
//	@ResponseBody
//	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
//		return aProductService.findHotProducts(num);
//	}
//
//	/**
//	 * 查找楼层商品
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/findfloors.do")
//	@ResponseBody
//	public SverResponse<ActionProductFloorVo> findFloorProducts() {
//		return aProductService.findFloorProducts();
//	}
//
//	/**
//	 * 根据商品编号，获取商品详情
//	 * 
//	 * @param productId
//	 * @return
//	 */
//	@RequestMapping("/getdetail.do")
//	@ResponseBody
//	public SverResponse<ActionProduct> getProductDetail(Integer productId) {
//		return aProductService.findProductDetailForPortal(productId);
//	}
//
//	/**
//	 * 根据条件查询商品
//	 * 
//	 * @param productTypeId
//	 * @param partsId
//	 * @param name
//	 * @param pageNum
//	 * @param pageSize
//	 * @return
//	 */
//	@RequestMapping("/findproducts.do")
//	@ResponseBody
//	public SverResponse<PageBean<ActionProductListVo>> searchProducts(Integer productTypeId, Integer partsId,
//			String name, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
//			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//		return aProductService.findProductsForprotal(productTypeId, partsId, name, pageNum, pageSize);
//	}
	 
}
