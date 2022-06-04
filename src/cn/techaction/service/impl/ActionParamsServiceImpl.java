 package cn.techaction.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionParamsService;
import cn.techaction.vo.ActionParamVo;

@Service
public class ActionParamsServiceImpl implements ActionParamsService {
	@Autowired
	private ActionParamsDao aParamDao;
	@Autowired
	private ActionProductDao actionProductDao;
	@Override
	public SverResponse<List<ActionParam>> findAllParams() {
		//查找一级子节点
		List<ActionParam> parammList=aParamDao.findParamsByParentId(0);
		//递归查询所有子节点
		for(ActionParam param : parammList) {
			findDirectChildren(param);
		}
		return SverResponse.createRespBySuccess(parammList);
	}
	//递归查找
	private void findDirectChildren(ActionParam parentParam) {
		//查找子节点
		List<ActionParam> paramList = aParamDao.findParamsByParentId(parentParam.getId());
		parentParam.setChildren(paramList);
		for(ActionParam p : paramList){
			findDirectChildren(p);
		}
	}
	
	@Override
	public SverResponse<String> addParam(ActionParam actionParam) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(actionParam.getName())) {
			return SverResponse.createByErrorMessage("参数异常！");
		}
		//判断新增类型名在同一级父类型中是否重名
		ActionParam param=aParamDao.findParamsByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null) {
			return SverResponse.createByErrorMessage("商品类型名已经存在！");
		}
		//可以调用dao的方法新增类型了
		actionParam.setStatus(true);
		actionParam.setCreated(new Date());
		actionParam.setUpdated(new Date());
		actionParam.setLevel(this.getParamLevel(actionParam.getParent_id()));
		int rs=aParamDao.insertParam(actionParam);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("新增类型成功！");
		}
		return SverResponse.createByErrorMessage("新增类型失败！");
	}
	/**
	 * 计算新增类型结点的level,实际上是它父类型level+1
	 * 如果新增的类型是根类型那么level为0
	 * @param parentId
	 * @return
	 */
	private int getParamLevel(int parentId) {
		ActionParam param=aParamDao.findParamById(parentId);
		if(param!=null) {
			return param.getLevel()+1;
		}
		return 0;
	}
	
	@Override
	public SverResponse<String> updateParam(ActionParam actionParam) {
		// TODO Auto-generated method stub
		//1.判断参数异常
		if(actionParam.getId()==0||StringUtils.isBlank(actionParam.getName())) {
			return SverResponse.createByErrorMessage("参数异常！");
		}
		//2.判断重名
		ActionParam param=aParamDao.findParamsByParentIdAndName(actionParam.getParent_id(), actionParam.getName());
		if(param!=null) {
			return SverResponse.createByErrorMessage("商品类型名已经存在！");
		}
		//3.属性修改
		ActionParam origin = aParamDao.findParamById(actionParam.getId());
		origin.setName(actionParam.getName());
		origin.setUpdated(new Date());
		//4.调用方法
		int rs=aParamDao.updateParam(origin);
		if(rs>0) {
			return SverResponse.createRespBySuccessMessage("修改类型成功！");
		}
		return SverResponse.createByErrorMessage("修改类型失败！");
	}
	
	@Override
	public SverResponse<String> delParam(Integer id) {
		// TODO Auto-generated method stub
		//1.判断当前的类型有没有子类型
		List<ActionParam> params= aParamDao.findParamsByParentId(id);
		if(params.size()!=0) {
			return SverResponse.createByErrorMessage("请先删除子类型！");
		}
		//2.判断当前类型是否被商品使用
		List<ActionProduct> products=actionProductDao.findProductsByPartsId(id);
		if(products.size()!=0) {
			return SverResponse.createByErrorMessage("不能删除有商品的类型！");
		}
		//3.调用dao方法
		int rs=aParamDao.deleteParam(id);
		if(rs==0) {
			return SverResponse.createByErrorMessage("删除失败！");
		}
		return SverResponse.createRespBySuccessMessage("删除成功！");
	}
	
	@Override
	public SverResponse<List<ActionParam>> findParamChildren(Integer id) {
		// TODO Auto-generated method stub
		//调用dao层方法
		List<ActionParam> params=aParamDao.findParamsByParentId(id);
		return SverResponse.createRespBySuccess(params);
	}
	@Override
	public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer id) {
		// TODO Auto-generated method stub
		Set<ActionParam> paramSet = Sets.newHashSet();
        this.findChildren(paramSet, id);
        List<ActionParam> paramsList = Lists.newArrayList();
        if (id != null) {
            Iterator var5 = paramSet.iterator();

            while(var5.hasNext()) {
                ActionParam param = (ActionParam)var5.next();
                paramsList.add(param);
            }
        }

        return SverResponse.createRespBySuccess(paramsList);
	}
	@Override
	public SverResponse<List<ActionParam>> findProdutTypeParams() {
		// TODO Auto-generated method stub
		List<ActionParam> list = this.aParamDao.findParamsByParentId(0);
        return SverResponse.createRespBySuccess(list);
	}
	@Override
	public SverResponse<List<ActionParam>> findAllPathParams() {
		// TODO Auto-generated method stub
		List<ActionParam> paramList = this.aParamDao.findParamsByParentId(0);
        Iterator var3 = paramList.iterator();

        while(var3.hasNext()) {
            ActionParam param = (ActionParam)var3.next();
            this.findDirectChildren(param);
        }

        List<ActionParam> list = Lists.newArrayList();
        Iterator var4 = paramList.iterator();

        while(var4.hasNext()) {
            ActionParam param = (ActionParam)var4.next();
            this.createDeepParam(list, param);
        }

        return SverResponse.createRespBySuccess(list);
	}
	@Override
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId) {
		// TODO Auto-generated method stub
		List<ActionParam> paramList = this.aParamDao.findParamsByParentId(productTypeId);
        Iterator var4 = paramList.iterator();

        while(var4.hasNext()) {
            ActionParam param = (ActionParam)var4.next();
            this.findDirectChildren(param);
        }

        List<ActionParamVo> volist = Lists.newArrayList();
        Iterator var5 = paramList.iterator();

        while(true) {
            while(var5.hasNext()) {
                ActionParam secondLevel = (ActionParam)var5.next();
                if (secondLevel.getChildren().size() > 0) {
                    Iterator var7 = secondLevel.getChildren().iterator();

                    while(var7.hasNext()) {
                        ActionParam thirdLevel = (ActionParam)var7.next();
                        ActionParamVo vo = new ActionParamVo();
                        vo.setId(thirdLevel.getId());
                        vo.setName(secondLevel.getName() + "/" + thirdLevel.getName());
                        volist.add(vo);
                    }
                } else {
                    ActionParamVo vo = new ActionParamVo();
                    vo.setId(secondLevel.getId());
                    vo.setName(secondLevel.getName());
                    volist.add(vo);
                }
            }

            return SverResponse.createRespBySuccess(volist);
        }
	}
	
	private Set<ActionParam> findChildren(Set<ActionParam> paramSet, Integer id) {
        ActionParam param = this.aParamDao.findParamById(id);
        if (param != null) {
            paramSet.add(param);
        }

        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(id);
        Iterator var6 = paramList.iterator();

        while(var6.hasNext()) {
            ActionParam p = (ActionParam)var6.next();
            this.findChildren(paramSet, p.getId());
        }

        return paramSet;
    }
	
	private void createDeepParam(List<ActionParam> list, ActionParam parent) {
        list.add(parent);
        Iterator var4 = parent.getChildren().iterator();

        while(var4.hasNext()) {
            ActionParam p = (ActionParam)var4.next();
            p.setName(parent.getName() + "/" + p.getName());
            this.createDeepParam(list, p);
        }

    }
}
