package cn.techaction.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.service.ActionAddrService;
@Service
public class ActionAddrServiceImpl implements ActionAddrService{
	@Autowired
	private ActionAddressDao aAddressDao;

	@Override
	public SverResponse<String> addAddress(ActionAddress addr) {
		//�жϲ���
		if  (addr==null){
			return SverResponse.createByErrorMessage("��������");
		}
		//�ж����е�ַ���Ƿ���Ĭ�ϵ�ַ,���û�дӱ���Ĭ�ϵ�ַ������Ϊһ���ַ
		int count=aAddressDao.findDefaultAddrByUserId(addr.getUid());
		if(count==0){
			addr.setDefault_addr(1);
		}else {
			addr.setDefault_addr(0);
		}
		addr.setCreated(new Date());
		addr.setUpdated(new Date());
		int rs=aAddressDao.insertAddress(addr);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("��ַ�����ɹ���");
		}
		return SverResponse.createByErrorMessage("��ַ����ʧ�ܣ�");
		}

	@Override
	public SverResponse<String> updateAddress(ActionAddress addr) {
		//�жϲ���
				if  (addr==null){
					return SverResponse.createByErrorMessage("��������");
				}
				addr.setUpdated(new Date());
				int rs=aAddressDao.updateAddress(addr);
				if(rs>0){
					return SverResponse.createRespBySuccessMessage("��ַ���³ɹ���");
				}
				return SverResponse.createByErrorMessage("��ַ����ʧ�ܣ�");
			}

	@Override
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId) {
		//�жϲ���
		if  (userId==null){
			return SverResponse.createByErrorMessage("��������");
		}
		List<ActionAddress> list=aAddressDao.findAddrsByUserId(userId);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> delAddress(Integer userId, Integer id) {
		if  (userId==null){
			return SverResponse.createByErrorMessage("��������");
		}
		//ɾ����ַ������del_state����
		ActionAddress address=new ActionAddress();
		address.setId(id);
		address.setDel_state(1);
		address.setUpdated(new Date());
		int rs=aAddressDao.updateAddress(address);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("��ַɾ���ɹ���");
		}
		return SverResponse.createByErrorMessage("��ַɾ��ʧ��");
	}

	@Override
	public SverResponse<String> updateAddrDedaultStatus(Integer userId, Integer id) {
		if  (userId==null|| id==null){
			return SverResponse.createByErrorMessage("��������");
		}
		//��ȡԭ������
		ActionAddress oldAddr=aAddressDao.findDefaultAddr(userId);
		if(oldAddr!=null){
			//ȡ��Ĭ�ϵ�ַ
			oldAddr.setDefault_addr(0);
			oldAddr.setUpdated(new Date());
			if(aAddressDao.updateAddress(oldAddr)<=0){
				return SverResponse.createByErrorMessage("����Ĭ�ϵ�ַʧ�ܣ�");
			}
		}
		//�����µ�Ĭ�ϵ�ַ
		ActionAddress newAddr=new ActionAddress();
		newAddr.setDefault_addr(1);
		newAddr.setId(id);
		newAddr.setUpdated(new Date());
		if(aAddressDao.updateAddress(newAddr)<=0){
			return SverResponse.createByErrorMessage("����Ĭ�ϵ�ַʧ�ܣ�");
		}
		return SverResponse.createRespBySuccessMessage("Ĭ�ϵ�ַ���óɹ���");
	}
	
	public SverResponse<ActionAddress> getAddressById(Integer id) {
        if (id == null) {
            return SverResponse.createByErrorMessage("��������");
        } else {
            ActionAddress address = this.aAddressDao.findAddrById(id);
            return SverResponse.createRespBySuccess(address);
        }
    }
}
