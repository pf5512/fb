package com.footballer.rest.api.v2.vo.enumType;

public enum PaymentStatus {
	
	
    UNPAID(0 ,"未支付"),                           //0
    PAID(1,"会员已支付"),                          //1
    YUEZHANPAID(2,"约战方会员已支付"),              //2
    YINGZHANPAID(3,"应战方会员已支付"),             //3
    WECHATPAID(4,"微信已支付"),                    //4
    YUEZHANWECHATPAID(5,"约战方微信已支付"),        //5
    YINGZHANWECHATPAID(6,"应战方微信已支付"),       //6
    ADMINORDERUNPAID(7,"管理员代预定未付款"),      //7
    APPPAID(8,"APP整场已支付"),					//8
    APPYUEZHANPAID(9,"APP约战已支付"),			//9
    APPYINGZHANPAID(10,"APP应战已支付"),			//10
    UNKOWN(11,"未知状态");                         //11
	
	// 成员变量
    private String name;
    private int index;
	
	// 构造方法
    private PaymentStatus(int index,String name) {
    	this.index = index;
    	this.name = name;
    }

 // 普通方法
    public static PaymentStatus getValue(int index) {
        for (PaymentStatus c : PaymentStatus.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    
    // 普通方法
    public static String getName(int index) {
        for (PaymentStatus c : PaymentStatus.values()) {
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
