package edu.bjtu.mintfit.util;

import android.app.Application;
import android.content.Context;

/**
 *     author : ByteSpider
 *     e-mail : algorirhy@gmail.com
 *     time   : 2018/10/03
 *     desc   : Application类，用于全局获取context
 *     version: 1.0
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
