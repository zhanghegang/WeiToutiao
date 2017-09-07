package zhanghegang.com.bawei.zhanghegang1507c20170830.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2017/9/5.
 */

public class MySqliter extends SQLiteOpenHelper {
    public MySqliter(Context context) {
        super(context, "toutiao.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table news(uid text,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
