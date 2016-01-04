package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;










import com.alibaba.fastjson.JSON;
import com.zlzkj.core.util.Fn;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.DepartmentMapper;
import com.zlzkj.app.model.Cadre;
import com.zlzkj.app.model.Department;
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
public class DepartmentService {

	@Autowired
	private DepartmentMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private CadreService cadreService;
	
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Department entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Department entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Department findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Department.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.order("sort_id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	
	public Map<String, Object> getStatis(Map<String, Object> where, Map<String, String> pageMap) throws Exception {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Department.class);
		String sql = sqlBuilder
				.fields("*")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.parseUIPageAndOrder(pageMap)
				.order("sort_id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			Department department = new Department();
			department = (Department) Fn.rowToModel(Department.class, row);
			department.setStaffCount(cadreService.countStaff(department.getId()));
			this.update(department);
		}
		List<Row> list2 = sqlRunner.select(sql);
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list2);
	}
	
	
	
	public List<Row> getDepartmentList(Map<String, Object> where) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Department.class);
		String sql = sqlBuilder
				.fields("id,name AS text")   //这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
/*				.parseUIPageAndOrder(pageMap)
*/				.order("sort_id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		return list;
	}
	
	/**
	 * 统计区域
	 * @param departmentId
	 * @return
	 */
	public Integer countStaff(Integer areaId) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Department.class);
		String countSql = sqlBuilder.fields("sum(staff_count)").where("area_id = " +areaId).buildSql();
		Integer  count = sqlRunner.count(countSql);
		return count;
	}
	
	public Integer countDepartment(Integer areaId) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Department.class);
		String countSql = sqlBuilder.fields("count(*)").where("area_id = " +areaId).buildSql();
		Integer  count = sqlRunner.count(countSql);
		return count;
	}
}
