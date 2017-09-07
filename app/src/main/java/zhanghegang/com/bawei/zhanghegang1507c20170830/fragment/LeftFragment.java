package zhanghegang.com.bawei.zhanghegang1507c20170830.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zhanghegang.com.bawei.zhanghegang1507c20170830.MainActivity;
import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.activity.MoreRegActivity;
import zhanghegang.com.bawei.zhanghegang1507c20170830.adapter.LeftAdapter;
import zhanghegang.com.bawei.zhanghegang1507c20170830.app.TotalApp;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.LeftData;

/**
 * Created by asus on 2017/8/30.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {
    public View view=null;
    private ListView left_lv;
    private Button btn;
public static ImageView iv_qq;
    private ImageView iv_weibo;
    private ImageView iv_yejian;
    private SharedPreferences change;
    private TextView tv_yanjian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
if(view==null)
{
    view=inflater.inflate(R.layout.left_fragment,container,false);
}
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListenter();
    }

    private void initData() {
String[] left_data={"我的关注","消息通知","头条商城","京东特供","我要爆料","用户反馈","系统设置"};
int[] imgs={R.drawable.d,0,0,0,0,0,0};
        String[] right_data={"我的直播","","邀请好友的200元现金奖励","新人领188元红包","","",""};
        List<LeftData> datas=new ArrayList<>();
        for (int i = 0; i <left_data.length; i++) {

          LeftData data=new LeftData();
            data.left=left_data[i];
            data.right=right_data[i];
            data.img=imgs[i];
            datas.add(data);
        }
        LeftAdapter adpter=new LeftAdapter(getActivity(),datas);
      left_lv.setAdapter(adpter);
    }
    private void initListenter() {

        if(iv_qq!=null) {
            iv_qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuLister);
                }
            });
        }
        if(iv_weibo!=null) {
            iv_weibo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, umAuLister);
                }
            });
        }

    }
    UMAuthListener umAuLister=new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };


    private void initView() {

        iv_qq=view.findViewById(R.id.iv_qq);
        iv_yejian = view.findViewById(R.id.iv_yejian);
        iv_weibo = view.findViewById(R.id.iv_weibo);
        left_lv = view.findViewById(R.id.left_lv);
        btn = view.findViewById(R.id.btn_more);
        btn.setOnClickListener(this);
        iv_yejian.setOnClickListener(this);
        tv_yanjian = view.findViewById(R.id.tv_yanjian);
        change = TotalApp.appContext.getSharedPreferences("change", Context.MODE_PRIVATE);
        if(change.getBoolean("day_night",false)==false) {
            tv_yanjian.setText("夜间");
        }
        else {
            tv_yanjian.setText("日间");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_more:
                getActivity().startActivity(new Intent(getActivity(), MoreRegActivity.class));
                break;
            case R.id.iv_yejian:

                if(change.getBoolean("day_night",false)==false){

                    System.out.println("false");
                    ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    getActivity().recreate();
                    change.edit().putBoolean("day_night",true).commit();

                }
                else
                {
                    System.out.println("true");

                    ((AppCompatActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    getActivity().recreate();
                    change.edit().putBoolean("day_night",false).commit();

                }

                break;
        }

    }
}
