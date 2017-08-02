package com.footballer.rest.api.v1.vo.enumType;

/**
 * create by justin  08/19/14 23:10 PM
 */
public enum TeamTactics {
    allOutAttacking(0, "全面进攻"), //全面进攻
    totalFootball(1, "全攻全守"),   //全攻全守
    openFootball(2,"拉开战术"),    //拉开战术
    offSideTrap(3,"越位陷阱"),     //越位陷阱
    wingPlay(4,"边锋战术"),        //边锋战术
    shootOnSight(5,"积极的抢射战术"),	 //积极的抢射战术
    timeWasting(6,"拖延战术"),	 //拖延战术
    setThePace(7,"掌握节奏"),	 //掌握节奏
	controlBall(8,"控球传球");    //控球传球
	
	private String name;
	private int index;

	// 构造方法
	private TeamTactics(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public static TeamTactics getValue(int index) {
		for (TeamTactics t : TeamTactics.values()) {
			if (t.getIndex() == index) {
				return t;
			}
		}
		return null;
	}

	public static String getName(int index) {
		for (TeamTactics t : TeamTactics.values()) {
			if (t.getIndex() == index) {
				return t.name;
			}
		}
		return null;
	}

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
