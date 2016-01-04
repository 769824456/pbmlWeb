package com.zlzkj.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zlzkj.app.model.Area;
import com.zlzkj.app.model.Cadre;
import com.zlzkj.app.service.AreaService;
import com.zlzkj.app.service.CadreService;
import com.zlzkj.app.service.DepartmentService;
import com.zlzkj.app.util.Param2Bean;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 人员名录库
 * @author Lee
 *
 */
@Controller
@RequestMapping(value={"directory"})
public class DirectoryController extends BaseController{
	
	@Autowired
	private CadreService cadreService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private AreaService areaService;
	
	
	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "directory/cadre_list";
	}
	
	/**
	 * 组织线干部人员名录
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value={"cadre_list"})
	public String cadreList(Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		String cadreName =  request.getParameter("cadreName");
		if (!StringUtil.isEmpty(cadreName)) {
			cadreName = new String(cadreName.getBytes("ISO-8859-1"), "UTF-8");
		}
		String  q_cadreName  = StringUtil.fuzzyQuery("cadre_name", cadreName);
		String  q_cadreSex   = StringUtil.listQuery("cadre_sex", request.getParameter("cadreSex"));
		String  q_departmentId   = StringUtil.listQuery("Cadre.department_id", request.getParameter("departmentId"));
		String  q_areaId   = StringUtil.listQuery("Cadre.area_id", request.getParameter("areaId"));
		String  Query = StringUtil.buildWhere(q_cadreName,q_cadreSex,q_departmentId,q_areaId);
		if(request.getMethod().equals("POST")){
			Map<String,Object> where = new HashMap<String,Object>();
			Map<String, Object>	departmentList = cadreService.getUIGridData(where,Query,UIUtils.getPageParams(request));
				return ajaxReturn(response,departmentList );
			}else{
		return "directory/cadre_list";
			}
	}
	
	/**
	 * 组织线干部人员添加
	 * @throws Exception 
	 */
	@RequestMapping(value={"cadre_add"})
	public String cadreAdd(Model model,HttpServletRequest request,HttpServletResponse response,Cadre entity) throws Exception {
		if(request.getMethod().equals("POST")){
			int flag =  cadreService.save(entity);
			if(flag == 1){
				return ajaxReturn(response, null, "添加成功", 1);
			}
			else{
				return ajaxReturn(response, null, "添加失败", 0);
			}
		}
		else {
			return "directory/cadre_add";
		}
	}
	
	/**
	 * 组织线干部人员编辑
	 * @throws Exception
	 */
	@RequestMapping(value={"cadre_edit"})
	public String cadreEdit(Model model, HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			Cadre entity = cadreService.findById(id);
			Param2Bean.edit_param2Bean(request, entity);
			int flag = cadreService.update(entity);
			if (flag == 1)
				return ajaxReturn(response, null, "修改成功", 1);
			else
				return ajaxReturn(response, null, "修改失败", 0);
		} else {
			if(id!=null) model.addAttribute("entity",cadreService.findById(id));
			return "directory/cadre_edit";
		}
	}
	
	/**
	 * 组织线干部人员详情
	 * @throws Exception
	 */
	@RequestMapping(value={"cadre_detail"})
	public String cadreDetail(Model model, HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		if (id != null)
			model.addAttribute("entity", cadreService.findById(id));
		return "directory/cadre_detail";
	}
	
	/**
	 * 组织线干部人员删除
	 * @throws Exception
	 */
	@RequestMapping(value={"cadre_delete"})
	public String areaDelete(Model model, HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			int flag = cadreService.delete(id);
			if (flag == 1)
				return ajaxReturn(response, null, "修改成功", 1);
			else
				return ajaxReturn(response, null, "修改失败", 0);
		} else {
			return "directory/cadre_delete";
		}
	}
	
	/**
	 * 部门列表
	 * @throws Exception 
	 */
	@RequestMapping(value={"staff_department_list"})
	public String staffDepartmentList(Model model,HttpServletRequest request,HttpServletResponse response,Integer areaId) throws Exception {
		if (request.getMethod().equals("POST")) {
			Map<String, Object> where = new HashMap<String, Object>();
			if (areaId != null)
				where.put("area_id", areaId);
			Map<String, Object> departmentList = departmentService
					.getStatis(where, UIUtils.getPageParams(request));
			return ajaxReturn(response, departmentList);
		} else {
			return "directory/staff_department_list";
		}
	}
	
	
	/**
	 * 区域列表
	 */
	@RequestMapping(value={"area_list"})
	public String areaList(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			return ajaxReturn(response, areaService.geStatistUIGridData());
		}else
		return "directory/area_list";
	}
	
	/**
	 * 组织线干部导入
	 */
	@RequestMapping(value={"cadre_import"})
	public String cadreImport(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "directory/cadre_import";
	}
	
	/**
	 * 组织线干部人员统计
	 */
	@RequestMapping(value={"statis_list"})
	public String statisList(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "directory/statis_list";
	}
	
	//人员列表
	@RequestMapping(value={"staff_list"})
	public String staffList(Model model,HttpServletRequest request,HttpServletResponse response,Integer departmentId) {
		if(departmentId!=null)  model.addAttribute("departmentId", departmentId);
		return "directory/staff_list";
	}
}
