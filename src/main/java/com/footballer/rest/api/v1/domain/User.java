package com.footballer.rest.api.v1.domain;

import java.util.Date;

import com.footballer.rest.api.v1.vo.enumType.HeavyFootType;

/**
 * Created by ian on 8/21/14.
 */

public class User {
	
	private Long accountId;

    // account info
    private String accountStatus;
    private String accountIdentity;

    // people info
    private String peopleName;
    private String peopleImgUrl;
    private Long peopleAddressId;
    private String peopleAddress;
    private String addressLatitude;
    private String addressLongitude;

    // player info
    // TODO: player properties
    private Long playerId;
    private String playerNickName;
	private Date birth;
	private Integer height;
	private Integer weight;
	private HeavyFootType heavyFoot;

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountIdentity() {
        return accountIdentity;
    }

    public void setAccountIdentity(String accountIdentity) {
        this.accountIdentity = accountIdentity;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleImgUrl() {
        return peopleImgUrl;
    }

    public void setPeopleImgUrl(String peopleImgUrl) {
        this.peopleImgUrl = peopleImgUrl;
    }

    public String getPeopleAddress() {
        return peopleAddress;
    }

    public void setPeopleAddress(String peopleAddress) {
        this.peopleAddress = peopleAddress;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public void setPlayerNickName(String playerNickName) {
        this.playerNickName = playerNickName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getPeopleAddressId() {
		return peopleAddressId;
	}

	public void setPeopleAddressId(Long peopleAddressId) {
		this.peopleAddressId = peopleAddressId;
	}

	public String getAddressLatitude() {
		return addressLatitude;
	}

	public void setAddressLatitude(String addressLatitude) {
		this.addressLatitude = addressLatitude;
	}

	public String getAddressLongitude() {
		return addressLongitude;
	}

	public void setAddressLongitude(String addressLongitude) {
		this.addressLongitude = addressLongitude;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public HeavyFootType getHeavyFoot() {
		return heavyFoot;
	}

	public void setHeavyFoot(HeavyFootType heavyFoot) {
		this.heavyFoot = heavyFoot;
	}
}
