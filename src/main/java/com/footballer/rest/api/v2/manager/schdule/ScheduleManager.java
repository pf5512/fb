package com.footballer.rest.api.v2.manager.schdule;

import java.util.List;

public class ScheduleManager {
	
	private ScheduleStrategy scheduleStrategy;
	
	public ScheduleManager(ScheduleStrategy scheduleStrategy) {
		this.scheduleStrategy = scheduleStrategy;
	}
	
	public List<ScheduleMatchItem> create(List<Long> teamIdList) {
		return this.scheduleStrategy.generate(teamIdList);
	}

}
