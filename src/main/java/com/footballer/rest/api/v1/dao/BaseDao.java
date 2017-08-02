package com.footballer.rest.api.v1.dao;

import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ian on 8/19/14.
 */
public abstract class BaseDao extends HibernateTemplate {

    @SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
    public <T> T findById(Class<T> clazz, Long id) {
        return (T) getSessionFactory().getCurrentSession().get(clazz, id);
    }
    
    /*
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public <T> T save(T entity) {
        Session session = getSessionFactory().getCurrentSession();

        session.saveOrUpdate(entity);
        session.flush();

        return entity;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public <T> T delete(T entity) {
        Session session = getSessionFactory().getCurrentSession();

        session.delete(entity);
        session.flush();

        return entity;
    }
    */

}
