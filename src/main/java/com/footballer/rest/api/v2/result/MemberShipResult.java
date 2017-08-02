package com.footballer.rest.api.v2.result;

import com.footballer.rest.api.v2.domain.ArenaMemberInfo;

public class MemberShipResult {

	private boolean memberOfArena;
	private ArenaMemberInfo arenaMember;
	
	
	public ArenaMemberInfo getArenaMember() {
		return arenaMember;
	}
	public void setArenaMember(ArenaMemberInfo arenaMember) {
		this.arenaMember = arenaMember;
	}
	public boolean isMemberOfArena() {
		return memberOfArena;
	}
	public void setMemberOfArena(boolean memberOfArena) {
		this.memberOfArena = memberOfArena;
	}
	
}
