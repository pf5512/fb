package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.util.ObjectUtil;

@Service
public class ArenaFieldManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	
	
	@SuppressWarnings("unchecked")
	public List<FieldChargeStandard> getArenaFieldChargeStandardsByArenaId(Long arenaId){
		return (List<FieldChargeStandard>) mybatisBaseDao.selectList("getArenaFieldChargeStandardsByArenaId", arenaId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveArenaFieldStandard(Long arenaId, Long fieldId, Date startTime, Date endTime, BigDecimal price, BigDecimal barginPrice){
		FieldChargeStandard fcs = new FieldChargeStandard();
		fcs.setArenaId(arenaId);
		fcs.setFieldId(fieldId);
		fcs.setStartTime(startTime);
		fcs.setEndTime(endTime);
		fcs.setPrice(price);
		fcs.setBarginPrice(barginPrice);
		
		mybatisBaseDao.insert("saveArenaFieldStandard", fcs);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void updateSameTimeShiftArenaFieldStandard(Long arenaId, Long arenaFieldStandardId, Date startTime, Date endTime){
		FieldChargeStandard fcs = new FieldChargeStandard();
		fcs.setArenaId(arenaId);
		fcs.setId(arenaFieldStandardId);
		fcs.setStartTime(startTime);
		fcs.setEndTime(endTime);

		mybatisBaseDao.update("updateSameTimeShiftArenaFieldStandard", fcs);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void updateArenaFieldStandardPriceByFCSId(Long ArenaFieldStandardId, BigDecimal price, BigDecimal weekendPrice){
		FieldChargeStandard fcs = new FieldChargeStandard();
		fcs.setId(ArenaFieldStandardId);
		fcs.setPrice(price);
		fcs.setWeekendPrice(weekendPrice);
		mybatisBaseDao.update("updateArenaFieldStandardPriceByFCSId", fcs);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteArenaFieldStandard(Long fieldChargeStandardId){
		mybatisBaseDao.delete("deleteArenaFieldStandard", fieldChargeStandardId);
	}
	
	public FieldChargeStandard getFieldChargeStandardById(Long fieldChargeStandardId){
		return (FieldChargeStandard) mybatisBaseDao.selectOne("getFieldChargeStandardById", fieldChargeStandardId);
	}
}

