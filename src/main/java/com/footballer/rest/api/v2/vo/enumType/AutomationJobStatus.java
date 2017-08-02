package com.footballer.rest.api.v2.vo.enumType;

public enum AutomationJobStatus {
	
	INACTIVE(0,"未激活"),
	ACTIVE(1,"激活");
	
	// 成员变量
    private String name;
    private int index;
	
	// 构造方法
    private AutomationJobStatus(int index,String name) {
    	this.index = index;
    	this.name = name;
    }

 // 普通方法
    public static AutomationJobStatus getValue(int index) {
        for (AutomationJobStatus c : AutomationJobStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (AutomationJobStatus c : AutomationJobStatus.values()) {
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
