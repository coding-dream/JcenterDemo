package org.live.test.database.dao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.live.test.database.DBDaoSession;
import org.live.test.database.entity.CMessage;
import org.live.test.database.gen.CMessageDao;

import java.util.List;

public class CMessageDbHelper {

    private static final int MAX_MESSAGE_COUNT = 1000;

    private CMessageDbHelper(){}

    public static CMessageDbHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private CMessageDao getCMessageDao(){
        return DBDaoSession.getInstance().getDaoSession().getCMessageDao();
    }

    public long insert(CMessage message){
        return getCMessageDao().insert(message);
    }

    public void insertOrReplace(List<CMessage> list){
        getCMessageDao().insertOrReplaceInTx(list);
    }

    public void delete(CMessage cMessage){
        getCMessageDao().delete(cMessage);
    }

    public void update(CMessage cMessage) {
        getCMessageDao().update(cMessage);
    }

    public List<CMessage> findAll() {
        QueryBuilder<CMessage> qb = getCMessageDao().queryBuilder();
        List<CMessage> list = qb.list();
        return list;
    }

    public CMessage findById(int id) {
        QueryBuilder<CMessage> qb = getCMessageDao().queryBuilder();
        List<CMessage> list = qb
                .where(CMessageDao.Properties.Id.eq(id))
                .offset(0)
                .limit(1)
                .orderDesc(CMessageDao.Properties.Time)
                .list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private static class SingletonHolder {
        private static final CMessageDbHelper INSTANCE = new CMessageDbHelper();
    }
}
