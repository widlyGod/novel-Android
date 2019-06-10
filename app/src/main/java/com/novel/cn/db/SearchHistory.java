package com.novel.cn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity
public class SearchHistory {


    @Id(autoincrement = true)
    private Long id;
    private String text;
    private Date date;
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Generated(hash = 1892577398)
    public SearchHistory(Long id, String text, Date date, int type) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }


}
