package com.footballer.rest.api.v1.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v2.manager.TournamentManager;

public class FetchVideoThumbnailTask {
	
	@Autowired
	private TournamentManager tMgr;
	
	public void fetchVideoThumbnail() {
		tMgr.updateVideoThumbnail();
	}

}
