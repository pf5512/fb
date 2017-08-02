package com.footballer.rest.api.v2.vo; 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v2.vo.base.GenericVo;

/**    
 *
 * @Description: PlayerRecordvo
 * @date 2015-09-05 17:53:07
 * @version V1.0   
 *
 */

@JsonInclude(Include.NON_NULL)
public class PlayerRecord  extends GenericVo{

    
	// playerId
	private Long playerId;
	// years
	private Integer years;
	// matches
	private Integer matches;
	// hours
	private Double hours;
	
	private Long minutes;
	// playerCount
	private Integer playerCount;
	// eventType
	private EventType eventType;
	
	private Integer distinctPlayerCount;
	
	public PlayerRecord(){
		years = 0;
		matches=0;
		hours = 0d;
		minutes= 0l;
		playerCount = 0;
		distinctPlayerCount = 0;
	}


	public Long getPlayerId(){
		return playerId;
	}

	public void setPlayerId(Long playerId){
		this.playerId = playerId;
	}
	public Integer getPlayerCount(){
		return playerCount;
	}

	public void setPlayerCount(Integer playerCount){
		this.playerCount = playerCount;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Long getMinutes() {
		return minutes;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}


	public Integer getDistinctPlayerCount() {
		return distinctPlayerCount;
	}


	public void setDistinctPlayerCount(Integer distinctPlayerCount) {
		this.distinctPlayerCount = distinctPlayerCount;
	}
	
	
	
}
