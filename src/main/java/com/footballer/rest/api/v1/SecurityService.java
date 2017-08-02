package com.footballer.rest.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.footballer.rest.api.v1.dao.SecurityDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.AppSecurity;

/**
 * Created by ian on 6/21/14.
 *
 * We need load app key token to cache to do security filter
 */
@Transactional
public class SecurityService {

    @Autowired
    private SecurityDao securityDao;

    @Autowired
    private UserDao userDao;

    public AppSecurity findAppSecurityByAppKey(String appKey) {
        return securityDao.findAppSecurityByAppKey(appKey);
    }

    public void createAppSecurity(AppSecurity appSecurity) {
        securityDao.save(appSecurity);
    }

    public List<AppSecurity> findAllAppSecurity() {
        return securityDao.findAllAppSecurity();
    }
    
    //@Cacheable(value = "accountCache", key = "#identify")
    public Account findUserByIdentify(String identify) {
        List<Account> accounts = userDao.findUsersByIdentify(identify);

        if (!CollectionUtils.isEmpty(accounts)) {
            return accounts.get(0);
        }

        return null;
    }
}
