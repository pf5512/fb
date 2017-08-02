package com.footballer.rest.api.v2.vo.enumType;

public enum VideoType {
	
	HIGHLIGHTS(0,"集锦"),
	TOP(1,"最佳"),
    FUN(2,"花絮"),
    FULL(3,"全场");
	
	// 成员变量
    private String name;
    private int index;
	
	// 构造方法
    private VideoType(int index,String name) {
    	this.index = index;
    	this.name = name;
    }

 // 普通方法
    public static VideoType getValue(int index) {
        for (VideoType c : VideoType.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (VideoType c : VideoType.values()) {
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
