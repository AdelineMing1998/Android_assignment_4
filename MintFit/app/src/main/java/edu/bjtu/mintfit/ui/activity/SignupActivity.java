package edu.bjtu.mintfit.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.ui.activity.LoginActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : ByteSpider
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/05
 * desc   : 注册页面Activity
 * version: 1.0
 */

public class SignupActivity extends AppCompatActivity {

    private Callback register_callback;
    private Callback verify_callback;

    private EditText et_register_mail;
    private EditText et_register_password;
    private EditText et_register_repeat;
    private EditText et_register_username;
    private EditText et_register_verification_code;

    private Button btn_register_get_verification_code;

    private Handler handler;

    private Boolean btn_verification_code_state = true;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_register_mail = findViewById(R.id.et_register_mail);
        et_register_password = findViewById(R.id.et_register_password);
        et_register_repeat = findViewById(R.id.et_register_repeat);
        et_register_verification_code = findViewById(R.id.et_register_verification_code);
        et_register_username = findViewById(R.id.et_register_username);

        btn_register_get_verification_code = findViewById(R.id.btn_register_get_verification_code);

        handler = new Handler();

        init_register_callback();
        init_verify_callback();

    }

    public void get_verification_code(View v) {

        final String GET_VERIFICATION_CODE_URL = "http://118.89.235.59:8080/get_verification_code";

        String email = et_register_mail.getText().toString().trim();
        if (btn_verification_code_state) {
            btn_verification_code_state = false;
            time = 60;
            btn_register_get_verification_code.setBackgroundResource(R.drawable.ic_send_verification_code);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (time < 0) {
                        btn_register_get_verification_code.setText(R.string.get_verification_code);
                        btn_register_get_verification_code.setBackgroundResource(R.drawable.ic_send_verification_code);
                        btn_verification_code_state = true;
                        return;
                    }
                    btn_register_get_verification_code.setText(String.valueOf(time));
                    handler.postDelayed(this, 1000);
                    time--;
                }

            };
            handler.removeCallbacks(runnable);
            handler.post(runnable);
            HashMap<String, String> params = new HashMap<>();
            params.put("email", email);
            async_http_post(GET_VERIFICATION_CODE_URL, params, verify_callback);
        }
    }

    public void init_register_callback() {
        register_callback = new Callback() {
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
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
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

    public void init_verify_callback() {
        verify_callback = new Callback() {
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
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "获取验证码成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "获取验证码失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
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

    public void onSignupBtnClick(View view) {
        final String REGISTER_URL = "http://118.89.235.59:8080/register";

        String email = et_register_mail.getText().toString().trim();
        String password = et_register_password.getText().toString().trim();
        String repeat = et_register_repeat.getText().toString().trim();
        String verification_code = et_register_verification_code.getText().toString().trim();
        String username = et_register_username.getText().toString().trim();

        if (!repeat.equals(password))
        {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("username", username);
        params.put("verification_code", verification_code);
        async_http_post(REGISTER_URL, params, register_callback);
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
}
