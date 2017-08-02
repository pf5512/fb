package com.footballer.rest.api.v2.result;

import java.util.Date;

import com.footballer.rest.api.v2.vo.enumType.AutomationJobStatus;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.rest.api.v2.vo.enumType.VideoStatus;
import com.footballer.util.DateUtil;

/**
 * Created by justin on 2016.04.11
 */
public class AutomationJobFieldReservationResult {
    
	private Long id;
	private Long arenaId;
	private String type;
	private Long fieldId;
	private Long fieldChargeStandardId;
	private Long playerId;
	private BookStatus bookStatus;
	private Integer dayOfWeek;
	private PaymentStatus paymentStatus;
	private VideoStatus videoStatus;
	private AutomationJobStatus jobStatus;
	private Date createDt;
	private String disPlayCreateDt;
	private String playerName;
	private String fieldName;
	private String cellphone;
	private String teamName;
	private String startTime;
	private String endTime;
	
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Long getFieldChargeStandardId() {
		return fieldChargeStandardId;
	}
	public void setFieldChargeStandardId(Long fieldChargeStandardId) {
		this.fieldChargeStandardId = fieldChargeStandardId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public BookStatus getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public VideoStatus getVideoStatus() {
		return videoStatus;
	}
	public void setVideoStatus(VideoStatus videoStatus) {
		this.videoStatus = videoStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public AutomationJobStatus getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(AutomationJobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getDisPlayCreateDt() {
		return DateUtil.formatShortDateChina(createDt);
	}
	public void setDisPlayCreateDt(String disPlayCreateDt) {
		this.disPlayCreateDt = disPlayCreateDt;
	}
	
}
