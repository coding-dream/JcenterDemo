package org.live.simple.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.live.simple.database.gen.DaoMaster;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 数据库表版本说明：
 * 版本6：XX
 * 版本5：XX
 * 版本4：XX
 * 版本3：XX
 * 版本2：XX
 * 版本1：XX
 */
public class DBOpenHelper extends DaoMaster.OpenHelper {

    private static final TreeMap<Integer, Upgrade> ALL_UPGRADES = new TreeMap<>();

    static {
        ALL_UPGRADES.put(1, new UpgradeV1());// 数据库版本为1时的建表操作
        ALL_UPGRADES.put(2, new UpgradeV2());// 数据库版本为2时的建表操作
        ALL_UPGRADES.put(3, new UpgradeV3());// 数据库版本为3时的建表操作
    }

    public DBOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        // 这句话一定不要忘记了,创建表的操作
        super.onCreate(db);
        Logger.d("onCreate");
        executeUpgrades(db, ALL_UPGRADES.keySet());
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //super.onUpgrade(db, oldVersion, newVersion);
        Logger.d("Upgrade from" + oldVersion + "to" + newVersion);
        if (newVersion > oldVersion) {
            SortedMap<Integer, Upgrade> subMap = ALL_UPGRADES.subMap(oldVersion, false, newVersion, true);
            if (subMap != null && subMap.size() > 0) {
                executeUpgrades(db, subMap.keySet());
            }
        }
    }

    private void executeUpgrades(Database db, Set<Integer> keySet) {
        if (keySet != null && keySet.size() > 0) {
            for (final Integer version : keySet) {
                ALL_UPGRADES.get(version).onUpgrade(db);
            }
        }
    }

    private interface Upgrade {
        void onUpgrade(Database db);
    }

    private static class UpgradeV1 implements Upgrade {
        @Override
        public void onUpgrade(Database db) {
        }
    }

    private static class UpgradeV2 implements Upgrade {
        @Override
        public void onUpgrade(Database db) {
            Logger.d("MusicEntityDao.createTable");
            // MusicEntityDao.createTable(db, false);
        }
    }

    private static class UpgradeV3 implements Upgrade {
        @Override
        public void onUpgrade(Database db) {
            Logger.d("BoardMessageDao.createTable, VideoCommentMessageDao.createTable, VideoLikeMessageDao.createTable");
        }
    }
}
