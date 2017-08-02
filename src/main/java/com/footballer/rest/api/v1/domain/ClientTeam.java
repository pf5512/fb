package com.footballer.rest.api.v1.domain;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.rest.api.v1.adapter.ShortDateAdapter;

/**
 * Created by ian on 8/30/14.
 */
public class ClientTeam {

    private Long id;
    private String name;
    private String logo;
    private Long captainUserId;
    private Long viceCaptainUserId;
    private Integer number;
    private String technical;
    private Date createDt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getCaptainUserId() {
        return captainUserId;
    }

    public void setCaptainUserId(Long captainUserId) {
        this.captainUserId = captainUserId;
    }

    public Long getViceCaptainUserId() {
        return viceCaptainUserId;
    }

    public void setViceCaptainUserId(Long viceCaptainUserId) {
        this.viceCaptainUserId = viceCaptainUserId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	@XmlJavaTypeAdapter(ShortDateAdapter.class)
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
}
