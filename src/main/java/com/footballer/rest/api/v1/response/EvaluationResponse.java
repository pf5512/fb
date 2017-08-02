package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.domain.RecogniseOtherPlayerRecord;
import com.footballer.rest.api.v1.vo.Event;

public class EvaluationResponse extends JsonResponse {

	private Event event;
	private List<PlayerBaseInfo> joinedPlayerList;
	
	//用户某个技能被认同情况：次数和 认同者
	private List<PlayerRecognisedRecord> currentPlayerRecognisedRecords;
	
	//用户认同其他球员的记录
	private List<RecogniseOtherPlayerRecord> recogniseOtherPlayerRecords;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public List<PlayerBaseInfo> getJoinedPlayerList() {
		return joinedPlayerList;
	}
	public void setJoinedPlayerList(List<PlayerBaseInfo> joinedPlayerList) {
		this.joinedPlayerList = joinedPlayerList;
	}
	public List<PlayerRecognisedRecord> getCurrentPlayerRecognisedRecords() {
		return currentPlayerRecognisedRecords;
	}
	public void setCurrentPlayerRecognisedRecords(
			List<PlayerRecognisedRecord> currentPlayerRecognisedRecords) {
		this.currentPlayerRecognisedRecords = currentPlayerRecognisedRecords;
	}
	public List<RecogniseOtherPlayerRecord> getRecogniseOtherPlayerRecords() {
		return recogniseOtherPlayerRecords;
	}
	public void setRecogniseOtherPlayerRecords(
			List<RecogniseOtherPlayerRecord> recogniseOtherPlayerRecords) {
		this.recogniseOtherPlayerRecords = recogniseOtherPlayerRecords;
	}
}
