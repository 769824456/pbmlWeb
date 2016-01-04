package com.zlzkj.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;








import com.alibaba.fastjson.JSON;
import com.zlzkj.core.util.Fn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.AreaMapper;
import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.model.Area;
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
public class AreaService {

	@Autowired
	private AreaMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	@Autowired
	private DepartmentService departmentService;
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Area entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Area entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Area findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Area.class);
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
	
	public List<Row> getUIComboData() {
		return getNodeTree("id,area_name AS text,pid,level,sort_id", "pid is null", 3);
	}
	
	public List<Row> getUIAreaList() {
		return getNodeTree("id,area_name AS text,pid,level,sort_id", "pid is null", 4);
	}

	public List<Row> getNodeTree(String fields,String where_pid ,Integer maxLevel){
		//查询条件
		String sql = SQLBuilder.getSQLBuilder(Area.class).fields(fields).where(where_pid).order("sort_id", "ASC").buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row one:list){
			if(one.getInt("level")<maxLevel){
				one.put("children",getNodeTree(fields, "pid = " +one.getInt("id"), maxLevel));
			}
		}
		return list;
	}
	
	public Map<String, Object> getUIGridData() {
		SQLBuilder sb = SQLBuilder.getSQLBuilder(Area.class);
		String sql = sb.fields("id,area_name As name,sort_id, level,is_show,pid AS _parentId")
				.order("sort_id", "ASC").buildSql();
        List<Row> list= sqlRunner.select(sql);
		for(Row row :list){
        	row.put("count",departmentService.countDepartment(row.getInt("id")) );
        }
		int count = sqlRunner.count(sb.fields("count(*)").buildSql());
		return UIUtils.getGridData(count, list);
	}
	
	
	public Map<String, Object> geStatistUIGridData() {
		SQLBuilder sb = SQLBuilder.getSQLBuilder(Area.class);
		String sql = sb.fields("id,area_name As name,sort_id, level,is_show,pid AS _parentId")
				.order("sort_id", "ASC").buildSql();
        List<Row> list= sqlRunner.select(sql);
        for(Row row :list){
        	row.put("sum",departmentService.countStaff(row.getInt("id")) );
        }
/*        System.out.println(JSON.toJSON(list));
*/		int count = sqlRunner.count(sb.fields("count(*)").buildSql());
		return UIUtils.getGridData(count, list );
	}
}
