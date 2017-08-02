package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.footballer.rest.api.v2.vo.Video;

public class GetTournamentVideoListResponse extends JsonResponse{
	
	private List<Video> videos;

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	
}
