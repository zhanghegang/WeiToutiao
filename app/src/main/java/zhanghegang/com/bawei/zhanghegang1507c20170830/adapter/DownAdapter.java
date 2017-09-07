package zhanghegang.com.bawei.zhanghegang1507c20170830.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.DownBean;

/**
 * Created by asus on 2017/9/5.
 */

public class DownAdapter extends RecyclerView.Adapter<DownAdapter.MyViewHolder>{
    private final Context context;
    private final List<DownBean> list;
    private OnItemListener onItem;

    public DownAdapter(Context context, List<DownBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //添加布局
        View view=View.inflate(context,R.layout.down_item,null);
        //实例化MyViewHolder传填充布局
        final MyViewHolder viewHolder=new MyViewHolder(view);
        //设置点击view监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) viewHolder.itemView.getTag();
onItem.method(pos,view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
holder.tv_down_item.setText(list.get(position).name);
        //存item的索引
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        private final CheckBox cb_down;
        private final TextView tv_down_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            //把控件赋给ViewHolder
            tv_down_item = itemView.findViewById(R.id.tv_down_item);
            cb_down = itemView.findViewById(R.id.cb_down);
        }
    }

    /**
     * 创建构造器
     */
    public void setOnItemClik(OnItemListener onItem){
        this.onItem=onItem;
    }
    //创建接口
    public interface OnItemListener{
        void method(int pos,View view);
    }

}
