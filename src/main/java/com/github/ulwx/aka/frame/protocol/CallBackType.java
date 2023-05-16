package com.github.ulwx.aka.frame.protocol;

import com.ulwx.tool.StringUtils;

public  enum CallBackType{
    //无回调
    NO_CALLBACK(0),
    // 1：异步回调
    ASYNC_CALLBACK(1),
    //2:网关回调
    GATEWAY_CALLBACK(2);


    private final int value;
    //构造方法必须是private或者默认
    private CallBackType(int value) {
        this.value = value;
    }

    public static CallBackType valueFrom(int value) {
        switch (value) {
            case 0:
                return CallBackType.NO_CALLBACK;
            case 1:
                return CallBackType.ASYNC_CALLBACK;
            case 2:
                return CallBackType.GATEWAY_CALLBACK;
            default:
                return null;
        }
    }


    public static CallBackType valueFrom(String value) {
        int val=0;
        try {
            val= Integer.valueOf(StringUtils.trim(value));
        } catch (Exception e) {

        }
        return valueFrom(val);
    }
    @Override
    public String toString(){
        return value+"";
    }

    public String toName(){
        switch (value) {
            case 0:
                return "NONE";
            case 1:
                return "NOTIFY";
            case 2:
                return "CALLBACK";
            default:
                return "";
        }
    }

}
