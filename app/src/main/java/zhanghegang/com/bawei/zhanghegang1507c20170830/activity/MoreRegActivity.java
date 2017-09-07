package zhanghegang.com.bawei.zhanghegang1507c20170830.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.URLEncoder;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import zhanghegang.com.bawei.zhanghegang1507c20170830.R;
import zhanghegang.com.bawei.zhanghegang1507c20170830.bean.User;
import zhanghegang.com.bawei.zhanghegang1507c20170830.utils.SharePrefenceUtils;

public class MoreRegActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.et_phone)
    EditText et_phone;
    @ViewInject(R.id.et_yanzheng)
    EditText et_yanzheng;
    @ViewInject(R.id.phone_msg)
    TextView phone_msg;
    @ViewInject(R.id.btn_more_reg)
    Button btn_more_reg;
    @ViewInject(R.id.more_qq)
    ImageView more_qq;
    private int time=20;
private Handler handler=new Handler();
    Runnable ru=new Runnable() {
        @Override
        public void run() {
            time--;
            if(time==0)
            {
                handler.removeCallbacks(ru);
                phone_msg.setEnabled(true);
                phone_msg.setText("再次获取");
                time=20;
            }
            else
            {
                phone_msg.setEnabled(false);
                phone_msg.setTextColor(Color.RED);
                phone_msg.setText(time+"s");
                handler.postDelayed(ru,1000);
            }
        }
    };
//    private QQOauthUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_reg);
        x.view().inject(this);
        initData();
//        utils = new QQOauthUtils();
        phone_msg.setOnClickListener(this);
        more_qq.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.more_qq:
//                really();
                break;
            case R.id.btn_more_reg:
//                if(TextUtils.isEmpty(et_phone.getText().toString())||TextUtils.isEmpty(et_yanzheng.getText().toString()))
//                {
//                    Toast.makeText(this, "信息不全", Toast.LENGTH_SHORT).show();
//                    return;
//                }
SMSSDK.submitVerificationCode("86",et_phone.getText().toString(),et_yanzheng.getText().toString());
                break;
            case R.id.phone_msg:
                if(TextUtils.isEmpty(et_phone.getText().toString()))
                {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                handler.postDelayed(ru,1000);
SMSSDK.getVerificationCode("86",et_phone.getText().toString());
                break;

        }
    }

//    private void really() {
//        utils=new QQOauthUtils();
//        utils.qqLogin(MoreRegActivity.this,new QQOauthUtils.QQCallBack(){
//            @Override
//            public void logsuccess() {
//
//            }
//
//            @Override
//            public void getuserinfo(Map<String, String> map) {
//
//            }
//
//
//            @Override
//            public void getUserName(String name) {
//
//            }
//
//            @Override
//            public void getImagepath(String url) {
////                Glide.with(MoreRegActivity.this).load(url).into(more_qq);
//            }
//        });
//
//    }

    private void initData() {
        EventHandler ev=new EventHandler(){
            @Override
            public void afterEvent(int i, int result, Object o) {

                if(o instanceof Throwable){
                    Throwable able= (Throwable) o;
                    final String message = able.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MoreRegActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //判断是否完成
                if(result== SMSSDK.RESULT_COMPLETE)
                {
                    //判断是否提交验证码成功
if(i==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
{
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MoreRegActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
            User user=new User();
            user.uid=et_phone.getText().toString();
            user.phone=et_phone.getText().toString();
            SharePrefenceUtils sp=new SharePrefenceUtils();
            sp.putData("uid",user.uid);
            sp.putData("phone",user.phone);

        }
    });
}
//判断是否获取验证码成功
else if(i==SMSSDK.EVENT_GET_VERIFICATION_CODE)
{
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MoreRegActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
        }
    });
}
//判断验证码是否符合国家列表
else if(i==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
{

}
else{
    ((Throwable)o).printStackTrace();
}
                }
            }
        };
        //注册短信回调
        SMSSDK.registerEventHandler(ev);


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        utils.onActivityResult(requestCode, resultCode, data);
//    }



}
