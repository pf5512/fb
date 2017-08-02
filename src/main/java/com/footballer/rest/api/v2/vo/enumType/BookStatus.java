package com.footballer.rest.api.v2.vo.enumType;

public enum BookStatus {
	
	UNBOOK("未预定"),
	BOOK("已预订"),
    CANCEL("已取消"),
    UNKOWN("未知状态");
	
    private String type;

    private BookStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
