package edu.bjtu.mintfit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.ui.fragment.ExerciseFragment;
import edu.bjtu.mintfit.ui.adapter.ViewPagerAdapter;
import edu.bjtu.mintfit.ui.fragment.DataFragment;
import edu.bjtu.mintfit.ui.fragment.MeFragment;
import edu.bjtu.mintfit.ui.fragment.TrendsFragment;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;

/**
 * author : algorirhy
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : MainActivity
 * desc   : MainActivity
 * version: 1.0
 */

public class MainActivity extends AppCompatActivity
        implements ExerciseFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener,
        TrendsFragment.OnFragmentInteractionListener,
        DataFragment.OnFragmentInteractionListener {

    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        nickname = intent.getStringExtra("nickname");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new TrendsFragment());
        fragmentList.add(new ExerciseFragment());
        fragmentList.add(new DataFragment());
        fragmentList.add(new MeFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);

        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tabbar);
        NavigationController navigationController = tab.material()
                .addItem(R.drawable.community, "动态")
                .addItem(R.drawable.exercise, "锻炼")
                .addItem(R.drawable.data, "数据")
                .addItem(R.drawable.me, "我的")
                .build();
        navigationController.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "Search !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_notifications:
                Toast.makeText(MainActivity.this, "Notification !", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(MainActivity.this, "this is：" + uri, Toast.LENGTH_SHORT).show();
    }

    // 第一次按下返回键的事件
    private long firstPressedTime;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstPressedTime < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstPressedTime = System.currentTimeMillis();
        }
    }

    public String getUsername() {
        return nickname;
    }
}
