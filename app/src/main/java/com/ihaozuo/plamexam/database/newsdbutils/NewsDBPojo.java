package com.ihaozuo.plamexam.database.newsdbutils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "newsdb" )
public class NewsDBPojo extends Model implements Serializable {
    @Column
    private String time;
    @Column
    private String url;
    @Column
    private String title;
    @Column
    private String subtitle;
    @Column
    private String img;
    @Column
    private String date;


    public NewsDBPojo(String url, String title, String subtitle, String img, String time, String date) {
        super();
        this.url = url;
        this.title = title;
        this.subtitle = subtitle;
        this.img = img;
        this.time = time;
        this.date = date;
    }

    public NewsDBPojo() {
        super();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
