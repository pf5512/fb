package com.footballer.rest.api.v2.vo;

/**   
 *
 * @Description: player_match_relationshipvo
 * @date 2015-10-24 19:35:06
 * @version V1.0   
 *
 */

public class PlayerMatchRelationship {

    
	// id
	private Long id;
	// playerId
	private Long playerId;
	// relatedPlayerId
	private Long relatedPlayerId;
	
	private Integer distinctPlayerCount;
	

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getPlayerId(){
		return playerId;
	}

	public void setPlayerId(Long playerId){
		this.playerId = playerId;
	}

	public Long getRelatedPlayerId() {
		return relatedPlayerId;
	}

	public void setRelatedPlayerId(Long relatedPlayerId) {
		this.relatedPlayerId = relatedPlayerId;
	}

	public Integer getDistinctPlayerCount() {
		return distinctPlayerCount;
	}

	public void setDistinctPlayerCount(Integer distinctPlayerCount) {
		this.distinctPlayerCount = distinctPlayerCount;
	}


	
	
}
