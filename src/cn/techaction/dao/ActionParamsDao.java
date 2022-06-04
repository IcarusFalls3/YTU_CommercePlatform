package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {

	/**
	 * ���ݽڵ�id���Ҳο�����
	 * @param id
	 * @return
	 */
	 public ActionParam findParamById(Integer id);	
	 
	/**
	 * ���ݸ��ڵ�id�����ӽڵ����
	 * @param id
	 * @return
	 */
	public  List<ActionParam> findParamsByParentId(Integer parentId);
 
	/**
	 * ���ݸ�����id���������Ʋ���������Ϣ
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamsByParentIdAndName(Integer parentId,String name);	

	/**
	 * ������Ʒ����
	 * @param params
	 * @return
	 */
	public int insertParam(ActionParam params);
	
	/**
	 * �޸�����
	 * @param params
	 * @return
	 */
	public int updateParam(ActionParam params);
	
	/**
	 * ����idɾ�����Ͷ���
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
	
	public ActionParam findParamByParentIdandName(Integer var1, String var2);
}
