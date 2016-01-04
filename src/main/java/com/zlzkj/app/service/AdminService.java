package com.zlzkj.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;







import com.zlzkj.core.util.Fn;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.AdminMapper;
import com.zlzkj.app.mapper.AdminMapper;
import com.zlzkj.app.model.Admin;
import com.zlzkj.app.model.User;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Admin entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Admin entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public List<Admin> findAll() {
		return mapper.selectAll();
	}
	
	public Admin findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Admin.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				/*.where("is_verify = 1")*/
				.parseUIPageAndOrder(pageMap)
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		System.out.println(where);
		for (Map<String,Object> row : list) {
			row.put("addTime", Fn.date(Integer.valueOf(row.get("addTime").toString())));
			row.put("lastLoginTime", Fn.date(Integer.valueOf(row.get("lastLoginTime").toString())));
		}
		String countSql = sqlBuilder.fields("count(*)")/*.where("is_verify = 1")*/.buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	public boolean checkLogin(String loginName, String loginPwd){
		HashMap<String, Object> where  = new HashMap<String,Object>();
		where.put("name", loginName);
		where.put("password",loginPwd);
		SQLBuilder sb = SQLBuilder.getSQLBuilder(User.class);
		String sql = sb.fields("*").where(where).buildSql();
//		List<Row> list = sqlRunner.select(sql,loginName,DigestUtils.md5Hex(loginPwd));
		List<Row> list = sqlRunner.select(sql);
		if(list.size()>0){ 
//			Row row = list.get(0);
//			Admin admin = findById(row.getInt("id"));
//			admin.setLastLoginTime(Fn.time());
//			try {
//				update(admin);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return true;
		}
		else
			return false;
	}
	
	public Integer loginId(String loginName, String loginPwd){
		String sql = SQLBuilder.getSQLBuilder(User.class)
				.fields("id")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where("name ='"+loginName+"' and password ='"+loginPwd+"'")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		System.out.println("#######"+id);
		return id;
	}
	
	public Integer findByName(String name){
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(User.class);
		String sql = sqlBuilder
				.fields("id")
				.where("name = '"+name+"'")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		Integer id = 0;
		for(Row row : list){
			id = Integer.valueOf(row.get("id").toString());
		}
		return id;
	}
}
