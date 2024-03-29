package com.novel.cn.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.novel.cn.view.readpage.BookChapterBean;
import com.novel.cn.db.Readcord;
import com.novel.cn.db.SearchHistory;
import com.novel.cn.db.LocalFile;

import com.novel.cn.db.BookChapterBeanDao;
import com.novel.cn.db.ReadcordDao;
import com.novel.cn.db.SearchHistoryDao;
import com.novel.cn.db.LocalFileDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookChapterBeanDaoConfig;
    private final DaoConfig readcordDaoConfig;
    private final DaoConfig searchHistoryDaoConfig;
    private final DaoConfig localFileDaoConfig;

    private final BookChapterBeanDao bookChapterBeanDao;
    private final ReadcordDao readcordDao;
    private final SearchHistoryDao searchHistoryDao;
    private final LocalFileDao localFileDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookChapterBeanDaoConfig = daoConfigMap.get(BookChapterBeanDao.class).clone();
        bookChapterBeanDaoConfig.initIdentityScope(type);

        readcordDaoConfig = daoConfigMap.get(ReadcordDao.class).clone();
        readcordDaoConfig.initIdentityScope(type);

        searchHistoryDaoConfig = daoConfigMap.get(SearchHistoryDao.class).clone();
        searchHistoryDaoConfig.initIdentityScope(type);

        localFileDaoConfig = daoConfigMap.get(LocalFileDao.class).clone();
        localFileDaoConfig.initIdentityScope(type);

        bookChapterBeanDao = new BookChapterBeanDao(bookChapterBeanDaoConfig, this);
        readcordDao = new ReadcordDao(readcordDaoConfig, this);
        searchHistoryDao = new SearchHistoryDao(searchHistoryDaoConfig, this);
        localFileDao = new LocalFileDao(localFileDaoConfig, this);

        registerDao(BookChapterBean.class, bookChapterBeanDao);
        registerDao(Readcord.class, readcordDao);
        registerDao(SearchHistory.class, searchHistoryDao);
        registerDao(LocalFile.class, localFileDao);
    }
    
    public void clear() {
        bookChapterBeanDaoConfig.clearIdentityScope();
        readcordDaoConfig.clearIdentityScope();
        searchHistoryDaoConfig.clearIdentityScope();
        localFileDaoConfig.clearIdentityScope();
    }

    public BookChapterBeanDao getBookChapterBeanDao() {
        return bookChapterBeanDao;
    }

    public ReadcordDao getReadcordDao() {
        return readcordDao;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return searchHistoryDao;
    }

    public LocalFileDao getLocalFileDao() {
        return localFileDao;
    }

}
