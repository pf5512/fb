package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.ArenaField;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Field;

/**
 * Created by justin on 6/29/14.
 */

public class ArenaResponse extends JsonResponse {

	private Arena arena;
	private List<Field> fieldList;
	private List<ArenaField> arenaFields;

	public ArenaResponse() {
		super();
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	public List<ArenaField> getArenaFields() {
		return arenaFields;
	}

	public void setArenaFields(List<ArenaField> arenaFields) {
		this.arenaFields = arenaFields;
	}
}
