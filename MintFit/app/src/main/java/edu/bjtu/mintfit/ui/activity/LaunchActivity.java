package edu.bjtu.mintfit.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.bjtu.mintfit.R;


/**
 * author : ByteSpider
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : 启动图Activity
 * version: 1.0
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Integer time = 1000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                /**
                 * 2018年11月14日晚上
                 * 对界面跳转进行修改
                 * author：Adeline.ming@icloud.com
                 */
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                LaunchActivity.this.finish();
            }
        }, time);
    }


}
