package com.footballer.rest.api.v1.vo.enumType;

/**
 * create by justin  2015.6.10
 */
public enum PlayerSkills {
    
	//最佳评选
    MVP("全场最佳"),
    BestAttacker("最佳进攻球员"),
    BestMidFielder("最佳中场"),
    BestDefender("最佳防守球员"),
    BestGoal("最佳进球"),
    BestSave("最佳扑救"),

    //进攻属性
    shootSkill("射门技巧"),
    shootingAccuracy("射门精度"),
    shootPower("射门力量"),
    passThreatBall("传威胁球"),
    breakThrough("突破"),                 //突破
    dribbling("盘带"),                           //盘带
    ballControl("控球"),                      //控球

    //防守属性
    steal("抢断"),                                        //抢断
    intercept("拦截"),                                //拦截
    confrontation("身体对抗"),                    //身体对抗


    //基本属性
    speed("速度"),
    power("力量"),
    physical("体能"),                             //体能
    pass("传球"),
    touch("停球"),
    positionSense("位置感"),                    //位置感
    fightingWill("精神意志"),                        //斗志
    handleball("处理球"),                        //处理球


    //娱乐标签
    XJBT("瞎jb踢"),                                    //瞎鸡巴踢
    MTLD("埋头乱带"),                                    //埋头乱带
    DSBC("打死不传"),                                    //打死不传
    underCover("卧底"),                        //卧底
    thinker("思考人生"),                                //思考人生
    openPlug("开挂"),                            //开挂
    ballPa("球霸"),                                    //球霸
    fly("飞侠"),                                            //飞侠
    invincible("无敌"),                            //无敌
    Mincer("绞肉机"),                                //绞肉机
    shootPlanePrince("打飞机王子");

    private String type;

    private PlayerSkills(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
//    public static List<PlayerSKillsInfo> getPlayerSkillsInfoList(){
//        List<PlayerSKillsInfo> list = new ArrayList<>();
//
//        for(PlayerSkills p : PlayerSkills.values()){
//            PlayerSKillsInfo psi = new PlayerSKillsInfo();
//
//            psi.setSkillName(p.name());
//            psi.setSkillType(p.getType());
//            psi.setSkillImgUrl(p.name());
//            list.add(psi);
//        }
//        return list;
//    }
}
