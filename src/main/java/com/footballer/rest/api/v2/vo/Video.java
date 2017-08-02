package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.footballer.rest.api.v2.vo.enumType.VideoType;
import com.footballer.util.DateUtil;

public class Video {

	private Long id;
	private Long tournamentId;
	private Long tournamentMatchId;
	private String name;
	private String url;
	private Date date;
	private String displayDate;
	private String displayLongDate;
	private VideoType type;	
	private Integer typeValue;	
	private String thumbnail;
	
	private Integer commentCount;
	private Integer supportCount;
	private Integer shareCount;
	private Integer watchCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VideoType getType() {
		return type;
	}
	public void setType(VideoType type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getFullDisplayDate() {
		return this.displayDate;
	}
	
	public String getDisplayDate() {
		return DateUtil.formatShortDate(date);
	}
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getSupportCount() {
		return supportCount;
	}
	public void setSupportCount(Integer supportCount) {
		this.supportCount = supportCount;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getTypeValue() {
		return typeValue != null ? typeValue : (type != null ? type.getIndex() : null);
	}
	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}
	public String getDisplayLongDate() {
		return date != null ? DateUtil.formatLongDate(date) : null;
	}
	public void setDisplayLongDate(String displayLongDate) {
		this.displayLongDate = displayLongDate;
	}
	public Integer getWatchCount() {
		return watchCount;
	}
	public void setWatchCount(Integer watchCount) {
		this.watchCount = watchCount;
	}	
}
