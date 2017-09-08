package zhanghegang.com.bawei.zhanghegang1507c20170830.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.activity.DownActivity;

import zhanghegang.com.bawei.zhanghegang1507c20170830.adapter.RightAdapter;
import zhanghegang.com.bawei.zhanghegang1507c20170830.app.TotalApp;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.LeftData;
import zhanghegang.com.bawei.zhanghegang1507c20170830.utils.NetWorkInfoUtils;


/**
 * Created by asus on 2017/8/30.
 */

public class RightFragment extends Fragment {
    public View view=null;
    private ListView right_lv;
    private ImageView iv;
    private String[] left_data;
    private SharedPreferences loadType;
    private Back back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
if(view==null)
{
    view=inflater.inflate(R.layout.right_fragment,container,false);
}
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

initView();
        initData();
    }
public void setBack(Back back){
    this.back=back;
}
   public interface Back{
       void back(boolean slidingBack);
   }
    private void initData() {
        left_data = new String[]{"清除缓存","字体大小","列表显示摘要","非wifi网络流量","非wifit网络播放提醒","推送通知","离线下载","检查版本"};
        int[] imgs={0,0,R.drawable.msg_back,0,0,R.drawable.msg_go,0,0};
        String[] right_data={"7.67MB","中","","极省流量（不下载图）","提醒一次","","","6.3.2"};
        final List<LeftData> datas=new ArrayList<>();
        for (int i = 0; i < left_data.length; i++) {

            LeftData data=new LeftData();
            data.left= left_data[i];
            data.right=right_data[i];
            data.img=imgs[i];
            datas.add(data);
        }
        RightAdapter adpter=new RightAdapter(getActivity(),datas);
        right_lv.setAdapter(adpter);
        right_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(datas.get(i).left.equals("离线下载"))
                {
                    startActivity(new Intent(getActivity(),DownActivity.class));
                }
                if(datas.get(i).left.equals("非wifi网络流量"))
                {
                    setdialog();
                 vertifyNet();
                }
//                if(datas.get(i).left.equals("推送通知"))
//                {
//
//                }

            }
        });
        adpter.setOnCheck(new RightAdapter.Oncheck() {
            @Override
            public void onCheck(boolean check) {
                if(check==false)
                {
                    JPushInterface.stopPush(getActivity());
Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    JPushInterface.resumePush(getActivity());
                }
            }
        });

    }

    private void setdialog() {
        final AlertDialog show = new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"大图", "无图"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String net = loadType.getString("net", "");

                if (i == 0) {
                    loadType.edit().putString("net", "hasnet").commit();

                } else if (i == 1) {
                    loadType.edit().putString("net", "nonet").commit();
                }
                dialogInterface.dismiss();
            }
        }).show();
    }

    private void vertifyNet() {
        new NetWorkInfoUtils().vertifyNet(getActivity(), new NetWorkInfoUtils.NetWorkListener() {
            @Override
            public void haveMobileNet() {
//                String net = loadType.getString("net", "");
//                Log.d("RightFragment", net);
//                if(net.equals("hasnet"))
//{
//    loadType.edit().putString("netStatus","big").commit();
//    String netStatus = loadType.getString("netStatus", "");
//    Log.d("RightFragment", netStatus);
//}
//else if(net.equals("nonet"))
//                {
//                    loadType.edit().putString("netStatus","noImg").commit();
//                }
            }

            @Override
            public void haveWifiNet() {
loadType.edit().putString("net","hasnet").commit();
            }

            @Override
            public void noNet() {
                loadType.edit().putString("net","nonet").commit();
            }
        });
    }

    private void initView() {
        right_lv = view.findViewById(R.id.right_lv);

        iv = view.findViewById(R.id.iv_right_back);
        loadType = TotalApp.appContext.getSharedPreferences("loadType", Context.MODE_PRIVATE);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroyView();
            }
        });
    }


}
