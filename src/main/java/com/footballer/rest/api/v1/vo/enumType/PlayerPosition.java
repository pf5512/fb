package com.footballer.rest.api.v1.vo.enumType;

public enum PlayerPosition {

	ST(0, "前锋-striker"),
	CF(1, "中锋-CentreForward"),
	SS(2, "影子前锋-secondStriker"),
	WF(3, "边锋-WingForward"),
	LWF(4, "左边锋-LeftWingForward"),
	RWF(5, "右边锋-RightWingForward"),
	
	AMF(6, "前腰-AttackMiddleForward"),
	AMC(7, "中前腰-AttackMiddleCentre"),
	AML(8, "左前腰-AttackMiddleLeft"),
	AMR(9, "右前腰-AttackMiddleRight"),
	
	SMF(10, "边前卫-SideMiddleForward"),
	LMF(11, "左前卫-LeftMiddleForward"),
	RMF(12, "右前卫-RightMiddleForward"),
	CMF(13, "中前卫-CentreMiddleForward"),
	
	DMF(14, "后腰-DefendMiddleForward"),
	CDM(15, "中后腰-CentreDefendMiddle"),
	LDM(16, "左后腰-LeftDefendMiddle"),
	RDM(17, "右后腰-RightDefendMiddle"),
	
	LB(18, "左后卫-LeftBack"), 
	RB(19, "右后卫-RightBack"),
	CB(20, "中后卫-CenterBack"),
	LCB(21, "左中卫-LeftCenterBack"),
	RCB(22, "右中卫-RightCenterBack"),
	
	CWP(23, "清道夫(拖后中卫)- Sweeper"),
	
	GK(24, "门将-GoalKeeper"),
	DG(25, "门卫-DoorGuard"), 
	
	
	Midfielder(26, "中场"), 
	Defender(27, "后卫"), 
	UtilityPlayer(28, "全能球员-能攻善守"), 
	Libero(29, "自由人"),
	
	MF(30, "中场"),
	
	//篮球
	PG(31, "控球后卫"),
	SG(32, "得分后卫"),
	SF(33, "小前锋"),
	PF(34, "大前锋"),
	C(35, "中锋");
	
	
	
	

	private String name;
	private int index;

	// 构造方法
	private PlayerPosition(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public static PlayerPosition getValue(int index) {
		for (PlayerPosition t : PlayerPosition.values()) {
			if (t.getIndex() == index) {
				return t;
			}
		}
		return null;
	}

	public static String getName(int index) {
		for (PlayerPosition t : PlayerPosition.values()) {
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