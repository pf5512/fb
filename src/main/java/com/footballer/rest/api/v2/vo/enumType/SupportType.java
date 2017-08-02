package com.footballer.rest.api.v2.vo.enumType;

public enum SupportType {
	
	DISAGREE(0,"反对"),
	AGREE(1,"支持");
	
	// 成员变量
    private String name;
    private int index;
	
	// 构造方法
    private SupportType(int index,String name) {
    	this.index = index;
    	this.name = name;
    }

 // 普通方法
    public static SupportType getValue(int index) {
        for (SupportType c : SupportType.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (SupportType c : SupportType.values()) {
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
