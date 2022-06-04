package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	/**
	 * 获取全换分类信息
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllParams();
	/**
	 * 新增类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	
	/**
	 * 修改类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);
	
	/**
	 * 删除类型
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	
	/**
	 * 根据父类型查找子类型
	 * @param id
	 * @return
	 */
    public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer var1);

    public SverResponse<List<ActionParam>> findProdutTypeParams();

    public SverResponse<List<ActionParam>> findAllPathParams();
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId);

}
