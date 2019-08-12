package com.egou.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.egou.commons.pojo.EasyUIDataGrid;
import com.egou.dubbo.service.ContentDubboService;
import com.egou.mapper.TbContentCategoryMapper;
import com.egou.mapper.TbContentMapper;
import com.egou.pojo.TbContent;
import com.egou.pojo.TbContentCategory;
import com.egou.pojo.TbContentCategoryExample;
import com.egou.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class ContentDubboServiceImpl implements ContentDubboService{
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Resource
	private TbContentMapper tbContentMapper;
	public List<TbContentCategory> showContentCat(long catid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(catid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}
	public EasyUIDataGrid selContentByCatId(int page, int rows, long catId) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		if(catId!=0) {
			example.createCriteria().andCategoryIdEqualTo(catId);
		}
		List<TbContent> bloBs = tbContentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pi =new PageInfo<TbContent>(bloBs);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}
	public int insContentCat(TbContentCategory cate) throws Exception {
		int index=tbContentCategoryMapper.insert(cate);
		if(index!=1) {
			throw new Exception();
		}
		return index;
	}
	public int updateContentCat(TbContentCategory cate) throws Exception {
		int index=tbContentCategoryMapper.updateByPrimaryKeySelective(cate);
		if(index!=1) {
			throw new Exception();
		}
		return index;
	}
	public TbContentCategory selBycatId(long catId) {
		return  tbContentCategoryMapper.selectByPrimaryKey(catId);
	}
	public long countByPid(long pid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.countByExample(example);
	}
	
	public int insertContent(TbContent con) throws Exception {
		int index = tbContentMapper.insertSelective(con);
		if(index==1) {
			return index;
		}else {
			throw new Exception("内容新增失败，请稍后再试");
		}
	}
	public int updateContent(TbContent con) throws Exception {
		int index=tbContentMapper.updateByPrimaryKeySelective(con);
		if(index!=1) {
			throw new Exception("内容更改失败，请稍后再试");
		}
		return index;
	}
	public int deleteContent(long[] ids) throws Exception {
		int index=0;
		for(int i=0;i<ids.length;i++) {
			index += tbContentMapper.deleteByPrimaryKey(ids[i]);
			
		}
		if(index==ids.length) {
			return 1;
		}else {
			throw new Exception("内容不存在，请重试!");
		}
	}
	public List<TbContent> selByCount(int count, boolean isSorted) {
		TbContentExample example = new TbContentExample();
		if(isSorted) {
			example.setOrderByClause("updated desc");
		}
		if(count!=0) {
			PageHelper.startPage(1, count);
			List<TbContent> bloBs = tbContentMapper.selectByExampleWithBLOBs(example);
			PageInfo<TbContent> pi = new PageInfo<TbContent>(bloBs);
			return pi.getList();
		}else {
			return tbContentMapper.selectByExampleWithBLOBs(example);
		}
	}
	
	
	

}
