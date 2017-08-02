package com.footballer.rest.api.v1.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.adapter.ShortDateChinaAdapter;
import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@Table(name="event")
public class Event extends GenericVo implements Serializable{
    
    private Long ownerId;
    private EventType type;
    private Date start_date;
    private Date end_date;
    private Long fieldId;
    private Date replyEndDate;
    private Integer playerCount;
    private Boolean isConfirmed;
    private Integer cost;
    private Long teamId;
            
    public Event() {
    }
    
    @Column(name = "owner_id", nullable = false)
    @XmlElement(name = "ownerId")
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    @Enumerated(value=EnumType.STRING)
    @Column(name = "type", nullable = false)
    @XmlElement(name = "type")
    public EventType getType() {
        return type;
    }
    public void setType(EventType type) {
        this.type = type;
    }
    
    @Column(name = "start_date", nullable = false)
    @XmlElement(name = "start_date")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    
    @Transient
    @XmlElement(name = "startDateDisplay")
    @XmlJavaTypeAdapter(ShortDateChinaAdapter.class)
    public Date getStartDateDisplay() {
		return start_date;
	}

	public void setStartDateDisplay(Date startDateDisplay) {
		
	}
	
	@Transient
	@XmlElement(name = "endDateDisplay")
	@XmlJavaTypeAdapter(ShortDateChinaAdapter.class)
	public Date getEndDateDisplay() {
		return end_date;
	}

	public void setEndDateDisplay(Date endDateDisplay) {
		
	}

    @Column(name = "end_date", nullable = false)
    @XmlElement(name = "end_date")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
    public Date getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    
    @Column(name = "field_id", nullable = false)
    @XmlElement(name = "fieldId")
    public Long getFieldId() {
        return fieldId;
    }
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }
    
    @Column(name = "reply_end_date", nullable = false)
    @XmlElement(name = "replyEndDate")
    @XmlJavaTypeAdapter(LongDateAdapter.class)
    public Date getReplyEndDate() {
        return replyEndDate;
    }
    public void setReplyEndDate(Date replyEndDate) {
        this.replyEndDate = replyEndDate;
    }
    
    @Column(name = "player_count", nullable = false)
    @XmlElement(name = "playerCount")
    public Integer getPlayerCount() {
        return playerCount;
    }
    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }
    
    public String toMessage() {
        return "新的" + type.getType() + "赛事：" + DateUtil.formatShortDateChina(start_date);  
    }

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	@Column(name = "cost", nullable = true)
    @XmlElement(name = "cost")
	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
    @Column(name = "team_id", nullable = true)
    @XmlElement(name = "teamId")
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
}
