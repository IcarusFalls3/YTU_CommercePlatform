package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.*;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.utils.ConstUtil.HotStatus;
import cn.techaction.utils.ConstUtil.ProductStatus;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.*;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;


import org.apache.commons.lang3.StringUtils;

@Service
public class ActionProductServiceImpl implements ActionProductService{
	@Autowired
	private ActionProductDao aProductDao;
//	@Autowired
//	private ActionParamsDao aParamsDao;

	@Override
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		// TODO Auto-generated method stub
		//1.先要根据条件获得查询的商品的总条数
		int totalCount=aProductDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean=new PageBean<>(pageNum, pageSize, totalCount);
		//2.调用dao层获得分页查询的商品信息
		pageBean.setData(aProductDao.findProductByInfo(productId, partsId,pageBean.getStartIndex(), pageSize));
		return SverResponse.createRespBySuccess(pageBean);
	}

	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForprotal(Integer productTypeId, Integer partsId,
			String name, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<String> updateStatus(Integer productId, Integer status, Integer hot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<String> saveOrupdateProduct(ActionProduct product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
//		//直接查询所需数据
//		List<ActionProduct> products = aProductDao.findHotProducts(num);
//		return SverResponse.createRespBySuccess(products);
//	}
//
//	@Override
//	public SverResponse<ActionProductFloorVo> findFloorProducts() {
//		//创建vo对象
//		ActionProductFloorVo vo = new ActionProductFloorVo();
//		//一楼数据
//		List<ActionProduct> products1 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
//		vo.setOneFloor(products1);
//		//二楼数据
//		List<ActionProduct> products2 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
//		vo.setTwoFloor(products2);
//		//三楼数据
//		List<ActionProduct> products3 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
//		vo.setThreeFloor(products3);
//		//四楼数据
//		List<ActionProduct> products4 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
//		vo.setFourFloor(products4);
//		return SverResponse.createRespBySuccess(vo);
//	}
//	
//	@Override
//	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
//		//判断产品编号是否为空
//		if(productId ==null) {
//			return SverResponse.createByErrorMessage("产品编号不能为空");
//		}
//		//查询商品详情
//		ActionProduct product =aProductDao.findProductById(productId);
//		//判断产品是否下架
//		if(product==null) {
//			return SverResponse.createByErrorMessage("产品已经下架！");
//		}
//		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
//			return SverResponse.createByErrorMessage("产品已经下架！");
//		}
//		return SverResponse.createRespBySuccess(product);
//	}
//
//	@Override
//		public SverResponse<PageBean<ActionProductListVo>> findProductsForprotal(Integer productTypeId, Integer partsId,
//			String name,int pageNum, int pageSize) {
//		// 创建对象
//		ActionProduct product =new ActionProduct();
//		int totalRecord=0;
//		//判断name是否为空
//		if(name !=null && !name.equals("")) {
//			product.setName(name);
//		}
//		if(productTypeId!=0) {
//			product.setProductId(productTypeId);
//		}
//		if(partsId!=0) {
//			product.setPartsId(partsId);
//		}
//		//前端显示商品都为在售
//		product.setStatus(2);
//		//查找符合条件的总记录数
//		totalRecord=aProductDao.getTotalCount(product);
//		//创建分页对象
//		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
//		//读取数据
//		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(),pageSize);
//		//封装vo
//		List<ActionProductListVo> voList = Lists.newArrayList();
//		for(ActionProduct p:products) {
//			voList.add(createProductListVo(p));
//		}
//		pageBean.setData(voList);
//		return SverResponse.createRespBySuccess(pageBean);
//	}
//	
//	//封装vo对象
//	private ActionProductListVo createProductListVo(ActionProduct product) {
//		ActionProductListVo vo = new ActionProductListVo();
//		vo.setId(product.getId());
//		vo.setName(product.getName());
//		vo.setPartsCategory(aParamsDao.findParamById(product.getPartsId()).getName());
//		vo.setProductCategory(aParamsDao.findParamById(product.getProductId()).getName());
//		vo.setPrice(product.getPrice());
//		vo.setStatus(product.getStatus());
//		vo.setIconUrl(product.getIconUrl());
//		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
//		vo.setHotStatus(ConstUtil.HotStatus.getHotDesc(product.getHot()));
//		vo.setHot(product.getHot());
//		return vo;
//	}
//	
//	@Override
//	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product) {
//		// TODO Auto-generated method stub
//		if(product.getName()!=null){
//			product.setName("%"+product.getName()+"%");
//		}
//		//调用Dao层中的方法实现查询
//		List<ActionProduct> products=aProductDao.findProductsNoPage(product);
//		//2.需要将ActionProduct转换为业务实体对象
//		List<ActionProductListVo> voList=Lists.newArrayList();
//		for(ActionProduct pro:products) {
//			voList.add(createProductListVo(pro));
//		}
//		return SverResponse.createRespBySuccess(voList);
//	}
//	
//	/**
//	 * 封装vo对象
//	 * @param product
//	 * @return
//	 */
//	
//
//	
//
//	@Override
//	public SverResponse<String> updateStatus(Integer productId, Integer status,
//			Integer hot) {
//		// TODO Auto-generated method stub
//		if (productId == null || status == null || hot == null) {
//			return SverResponse.createByErrorMessage("参数非法！");
//		}
//		ActionProduct product = new ActionProduct();
//		product.setId(productId);
//		product.setUpdated(new Date());
//		// 判断是修改上下架还是修改热销
//		if (status == -1) {
//			product.setHot(hot);
//		} else if (hot == -1) {
//			product.setStatus(status);
//		}
//		// 调用dao层的修改商品方法
//		int rs = aProductDao.updateProduct(product);
//		if (rs > 0) {
//			return SverResponse.createRespBySuccessMessage("修改商品状态成功！");
//		}
//		return SverResponse.createByErrorMessage("修改商品状态失败！");
//	}
//
//	@Override
//	public SverResponse<String>  saveOrupdateProduct(ActionProduct product) {
//		// TODO Auto-generated method stub
//		if (product == null) {
//			return SverResponse.createByErrorMessage("商品的参数无效！");
//		}
//		// 1.处理主图和子图的链接，从前端传递过来的图链接放在了subImages里
//		// 第一个链接作为主图链接，其他的作为子图链接
//		//修改时：重新上传了图片，会清空原来的，新的连接处理和新增相同
//		//修改时：如果没有重新上传图片，组合如 ，null
//		if (!StringUtils.isEmpty(product.getSubImages())) {
//			String[] array = product.getSubImages().split(",");
//			// 拿出第一个元素作为主图
//			product.setIconUrl(array[0]);
//			String temp = product.getSubImages();
//			int index = temp.indexOf(",");// 判断有，吗
//			if (index != -1) {// 有,号
//				if(temp.substring(index+1).equals("null"))
//				{
//					product.setSubImages(null);
//				}
//				else {
//					product.setSubImages(temp.substring(index + 1));// 截取
//				}
//				
//			} else {
//				product.setSubImages(null);
//			}
//		}
//		int rs=0;
//		// 判断是新增还是修改
//		if (product.getId() != null) {//修改
//			product.setUpdated(new Date());
//			//调用dao的修改方法
//			rs=aProductDao.updateProduct(product);
//		} else {
//			// 2.处理其他的属性
//			product.setStatus(ConstUtil.ProductStatus.STATUS_NEW);
//			product.setHot(ConstUtil.HotStatus.NORMAL_STATUS);
//			product.setCreated(new Date());
//			product.setUpdated(new Date());
//			// 3.调用dao层的方法：新增方法
//			rs = aProductDao.insertProduct(product);
//		}
//		if (rs > 0) {
//			return SverResponse.createRespBySuccessMessage("对商品操作成功！");
//		}
//		return SverResponse.createByErrorMessage("对商操作失败！");
//	}
//	@Override
//    public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path) {
//        String fileName = file.getOriginalFilename();
//        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
//        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
//        File fileDir = new File(path);
//        if (!fileDir.exists()) {
//            fileDir.setWritable(true);
//            fileDir.mkdirs();
//        }
//
//        File targetFile = new File(path, uploadFileName);
//
//        try {
//            file.transferTo(targetFile);
//        } catch (IOException var9) {
//            return SverResponse.createByErrorMessage("文件上传错误！");
//        }
//
//        Map<String, String> fileMap = Maps.newHashMap();
//        fileMap.put("url", "/upload/" + targetFile.getName());
//        System.out.println((String)fileMap.get("url"));
//        return SverResponse.createRespBySuccess(fileMap);
//    }
//
//	@Override
//    public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum, int pageSize) {
//        if (product.getName() != null) {
//            product.setName("%" + product.getName() + "%");
//        }
//
//        int totalRecord = this.aProductDao.getTotalCount(product);
//        PageBean<ActionProductListVo> pageBean = new PageBean(pageNum, pageSize, totalRecord);
//        List<ActionProduct> products = this.aProductDao.findProducts(product, pageBean.getStartIndex(), pageSize);
//        List<ActionProductListVo> voList = Lists.newArrayList();
//        Iterator var9 = products.iterator();
//
//        while(var9.hasNext()) {
//            ActionProduct p = (ActionProduct)var9.next();
//            voList.add(this.createProductListVo(p));
//        }
//
//        pageBean.setData(voList);
//        return SverResponse.createRespBySuccess(pageBean);
//    }
//
//	@Override
//	   public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
//        if (productId == null) {
//            return SverResponse.createByErrorMessage("产品编号不能为空！");
//        } else {
//            ActionProduct product = this.aProductDao.findProductById(productId);
//            return product == null ? SverResponse.createByErrorMessage("产品已经下架！") : SverResponse.createRespBySuccess(product);
//        }
//    }
	
}
