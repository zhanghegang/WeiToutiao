package zhanghegang.com.bawei.horizontalview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;



/**
 * Created by asus on 2017/8/30.
 */

public class HorizontalView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Context context;
    private View view;
    private LinearLayout ll_content;
    private ViewPager vp;
    private List<String> types;
    private List<Fragment> fragments;
    private HorizontalScrollView hlv;

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
    public  void init(Context context)
    {
//        types=new ArrayList<>();
//        fragments=new ArrayList<>();
        this.context=context;
        view = LayoutInflater.from(context).inflate(R.layout.div_scroll,this,true);
        //获得控件
        hlv = view.findViewById(R.id.hlv_content);
        ll_content = view.findViewById(R.id.ll_content);
       vp = view.findViewById(R.id.vp_type);
        vp.addOnPageChangeListener(this);


    }
//获得内容方法
    public void getContent(List<String> types, List<Fragment> framents){
        this.types=types;
        this.fragments=framents;
        draw();
    }
    private void draw() {
        //画TextVIew
        drawTextView();

       vp.setAdapter(new MyAdapter( ((FragmentActivity)context).getSupportFragmentManager()));

    }

    /**
     * 居中显示文字
     * @param tv
     */
    private void moveItemToCenter(TextView tv) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int screenWitdth = getResources().getDisplayMetrics().widthPixels;
       int screenWidth = dm.widthPixels;
        int[] locations = new int[2];
        tv.getLocationInWindow(locations);
        int measuredWidth = tv.getMeasuredWidth();
        int left = tv.getLeft();

        int rbWidth = tv.getWidth();
//        hlv.smoothScrollBy((locations[0] + rbWidth / 2 - screenWidth / 2),
//                0);
        hlv.smoothScrollBy((left+measuredWidth/2-screenWidth/2),0);
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
                    moveItemToCenter(tv);
                }
            });

            //添加到线性布局
            LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
