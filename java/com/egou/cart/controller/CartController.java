package com.egou.cart.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.egou.cart.service.CartService;
import com.egou.commons.pojo.DataResult;
import com.egou.commons.pojo.TbItemChild;

@Controller
public class CartController {
	
	@Resource
	private CartService cartServiceImpl;
	/**
	 * 添加到购物车
	 * @param id
	 * @return
	 */
	@RequestMapping("/cart/add/{id}.html")
	public String addCart(@PathVariable long id,int num,HttpServletRequest request) {
		cartServiceImpl.addCart(id, num, request);
		return "cartSuccess";
	}
	
	/**
	 * 显示购物车里商品
	 * @return
	 */
	@RequestMapping("/cart/cart.html")
	public String settlement(HttpServletRequest request,Model model) {
		List<TbItemChild> cartlist =cartServiceImpl.getCartmsg(request);
		model.addAttribute("cartList", cartlist);
		return "cart";
	}
	@RequestMapping("/cart/update/num/{id}/{num}.action")
	@ResponseBody
	public String updateCart(@PathVariable long id,@PathVariable int num,HttpServletRequest request) {
		cartServiceImpl.updateCart(id, num, request);
		return null;
	}
	
	@RequestMapping("/cart/delete/{id}.action")
	@ResponseBody
	public DataResult delete(@PathVariable long id,HttpServletRequest request) {
		return cartServiceImpl.deleteCartItem(id, request);
	}
}
