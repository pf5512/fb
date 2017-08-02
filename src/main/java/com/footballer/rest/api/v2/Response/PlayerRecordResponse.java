package com.footballer.rest.api.v2.Response;

import com.footballer.rest.api.v2.vo.PlayerRecord;

public class PlayerRecordResponse extends JsonResponse {
	
	private PlayerRecord playerRecordTotals;
	private PlayerRecord playerFootballGameTotalRecord;
	private PlayerRecord playerFootballGameYearRecord;
	private PlayerRecord playerFootballMatchTotalRecord;
	private PlayerRecord playerFootballMatchYearRecord;
	


	public PlayerRecord getPlayerFootballGameTotalRecord() {
		return playerFootballGameTotalRecord;
	}

	public void setPlayerFootballGameTotalRecord(
			PlayerRecord playerFootballGameTotalRecord) {
		this.playerFootballGameTotalRecord = playerFootballGameTotalRecord;
	}

	public PlayerRecord getPlayerFootballGameYearRecord() {
		return playerFootballGameYearRecord;
	}

	public void setPlayerFootballGameYearRecord(
			PlayerRecord playerFootballGameYearRecord) {
		this.playerFootballGameYearRecord = playerFootballGameYearRecord;
	}

	public PlayerRecord getPlayerFootballMatchTotalRecord() {
		return playerFootballMatchTotalRecord;
	}

	public void setPlayerFootballMatchTotalRecord(
			PlayerRecord playerFootballMatchTotalRecord) {
		this.playerFootballMatchTotalRecord = playerFootballMatchTotalRecord;
	}

	public PlayerRecord getPlayerFootballMatchYearRecord() {
		return playerFootballMatchYearRecord;
	}

	public void setPlayerFootballMatchYearRecord(
			PlayerRecord playerFootballMatchYearRecord) {
		this.playerFootballMatchYearRecord = playerFootballMatchYearRecord;
	}

	public PlayerRecord getPlayerRecordTotals() {
		return playerRecordTotals;
	}

	public void setPlayerRecordTotals(PlayerRecord playerRecordTotals) {
		this.playerRecordTotals = playerRecordTotals;
	}

	
	

}
