package zhanghegang.com.bawei.zhanghegang1507c20170830.app;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by asus on 2017/8/30.
 */

public class TotalApp extends Application {
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
    private String Appkey="20a1e7e2dda4f";
    private String Appsecret="a7dfaca0cff47cc4cc546690082a2ea7";
    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();


        UMShareAPI.get(this);
        initXutils();
        initImageLoader();
        initMob();
        //设置极光推送bug模式
        JPushInterface.setDebugMode(true);
        //初始化极光推送
        JPushInterface.init(this);
    }

    private void initMob() {
        appContext=this;
        MobSDK.init(this,Appkey,Appsecret);
    }

    private void initImageLoader() {
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .build();
        ImageLoaderConfiguration con=new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(con);
    }

    private void initXutils() {
        x.Ext.init(this);
        x.Ext.setDebug(false);

    }
}
