package com.footballer.rest.api.v2.vo; 
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.rest.api.v2.vo.enumType.VideoStatus;

/**    
 *
 * @author justin
 * @date 2015-11-14 14:53:07
 * @version V1.0   
 * 
 *
 */

@JsonInclude(Include.NON_NULL)
public class FieldReservation  extends GenericVo{

    
	private Long arenaId;
	private String type;
	private Long fieldId;
	private Long fieldChargeStandardId;
	private Long playerId;
	private Long guestPlayerId;
	private BookStatus bookStatus;
	private Date useDate;
	private Date bookDate;
	private Date cancelDate;
	private PaymentStatus paymentStatus;
	private PaymentStatus guestPaymentStatus;
	private VideoStatus videoStatus;
	
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public BookStatus getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public Date getBookDate() {
		return bookDate;
	}
	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getFieldChargeStandardId() {
		return fieldChargeStandardId;
	}
	public void setFieldChargeStandardId(Long fieldChargeStandardId) {
		this.fieldChargeStandardId = fieldChargeStandardId;
	}
	public Long getGuestPlayerId() {
		return guestPlayerId;
	}
	public void setGuestPlayerId(Long guestPlayerId) {
		this.guestPlayerId = guestPlayerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public VideoStatus getVideoStatus() {
		return videoStatus;
	}
	public void setVideoStatus(VideoStatus videoStatus) {
		this.videoStatus = videoStatus;
	}
	public PaymentStatus getGuestPaymentStatus() {
		return guestPaymentStatus;
	}
	public void setGuestPaymentStatus(PaymentStatus guestPaymentStatus) {
		this.guestPaymentStatus = guestPaymentStatus;
	}
	
}
