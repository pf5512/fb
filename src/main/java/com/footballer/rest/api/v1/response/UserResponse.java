package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.UserWithStatus;

public class UserResponse extends JsonResponse {
	
	private UserWithStatus userWithStatus;
	private List<UserWithStatus> registerUserList;
	private List<String> unRegisteredUserList;

	public List<String> getUnRegisteredUserList() {
		return unRegisteredUserList;
	}

	public void setUnRegisteredUserList(List<String> unRegisteredUserList) {
		this.unRegisteredUserList = unRegisteredUserList;
	}

	public List<UserWithStatus> getRegisterUserList() {
		return registerUserList;
	}

	public void setRegisterUserList(List<UserWithStatus> registerUserList) {
		this.registerUserList = registerUserList;
	}

	public UserWithStatus getUserWithStatus() {
		return userWithStatus;
	}

	public void setUserWithStatus(UserWithStatus userWithStatus) {
		this.userWithStatus = userWithStatus;
	}
}
