package zhanghegang.com.bawei.zhanghegang1507c20170830.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.LeftData;

/**
 * Created by asus on 2017/8/31.
 */

public class RightAdapter extends BaseAdapter {
    private final Context context;
    private final List<LeftData> list;
    private Oncheck onCheck;

    public RightAdapter(Context context, List<LeftData> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view=View.inflate(context, R.layout.right_item,null);
        }
        if(i==0||i==6)
        {
           View v= view.findViewById(R.id.view_hui);
            v.setVisibility(View.VISIBLE);
        }
        TextView tv1=view.findViewById(R.id.tv_right_data);
        TextView tv2=view.findViewById(R.id.tv_other2);
        ImageView iv=view.findViewById(R.id.iv_other2);
        Switch sw=view.findViewById(R.id.right_switch);


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {

                   onCheck.onCheck(b);
                }
                else {
                    onCheck.onCheck(b);
                }
            }
        });
        LeftData leftData = list.get(i);


        if(leftData.left.equals("推送通知"))
        {
            sw.setVisibility(View.VISIBLE);
        }
        tv1.setText(leftData.left);

//            iv.setImageResource(leftData.img);

        tv2.setText(leftData.right);
        return view;
    }
    public void setOnCheck(Oncheck onCheck){
        this.onCheck=onCheck;
    }
    public interface Oncheck{
        void onCheck(boolean check);
    }
}
