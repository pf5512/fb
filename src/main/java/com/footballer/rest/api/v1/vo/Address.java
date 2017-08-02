package com.footballer.rest.api.v1.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;

@Entity
@Table(name="address")
public class Address extends GenericVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 669902070942810534L;
	private String longitude;
	private String latitude;
	private String name;
	private List<Player> players;
	
	@Column(name = "longitude")
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "latitude")
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
		
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
