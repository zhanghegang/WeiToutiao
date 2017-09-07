package zhanghegang.com.bawei.zhanghegang1507c20170830;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.app.TotalApp;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.News;

/**
 * Created by asus on 2017/8/30.
 */

public class MyAdapter extends BaseAdapter {
    private final MainActivity context;
    private final List<News.ResultBean.DataBean> list;
    private  final int a=0;
    private final int b=1;
    private final int total=2;
    SharedPreferences loadType;

    public MyAdapter(MainActivity context, List<News.ResultBean.DataBean> list) {
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
    public int getItemViewType(int position) {
        if(position%2==0)
        {
            return a;
        }
        else
        {
            return b;
        }

    }

    @Override
    public int getViewTypeCount() {
        return total;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        loadType = TotalApp.appContext.getSharedPreferences("loadType", Context.MODE_PRIVATE);
        ViewHolder1 v1=null;
        ViewHolder2 v2=null;
        int type = getItemViewType(i);
        if(view==null)
        {
            switch (type){
                case a:
                    v1=new ViewHolder1();
                    view=View.inflate(context,R.layout.horizontal_item,null);
                    v1.tv_date=view.findViewById(R.id.tv_date);
                    v1.tv_title=view.findViewById(R.id.tv_title);
                    v1.tv_type=view.findViewById(R.id.tv_type);
                    v1.iv1=view.findViewById(R.id.iv_horizontal);
                    view.setTag(v1);
                    break;
                case b:
                    v2=new ViewHolder2();
                    view=View.inflate(context,R.layout.vertical_item,null);
                    v2.tv_date2=view.findViewById(R.id.tv_date2);
                    v2.tv_title2=view.findViewById(R.id.tv_title2);
                    v2.tv_type2=view.findViewById(R.id.tv_type2);
                    v2.iv2=view.findViewById(R.id.iv_vertical);
                    view.setTag(v2);
                    break;
            }
        }
        else
            switch (type) {
                case a:
                    v1 = (ViewHolder1) view.getTag();
                    break;
                case b:
                    v2 = (ViewHolder2) view.getTag();
                    break;
            }
        News.ResultBean.DataBean bean = list.get(i);

        switch (type) {
            case a:
v1.tv_date.setText(bean.getDate());
                v1.tv_title.setText(bean.getTitle());
                v1.tv_type.setText(bean.getAuthor_name());
                String netStatus = loadType.getString("net", "");
                System.out.println("==================netstaus"+netStatus);
                if(netStatus.equals("hasnet")) {
                    ImageLoader.getInstance().displayImage(bean.getThumbnail_pic_s(), v1.iv1);
                }

                break;
            case b:
                v2.tv_date2.setText(bean.getDate());
                v2.tv_title2.setText(bean.getTitle());
                v2.tv_type2.setText(bean.getAuthor_name());
                String netStatus2 = loadType.getString("net", "");
                if(netStatus2.equals("hasnet")) {
                    ImageLoader.getInstance().displayImage(bean.getThumbnail_pic_s(), v2.iv2);
                }
                break;
        }

        return view;
    }
    class ViewHolder1{
        TextView tv_title;
        TextView tv_type;
        TextView tv_date;
        ImageView iv1;
    }
    class ViewHolder2{
        TextView tv_title2;
        TextView tv_type2;
        TextView tv_date2;
        ImageView iv2;
    }
}
