package com.zlzkj.app.interceptor;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zlzkj.app.service.ActionNodeService;

/**
 * 核心拦截器，配置request的一些初始值
 * @author Simon
 *
 */
public class CoreInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ActionNodeService actionNodeService;
	
	/**
	 * 拦截器白名单列表
	 */
	public String[] exclude; 
	
	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//白名单放行
		if(exclude!=null && exclude.length!=0){
			String uri = request.getRequestURI().substring(request.getContextPath().length());
			for(String one:exclude){
				if(uri.startsWith(one)){
					return true;
				}
			}
		}
		//解析url验证是否显示左侧
		String path = request.getServletPath();  
        //String[] str = path.split("/");
         //拦截地址清单, 如果请求中是以以下路径开头，则进行拦截
        String[] urls = {
//        		"/project","/enterprise","/evaluate","/department","/user","/admin"
        };
        
        //验证是否已登陆,已登陆将登陆信息输出到页面上
//        if(request.getSession().getAttribute("loginName") == null){
//			for(String s:urls){
//	        	if(path.contains(s))
//	        		this.sendRedirect(request, response, "/public/login");
//	        }
//			
//		}
        if(request.getSession().getAttribute("loginName") != null){
        	
        	
        	
        	System.out.println("url:"+request.getRequestURI().substring(request.getContextPath().length()+1));
        	//权限拦截
			for(String s:urls){
				if(path.contains(s)){
					if(!actionNodeService.findByNode(request.getRequestURI().substring(request.getContextPath().length()+1),(Integer) request.getSession().getAttribute("roleId"))){
						return true;
					} else{
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
//						httpServletResponse.setHeader("Access-Status", "-1");
//						response.setHeader("Access-Status", "-1");
						response.setDateHeader("Expires", 0);
						PrintWriter out = response.getWriter();
			            out.println("<div style='text-align:center;font-size:16px;color:#ff851b;margin:20px;'>您没有权限!</div>");
			            out.flush();
			            out.close();
					}
				}
//					System.out.println("String s:"+s);
					
			}
        	
        	return true;
        }else{
        	response.sendRedirect(request.getContextPath() + "/public/login");
        }
        
		return true;
		
		
//		if(request.getSession().getAttribute("loginName") == null){
//			response.sendRedirect(request.getContextPath() + "/public/login");
//		} else if(userService.)
	}
//	/**
//	 * 验证成功设置跳转地址
//	 * @throws IOException 
//	 */
//	private void sendRedirect(HttpServletRequest request, HttpServletResponse response,String loginUrl) throws IOException{
//		if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			response.setHeader("Cache-Control", "no-cache");
////			httpServletResponse.setHeader("Access-Status", "-1");
//			response.setHeader("Access-Status", "-1");
//			response.setDateHeader("Expires", 0);
//			PrintWriter out = response.getWriter();
//            out.println("{\"data\":\""+request.getContextPath()+loginUrl+"\",\"info\":\"请先登录!\",\"status\":-1}");
//            out.flush();
//            out.close();
//			response.sendRedirect("/public/login");
//		}else{
//			response.sendRedirect(request.getContextPath()+loginUrl);
//		}
//	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
