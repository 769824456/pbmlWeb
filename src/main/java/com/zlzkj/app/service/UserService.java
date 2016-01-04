package com.zlzkj.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zlzkj.core.util.Fn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.UserMapper;
import com.zlzkj.app.model.Area;
import com.zlzkj.app.model.Cadre;
import com.zlzkj.app.model.Department;
import com.zlzkj.app.model.Duties;
import com.zlzkj.app.model.Role;
import com.zlzkj.app.model.User;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.JsonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private RoleService roleService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(User entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(User entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Integer add(User entity) throws Exception{
		
		mapper.insert(entity);
		return (Integer)entity.getId();
	}
	

	public User findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public String findByName(String name){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("name ='"+name+"'")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		String pass = "";
		for (Map<String,Object> row : list){
			pass = row.get("password").toString();
		}
		System.out.println(pass);
		return pass;
	}
	
	public Integer find(String name){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("name ='"+name+"'")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for (Map<String,Object> row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		System.out.println(id);
		return id;
	}
	
	
	public User getObjByUserName(String userName){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("name = '"+userName+"'")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		User user = this.findById(list.get(0).getInt("id"));
		return user;
	}
	
	
	public User getObjByProperties(HashMap<String, Object> where) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder.fields("*").where(where).buildSql();
		List<Row> list = sqlRunner.select(sql);
		if (list.size() == 0)
			return null;
		else
			return this.findById(list.get(0).getInt("id"));
	}
	
	public boolean userNameIsExist(String userName){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder.fields("*").where("name='"+userName+"'").buildSql();
		List<Row> list = sqlRunner.select(sql);
		if(list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	
	public Map<String, Object> test(Map<String, Object> where,String where2, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.where(where2)
//				.parseUIPageAndOrder(pageMap)
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);

		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			row.put("addTime", Fn.date(row.getInt("addTime")));
			Role role = roleService.findById(row.getInt("roleId"));
			if(role!=null) row.put("roleName", role.getName()); 
		}

		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where,String Query, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("User.id as id , User.name as name ,area_name,Department.name As departmentName"
						+ ",User.is_active AS isActive, role_id,User.add_time AS addTime")  
						//这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.where(Query)
				.parseUIPageAndOrder(pageMap)
				.join(Area.class, "User.area_id  = Area.id","LEFT")
				.join(Department.class, "User.department_id  = Department.id","LEFT")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			row.put("addTime", Fn.date(row.getInt("addTime")));
			Role role = roleService.findById(row.getInt("roleId"));
			if(role!=null) row.put("roleName", role.getName()); 
		}
		String countSql = sqlBuilder.fields("count(*)").where(where).where(Query)
				.join(Area.class, "User.area_id  = Area.id","LEFT")
				.join(Department.class, "User.department_id  = Department.id","LEFT").buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
}
