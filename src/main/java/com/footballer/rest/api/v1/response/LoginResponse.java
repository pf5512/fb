package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.User;
import com.footballer.rest.api.v2.vo.Arena;
import com.footballer.rest.api.v2.vo.Tournament;

public class LoginResponse extends JsonResponse {

    private User user;
    private List<Arena> arenas;
    private List<Tournament> tournaments;
    //identity_appId_apptoken_4_backend_api
    private String iaa;
    //url_call_prefix
    private String urlPre;
    private Long playerAccountId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public List<Arena> getArenas() {
		return arenas;
	}

	public void setArenas(List<Arena> arenas) {
		this.arenas = arenas;
	}

	public String getIaa() {
		return iaa;
	}

	public void setIaa(String iaa) {
		this.iaa = iaa;
	}

	public String getUrlPre() {
		return urlPre;
	}

	public void setUrlPre(String urlPre) {
		this.urlPre = urlPre;
	}

	public Long getPlayerAccountId() {
		return playerAccountId;
	}

	public void setPlayerAccountId(Long playerAccountId) {
		this.playerAccountId = playerAccountId;
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}


}



