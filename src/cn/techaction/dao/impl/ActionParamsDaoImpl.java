package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
@Repository
public class ActionParamsDaoImpl implements ActionParamsDao{

	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public ActionParam findParamById(Integer id) {
		String sql = "select id,parent_id,name,sort_order,status,created,updated,level from action_params where id= ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),id);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<ActionParam> findParamsByParentId(Integer parentId) {
		String sql = "select id,parent_id,name,sort_order,status,level,created,updated from action_params where "
				+"parent_id = ? order by sort_order";
//		String sql = "select * from action_params where parent_id = ? order by sort_order";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionParam>(ActionParam.class),parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ActionParam findParamsByParentIdAndName(Integer parentId, String name) {
		// TODO Auto-generated method stub
//		String sql="select * from action_params where parent_id=? and name=?";
		String sql="select id,parent_id,name,sort_order,status,level,created,updated from action_params where parent_id=? and name=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),parentId,name);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertParam(ActionParam params) {
		// TODO Auto-generated method stub
		String sql="insert action_params(parent_id,name,sort_order,status,level,created,updated) values(?,?,?,?,?,?,?)";
		Object[] param={params.getParent_id(),params.getName(),params.getSort_order(),params.getStatus(),params.getLevel(),params.getCreated(),params.getUpdated()};
		try {
			return queryRunner.update(sql, param);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	} 

	@Override
	public int updateParam(ActionParam params) {
		// TODO Auto-generated method stub
		String sql="update action_params set parent_id=?,name=?,sort_order=?,status=?,level=?,created=?,updated=? where id=?";
		Object[] param={params.getParent_id(),params.getName(),
				params.getSort_order(),params.getStatus(),params.getLevel(),
				params.getCreated(),params.getUpdated(),params.getId()};
		try {
			return queryRunner.update(sql, param);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteParam(Integer id) {
		// TODO Auto-generated method stub
		String sql="delete from action_params where id=?";
		try {
			return queryRunner.update(sql,id);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public ActionParam findParamByParentIdandName(Integer parentId, String name) {
       String sql = "select * from action_params where parent_id = ? and name = ?";
       try {
           return (ActionParam)this.queryRunner.query(sql, new BeanHandler(ActionParam.class),new Object[]{parentId, name});
       } catch (SQLException var5) {
           var5.printStackTrace();
           return null;
       }
   }
}
