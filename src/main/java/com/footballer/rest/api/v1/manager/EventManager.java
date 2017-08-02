package com.footballer.rest.api.v1.manager;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.dao.AddressDao;
import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.vo.Address;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.rest.api.v1.vo.TeamPlayer;
import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;
import com.footballer.util.MapUtil;
import com.footballer.util.ObjectUtil;

/**
 * Created by justin on 2015.06.30.
 */

@Service
public class EventManager {
    
	@Autowired
	public PlayerDao playerDao;
	
	@Autowired
	public TeamDao teamDao;
    
    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private FieldDao fieldDao;
    
    @Autowired
    private ArenaDao arenaDao;
    
    @Autowired
    private AddressDao addressDao;
    
    public boolean checkEventAndPlayersExists(Long eventId, List<Long> playerIds){
    	if(ObjectUtil.isNotNull(eventDao.findById(Event.class, eventId)) && playerDao.checkPlayersAllExist(playerIds)) 
    	{
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean checkPlayerIsInEventFieldPosition(Long eventId, String longitude,  String latitude)
    {
    	Event e = eventDao.findById(Event.class, eventId);
    	Field f = fieldDao.findById(Field.class, e.getFieldId());
    	Arena a =arenaDao.findById(Arena.class, f.getArena_id());
    	Address address =addressDao.findById(Address.class, a.getAddressId());
    	
    	Double distance = MapUtil.GetDistance(Double.parseDouble(address.getLatitude()),
    																			 Double.parseDouble(address.getLongitude()), 
    																			 Double.parseDouble(latitude), 
    																		     Double.parseDouble(longitude));
    	int dis = distance.intValue();
    	System.out.println("球员距球场实际距离： "+ dis+ " 米");
    	if(dis>0 && dis < 500){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public PlayerTeamActivityType recordPlayerEventActivity(Long eventId, Long playerId, Date signTime){
    	Event e = eventDao.findById(Event.class, eventId);
    	if(signTime.getTime() < e.getStart_date().getTime()){
    		teamDao.createPlayerTeamActivity(playerId, null, eventId, PlayerTeamActivityType.ATTEND);
    		return PlayerTeamActivityType.ATTEND;
    	}else if (signTime.getTime()> e.getStart_date().getTime() && signTime.getTime()< e.getEnd_date().getTime()){
    		teamDao.createPlayerTeamActivity(playerId, null, eventId, PlayerTeamActivityType.LATE);
    		return PlayerTeamActivityType.LATE;
    	}else{
    		teamDao.createPlayerTeamActivity(playerId, null, eventId, PlayerTeamActivityType.ABSENCE);
    		return PlayerTeamActivityType.ABSENCE;
    	}
    }
}
