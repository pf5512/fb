package com.footballer.rest.api.v1.response;

import com.footballer.rest.api.v1.result.InvitationResult;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;

import java.util.List;

/**
 * Created by justin on 2015.2.12.
 * @param <T>
 */

public class InvitationResponse<T> extends JsonResponse {

	private T domain;
	private List<InvitationResult> invitations;
	private InvitationResponseType type;

	public T getDomain() {
		return domain;
	}

	public void setDomain(T domain) {
		this.domain = domain;
	}

	public InvitationResponseType getType() {
		return type;
	}

	public void setType(InvitationResponseType type) {
		this.type = type;
	}

	public List<InvitationResult> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<InvitationResult> invitations) {
		this.invitations = invitations;
	}
}
