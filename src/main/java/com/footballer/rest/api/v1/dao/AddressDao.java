package com.footballer.rest.api.v1.dao;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.vo.Address;
import com.footballer.util.CommonUtil;

/**
 * Created by justin on 01/22/15.
 */

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class AddressDao extends BaseDao {

    public void save(Address address)  {    	
    	CommonUtil.addAuditableAttributes(address);
        saveOrUpdate(address);
        flush();
    }
    
    public void update(Address address)  {
    	saveOrUpdate(address);
    	flush();
    }

}
