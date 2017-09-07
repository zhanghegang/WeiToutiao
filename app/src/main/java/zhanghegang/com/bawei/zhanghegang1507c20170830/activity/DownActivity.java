package zhanghegang.com.bawei.zhanghegang1507c20170830.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.adapter.DownAdapter;
import zhanghegang.com.bawei.zhanghegang1507c20170830.api.HttpUrl;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.DownBean;
import zhanghegang.com.bawei.zhanghegang1507c20170830.sqlite.MyDao;

public class DownActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private TextView tv_down;
    private List<DownBean> downs;
    private MyDao myDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        initView();
        initData();

    }

    private void initData() {
        downs = new ArrayList<>();
        DownBean down=new DownBean();
        down.uid="top";
        down.name="头条";
        downs.add(down);

        down=new DownBean();
        down.uid="shehui";
        down.name="社会";
        downs.add(down);

        down=new DownBean();
        down.uid="junshi";
        down.name="军事";
        downs.add(down);

        DownAdapter adapter=new DownAdapter(this, downs);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
adapter.setOnItemClik(new DownAdapter.OnItemListener() {
    @Override
    public void method(int pos, View view) {
       CheckBox cb_down= view.findViewById(R.id.cb_down);
        DownBean db=downs.get(pos);
        if(cb_down.isChecked())
        {
            cb_down.setChecked(false);
            db.status=false;
        }
        else
        {
            cb_down.setChecked(true);
          db.status=true;
        }
        downs.set(pos,db);


    }
});


    }

    private void initView() {
        myDao = new MyDao(this);
//        AlertController.RecycleListView
        recyclerview = (RecyclerView) findViewById(R.id.lv_recycle);
        tv_down = (TextView) findViewById(R.id.tv_down_head);
        tv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(downs!=null&&downs.size()>0) {


                    for (int i = 0; i < downs.size(); i++) {
                        Log.d("DownActivity", "downs.get(i).status:" + downs.get(i).status);
if(downs.get(i).status)
{
   loadData(downs.get(i).uid);
}

                    }

                }
            }
        });
    }

    private void loadData(final String uid) {
        RequestParams params=new RequestParams(HttpUrl.POST_URL);
        params.addBodyParameter("key",HttpUrl.KEY);
        params.addBodyParameter("type",uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null)
                {
                    Log.d("DownActivity", "经过");
myDao.add(uid,result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
