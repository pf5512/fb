package redis.service;

import java.io.Serializable;

public class FakeAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3742963946733183958L;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Long accountId;
	private String name;
	
	
	@Override
	public String toString() {
		return "Account info: [id:" + accountId + ", name: " + name + " ]";
	}

}
