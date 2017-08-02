package com.footballer.rest.api.v1.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;

@Entity
@Table(name = "arena")
public class Arena extends GenericVo implements Serializable {

	private static final long serialVersionUID = -8314697199055303670L;
	
	private Long ownerUserId;
	private Long addressId;
	private String addressName;
	private String name;
	private String tel;
	private String website;
	private String pic;
	
	public Arena() {
	}
	
	@Column(name = "owner_user_id", nullable = false)
	public Long getOwnerUserId() {
		return ownerUserId;
	}
	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address_id", nullable = true)
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	@Column(name = "tel", nullable = true)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name = "website", nullable = true)
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name = "pic", nullable = true)
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column(name = "address_name", nullable = true)
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

}
