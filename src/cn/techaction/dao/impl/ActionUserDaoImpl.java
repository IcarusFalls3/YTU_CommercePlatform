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
		String sql="select count(account) as num from action_users where account=?";
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),account);
			return rs.size()>0?rs.get(0).intValue():0;
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
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
	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		String sql="select * from action_users where del=0;";
		try{
			return  queryRunner.query(sql, new BeanListHandler<User>(User.class));
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public User findUserById(Integer id) {
		// TODO Auto-generated method stub
		String sql="select * from action_users where id=?;";
		try{
			return  queryRunner.query(sql, new BeanHandler<User>(User.class),id);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}

	}
	@Override
	public int updateUserInfo(User user) {
		// TODO Auto-generated method stub
		String sql="UPDATE action_users set account=?,password=?,email=?,phone=?,question=?,asw=?,role=?,"
				+ "create_time=?,update_time=?,age=?,sex=?,del=?,name=? where id=?";
		List<Object> param=new ArrayList<>();
		param.add(user.getAccount());
		param.add(user.getPassword());
		param.add(user.getEmail());
		param.add(user.getPhone());
		param.add(user.getQuestion());
		param.add(user.getAsw());
		param.add(user.getRole());
		param.add(user.getCreate_time());
		param.add(user.getUpdate_time());
		param.add(user.getAge());
		param.add(user.getSex());
		param.add(user.getDel());
		param.add(user.getName());
		param.add(user.getId());
		try{
			return queryRunner.update(sql, param.toArray());
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int checkUserByEmail(String email) {
		// TODO Auto-generated method stub
		String sql="select count(account) as num from action_users where email=?" ;
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),email);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	
	}
	@Override
	public int checkUserByPhone(String phone) {
		// TODO Auto-generated method stub
		String sql="select count(account) as num from action_users where phone=?" ;
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),phone);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
}
	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		String sql="insert into action_users(account,password,email,phone,question,asw,role"
				+ ",create_time,update_time) values(?,?,?,?,?,?,?,?,?)";
		Object[] params={user.getAccount(),user.getPassword(),user.getEmail(),
				user.getPhone(),user.getQuestion(),user.getAsw(),user.getRole(),
				user.getCreate_time(),user.getUpdate_time()};
		try {
			return queryRunner.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public User findUserByAccount(String account) {
		// TODO Auto-generated method stub
		String sql="select * from action_users where account=?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class),account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	@Override
	public int checkUserAnswer(String account, String question, String asw) {
		// TODO Auto-generated method stub
		String sql="select count(account) as num from action_users"
				+ " where account=? and question=? and asw=?";
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),account,question,asw);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
	}
	@Override
	public int checkPassword(String account, String password) {
		String sql="select count(account) as num from action_users where account=? and password=? ";
		try {
			List<Long> rs=queryRunner.query(sql, new ColumnListHandler<Long>("num"),account,password);
			return rs.size()>0?rs.get(0).intValue():0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}
}