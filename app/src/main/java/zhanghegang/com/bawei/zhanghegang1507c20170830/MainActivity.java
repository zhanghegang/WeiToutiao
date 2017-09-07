package zhanghegang.com.bawei.zhanghegang1507c20170830;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kson.slidingmenu.SlidingMenu;
import com.umeng.socialize.UMShareAPI;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.Channel;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.NewsType;
import zhanghegang.com.bawei.zhanghegang1507c20170830.fragment.LeftFragment;
import zhanghegang.com.bawei.zhanghegang1507c20170830.fragment.RightFragment;
import zhanghegang.com.bawei.zhanghegang1507c20170830.fragment.TopFragment;
import zhanghegang.com.bawei.zhanghegang1507c20170830.view.HorizontalView;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SlidingMenu menu;

    @ViewInject(R.id.iv_user)
    ImageView iv_user;
    @ViewInject(R.id.iv_more)
    ImageView iv_more;

@ViewInject(R.id.horizontal_scolled)
    HorizontalView horizontalView;
    private LeftFragment leftFragment;
    private ArrayList<String> types;
    private String[] typeId={"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        setContentView(R.layout.activity_main);
        x.view().inject(MainActivity.this);


        initMenu();
pressListenter();
        initData();

    }

//    private void initListenter() {
////        View view=View.inflate(MainActivity.this,R.layout.left_fragment,null);
////        ImageView iv=view.findViewById(R.id.iv_qq);
//        if(leftFragment.iv_qq!=null) {
//            leftFragment.iv_qq.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuLister);
//                }
//            });
//        }
//    }
//    UMAuthListener umAuLister=new UMAuthListener() {
//        @Override
//        public void onStart(SHARE_MEDIA share_media) {
//
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA share_media, int i) {
//
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
//    }

    private void initData() {
        types = new ArrayList<>();
        //,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
        String[] typeAll={"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
        for (int i = 0; i < typeAll.length; i++) {
            types.add(typeAll[i]);
        }
        List<Fragment> fragments=new ArrayList<>();

        for (int i = 0; i <typeId.length ; i++) {
            TopFragment top=new TopFragment();
            Bundle bundle=new Bundle();
            bundle.putString("type",typeId[i]);
            top.setArguments(bundle);
            fragments.add(top);
        }





        horizontalView.getContent(types, fragments, new HorizontalView.ClikLitener() {
            @Override
            public void onClikLitener(boolean press) {
                if(press)
                {
//                    Intent intent=new Intent(MainActivity.this,ChannelMannagerActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putStringArrayList("type_name", types);
//
//                intent.putExtras(bundle);
//
//                startActivity(intent);
                    List<ChannelBean> list = new ArrayList<>();
                    ChannelBean channel;
                    if(types !=null) {
                        for (int i = 0; i < types.size(); i++) {
                            channel = new ChannelBean(types.get(i), true);
                            list.add(channel);
                        }
                    }
                    ChannelActivity.startChannelActivity(MainActivity.this,list);

                }
            }
        });
    }

    private void pressListenter() {
        iv_more.setOnClickListener(this);
        iv_user.setOnClickListener(this);
    }

    private void initMenu() {
        menu = new SlidingMenu(this);
        menu.setMenu(R.layout.left_layout);
        //添加做菜单
//        setBehindContentView(R.layout.left_layout);
        //替换左菜单
        leftFragment = new LeftFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_left, leftFragment).commit();
//        initListenter();

        //设置相关属性

        //设置左右菜单
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置边缘滑动
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置滑动后主布局剩余宽度
        menu.setBehindOffsetRes(R.dimen.margin);

        //设置右菜单
        menu.setSecondaryMenu(R.layout.right_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_right,new RightFragment()).commit();
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode,resultCode,data);
        if(resultCode==101)
        {
            //获得ChanneActivity
            String json = data.getStringExtra("json");
            Gson gson=new Gson();
            //定义集合
            List<NewsType> newsType_list=new ArrayList<>();
            final List<Channel> channel_list=gson.fromJson(json,new TypeToken<List<Channel>>(){}.getType());
          if(channel_list!=null) {
              types.clear();
              for (int i = 0; i < channel_list.size(); i++) {
                  if(channel_list.get(i).isIsSelect()) {
                      NewsType newsType=new NewsType();
                      String name = channel_list.get(i).getName();

                      types.add(name);
                      newsType.name=name;
                      newsType.uid=typeId[i];
                      newsType_list.add(newsType);
                  }
              }
              List<Fragment> fragments=new ArrayList<>();


              for (int i = 0; i <newsType_list.size() ; i++) {
                  //创建Fragment
                  TopFragment top=new TopFragment();
                  //创建bundle
                  Bundle bundle=new Bundle();
                  //存数据在bundle容器中
                  bundle.putString("type",newsType_list.get(i).uid);
                  //把bundle通过Arguments发送
                  top.setArguments(bundle);
                  //把创建成功的Fragment加入Fragment集合
                  fragments.add(top);

              }
              //重新向自定义View发送数据
              horizontalView.getContent(types, fragments, new HorizontalView.ClikLitener() {
                  @Override
                  public void onClikLitener(boolean press) {

                      List<ChannelBean> list = new ArrayList<>();
                      ChannelBean channel;
                      if(channel_list !=null) {
                          for (int i = 0; i < channel_list.size(); i++) {
                              //判断数据的状态并存在集合中
                              channel = new ChannelBean(channel_list.get(i).getName(), channel_list.get(i).isIsSelect());
                              list.add(channel);
                          }
                      }
                      //跳转到ChannelActivity中
                      ChannelActivity.startChannelActivity(MainActivity.this,list);
                  }
              });
          }


        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_user:
               menu.showMenu();
                break;
            case R.id.iv_more:
                menu.showSecondaryMenu(true);
                break;
        }
    }



}
