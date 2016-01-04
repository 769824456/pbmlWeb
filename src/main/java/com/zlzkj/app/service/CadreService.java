package com.zlzkj.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.CadreMapper;
import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.model.Area;
import com.zlzkj.app.model.Cadre;
import com.zlzkj.app.model.Department;
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
public class CadreService {

	@Autowired
	private CadreMapper mapper;
	
	@Autowired
	private SqlRunner sqlRunner;
	
	
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(Cadre entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(Cadre entity) throws Exception{
		
		return mapper.insert(entity);
	}
	
	public Cadre findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public Map<String, Object> getUIGridData(Map<String, Object> where,String Query, Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Cadre.class);
		String sql = sqlBuilder
				.fields("Cadre.id as id , cadre_name,cadre_sex,area_name,duties_name,Department.name As departmentName"
						+ ",Cadre.office_tel as officeTel,moble_phone,Cadre.remark As remark")  
						//这里约定前端grid需要显示多少个具体列，也可以全部*
				.where(where)
				.where(Query)
				.parseUIPageAndOrder(pageMap)
				.join(Area.class, "Cadre.area_id  = Area.id","LEFT")
				.join(Department.class, "Cadre.department_id  = Department.id","LEFT")
				.join(Duties.class, "Cadre.duties_id  = Duties.id","LEFT")
				.order("id", "asc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		String countSql = sqlBuilder.fields("count(*)").where(where).where(Query)
				.join(Area.class, "Cadre.area_id  = Area.id","LEFT")
				.join(Department.class, "Cadre.department_id  = Department.id","LEFT")
				.join(Duties.class, "Cadre.duties_id  = Duties.id","LEFT").buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	/**
	 * 统计部门人数
	 * @param departmentId
	 * @return
	 */
	public Integer countStaff(Integer departmentId) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Cadre.class);
		String countSql = sqlBuilder.fields("count(*)").where("department_id = " +departmentId).buildSql();
		return sqlRunner.count(countSql);
	}
}
