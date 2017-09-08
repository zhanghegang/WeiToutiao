package zhanghegang.com.bawei.zhanghegang1507c20170830.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;
import zhanghegang.com.bawei.zhanghegang1507c20170830.MainActivity;
import zhanghegang.com.bawei.zhanghegang1507c20170830.MyAdapter;
import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.activity.WebActivity;
import zhanghegang.com.bawei.zhanghegang1507c20170830.api.HttpUrl;
import zhanghegang.com.bawei.zhanghegang1507c20170830.app.TotalApp;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.News;



/**
 * Created by asus on 2017/8/31.
 */

public class TopFragment extends Fragment implements XListView.IXListViewListener {

    private SlidingMenu menu;
    private View view;
    private MyAdapter adapter;
    private Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.top_fragment,null);
        return view;
    }
//    @ViewInject(R.id.xlv)
    XListView lv_news;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     lv_news=view.findViewById(R.id.xlv);
        lv_news.setPullRefreshEnable(true);
        lv_news.setPullLoadEnable(true);
        lv_news.setXListViewListener(this);
        bundle=null;
        loadNews();

    }

    private void loadNews() {
        //获得activity传过来的参数

        bundle = getArguments();
        String type = bundle.getString("type");

        System.out.println("--------------------2222----"+type);
        RequestParams params=new RequestParams(HttpUrl.POST_URL);
        params.addBodyParameter("key",HttpUrl.KEY);
        params.addBodyParameter("type",type);
        //post请求
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(result!=null)
                {
                    System.out.println(result);
                    //解析
                    pareseData(result);
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

    private void pareseData(final String data) {
        Gson gson=new Gson();
        News news = gson.fromJson(data, News.class);
        final List<News.ResultBean.DataBean> data1 = news.getResult().getData();

        if(adapter==null)
        {
            adapter = new MyAdapter((MainActivity) getActivity(),data1);
            lv_news.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }



        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = data1.get(i).getUrl();
                Intent intent=new Intent(getActivity(),WebActivity.class);

                intent.putExtra("url",url);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
        lv_news.stopLoadMore();
        lv_news.stopRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();



    }

    @Override
    public void onStop() {
        super.onStop();

        adapter=null;
    }

    @Override
    public void onRefresh() {
adapter=null;
        loadNews();
    }

    @Override
    public void onLoadMore() {
loadNews();
    }
}
