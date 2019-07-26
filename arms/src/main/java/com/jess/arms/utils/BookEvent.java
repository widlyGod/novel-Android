package com.jess.arms.utils;

public class BookEvent {

    public BookEvent(String novelId) {
        this.novelId = novelId;
    }

    private String novelId = "";

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }
}
