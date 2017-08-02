package com.footballer.rest.api.v1.vo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Created by ian on 7/29/14.
 */

@Entity
@Table(name="football_player")
public class Player {

    private Long id;
    private String nickName;
    private PlayerProperty playerProperty;
    private Account account;
    private Set<Team> teams;    
    private Address address;
    private String imageUrl;
    private String deviceToken;
    
    @Column(name = "nick_name", nullable = true)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Account getAccount() {
        return account;
    }

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters=@Parameter(name = "property", value = "account"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "team_player",
            joinColumns ={@JoinColumn(name = "player_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id", referencedColumnName = "id")  })
    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    //@JoinColumn(name="player_property_id")  
	public PlayerProperty getPlayerProperty() {
		return playerProperty;
	}

	public void setPlayerProperty(PlayerProperty playerProperty) {
		this.playerProperty = playerProperty;
	}
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    @JoinColumn(name = "address_id")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Column(name = "imageUrl")
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Column(name = "deviceToken")
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
}
