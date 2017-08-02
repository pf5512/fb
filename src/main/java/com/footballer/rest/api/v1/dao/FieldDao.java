package com.footballer.rest.api.v1.dao;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.vo.Field;
import com.footballer.util.CommonUtil;

/**
 * Created by justin on 7/9/14.
 */

public class FieldDao extends BaseDao {

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void save(Field field)  {    	
    	CommonUtil.addAuditableAttributes(field);
        saveOrUpdate(field);
        flush();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void update(Field field)  {
        saveOrUpdate(field);
        flush();
    }

    @SuppressWarnings("unchecked")
	public List<Field> findFieldsByName(String name) {
    	String sql = "from Field f where name = ?";
    	return (List<Field>) find(sql, name);
    }

    public Field findFieldById(String id) {
    	String sql = "from Field f where id = '"+id+"'";
        return (Field) find(sql).get(0);
    }
}
