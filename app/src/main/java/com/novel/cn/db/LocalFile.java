package com.novel.cn.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LocalFile {
    @Id(autoincrement = true)
    private Long id;
    
    private String mFilePath;
    private String mFileName;
    @Generated(hash = 1251461480)
    public LocalFile(Long id, String mFilePath, String mFileName) {
        this.id = id;
        this.mFilePath = mFilePath;
        this.mFileName = mFileName;
    }
    @Generated(hash = 2106851084)
    public LocalFile() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMFilePath() {
        return this.mFilePath;
    }
    public void setMFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }
    public String getMFileName() {
        return this.mFileName;
    }
    public void setMFileName(String mFileName) {
        this.mFileName = mFileName;
    }
}
