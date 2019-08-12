package com.egou.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.egou.commons.pojo.EasyUIDataGrid;
import com.egou.dubbo.service.TbItemParamDubboService;
import com.egou.mapper.TbItemParamItemMapper;
import com.egou.mapper.TbItemParamMapper;
import com.egou.pojo.TbItemParam;
import com.egou.pojo.TbItemParamExample;
import com.egou.pojo.TbItemParamItem;
import com.egou.pojo.TbItemParamItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	public EasyUIDataGrid showTItemParam(int page, int rows) {
		//xxxxExample()设置了什么，where语句添加条件
		TbItemParamExample example = new TbItemParamExample();//用来构造where语句的
		//设置查询的SQL语句
		List<TbItemParam> bloBs = tbItemParamMapper.selectByExampleWithBLOBs(example);
		//结合分页插件产生最终结果，封装到PageInfo
		PageHelper.startPage(page, rows);
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(bloBs);
		//放到实体类中
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pageInfo.getList());
		dataGrid.setTotal(pageInfo.getTotal());
		return dataGrid;
	}
	public int deleteItemParam(String ids) throws Exception {
		String[] idsStr = ids.split(",");
		int index=0;
		for(String id:idsStr) {
			index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));			
		}
		if(index==idsStr.length) {
			return 1;
		}else {
			throw new Exception("删除失败，请重试");
		}
	}
	public TbItemParam selItemParamByCatId(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catId);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			//要求每个类目只有一个模版
			return list.get(0);
		}
		return null;
	}
	public int insItemParam(TbItemParam itemParam) {
		int index = tbItemParamMapper.insertSelective(itemParam);
		if(index==1) {
			return 1;
		}
		return 0;
	}
	public TbItemParamItem selParamItemByItemId(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}
		
	}
}
