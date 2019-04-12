package com.novel.cn.db

import android.app.Application
import java.util.*

object DbManager {


    const val DB_NAME = "novel-db"

    private lateinit var daoSession: DaoSession


    fun init(application: Application) {
        val helper = DaoMaster.DevOpenHelper(application, DB_NAME)
        val db = helper.writableDb
        val session = DaoMaster(db).newSession()
        daoSession = session
    }

    /**
     * 保存搜索历史
     * 如果存在了，则更新
     *
     */
    fun saveSearch(keyword: String) {

        var data = daoSession.searchHistoryDao.queryBuilder().where(SearchHistoryDao.Properties.Text.eq(keyword)).limit(1).unique()
        if (data == null) {
            data = SearchHistory()
            data.text = keyword
            data.date = Date()
            daoSession.searchHistoryDao.insert(data)
        } else {
            data.date = Date()
            daoSession.searchHistoryDao.update(data)
        }
    }

    /**
     * 获取搜索历史记录 获取10条
     */
    fun getSearchRecordList() = daoSession.searchHistoryDao.queryBuilder()
            .orderDesc(SearchHistoryDao.Properties.Date)
            .limit(10)
            .list()

    /**
     * 清除所有历史记录
     */
    fun clearSearchRecord() = daoSession.searchHistoryDao.deleteAll()

}