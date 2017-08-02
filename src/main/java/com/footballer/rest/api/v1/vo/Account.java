package com.footballer.rest.api.v1.vo;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;
 
@Entity
@Table(name = "account")
public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 389316666215993638L;
	private Long id;
	private String name;
	private String password;
	private String status;
	
	private String weChatPicUrl;
	private String weChatOpenId;
	private String cellphone;
	
	private UserToken userToken;
	
	public Account() {
		
	}

    public Account(String name, String password) {
        this.name = name;
        this.password = password;

        UserToken token = new UserToken();
        token.setIdentity(UUID.randomUUID().toString());

        setUserToken(token);

        token.setAccount(this);
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "pwd", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "status", nullable = true)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}
	
	@Column(name = "wechat_pic_url", nullable = false)
	public String getWeChatPicUrl() {
		return weChatPicUrl;
	}
	
	public void setWeChatPicUrl(String weChatPicUrl) {
		this.weChatPicUrl = weChatPicUrl;
	}
	
	@Column(name = "wechat_openid", nullable = false)
	public String getWeChatOpenId() {
		return weChatOpenId;
	}

	public void setWeChatOpenId(String weChatOpenId) {
		this.weChatOpenId = weChatOpenId;
	}

	@Column(name = "cellphone", nullable = false)
	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

}
