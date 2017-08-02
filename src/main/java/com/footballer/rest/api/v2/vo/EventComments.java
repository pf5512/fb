package com.footballer.rest.api.v2.vo;

import java.io.Serializable;
import com.footballer.rest.api.v1.vo.base.GenericVo;

public class EventComments extends GenericVo implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private Long eventId;
    private Long playerId;
    private String comments;
            
    public EventComments() {
    }
    
    public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
    
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
