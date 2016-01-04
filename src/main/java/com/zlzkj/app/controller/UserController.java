package com.zlzkj.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zlzkj.app.model.User;
import com.zlzkj.app.service.UserService;
import com.zlzkj.app.util.JsonUtil;
import com.zlzkj.app.util.MD5String;
import com.zlzkj.app.util.Param2Bean;
import com.zlzkj.app.util.StringUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.base.BaseController;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.util.Fn;

/**
 * 平台用户控制器
 */
@Controller
@RequestMapping(value={"user"})
public class UserController extends BaseController{

	@Autowired
	private UserService userService;

	@RequestMapping(value={"/"})
	public String index(Model model,HttpServletRequest request,HttpServletResponse response) {

		return "user/list";
	}

	@RequestMapping(value={"list"})
	public String list(Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		if(request.getMethod().equals("POST")){
			String name =  request.getParameter("name");
			if (!StringUtil.isEmpty(name)) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
			}
			String  q_name  = StringUtil.fuzzyQuery("User.name", name);
			String  q_roleId   = StringUtil.listQuery("role_id", request.getParameter("roleId"));
			String  q_departmentId   = StringUtil.listQuery("User.department_id", request.getParameter("departmentId"));
			String  q_areaId   = StringUtil.listQuery("User.area_id", request.getParameter("areaId"));
			String  Query = StringUtil.buildWhere(q_name,q_roleId,q_departmentId,q_areaId);
		Map<String, Object>	userList = userService.getUIGridData(null,Query,UIUtils.getPageParams(request));
			return ajaxReturn(response,userList );
		}
		else{
		return "user/list";
		}
	}
	
	
	@RequestMapping(value={"user_add"})
	public String userAdd(Model model,HttpServletRequest request,HttpServletResponse response,User entity) throws Exception {
		if (request.getMethod().equals("POST")) {
			if (userService.userNameIsExist(entity.getName())) {
				return ajaxReturn(response, null, "用户名已存在", 0);
			} else {
				entity.setPassword(MD5String.getMD5Str(entity.getPassword()));
				entity.setAddTime(Fn.time());
				int flag = userService.save(entity);
				if (flag == 1) {
					return ajaxReturn(response, null, "添加成功", 1);
				} else {
					return ajaxReturn(response, null, "添加失败", 0);
				}
			}
		} else {
			return "user/user_add";
		}
	}
	
	
	
	@RequestMapping(value={"user_edit"})
	public String userEdit(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
				User entity  = userService.findById(id);
				Param2Bean.edit_param2Bean(request, entity);
				int flag = userService.update(entity);
				if (flag == 1) {
					return ajaxReturn(response, null, "修改成功", 1);
				} else {
					return ajaxReturn(response, null, "修改失败", 0);
				}
		} else {
			if(id!=null) model.addAttribute("entity", userService.findById(id));
			return "user/user_edit";
		}
	}
	
	
	@RequestMapping(value={"reset_pass"})
	public String resetPass(Model model,HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception {
		if (request.getMethod().equals("POST")) {
				User entity  = userService.findById(id);
				Param2Bean.edit_param2Bean(request, entity);
				entity.setPassword(MD5String.getMD5Str(entity.getPassword()));
				int flag = userService.update(entity);
				if (flag == 1) {
					return ajaxReturn(response, null, "重置成功", 1);
				} else {
					return ajaxReturn(response, null, "重置失败", 0);
				}
		} else {
			if(id!=null) model.addAttribute("entity", userService.findById(id));
			return "user/reset_pass";
		}
	}
	
	
	@RequestMapping(value={"change_pass"})
	public String changePass(Model model,HttpServletRequest request,HttpServletResponse response,String name,
			String OPass, String NPass) throws Exception {
		if (request.getMethod().equals("POST")) {
			HashMap<String, Object> where = new HashMap<String, Object>();
			where.put("name", name);
			where.put("password", MD5String.getMD5Str(OPass));
			User user = userService.getObjByProperties(where);
			if (StringUtil.isEmpty(user))
				return ajaxReturn(response, null, "原密码错误", 0);
			else {
				user.setPassword(MD5String.getMD5Str(NPass));
				int temp = userService.update(user);
				if (temp == 1)
					return ajaxReturn(response, null, "修改成功", 1);
				else
					return ajaxReturn(response, null, "修改失败", 0);
			}
		}
		return "user/change_pass";
	}

}


