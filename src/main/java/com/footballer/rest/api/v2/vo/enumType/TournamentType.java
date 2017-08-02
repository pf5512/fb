package com.footballer.rest.api.v2.vo.enumType;

public enum TournamentType {

	
	   CUP(0 ,"杯赛"),
	   LEAGUE(1,"联赛"),
	   BASKETBALLCUP(2, "篮球杯赛"),
	   BASKETBALLLEAGUE(3, "篮球联赛"),
	   FOOTBALLCLUBLEAGUE(4, "足球俱乐部定制联赛");	
		
		// 成员变量
	    private String name;
	    private int index;
		
		// 构造方法
	    private TournamentType(int index,String name) {
	    	this.index = index;
	    	this.name = name;
	    }

	 // 普通方法
	    public static TournamentType getValue(int index) {
	        for (TournamentType t : TournamentType.values()) {
	            if (t.getIndex() == index) {
	                return t;
	            }
	        }
	        return null;
	    }
	    
	    // 普通方法
	    public static String getName(int index) {
	        for (TournamentType t : TournamentType.values()) {
	            if (t.getIndex() == index) {
	                return t.name;
	            }
	        }
	        return null;
	    }

	    // get set 方法
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public int getIndex() {
	        return index;
	    }

	    public void setIndex(int index) {
	        this.index = index;
	    }
}
