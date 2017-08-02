package com.footballer.rest.api.v1.vo.enumType;

public class TestEnum {

	public static void main(String[] args){
       for(PlayerSkills p : PlayerSkills.values())
            System.out.println(p.name()+" "+p.getType());
    }
}
