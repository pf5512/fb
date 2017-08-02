package com.footballer.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.footballer.rest.api.v2.vo.enumType.TournamentType;

public class TournamentTypeAdapter extends XmlAdapter<TournamentType, String> {

	@Override
	public String unmarshal(TournamentType v) throws Exception {
		return v.getName();
	}

	@Override
	public TournamentType marshal(String v) throws Exception {
		return TournamentType.getValue(Integer.valueOf(v));
	}

}
