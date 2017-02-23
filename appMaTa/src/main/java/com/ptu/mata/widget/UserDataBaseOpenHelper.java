package com.ptu.mata.widget;

/**
 * Created by SinMin on 2016/10/29
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ptu.mata.bean.UserBean;

import java.sql.SQLException;

/**
 * Created by level on 2016/10/20.
 */
public class UserDataBaseOpenHelper extends OrmLiteSqliteOpenHelper {
    //获取一个单例的对象
    private static UserDataBaseOpenHelper openHelper;

    public static UserDataBaseOpenHelper getInstance(Context context) {
        if (openHelper == null) {
            openHelper = new UserDataBaseOpenHelper(context);
        }
        return openHelper;
    }

    public UserDataBaseOpenHelper(Context context) {

        super(context, "myuser.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, UserBean.class);//创建对象对应的数据库表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        if (oldVersion > newVersion) return;

        try {
            TableUtils.dropTable(connectionSource, UserBean.class, true);//丢弃老的数据库表

            onCreate(sqLiteDatabase, connectionSource);//创建新表
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
