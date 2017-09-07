package zhanghegang.com.bawei.zhanghegang1507c20170830.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;

/**
 * Created by asus on 2017/8/30.
 */

public class HorizontalView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Context context;
    private View view;
    private LinearLayout ll_content;
    private ViewPager vp;
    private ArrayList<String> types;
    private List<Fragment> fragments;
    private HorizontalScrollView hlv;
    private TextView tv_channel;

    public HorizontalView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public  void init(final Context context)
    {

//        types=new ArrayList<>();
//        fragments=new ArrayList<>();
        this.context=context;
        view = LayoutInflater.from(context).inflate(R.layout.div_scroll,this,true);
        //获得控件
        tv_channel = view.findViewById(R.id.tv_channel);
        hlv = view.findViewById(R.id.hlv_content);
        ll_content = view.findViewById(R.id.ll_content);

       vp = view.findViewById(R.id.vp_type);
        vp.addOnPageChangeListener(this);



    }
//获得内容方法
    public void getContent(final ArrayList<String> types, List<Fragment> framents, final ClikLitener clikLitener){
        this.types=types;
        this.fragments=framents;
        ll_content.removeAllViews();
        tv_channel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               clikLitener.onClikLitener(true);

            }
        });
        draw();
    }
    public interface ClikLitener{
        void onClikLitener(boolean press);
    }
    private void draw() {
        //画TextVIew
        drawTextView();

       vp.setAdapter(new MyAdapter( ((FragmentActivity)context).getSupportFragmentManager()));

    }

    private void drawTextView() {
        for (int i = 0; i <types.size() ; i++) {
            final TextView tv=(TextView) View.inflate(context,R.layout.text_type,null);



            tv.setText(types.get(i));
            if(i==0) {
                tv.setSelected(true);
            }
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    tv.setSelected(true);
                    vp.setCurrentItem(finalI);
                }
            });

            //添加到线性布局
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);
            ll_content.addView(tv,params);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i <types.size() ; i++) {
            TextView tv2 = (TextView) ll_content.getChildAt(i);
            if(i==position)
            {
                tv2.setSelected(true);
            }
            else {
                tv2.setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
