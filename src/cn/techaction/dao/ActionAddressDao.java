package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionAddress;

public interface ActionAddressDao {
	/**
	 * ��ѯ�Ƿ����Ĭ�ϵ�ַ
	 * @param uid
	 * @return
	 */
	public int findDefaultAddrByUserId(Integer userId);
	/**
	 * �����ջ��˵�ַ
	 * @param addr
	 * @return
	 */
	public int insertAddress(ActionAddress addr);
	/**
	 * �����ռ��˵�ַ��Ϣ
	 * @param addr
	 * @return
	 */
	public int updateAddress(ActionAddress addr);
	/**
	 * ��ѯ�û����ռ���ַ��Ϣ
	 * @param userId
	 * @return
	 */
	public List<ActionAddress> findAddrsByUserId(Integer userId);
	/**
	 * ��ȡ�û�Ĭ�ϵ�ַ
	 * @param userId
	 * @return
	 */
	public ActionAddress findDefaultAddr(Integer userId);
	
	public ActionAddress findAddrById(Integer id);

	
}
