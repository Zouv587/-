package com.egou.dubbo.service.impl;

import javax.annotation.Resource;

import com.egou.dubbo.service.TbItemDescDubboService;
import com.egou.mapper.TbItemDescMapper;
import com.egou.pojo.TbItemDesc;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService{
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	/**
	 * 新增商品描述
	 */
	public int insItemDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.insTbItemDesc(itemDesc);
	}
	public int updateItemDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.updateTbItemDesc(itemDesc);
	}
	public int deleeItemDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.deleteTbItemDesc(itemDesc);
	}
	public TbItemDesc selItemDesc(long itemId) {
		return tbItemDescMapper.selectByprimaryKey(itemId);
	}

}
