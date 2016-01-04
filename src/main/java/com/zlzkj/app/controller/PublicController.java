package com.zlzkj.app.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zlzkj.app.model.User;
import com.zlzkj.app.service.AdminService;
import com.zlzkj.app.service.AreaService;
import com.zlzkj.app.service.DepartmentService;
import com.zlzkj.app.service.DutiesService;
import com.zlzkj.app.service.RoleService;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.MD5String;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.app.util.UploadUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.util.Fn;

/**
 * 公共控制器
 */
@Controller
@RequestMapping(value = { "public" })
public class PublicController extends BaseController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DutiesService dutiesService;

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("imgFile") MultipartFile picWebFile) {
		System.out.println("upload");
		Map<String, Object> data = new HashMap<String, Object>();
		String picWeb = ""; // 图片保存名
		System.out.println("aaa");
		Map<String, Object> picWebInfo = UploadUtils
				.saveMultipartFile(picWebFile);
		System.out.println("bbb");
		if ((Integer) picWebInfo.get("status") > 0) { // 上传完成
			picWeb = UploadUtils.parseFileUrl(picWebInfo.get("saveName")
					.toString());
		} else { // 上传出错
			return ajaxReturn(response, null, picWebInfo.get("errorMsg")
					.toString(), 0);
		}
		data.put("url", picWeb);
		data.put("alt", "");
		return ajaxReturn(response, data, null, 1);
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = { "login" })
	public String login(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (request.getMethod() == "POST") {
			String loginName = request.getParameter("loginName");
			String loginPwd = request.getParameter("loginPass");
			if (adminService.checkLogin(loginName, MD5String.getMD5Str(loginPwd))) {
				User user = userService.findById(adminService.loginId(
						loginName, MD5String.getMD5Str(loginPwd)));
				int roleId = user.getRoleId();
				request.getSession().setAttribute("loginName", loginName);// 登录成功放入session的内容，供前端页面访问
				request.getSession().setAttribute("roleId",roleId);
				request.getSession().setAttribute("roleName",roleService.findById(roleId).getName());
			    if(user.getIsActive()==0){
				  return ajaxReturn(response, null, "该用户未被启用", 0);
			    }else{
				return ajaxReturn(response, null, "登录成功", 1);
			    }
			} else
				return ajaxReturn(response, null, "账号或密码错误", 0);
		} else {
			return "public/login";
		}
	}

	
	@RequestMapping(value = { "register_ok" })
	public String registerOk(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "public/register_ok";
	}
	@RequestMapping(value = { "register_fail" })
	public String registerFail(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return "public/register_fail";
	}
	

	@RequestMapping(value = { "logout" })
	public String logout(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Enumeration<String> em = request.getSession().getAttributeNames();
		// System.out.println(request.getSession().getAttributeNames());
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement());
		}
		request.getSession().invalidate();
		return "redirect:/public/login";
	}
	
	/**
	 * 区域列表
	 */
	@RequestMapping(value={"area_list"})
	public String areaList(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			if("combo".equals(request.getParameter("ui"))){
				return ajaxReturn(response, areaService.getUIAreaList());
			}else{
				List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("id", null);
				node.put("text", "--不限--");
				res.add(node);
				res.addAll(areaService.getUIComboData());
				return ajaxReturn(response, res);
			}
		}
		else   return "public/area_list";
	}
	
	
	@RequestMapping(value={"department_list"})
	public String departmentList(Model model,HttpServletRequest request,HttpServletResponse response,Integer areaId) {
		if(request.getMethod().equals("POST")){
			List<Row> list = new ArrayList<Row>();
			if(areaId!=null){
			    Map<String,Object> where =  new HashMap<String,Object>();
			    where.put("area_id", areaId);
			    list = departmentService.getDepartmentList(where);
			}
			if("combo".equals(request.getParameter("ui"))){
				Row row = new Row();
				row.put("id", null);
				row.put("text", "--不限--");
				list.add(0,row);
			}
			return ajaxReturn(response, list);
		}
		else   return "public/department_list";
	}
	
	
	@RequestMapping(value={"duties_list"})
	public String dutiesList(Model model,HttpServletRequest request,HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			List<Row> list = new ArrayList<Row>();
			list = dutiesService.getDutiesList(null);
			if("query".equals(request.getParameter("ui"))){
				Row row = new Row();
				row.put("id", null);
				row.put("text", "--不限--");
				list.add(0,row);
			}
			return ajaxReturn(response, list);
		}
		else   return "public/duties_list";
	}
	
	@RequestMapping(value = { "role_list" })
	public String rolelists(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if(request.getMethod().equals("POST")){
			List<Row> list = new ArrayList<Row>();
			list = roleService.getRoleLists(null);
			if("query".equals(request.getParameter("ui"))){
				Row row = new Row();
				row.put("id", null);
				row.put("text", "--不限--");
				list.add(0,row);
			}
			return ajaxReturn(response, list);
		}
		else   return "public/role_list";
	}
}
