package com.footballer.rest.api.v1.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;

import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.vo.PlayerBalanceLine;
import com.footballer.rest.api.v1.vo.PlayerDepositAccount;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;

public class ClientPlayerBalanceLine implements Comparable<ClientPlayerBalanceLine>{
	
	private PlayerBalanceLine line;
	
	public ClientPlayerBalanceLine(PlayerBalanceLine line) {
		this.line = line;
	}
	
	public PlayerDepositAccount getAccount() {
		return line.getAccount();
	}
	
	public String getComment() {
		return line.getComment();
	}
	
	public FeeType getFeeTypeEnum() {
		return line.getFeeType();
	}
	
	public PayMethod getPayMethodEnum() {
		return line.getPayMethod();
	}
	
	public String getFeeType() {
		return line.getFeeType().getType();
	}
	
	public String getPayMethod() {
		return line.getPayMethod().getType();
	}
	
	public BigDecimal getFee() {
		return line.getFee();
	} 
	
	public Long getEventId() {
		return line.getEventId();
	}
	
	public String getPlayerName() {
		return line.getPlayerName();
	}
	
	public BigDecimal getBalances() {
		return line.getBalances();
	}
		
	@XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getUpdateDt() {
		return line.getUpdateDt();
	}

	@Override
	public int compareTo(ClientPlayerBalanceLine o) {
		if (null == getUpdateDt() || null == o.getUpdateDt()) {
			return 0;
		}
		return -(getUpdateDt().compareTo(o.getUpdateDt()));
	}
	
	public static void main(String[] args) {
		PlayerBalanceLine line1 = new PlayerBalanceLine();
		line1.setPlayerName("first one");
		line1.setUpdateDt(DateTime.now().toDate());
		
		PlayerBalanceLine line2 = new PlayerBalanceLine();
		line2.setPlayerName("sceond one");
		line2.setUpdateDt(DateTime.now().plusDays(1).toDate());
		
		ClientPlayerBalanceLine c1 = new ClientPlayerBalanceLine(line1);
		ClientPlayerBalanceLine c2 = new ClientPlayerBalanceLine(line2);
		
		List<ClientPlayerBalanceLine> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		
		Collections.sort(list);
		
		for (ClientPlayerBalanceLine line : list) {
			System.out.println(line.getPlayerName());
		}
	}

}
