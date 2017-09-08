package zhanghegang.com.bawei.zhanghegang1507c20170830.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;

public class WebActivity extends AppCompatActivity implements View.OnTouchListener {

    private WebView wv;
    private VelocityTracker tracker;
    //速度
    public static int MINSUDU=800;
    //最短距离
    public static int JULI=300;
public float downX;
    public float moveX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initSet();
        initload();
    }

    private void initload() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        wv.loadUrl(url);
    }

    private void initSet() {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wv.setOnTouchListener(this);
    }

    private void initView() {
        wv = (WebView) findViewById(R.id.wv_detail);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //创建获得速度的方法
        createVorket(motionEvent);

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
downX=motionEvent.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
               moveX= motionEvent.getRawX();
                //获得移动距离
                int v = (int) (moveX - downX);
                //获得速度
                tracker.computeCurrentVelocity(1000);
               int xVelocity = (int) tracker.getXVelocity();
                //获得速度的平均值
                int abs = Math.abs(xVelocity);
                if(v>JULI&&abs>MINSUDU){
                    finish();
                }


                break;
            case MotionEvent.ACTION_UP:
                //销毁
                tracker.recycle();
                tracker=null;
                break;
        }
        return false;
    }

    private void createVorket(MotionEvent motionEvent) {
        if(tracker==null) {
            tracker = VelocityTracker.obtain();

        }
        //把事件传给速度
        tracker.addMovement(motionEvent);
    }

}
