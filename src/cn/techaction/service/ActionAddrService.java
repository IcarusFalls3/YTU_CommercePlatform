package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;

public interface ActionAddrService {
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public SverResponse<String> addAddress(ActionAddress addr);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public SverResponse<String> updateAddress(ActionAddress addr);
	/**
	 * ����ĳ���û��������ջ��ַ/
	 * @param userId
	 * @return
	 */
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId);
	/**
	 *
	 * �����ռ���ɾ����ַ��Ϣ
	 */
	public SverResponse<String> delAddress(Integer userId, Integer id);
	/**
	 * ����Ĭ�ϵ�ַ
	 * @param id
	 * @param id2
	 * @return
	 */
	public SverResponse<String> updateAddrDedaultStatus(Integer userId, Integer id);

	public SverResponse<ActionAddress> getAddressById(Integer id);
}
