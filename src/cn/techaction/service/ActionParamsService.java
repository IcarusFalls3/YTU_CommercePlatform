package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;

public interface ActionParamsService {
	/**
	 * ��ȡȫ��������Ϣ
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllParams();
	/**
	 * ��������
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	
	/**
	 * �޸�����
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);
	
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	
	/**
	 * ���ݸ����Ͳ���������
	 * @param id
	 * @return
	 */
    public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer var1);

    public SverResponse<List<ActionParam>> findProdutTypeParams();

    public SverResponse<List<ActionParam>> findAllPathParams();
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
	public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId);

}
