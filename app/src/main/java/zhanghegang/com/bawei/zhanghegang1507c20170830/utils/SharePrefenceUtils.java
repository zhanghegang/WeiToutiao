package zhanghegang.com.bawei.zhanghegang1507c20170830.utils;

import android.content.Context;
import android.content.SharedPreferences;

import zhanghegang.com.bawei.zhanghegang1507c20170830.app.TotalApp;

/**
 * Created by asus on 2017/9/1.
 */

public class SharePrefenceUtils {
    public SharedPreferences getSp(){
        return TotalApp.appContext.getSharedPreferences("user_phone", Context.MODE_PRIVATE);
    }
    public void putData(String key,String value){
        SharedPreferences.Editor editor=getSp().edit();
        editor.putString(key,value);
        editor.commit();
    }
}
