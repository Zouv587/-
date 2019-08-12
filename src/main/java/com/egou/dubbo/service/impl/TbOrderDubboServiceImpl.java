package com.egou.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.egou.dubbo.service.TbOrderDubboService;
import com.egou.mapper.TbOrderItemMapper;
import com.egou.mapper.TbOrderMapper;
import com.egou.mapper.TbOrderShippingMapper;
import com.egou.pojo.TbOrder;
import com.egou.pojo.TbOrderItem;
import com.egou.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService{
	@Resource
	private TbOrderMapper orderMapper;
	@Resource
	private TbOrderItemMapper orderItemMapper;
	@Resource
	private TbOrderShippingMapper orderShipingMapper;
	
	public int insOrder(TbOrder order, List<TbOrderItem> orderItem, TbOrderShipping orderShipping) throws Exception {
		int index =orderMapper.insertSelective(order);
		for(TbOrderItem ot:orderItem) {
			index +=orderItemMapper.insertSelective(ot);
		}
		index += orderShipingMapper.insertSelective(orderShipping);
		if(index==2+orderItem.size()) {
			return 1;
		}else {
			throw new Exception("订单创建失败");
		}
	}

}
