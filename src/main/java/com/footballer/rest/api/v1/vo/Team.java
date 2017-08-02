package com.footballer.rest.api.v1.vo;

import javax.persistence.*;

import com.footballer.rest.api.v1.vo.base.GenericVo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "team")
public class Team extends GenericVo implements Serializable {

	private static final long serialVersionUID = -5753601780732704598L;
	
	private String name;
	private String logo;
	private Long captainUserId;
	private Long viceCaptainUserId;
	private Integer number;
	private String technical;
	
	private List<Field> fields;
    private Set<Player> players;
    	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)  
    @JoinTable(name = "team_field",   
            joinColumns ={@JoinColumn(name = "team_id", referencedColumnName = "id") },   
            inverseJoinColumns = { @JoinColumn(name = "field_id", referencedColumnName = "id")  })   
    public List<Field> getFields() {  
        return fields;  
    }  
  
    public void setFields(List<Field> fields) {  
        this.fields = fields;  
    }  
    
    public Team() {
    	super();
    }
    
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "logo", nullable = true)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(name = "captain_user_id", nullable = true)
	public Long getCaptainUserId() {
		return captainUserId;
	}

	public void setCaptainUserId(Long captainUserId) {
		this.captainUserId = captainUserId;
	}
	
	@Column(name = "viceCaptain_user_id", nullable = true)
	public Long getViceCaptainUserId() {
		return viceCaptainUserId;
	}

	public void setViceCaptainUserId(Long viceCaptainUserId) {
		this.viceCaptainUserId = viceCaptainUserId;
	}
	
	@Column(name = "number", nullable = true)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Column(name = "technical", nullable = true)
	public String getTechnical() {
		return technical;
	}

	public void setTechnical(String technical)
    {
		this.technical = technical;
	}

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teams")
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
