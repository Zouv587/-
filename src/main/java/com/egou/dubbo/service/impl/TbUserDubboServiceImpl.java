package com.egou.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.egou.dubbo.service.TbUserDubboService;
import com.egou.mapper.TbUserMapper;
import com.egou.pojo.TbUser;
import com.egou.pojo.TbUserExample;

public class TbUserDubboServiceImpl implements TbUserDubboService{
	@Resource
	private TbUserMapper tbuserMapper;
	public TbUser userselect(TbUser user) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
		List<TbUser> list = tbuserMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	public int insUser(TbUser user) {
		int index =tbuserMapper.insertSelective(user);
		if(index==1) {
			return 1;
		}
		return 0;
	}
	public int checkRegister(String uname,String phoneNum,int in) {
		TbUserExample example = new TbUserExample();
		
		if(in==1) {
			example.createCriteria().andUsernameEqualTo(uname);
		}else if(in==2) {
			example.createCriteria().andPhoneEqualTo(phoneNum);
		}
		List<TbUser> ul =tbuserMapper.selectByExample(example);

		if(ul!=null&&ul.size()>0) {
			return 0;
		}
		return 1;
	}
	public TbUser loginUser(String uname) {
		TbUserExample example = new TbUserExample();
		example.createCriteria().andUsernameEqualTo(uname);
		List<TbUser> ul =tbuserMapper.selectByExample(example);
		if(ul!=null&&ul.size()>0) {
			TbUser u = ul.get(0);
			return u;
		}
		return null;
	}
}
