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
		//判断参数
		if  (addr==null){
			return SverResponse.createByErrorMessage("参数错误");
		}
		//判断已有地址中是否含有默认地址,如果没有从本条默认地址，否则为一般地址
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
			return SverResponse.createRespBySuccessMessage("地址新增成功！");
		}
		return SverResponse.createByErrorMessage("地址新增失败！");
		}

	@Override
	public SverResponse<String> updateAddress(ActionAddress addr) {
		//判断参数
				if  (addr==null){
					return SverResponse.createByErrorMessage("参数错误！");
				}
				addr.setUpdated(new Date());
				int rs=aAddressDao.updateAddress(addr);
				if(rs>0){
					return SverResponse.createRespBySuccessMessage("地址更新成功！");
				}
				return SverResponse.createByErrorMessage("地址更新失败！");
			}

	@Override
	public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId) {
		//判断参数
		if  (userId==null){
			return SverResponse.createByErrorMessage("参数错误！");
		}
		List<ActionAddress> list=aAddressDao.findAddrsByUserId(userId);
		return SverResponse.createRespBySuccess(list);
	}

	@Override
	public SverResponse<String> delAddress(Integer userId, Integer id) {
		if  (userId==null){
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//删除地址，并对del_state更新
		ActionAddress address=new ActionAddress();
		address.setId(id);
		address.setDel_state(1);
		address.setUpdated(new Date());
		int rs=aAddressDao.updateAddress(address);
		if(rs>0){
			return SverResponse.createRespBySuccessMessage("地址删除成功！");
		}
		return SverResponse.createByErrorMessage("地址删除失败");
	}

	@Override
	public SverResponse<String> updateAddrDedaultStatus(Integer userId, Integer id) {
		if  (userId==null|| id==null){
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//读取原来数据
		ActionAddress oldAddr=aAddressDao.findDefaultAddr(userId);
		if(oldAddr!=null){
			//取消默认地址
			oldAddr.setDefault_addr(0);
			oldAddr.setUpdated(new Date());
			if(aAddressDao.updateAddress(oldAddr)<=0){
				return SverResponse.createByErrorMessage("设置默认地址失败！");
			}
		}
		//设置新的默认地址
		ActionAddress newAddr=new ActionAddress();
		newAddr.setDefault_addr(1);
		newAddr.setId(id);
		newAddr.setUpdated(new Date());
		if(aAddressDao.updateAddress(newAddr)<=0){
			return SverResponse.createByErrorMessage("设置默认地址失败！");
		}
		return SverResponse.createRespBySuccessMessage("默认地址设置成功！");
	}
	
	public SverResponse<ActionAddress> getAddressById(Integer id) {
        if (id == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionAddress address = this.aAddressDao.findAddrById(id);
            return SverResponse.createRespBySuccess(address);
        }
    }
}
