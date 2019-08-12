package com.egou.dubbo.service.impl;
import java.util.List;

import javax.annotation.Resource;
import com.egou.commons.pojo.EasyUIDataGrid;
import com.egou.dubbo.service.TbItemDubboService;
import com.egou.mapper.TbItemDescMapper;
import com.egou.mapper.TbItemMapper;
import com.egou.mapper.TbItemParamItemMapper;
import com.egou.pojo.TbItem;
import com.egou.pojo.TbItemDesc;
import com.egou.pojo.TbItemExample;
import com.egou.pojo.TbItemParamItem;
import com.egou.pojo.TbItemParamItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	public EasyUIDataGrid show(int page, int rows) {
		PageHelper.startPage(page,rows);
		//查询全部
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		//分页代码
		//设置分页条件
		PageInfo<TbItem> pi = new PageInfo<TbItem>(list);
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}
	
	public int updateTbItem(TbItem item,TbItemDesc itemDesc,TbItemParamItem paramItem) throws Exception {
		int index=0;
		try {
			index += tbItemMapper.updateByPrimaryKeySelective(item);
			index += tbItemDescMapper.updateTbItemDesc(itemDesc);
			index += tbItemParamItemMapper.updateByPrimaryKeySelective(paramItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(index==3) {
			return 1;
		}else {
			throw new Exception("更改商品状态失败，请重试");
		}
	}

	public int updateTbItemBySelective(TbItem item) {
		int index=0;
		index = tbItemMapper.updateByPrimaryKeySelective(item);
		if(index==1) {
			return index;
		}
		return 0;
	}

	public int insertTbItem(TbItem item) {
		return tbItemMapper.insertSelective(item);
	}

	public int insertTbItem(TbItem item, TbItemDesc desc,TbItemParamItem paramItem) throws Exception {
		int index=0;
		try {
			index+=tbItemMapper.insertSelective(item);
			index+=tbItemDescMapper.insTbItemDesc(desc);
			index+=tbItemParamItemMapper.insertSelective(paramItem);
		} catch (Exception e) {//抛出异常，spring进行事务回滚
			e.printStackTrace();
		}
		if(index==3) {
			return 1;
		}else{
			throw new Exception("新增商品失败，进行事务回滚");
		}
	}

	public List<TbItem> showAll(byte status) {
		TbItemExample tbexample = new TbItemExample();
		tbexample.createCriteria().andStatusEqualTo(status);
		return tbItemMapper.selectByExample(tbexample);
	}

	public TbItem selById(long id) {
		
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
