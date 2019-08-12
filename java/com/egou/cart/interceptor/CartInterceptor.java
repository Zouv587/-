package com.egou.cart.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.impl.client.HttpClients;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.egou.commons.pojo.DataResult;
import com.egou.commons.utils.CookieUtils;
import com.egou.commons.utils.HttpClientUtil;
import com.egou.commons.utils.JsonUtil;

public class CartInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//是否登录
		if(token!=null&&!token.equals("")) {
			String doGet = HttpClientUtil.doGet("http://localhost:8084/user/token/"+token);
			DataResult dr = JsonUtil.jsonToObject(doGet, DataResult.class);
			if(dr.getStatus()==200) {
				return true;
			}
		}
		//这里是在登录成功后给添加购物车传值，不然会报错
		String num = request.getParameter("num");
		response.sendRedirect("http://localhost:8084/user/showLogin?innerurl="+request.getRequestURL()+"3%Fnum="+num);
		return false;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
