package com.footballer.rest.api.v2.manager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.result.PlayerFeeOfEventResult;


@Service
public class PlayerBalanceLineManager {
	
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@SuppressWarnings("unchecked")
	public List<PlayerFeeOfEventResult> findAllPlayerChargeFeeOfEventByEventId(Long eventId){
		List<PlayerFeeOfEventResult> list = (List<PlayerFeeOfEventResult>) mybatisBaseDao.selectList("findAllPlayerChargeFeeOfEventByEventId", eventId);
		return list;
	}
}