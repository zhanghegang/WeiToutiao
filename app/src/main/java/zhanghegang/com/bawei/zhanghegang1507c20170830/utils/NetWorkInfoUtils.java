package zhanghegang.com.bawei.zhanghegang1507c20170830.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by asus on 2017/9/5.
 */

public class NetWorkInfoUtils {
private Context context;
    public void vertifyNet(Context context,NetWorkListener network){
        this.context=context;
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        //判断网络存否和类型
        if(info!=null)
        {
            if(info.getType()==ConnectivityManager.TYPE_MOBILE)
            {
network.haveMobileNet();
            }
            else if(info.getType()==ConnectivityManager.TYPE_WIFI)
            {
network.haveWifiNet();
            }
            else
            {
network.noNet();
            }
        }
        else
        {
network.noNet();
        }
    }
    //创建接口
    public interface NetWorkListener{
void haveMobileNet();
        void haveWifiNet();
        void noNet();
    }
}

