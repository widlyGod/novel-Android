package com.novel.cn.view.readpage;

import com.jess.arms.base.BaseApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CacheManager {

    public static String BOOK_CACHE_PATH = BaseApplication.getInstance().getAppComponent().cacheFile() + File.separator
            + "read_cache" + File.separator;

    public static final String SUFFIX_NB = "_.nb";
    public static final String SUFFIX_TXT = ".txt";


    private static CacheManager manager;

    public static CacheManager getInstance() {
        return manager == null ? (manager = new CacheManager()) : manager;
    }


    public void saveChapterInfo(String folderName, String fileName, String content) {
        File file = FileUtils.getFile(BOOK_CACHE_PATH + folderName
                + File.separator + fileName + SUFFIX_NB);
        //获取流并存储
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    public File getChapterFile(String folderName, String fileName) {
        File file = FileUtils.getFile(BOOK_CACHE_PATH + folderName
                + File.separator + fileName + SUFFIX_NB);

        return file;
    }

    public boolean isChapterCached(String folderName, String fileName) {
        File file = new File(BOOK_CACHE_PATH + folderName
                + File.separator + fileName + SUFFIX_NB);
        return file.exists();
    }
}
