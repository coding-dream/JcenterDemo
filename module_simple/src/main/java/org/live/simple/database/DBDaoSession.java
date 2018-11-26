package org.live.simple.database;


import org.live.simple.App;
import org.live.simple.database.gen.DaoMaster;
import org.live.simple.database.gen.DaoSession;

public class DBDaoSession {
    private static final String DB_NAME = "nkt_db";

    private DBOpenHelper openHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DBDaoSession() {
    }

    public static DBDaoSession getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (daoMaster != null) {
            daoMaster = null;
        }
        if (openHelper != null) {
            openHelper.close();
            openHelper = null;
        }
    }

    /**
     * 获取数据连接Session
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getNewDaoSession();
        }
        return daoSession;
    }

    /**
     * 获取数据连接Session
     */
    public DaoSession getNewDaoSession() {
        if (daoMaster == null) {
            if (openHelper == null) {
                openHelper = new DBOpenHelper(App.getAppContext(), DB_NAME);
            }
            daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        }
        return daoMaster.newSession();
    }

    private static class SingletonHolder {
        private static final DBDaoSession INSTANCE = new DBDaoSession();
    }
}
