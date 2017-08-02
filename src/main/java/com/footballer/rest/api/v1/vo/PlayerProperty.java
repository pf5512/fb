package com.footballer.rest.api.v1.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.footballer.rest.api.v1.adapter.ShortDateAdapter;
import com.footballer.rest.api.v1.vo.base.BaseRecordVo;
import com.footballer.rest.api.v1.vo.enumType.HeavyFootType;
 
@SuppressWarnings("serial")
@Entity
@Table(name = "player_property")
public class PlayerProperty extends BaseRecordVo implements Serializable{

	
	private Long id;
	private Account account;
	private Date birth;
	private Integer height;
	private Integer weight;
	private HeavyFootType heavyFoot;
	
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
	
	
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Account getAccount() {
        return account;
    }
    @JsonBackReference
    public void setAccount(Account account) {
        this.account = account;
    }
	
    
	@Column(name = "birth", nullable = true)
	@XmlElement(name = "birth")
    @XmlJavaTypeAdapter(ShortDateAdapter.class)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Column(name = "height", nullable = true)
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "weight", nullable = true)
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Enumerated(value=EnumType.STRING)
	@Column(name = "heavy_foot", nullable = true)
	public HeavyFootType getHeavyFoot() {
		return heavyFoot;
	}
	public void setHeavyFoot(HeavyFootType heavyFoot) {
		this.heavyFoot = heavyFoot;
	}
	
}
