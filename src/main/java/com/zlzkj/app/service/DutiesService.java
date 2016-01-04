package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import com.zlzkj.core.util.Fn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.DutiesMapper;
import com.zlzkj.app.model.Duties;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;

@Service
@Transactional
public class DutiesService {

	@Autowired
	private DutiesMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Duties entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Duties entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Duties findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Duties.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.order("sort_id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		System.out.println(where);
		for (Row row : list) {
			row.put("addTime", Fn.date(row.getInt("addTime")));
		}
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	
	public List<Row> getDutiesList(Map<String, Object> where) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Duties.class);
		String sql = sqlBuilder
				.fields("duties_name as text,id")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.order("sort_id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
}
