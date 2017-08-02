package com.footballer.rest.api.v1.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;

@Entity
@Table(name = "field")
public class Field extends GenericVo implements Serializable {

	private static final long serialVersionUID = -8314697199055303670L;
	
	private Long arena_id;
	private String name;
	private Integer number;
	private String type;
	private Double price;
	private String conditions;
	private String pic;
	private BigDecimal cancelReservationFee;
	private Long timeTemplateId;
	
//	private List<Team> teams;
//	
//	
//	@ManyToMany(mappedBy = "fields")  
//    public List<Team> getTeams() {  
//        return teams;  
//    }  
//  
//    public void setTeams(List<Team> teams) {  
//        this.teams = teams;  
//    }  
	
	public Field() {
	}

	@Column(name = "arena_id", nullable = false)
	public Long getArena_id() {
		return arena_id;
	}

	public void setArena_id(Long arena_id) {
		this.arena_id = arena_id;
	}
	
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "number", nullable = true)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	@Column(name = "type", nullable = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "price", nullable = true)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "time_template_id", nullable = true)
	public Long getTimeTemplateId() {
		return timeTemplateId;
	}

	public void setTimeTemplateId(Long timeTemplateId) {
		this.timeTemplateId = timeTemplateId;
	}
	
	@Column(name = "pic", nullable = true)
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@Column(name = "conditions", nullable = true)
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	
	@Column(name = "cancel_reservation_fee", nullable = true)
	public BigDecimal getCancelReservationFee() {
		return cancelReservationFee;
	}

	public void setCancelReservationFee(BigDecimal cancelReservationFee) {
		this.cancelReservationFee = cancelReservationFee;
	}



}




