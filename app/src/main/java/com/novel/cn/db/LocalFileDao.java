package com.novel.cn.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL_FILE".
*/
public class LocalFileDao extends AbstractDao<LocalFile, Long> {

    public static final String TABLENAME = "LOCAL_FILE";

    /**
     * Properties of entity LocalFile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MFilePath = new Property(1, String.class, "mFilePath", false, "M_FILE_PATH");
        public final static Property MFileName = new Property(2, String.class, "mFileName", false, "M_FILE_NAME");
    }


    public LocalFileDao(DaoConfig config) {
        super(config);
    }
    
    public LocalFileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL_FILE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"M_FILE_PATH\" TEXT," + // 1: mFilePath
                "\"M_FILE_NAME\" TEXT);"); // 2: mFileName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL_FILE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocalFile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mFilePath = entity.getMFilePath();
        if (mFilePath != null) {
            stmt.bindString(2, mFilePath);
        }
 
        String mFileName = entity.getMFileName();
        if (mFileName != null) {
            stmt.bindString(3, mFileName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocalFile entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String mFilePath = entity.getMFilePath();
        if (mFilePath != null) {
            stmt.bindString(2, mFilePath);
        }
 
        String mFileName = entity.getMFileName();
        if (mFileName != null) {
            stmt.bindString(3, mFileName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public LocalFile readEntity(Cursor cursor, int offset) {
        LocalFile entity = new LocalFile( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // mFilePath
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // mFileName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocalFile entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMFilePath(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMFileName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(LocalFile entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(LocalFile entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LocalFile entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
