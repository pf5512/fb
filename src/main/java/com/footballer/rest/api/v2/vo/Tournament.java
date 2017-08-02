package com.footballer.rest.api.v2.vo;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.adapter.DateAdapter;
import com.footballer.adapter.TournamentTypeAdapter;
import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.rest.api.v2.vo.enumType.TournamentType;
import com.footballer.util.DateUtil;

public class Tournament extends GenericVo{
	
	private String name;
	private Long arenaId;
	private String arenaName;
	
	@XmlJavaTypeAdapter(TournamentTypeAdapter.class)
	private TournamentType type;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date startDt;	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date endDt;
	
	private String logo;
	private String videoUrl;
	
	@SuppressWarnings("unused")
	private String displayStartDate;
	@SuppressWarnings("unused")
	private String displayEndDate;
	
	private String mainOrganiserName;
	private String mainOrganiserLogo;
	
	/**
	 * 0 : 未审核通过
	 * 1 : 可报名, 审核通过而且比赛未开赛
	 * 2 : 赛事进行中：未过赛事结束日期
	 * 3 : 赛事结束, 已过赛事结束日期
	 */
	@SuppressWarnings("unused")
	private String tournamentStatus;
	
	/**
	 * 审核状态:
	 * 0 未审核通过
	 * 1 审核通过 	 
	 */
	private String status;
	
	/**
	 * 报名开始时间
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date registerStartDate;
	
	/**
	 * 报名结束时间
	 */
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date registerEndDate;
	
	/**
	 * 比赛类型：5人制等
	 */
	private String matchType;
	
	/**
	 * 行政区域
	 */
	private String address;
	
	/**
	 * 行政区域Id
	 */
	private String addressId;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public TournamentType getType() {
		return type;
	}
	public void setType(TournamentType type) {
		this.type = type;
	}
	public String getArenaName() {
		return arenaName;
	}
	public void setArenaName(String arenaName) {
		this.arenaName = arenaName;
	}
	public String getDisplayStartDate() {
		return DateUtil.formatLongDate(startDt);
	}
	public String getDisplayEndDate() {
		return DateUtil.formatLongDate(endDt);
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getMainOrganiserName() {
		return mainOrganiserName;
	}
	public void setMainOrganiserName(String mainOrganiserName) {
		this.mainOrganiserName = mainOrganiserName;
	}
	public String getMainOrganiserLogo() {
		return mainOrganiserLogo;
	}
	public void setMainOrganiserLogo(String mainOrganiserLogo) {
		this.mainOrganiserLogo = mainOrganiserLogo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRegisterStartDateTime() {
		return registerStartDate;
	}
	public void setRegisterStartDateTime(Date registerStartDateTime) {
		this.registerStartDate = registerStartDateTime;
	}
	public Date getRegisterEndDateTime() {
		return registerEndDate;
	}
	public void setRegisterEndDateTime(Date registerEndDateTime) {
		this.registerEndDate = registerEndDateTime;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDisplayStartDate(String displayStartDate) {
		this.displayStartDate = displayStartDate;
	}
	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	
	/**
	 * 返回赛事状态
	 * 
	 * 0 : 未审核通过
	 * 1 : 可报名, 审核通过而且比赛未开赛
	 * 2 : 赛事进行中：未过赛事结束日期
	 * 3 : 赛事结束, 已过赛事结束日期
	 * @return
	 */
	public String getTournamentStatus() {
		Date today = new Date();

		if ("0".equals(status)) {
			// 0 : 未审核通过
			return status;
		} else if ("1".equals(status)) {
			// 比赛未开赛
			Boolean isMatchNotStart = today.before(startDt);
			if (isMatchNotStart) {
				// 1 : 可报名, 审核通过而且比赛未开赛
				return status;
			} else {
				Boolean isMatchEnd = today.after(endDt);
				if (isMatchEnd) {
					// 3 : 赛事结束, 已过赛事结束日期
					return "3";
				} else {
					// 2 : 赛事进行中：未过赛事结束日期
					return "2";
				}
			}
		} else {
			return "3";
		}
	}
}
