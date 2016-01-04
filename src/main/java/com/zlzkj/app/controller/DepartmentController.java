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

import com.zlzkj.app.model.ActionNode;
import com.zlzkj.app.model.Area;
import com.zlzkj.app.model.Department;
import com.zlzkj.app.model.Duties;
import com.zlzkj.app.service.AreaService;
import com.zlzkj.app.service.DepartmentService;
import com.zlzkj.app.service.DutiesService;
import com.zlzkj.app.util.Param2Bean;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.util.Fn;

/**
 * 部门管理
 * @author Lee
 *
 */
@Controller
@RequestMapping(value={"department"})
public class DepartmentController extends BaseController{
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DutiesService dutiesService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "department/area_list";
	}
	
	
	/**
	 * 区域列表
	 */
	@RequestMapping(value={"area_list"})
	public String areaList(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			if("combo".equals(request.getParameter("ui"))){
				List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", null);
				node.put("text", "--省级区域--");
				res.add(node);
				res.addAll(areaService.getUIComboData());
				return ajaxReturn(response, res);
			}else{
				return ajaxReturn(response, areaService.getUIGridData());
			}
		}else
		return "department/area_list";
	}
	
	/**
	 * 新增区域
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"area_add"})
	public String areaAdd(HttpServletRequest request,
			HttpServletResponse response, Area entity) throws Exception {
		if (request.getMethod().equals("POST")) {
			if(entity.getPid()==null){
				entity.setLevel(1);
			}else{
				entity.setLevel(areaService.findById(entity.getPid()).getLevel()+1);
			}
			int flag = areaService.save(entity);
			if (flag == 1)
				return ajaxReturn(response, null, "添加成功", 1);
			else
				return ajaxReturn(response, null, "添加失败", 0);
		} else {
			return "department/area_add";
		}
	}
	
	
	/**
	 * 编辑区域
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"area_edit"})
	public String areaEdit(Model model, HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			Area entity = areaService.findById(id);
			Param2Bean.edit_param2Bean(request, entity);
			if(entity.getPid()==null){
				entity.setLevel(1);
			}else{
				entity.setLevel(areaService.findById(entity.getPid()).getLevel()+1);
			}
			int flag = areaService.update(entity);
			if (flag == 1)
				return ajaxReturn(response, null, "修改成功", 1);
			else
				return ajaxReturn(response, null, "修改失败", 0);
		} else {
			if(id!=null) model.addAttribute("entity",areaService.findById(id));
			return "department/area_edit";
		}
	}
	
	
	/**
	 * 删除区域
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value={"area_delete"})
	public String areaDelete(Model model, HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			int flag = areaService.delete(id);
			if (flag == 1)
				return ajaxReturn(response, null, "修改成功", 1);
			else
				return ajaxReturn(response, null, "修改失败", 0);
		} else {
			return "department/area_delete";
		}
	}
	
	
	
	
	/**
	 * 区域行政图
	 */
	@RequestMapping(value={"executive_list"})
	public String executiveList(Model model,HttpServletRequest request,HttpServletResponse response) {
		return "department/executive_list";
	}
	
	/**
	 * 部门列表
	 */
	@RequestMapping(value={"department_list"})
	public String departmentList(Model model,HttpServletRequest request,HttpServletResponse response,Integer areaId) {
		if (request.getMethod().equals("POST")) {
			Map<String, Object> where = new HashMap<String, Object>();
			if (areaId != null)
				where.put("area_id", areaId);
			Map<String, Object> departmentList = departmentService
					.getUIGridData(where, UIUtils.getPageParams(request));
			return ajaxReturn(response, departmentList);
		} else {
			return "department/department_list";
		}
	}
	
	
	@RequestMapping(value={"executive_add"})
	public String executive_add(Model model,HttpServletRequest request,HttpServletResponse response,Department entity ,Integer areaId) throws Exception {
		if (request.getMethod().equals("POST")) {
			entity.setType(areaService.findById(entity.getAreaId()).getLevel());
			int flag = departmentService.save(entity);
			if (flag == 1) {
				return ajaxReturn(response, null, "添加成功", 1);
			} else {
				return ajaxReturn(response, null, "添加失败", 0);
			}
		} else {
			if (areaId != null)
				model.addAttribute("areaId", areaId);
			return "department/executive_add";
		}
	}
	
	@RequestMapping(value={"executive_edit"})
	public String executiveEdit(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			Department entity = departmentService.findById(id);
			Param2Bean.edit_param2Bean(request, entity);
			int flag = departmentService.update(entity);
			if (flag == 1) {
				return ajaxReturn(response, null, "修改成功", 1);
			} else {
				return ajaxReturn(response, null, "修改失败", 0);
			}
		} else {
			if (id != null)
				model.addAttribute("entity", departmentService.findById(id));
			return "department/executive_edit";
		}
	}
	
	@RequestMapping(value={"executive_delete"})
	public String executiveDelete(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			int flag = departmentService.delete(id);
			if (flag == 1) {
			    return	ajaxReturn(response, null, "删除成功", 1);
			} else {
				return ajaxReturn(response, null, "删除失败", 0);
			}
		} else{
			return "department/executive_delete";

		}
	}
	
	
	/**
	 * 职务列表
	 */
	@RequestMapping(value={"duties_list"})
	public String dutiesList(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
		Map<String, Object>	departmentList = dutiesService.getUIGridData(null,UIUtils.getPageParams(request));
			return ajaxReturn(response,departmentList );
		}else{
			return "department/duties_list";
		}
	}
	
	@RequestMapping(value={"duties_add"})
	public String dutiesAdd(Model model,HttpServletRequest request,HttpServletResponse response,Duties entity) throws Exception {
		if (request.getMethod().equals("POST")) {
			entity.setAddTime(Fn.time());
			int flag = dutiesService.save(entity);
			if (flag == 1) {
			   return	ajaxReturn(response, null, "添加成功", 1);
			} else {
			return  ajaxReturn(response, null, "添加失败", 0);
			}

		} else {
			return "department/duties_add";
		}
	}
	
	@RequestMapping(value={"duties_edit"})
	public String dutiesEdit(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			Duties entity = dutiesService.findById(id);
			Param2Bean.edit_param2Bean(request, entity);
			int flag = dutiesService.update(entity);
			if (flag == 1) {
				return  ajaxReturn(response, null, "修改成功", 1);
			} else {
				return ajaxReturn(response, null, "修改失败", 0);
			}
		} else{			
		if(id!=null) model.addAttribute("entity", dutiesService.findById(id));
		return "department/duties_edit";
		}
	}
	
	@RequestMapping(value={"duties_delete"})
	public String dutiesDelete(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
			int flag = dutiesService.delete(id);
			if (flag == 1) {
			    return	ajaxReturn(response, null, "删除成功", 1);
			} else {
				return ajaxReturn(response, null, "删除失败", 0);
			}
		} else{
			return "department/duties_delete";

		}
	}
}
