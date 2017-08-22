package com.water.wechat.reception.utils;

/**
 * Created by Jiangxiong Lin on 2015/4/22.
 */
public class TimeHandler {
    public static int getSecondsByDay(int day) {
        return day > 0 ? day * 24 * 60 * 60 : 0;
    }

    public static int getSecondsByHour(int hour) {
        return hour > 0 ? hour * 60 * 60 : 0;
    }

    public static int getSecondsByMinute(int minute) {
        return minute > 0 ? minute * 60 : 0;
    }
}
