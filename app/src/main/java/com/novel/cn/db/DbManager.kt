package com.novel.cn.db

import android.app.Application
import java.util.*

object DbManager {


    const val DB_NAME = "novel_db"

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
    fun saveSearch(keyword: String, type: Int) {

        var data = daoSession.searchHistoryDao.queryBuilder().where(SearchHistoryDao.Properties.Type.eq(type)).where(SearchHistoryDao.Properties.Text.eq(keyword)).limit(1).unique()
        if (data == null) {
            data = SearchHistory()
            data.text = keyword
            data.date = Date()
            data.type = type

            daoSession.searchHistoryDao.insert(data)
        } else {
            data.date = Date()
            daoSession.searchHistoryDao.update(data)
        }
    }

    /**
     * 获取搜索历史记录 获取10条
     */
    fun getSearchRecordList(type: Int) = daoSession.searchHistoryDao.queryBuilder()
            .where(SearchHistoryDao.Properties.Type.eq(type))
            .orderDesc(SearchHistoryDao.Properties.Date)
            .limit(10)
            .list()

    /**
     * 清除所有历史记录
     */
    fun clearSearchRecord() = daoSession.searchHistoryDao.deleteAll()


    fun getReadcord(bookId: String?): Readcord? {
        return daoSession.readcordDao.queryBuilder().where(ReadcordDao.Properties.BookId.eq(bookId)).unique()
                ?: Readcord()
    }

    fun saveRecord(mBookRecord: Readcord) {
        val data = daoSession.readcordDao.queryBuilder().where(ReadcordDao.Properties.BookId.eq(mBookRecord.bookId)).limit(1).unique()
        if (data == null) {
            daoSession.readcordDao.save(mBookRecord)
        } else {
            daoSession.readcordDao.update(mBookRecord)
        }

    }


}