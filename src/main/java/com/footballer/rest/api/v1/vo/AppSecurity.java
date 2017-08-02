package com.footballer.rest.api.v1.vo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ian on 6/21/14.
 */

@Entity
@Table(name = "app_security")
public class AppSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "app_id", nullable = true)
    private long appId;

    @Column(name = "app_security", nullable = true)
    private String appSecurity;

    private String type;

    public AppSecurity() {}

    public AppSecurity(long appId, String appSecurity) {
        this.appId = appId;
        this.appSecurity = appSecurity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppSecurity() {
        return appSecurity;
    }

    public void setAppSecurity(String appSecurity) {
        this.appSecurity = appSecurity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
