package com.egou.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.egou.cart.service.CartService;
import com.egou.commons.pojo.DataResult;
import com.egou.commons.pojo.TbItemChild;
import com.egou.commons.utils.CookieUtils;
import com.egou.commons.utils.HttpClientUtil;
import com.egou.commons.utils.JsonUtil;
import com.egou.dubbo.service.TbItemDubboService;
import com.egou.pojo.TbItem;
import com.egou.redis.dao.JedisDao;
@Service
public class CartServiceImpl implements CartService{
	@Reference
	private TbItemDubboService itemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${login.url}")
	private String loginurl;
	@Value("${cart.key}")
	private String cartkey;
	@Override
	public void addCart(long itemId, int num, HttpServletRequest request) {
		List<TbItemChild> list = new ArrayList<TbItemChild>();
		//验证用户信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String dataresultStr =HttpClientUtil.doGet(loginurl+"/"+token);
		DataResult dr =JsonUtil.jsonToObject(dataresultStr, DataResult.class);
		String key = cartkey+((LinkedHashMap)dr.getData()).get("username");
		//缓存中是否已经存在购物车
		if(jedisDaoImpl.exists(key)){
			list = JsonUtil.jsonToList(jedisDaoImpl.get(key),TbItemChild.class);
			for(TbItemChild child :list) {
				//商品是否已经存在购物车中
				if(child.getId()==itemId) {
					child.setNum(child.getNum()+num);
					String value=JsonUtil.objectToJson(list);
					jedisDaoImpl.set(key, value);
					return ;
				}
			}
		}
		//从数据库查商品信息
		TbItemChild child = new TbItemChild();
		TbItem item = itemDubboServiceImpl.selById(itemId);
		child.setId(itemId);
		child.setNum(num);
		child.setPrice(item.getPrice());
		child.setTitle(item.getTitle());
		child.setImages(item.getImage()==null?new String[1]:item.getImage().split(","));
		//不管购物车存不存在，往购物车放新的商品
		list.add(child);
		String value=JsonUtil.objectToJson(list);
		jedisDaoImpl.set(key, value);
	}
	@Override
	public List<TbItemChild> getCartmsg(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String dataResultstr = HttpClientUtil.doGet(loginurl+"/"+token);
		DataResult dr = JsonUtil.jsonToObject(dataResultstr, DataResult.class);
		if(dr.getStatus()==200) {
			String liststr = jedisDaoImpl.get(cartkey+((LinkedHashMap)dr.getData()).get("username"));
			List<TbItemChild> list = JsonUtil.jsonToList(liststr, TbItemChild.class);
			
			return list;
		}
		return null;
	}
	@Override
	public int updateCart(long itemId, int num,HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String dataResultstr = HttpClientUtil.doGet(loginurl+"/"+token);
		DataResult dr = JsonUtil.jsonToObject(dataResultstr, DataResult.class);
		String key = cartkey+((LinkedHashMap)dr.getData()).get("username");
		if(dr.getStatus()==200) {
			String liststr = jedisDaoImpl.get(key);
			List<TbItemChild> list = JsonUtil.jsonToList(liststr, TbItemChild.class);
			for(TbItemChild child:list) {
				if((long)child.getId()==itemId) {
					child.setNum(num);
					String value = JsonUtil.objectToJson(list);
					String ok =	jedisDaoImpl.set(key, value);
					return 1;
				}
			}
		}	
		return 0;
	}
	@Override
	public DataResult deleteCartItem(long itemId, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String dataResultstr = HttpClientUtil.doGet(loginurl+"/"+token);
		DataResult dr = JsonUtil.jsonToObject(dataResultstr, DataResult.class);
		String key = cartkey+((LinkedHashMap)dr.getData()).get("username");
		String liststr = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtil.jsonToList(liststr, TbItemChild.class);
		TbItemChild itemChild = null;
		for(TbItemChild child:list) {
			//遍历不能移除里面的元素
			if((long)child.getId()==itemId) {
				itemChild = child;
			}
		}	
		boolean flag =list.remove(itemChild);
		if(flag) {
			String ok = jedisDaoImpl.set(key, JsonUtil.objectToJson(list));
			DataResult dataResult = new DataResult();
			if(ok.equals("OK")) {
				dataResult.setStatus(200);
			}
			
			return dataResult;
		}
		return null;
	}

}
