package com.footballer.rest.api.v2.result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.footballer.util.DateUtil;
import com.footballer.util.StringUtil;

/**
 * Created by justin on 2015.11.16
 * updated by justin on 2016.01.29
 * updated by justin on 2016.02.10
 */
public class ArenasFieldsReservationResult {
    
	private Long fieldReservationId;
	private String reservationType;
	private Long fieldId;
	private String fieldName;
	private String fieldType;
	private String fieldCondition;
	private Long fieldChargeStandardId;
	private String affectFcsId;
	private String startTime;
	private String endTime;
	private BigDecimal price;
	private BigDecimal weekendPrice;
	private BigDecimal barginPrice;
	private String playerId;
	private String playerName;
	private Long guestPlayerId;
	private String bookStatus;
	private Date useDate;
	private Date bookDate;
	private String paymentStatus;
	private String guestPaymentStatus;
	private String videoService;
	private String weChatPicUrl;
	
	private String teamName;
	private Long hostCell;
	private String guestPlayerName;
	private String guestTeamName;
	private String guestWeChatPicUrl;
	private Long guestCell;
	private List<String> playerIdList;
	private List<String> playerNameList;
	
	private String queryDate;
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getStartTime() {
		//仅显示 时：分
		return StringUtil.subString(startTime, 5);
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		//仅显示 时：分
		return StringUtil.subString(endTime, 5);
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getBarginPrice() {
		return barginPrice;
	}
	public void setBarginPrice(BigDecimal barginPrice) {
		this.barginPrice = barginPrice;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getDisPlayUseDate() {
		return DateUtil.formatPhoneDateChina(getUseDate());
	}
	public Long getFieldReservationId() {
		return fieldReservationId;
	}
	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
	}
	public String getReservationType() {
		return reservationType;
	}
	public void setReservationType(String reservationType) {
		this.reservationType = reservationType;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Long getHostCell() {
		return hostCell;
	}
	public void setHostCell(Long hostCell) {
		this.hostCell = hostCell;
	}
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	public String getGuestPlayerName() {
		return guestPlayerName;
	}
	public void setGuestPlayerName(String guestPlayerName) {
		this.guestPlayerName = guestPlayerName;
	}
	public String getGuestTeamName() {
		return guestTeamName;
	}
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	public Long getGuestCell() {
		return guestCell;
	}
	public void setGuestCell(Long guestCell) {
		this.guestCell = guestCell;
	}
	public String getVideoService() {
		return videoService;
	}
	public void setVideoService(String videoService) {
		this.videoService = videoService;
	}
	public List<String> getPlayerIdList() {
		return playerIdList;
	}
	public void setPlayerIdList(List<String> playerIdList) {
		this.playerIdList = playerIdList;
	}
	public List<String> getPlayerNameList() {
		return playerNameList;
	}
	public void setPlayerNameList(List<String> playerNameList) {
		this.playerNameList = playerNameList;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public String getGuestPaymentStatus() {
		return guestPaymentStatus;
	}
	public void setGuestPaymentStatus(String guestPaymentStatus) {
		this.guestPaymentStatus = guestPaymentStatus;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getWeChatPicUrl() {
		return weChatPicUrl;
	}
	public void setWeChatPicUrl(String weChatPicUrl) {
		this.weChatPicUrl = weChatPicUrl;
	}
	public String getGuestWeChatPicUrl() {
		return guestWeChatPicUrl;
	}
	public void setGuestWeChatPicUrl(String guestWeChatPicUrl) {
		this.guestWeChatPicUrl = guestWeChatPicUrl;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldCondition() {
		return fieldCondition;
	}
	public void setFieldCondition(String fieldCondition) {
		this.fieldCondition = fieldCondition;
	}
	public BigDecimal getWeekendPrice() {
		return weekendPrice;
	}
	public void setWeekendPrice(BigDecimal weekendPrice) {
		this.weekendPrice = weekendPrice;
	}
	public String getAffectFcsId() {
		return affectFcsId;
	}
	public void setAffectFcsId(String affectFcsId) {
		this.affectFcsId = affectFcsId;
	}
}
