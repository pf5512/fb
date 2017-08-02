package com.footballer.rest.api.v1.task;


import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.vo.Arena;

public class HeartBeatsCheckTask {

	
	@Autowired
	public ArenaDao arenaDao;
	
	
	
	public void test() {
	    System.out.println("==> 启动 HeartBeats 任务 ==>");
	}
	 
	 
	public void heartBeats() throws DomainNotFoundException{

		System.out.println("================================> 启动 HeartBeats 任务 >==============================================");
		Arena arena = arenaDao.findById(Arena.class, 52L);
		System.out.println("================================> 主场地检测："+ arena.getName() +" <===============================");
		System.out.println("================================> 结束 HeartBeats 任务 >==============================================");
	 }
}
