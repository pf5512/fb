package com.footballer.rest.api.v2.request;

public class PlayerAccountRequest {
	private Long playerAccountId;
	private Long cell;
	private String newPwd;
	private String accountName;
	private String pwd;
	
	public Long getPlayerAccountId() {
		return playerAccountId;
	}
	public void setPlayerAccountId(Long playerAccountId) {
		this.playerAccountId = playerAccountId;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public Long getCell() {
		return cell;
	}
	public void setCell(Long cell) {
		this.cell = cell;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
