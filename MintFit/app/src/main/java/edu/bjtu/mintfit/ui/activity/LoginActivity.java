package edu.bjtu.mintfit.ui.activity;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.database.CacheDatabase;
import edu.bjtu.mintfit.data.entity.ExerciseEntity;
import edu.bjtu.mintfit.data.entity.Exercises;
import edu.bjtu.mintfit.data.entity.TrendEntity;
import edu.bjtu.mintfit.data.entity.Trends;
import edu.bjtu.mintfit.data.entity.UserEntity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Handler handler;

    private Callback login_callback;
    private Callback token_login_callback;

    private EditText et_mail;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_mail = findViewById(R.id.et_mail);
        et_password = findViewById(R.id.et_password);
        handler = new Handler();
        simulationReq();
        init_login_callback();
        init_token_login_callback();
        try_to_skip_login();

    }

    private void try_to_skip_login() {
        //检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        final String TOKEN_LOGIN_URL = "http://118.89.235.59:8080/check_login_state";
        SharedPreferences sp = getSharedPreferences("bohe", Context.MODE_PRIVATE);
        String token = sp.getString("token", null);
        String email = sp.getString("email", null);
        if (token != null && email != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("token", token);
            params.put("serial_number", android.os.Build.getSerial());
            params.put("email", email);
            async_http_post(TOKEN_LOGIN_URL, params, token_login_callback);
        }
    }

    private void init_login_callback() {
        login_callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Ignore
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if (json.has("errno") && json.getInt("errno") == 0) {
                        SharedPreferences sp = getSharedPreferences("bohe", Context.MODE_PRIVATE);
                        sp.edit().putString("token", json.getString("token")).apply();
                        sp.edit().putString("email", json.getString("email")).apply();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "服务器内部错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };
    }

    private void init_token_login_callback() {
        token_login_callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Ignore
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if (json.has("errno") && json.getInt("errno") == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    public void async_http_post(String url, HashMap<String, String> params, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody requestBody = builder.build();

        final Request request = new Request
                .Builder()
                .addHeader("Connection", "close")
                .post(requestBody)
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }


    public void login(View v) {

        //检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        final String LOGIN_URL = "http://118.89.235.59:8080/login";

        String email = et_mail.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("serial_number", android.os.Build.getSerial());
        params.put("email", email);
        params.put("password", password);
        async_http_post(LOGIN_URL, params, login_callback);
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void simulationReq() {
        CacheDatabase cdb = Room.databaseBuilder(this, CacheDatabase.class, "bohe").allowMainThreadQueries().build();
        cdb.trendDao().insertTrends(
                new TrendEntity("薄荷小助手", "我还能跑10KM！！！", R.drawable.head_portrait, R.drawable.football),
                new TrendEntity("薄荷小助手", "我还能跑20KM！！！", R.drawable.head_portrait, R.drawable.basketball),
                new TrendEntity("薄荷小助手", "我还能跑30KM！！！", R.drawable.head_portrait, R.drawable.table_tennis),
                new TrendEntity("薄荷小助手", "我还能跑10KM！！！", R.drawable.head_portrait, R.drawable.football),
                new TrendEntity("薄荷小助手", "我还能跑20KM！！！", R.drawable.head_portrait, R.drawable.basketball),
                new TrendEntity("薄荷小助手", "我还能跑30KM！！！", R.drawable.head_portrait, R.drawable.table_tennis));
        cdb.exerciseDao().insertExercises(
                new ExerciseEntity("乒乓球", R.drawable.table_tennis),
                new ExerciseEntity("乒乓球", R.drawable.table_tennis),
                new ExerciseEntity("篮球", R.drawable.basketball),
                new ExerciseEntity("篮球", R.drawable.basketball),
                new ExerciseEntity("足球", R.drawable.football),
                new ExerciseEntity("足球", R.drawable.football),
                new ExerciseEntity("乒乓球", R.drawable.table_tennis),
                new ExerciseEntity("乒乓球", R.drawable.table_tennis),
                new ExerciseEntity("篮球", R.drawable.basketball),
                new ExerciseEntity("篮球", R.drawable.basketball),
                new ExerciseEntity("足球", R.drawable.football),
                new ExerciseEntity("足球", R.drawable.football));
        cdb.userDao().insertUser(new UserEntity("16301013@bjtu.edu.cn", "mingxuran", "female", "20", "18800173037"));
        cdb.close();
    }
}

