package com.footballer.rest.api.v2.vo; 
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.rest.api.v2.vo.enumType.AutomationJobStatus;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.rest.api.v2.vo.enumType.VideoStatus;

/**    
 *
 * @author justin
 * @date 2016-04-11 14:53:07
 * @version V1.0   
 * 
 *
 */

@JsonInclude(Include.NON_NULL)
public class AutomationJobFieldReservation  extends GenericVo{

    
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
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public AutomationJobStatus getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(AutomationJobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	
}
