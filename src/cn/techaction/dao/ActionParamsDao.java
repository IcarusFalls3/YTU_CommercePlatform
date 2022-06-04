package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
	 * 根据节点id查找参考对象
	 * @param id
	 * @return
	 */
	 public ActionParam findParamById(Integer id);	
	 
	/**
	 * 根据父节点id查找子节点参数
	 * @param id
	 * @return
	 */
	public  List<ActionParam> findParamsByParentId(Integer parentId);
 
	/**
	 * 根据父类型id和类型名称查找类型信息
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamsByParentIdAndName(Integer parentId,String name);	

	/**
	 * 新增商品类型
	 * @param params
	 * @return
	 */
	public int insertParam(ActionParam params);
	
	/**
	 * 修改类型
	 * @param params
	 * @return
	 */
	public int updateParam(ActionParam params);
	
	/**
	 * 根据id删除类型对象
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
	
	public ActionParam findParamByParentIdandName(Integer var1, String var2);
}
