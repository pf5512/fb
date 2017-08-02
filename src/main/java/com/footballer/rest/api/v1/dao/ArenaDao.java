package com.footballer.rest.api.v1.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.domain.ArenaField;
import com.footballer.rest.api.v1.vo.Address;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.util.CommonUtil;

/**
 * Created by justin on 01/16/15.
 */

public class ArenaDao extends BaseDao {
	
	@Autowired
	public AddressDao addressDao;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void save(Arena arena)  {    
    	CommonUtil.addAuditableAttributes(arena);
        saveOrUpdate(arena);
        flush();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void update(Arena arena)  {
        saveOrUpdate(arena);
        flush();
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<Field> findAllFieldsById(Long arena_id) {
    	 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Field.class);
         if (arena_id != null) {
        	 criteria.add(Property.forName("arena_id").eq(arena_id));
         }
         criteria.addOrder(Order.asc("id"));
         List<Field> fields = criteria.list();
         return fields;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<ArenaField> findAllArenasByName(String name) {
    	 List<ArenaField> arenaFields = new ArrayList<ArenaField>();	
    	
    	 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Arena.class);
    	 if (name != null) {
    		 criteria.add(Restrictions.like("name", "%"+name+"%"));
         }
    	 
    	 criteria.addOrder(Order.asc("id"));
    	 @SuppressWarnings("unchecked")
		 List<Arena> arenas = criteria.list();
         for(Arena arena: arenas){
        	 ArenaField arenaField = new ArenaField();
        	 Address address = addressDao.findById(Address.class, arena.getAddressId());
             arena.setAddressName(address.getName());
         	 arenaField.setArena(arena);
         	 arenaField.setArenaLongitude(address.getLongitude());
         	 arenaField.setArenaLatitude(address.getLatitude());
        	 arenaField.setFields(findAllFieldsById(arena.getId()));
        	 arenaFields.add(arenaField);
         }
         return arenaFields;
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public List<ArenaField> findAllArenas() {
    	 List<ArenaField> arenaFields = new ArrayList<ArenaField>();	
    	
    	 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Arena.class);
    	 criteria.addOrder(Order.asc("id"));
    	 @SuppressWarnings("unchecked")
		 List<Arena> arenas = criteria.list();
         for(Arena arena: arenas){
        	 ArenaField arenaField = new ArenaField();
             Address address = addressDao.findById(Address.class, arena.getAddressId());
             arena.setAddressName(address.getName());
        	 arenaField.setArena(arena);
        	 arenaField.setArenaLongitude(address.getLongitude());
        	 arenaField.setArenaLatitude(address.getLatitude());
        	 arenaField.setFields(findAllFieldsById(arena.getId()));
        	 arenaFields.add(arenaField);
         }
         return arenaFields;
    }
}
