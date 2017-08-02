package com.footballer.rest.api.v2.vo.enumType;

public enum VideoStatus {
	
	UNBOOK(0,"未预定"),
	BOOK(1,"已预订"),
    UNKOWN(2,"未知状态");
	
	// 成员变量
    private String name;
    private int index;
	
	// 构造方法
    private VideoStatus(int index,String name) {
    	this.index = index;
    	this.name = name;
    }

 // 普通方法
    public static VideoStatus getValue(int index) {
        for (VideoStatus c : VideoStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (VideoStatus c : VideoStatus.values()) {
            if (c.getIndex() == index) {
                return c.name;
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
