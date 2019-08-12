package com.egou.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.egou.dubbo.service.TbItemCatDubboService;
import com.egou.mapper.TbItemCatMapper;
import com.egou.pojo.TbItemCat;
import com.egou.pojo.TbItemCatExample;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{
	@Resource
	private TbItemCatMapper tbItemCatMapper;
	public List<TbItemCat> selTbItemCat(long id) {
		TbItemCatExample example = new TbItemCatExample();
		example.createCriteria().andParentIdEqualTo(id);
		List<TbItemCat> cats = tbItemCatMapper.selectByExample(example);
		
		return cats;
	}
	public TbItemCat selByPrimaryKey(long id) {
		return tbItemCatMapper.selectByPrimaryKey(id);
	}
}
