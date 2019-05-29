package com.novel.cn.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Readcord {
    @Id(autoincrement = true)
    private Long id;

    private String bookId;

    private int chapter;

    private int pagePos;

    private int volumePos;


    @Generated(hash = 1943779901)
    public Readcord(Long id, String bookId, int chapter, int pagePos,
            int volumePos) {
        this.id = id;
        this.bookId = bookId;
        this.chapter = chapter;
        this.pagePos = pagePos;
        this.volumePos = volumePos;
    }

    @Generated(hash = 1619034495)
    public Readcord() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPagePos() {
        return pagePos;
    }

    public void setPagePos(int pagePos) {
        this.pagePos = pagePos;
    }

    public int getVolumePos() {
        return volumePos;
    }

    public void setVolumePos(int volumePos) {
        this.volumePos = volumePos;
    }
}
