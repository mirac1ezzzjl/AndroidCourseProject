package com.example.zx50.mydaygram;

import java.io.Serializable;

public class Diary implements Serializable{
    //年份
    int year;
    //月份
    int month;
    //星期几
    String day;
    //日期
    int date;
    //日记内容
    String text;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
