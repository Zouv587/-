package com.egou.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.egou.commons.pojo.DataResult;
import com.egou.commons.pojo.TbItemChild;

public interface CartService {
	/**
	 * 往缓存中添加购物车信息
	 * @param itemId
	 * @param num
	 * @param request
	 */
	public void addCart(long itemId,int num,HttpServletRequest request);
	
	/**
	 * 获取购物车信息
	 * @return
	 */
	public List<TbItemChild> getCartmsg(HttpServletRequest request);
	
	/**
	 * 修改购物车的数量
	 * @param itemId
	 * @param num
	 * @return
	 */
	public int updateCart(long itemId,int num,HttpServletRequest request);
	
	/**
	 * 删除购物车商品
	 * @param itemId
	 * @param request
	 * @return
	 */
	public DataResult deleteCartItem(long itemId,HttpServletRequest request);
}
