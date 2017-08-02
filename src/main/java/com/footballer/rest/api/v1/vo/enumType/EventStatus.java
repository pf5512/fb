package com.footballer.rest.api.v1.vo.enumType;

/**
 * create by justin  2015.09.27
 */
public enum EventStatus {
    
    INIT("初始创建"),
    REGCLOSE("报名截止"),
    STARTSIGNIN("开始签到"),
    GAMEBEGIN("比赛开始"),
    GAMEOVER("比赛结束"),
    CAPCONFIRM("队长确认"),
    EVALUATION("互评"),
    END("最终状态"),
    UNKOWN("未知状态");
	
    private String type;

    private EventStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
