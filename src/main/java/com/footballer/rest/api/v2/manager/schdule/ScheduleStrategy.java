package com.footballer.rest.api.v2.manager.schdule;

import java.util.List;

public interface ScheduleStrategy {
	
	public List<ScheduleMatchItem> generate(List<Long> teamIdList);

}
