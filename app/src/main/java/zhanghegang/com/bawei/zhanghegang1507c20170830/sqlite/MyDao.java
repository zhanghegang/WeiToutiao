package zhanghegang.com.bawei.zhanghegang1507c20170830.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asus on 2017/9/5.
 */

public class MyDao {
    private final Context contex;
    private final MySqliter sqliter;

    public MyDao(Context context) {
        this.contex=context;
        sqliter = new MySqliter(context);
    }
    public void add(String uid,String json){
        SQLiteDatabase db = sqliter.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("uid",uid);
        values.put("json",json);
        db.insert("news",null,values);
        db.close();
    }
    public String select(String uid){
        String line=null;
        SQLiteDatabase db = sqliter.getWritableDatabase();
        Cursor news = db.query("news", null, "uid=?", new String[]{uid}, null, null, null);
        while(news.moveToNext())
        {
           line= news.getString(news.getColumnIndex("json"));
        }
        return line;
    }

}
