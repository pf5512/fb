package com.footballer.rest.api.v1.domain;

import java.util.List;

import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Field;

/**
 * Created by justin on 2015.01.17
 */
public class ArenaField {
    
	private Arena arena;
	private String arenaLongitude;
	private String arenaLatitude;
    private List<Field> fields;

    public Arena getArena() {
		return arena;
	}
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public String getArenaLongitude() {
		return arenaLongitude;
	}
	public void setArenaLongitude(String arenaLongitude) {
		this.arenaLongitude = arenaLongitude;
	}
	public String getArenaLatitude() {
		return arenaLatitude;
	}
	public void setArenaLatitude(String arenaLatitude) {
		this.arenaLatitude = arenaLatitude;
	}
    
}
