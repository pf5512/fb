package com.footballer.rest.api.v1.vo.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericNonGenerationTypeIdentityVo extends BaseRecordVo {
	
	@Id
	private Long id;
	
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
