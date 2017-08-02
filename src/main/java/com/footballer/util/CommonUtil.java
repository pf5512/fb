package com.footballer.util;

import java.util.Date;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.rest.api.v1.vo.base.BaseRecordVo;

public class CommonUtil {

	public static void addAuditableAttributes(BaseRecordVo target) {
		ApplicationContext context = AppContextHolder.getContext();
        addAuditableAttributes(context, target);
    }
   
    public static void addAuditableAttributes(ApplicationContext context, BaseRecordVo target) {
    	if (context != null && context.getAccount() != null) {
	        target.setCreateBy(context.getAccount().getId().toString());
	        target.setUpdateBy(context.getAccount().getId().toString());
    	}
        Date curDate = new Date();
        target.setCreateDt(curDate);
        target.setUpdateDt(curDate);
    }
    
    
    //for V2
    public static void addAuditableAttributes(com.footballer.rest.api.v2.vo.base.BaseRecordVo target) {
		ApplicationContext context = AppContextHolder.getContext();
        addAuditableAttributes(context, target);
    }
    
	public static void addAuditableAttributes(ApplicationContext context,
			com.footballer.rest.api.v2.vo.base.BaseRecordVo target) {
		if (null != context) {
	        target.setCreateBy(context.getAccount().getId().toString());
	        target.setUpdateBy(context.getAccount().getId().toString());
		}
        Date curDate = new Date();
        target.setCreateDt(curDate);
        target.setUpdateDt(curDate);
    }

}
