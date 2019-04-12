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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Generated(hash = 842522063)
    public SearchHistory(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }


}
