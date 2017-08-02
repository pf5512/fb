package com.footballer.rest.api.v1.dao;

import com.footballer.rest.api.v1.vo.AppSecurity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by ian on 6/21/14.
 */
public class SecurityDao extends HibernateDaoSupport {

    public void save(AppSecurity appSecurity) {
        currentSession().save(appSecurity);
    }

    public AppSecurity findAppSecurityByAppKey(String appKey) {
        Criteria criteria = currentSession().createCriteria(AppSecurity.class)
                .add(Restrictions.eq("appKey", appKey));
        return (AppSecurity) criteria.uniqueResult();
    }


    public List<AppSecurity> findAllAppSecurity() {
        @SuppressWarnings("unchecked")
		List<AppSecurity> list = currentSession().createCriteria(AppSecurity.class).list();
		return list;
    }
}
