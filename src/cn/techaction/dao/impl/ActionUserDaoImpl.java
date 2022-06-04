package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;


import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;


@Repository
public class ActionUserDaoImpl implements ActionUserDao{
	@Resource
	private QueryRunner queryRunner;
	@Override
	public int checkUserByAccount(String account) {
		// TODO Auto-generated method stub
		String sql="select count(*) as num from action_users where account=?";
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),account);
			return rs.size()>0?rs.get(0).intValue():0;
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
//		
	}
	public User findUserByAccountAndPassword(String account, String password) {
		// TODO Auto-generated method stub
		String sql="select * from action_users where account=? and password=?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class),account,password);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
}