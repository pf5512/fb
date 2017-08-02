package com.footballer.rest.api.v1.vo.enumType;

public enum TeamIntegrity {
	great,			//1.很诚信：提前到场，人员齐整
	good,			//2.诚信：准时到场
	normal,			//3.一般诚信：比赛迟到时间短，人员不整
	bad,			//4.不诚信：比赛迟到时间长，队员人数不够
	worsest			//5.很不诚信：赛前没有通知，未出场
}
