package com.egou.mapper;

import com.egou.pojo.TbItemDesc;

public interface TbItemDescMapper {
	int insTbItemDesc(TbItemDesc itemDesc);
	int updateTbItemDesc(TbItemDesc itemDesc);
	int deleteTbItemDesc(TbItemDesc itemDesc);
	TbItemDesc selectByprimaryKey(long itemId);
}
