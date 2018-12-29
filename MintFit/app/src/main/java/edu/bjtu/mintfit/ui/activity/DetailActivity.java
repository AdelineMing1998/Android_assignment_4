package edu.bjtu.mintfit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import edu.bjtu.mintfit.R;

/**
 *
 * author : algorirhy
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/09
 * desc   : 视频播放详情Activity
 * version: 1.0
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tv_Coach = findViewById(R.id.coach_tel);
        tv_Coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("tel:13123332333"));
                startActivity(intent);
            }
        });

        VideoView videoView = findViewById(R.id.video_view);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/Coding/1545993443725.mp4";
        /**
         本地视频
         */
        videoView.setVideoPath(path);
        /**
         * 网络视频
         */
//        videoView.setVideoURI(Uri.parse(""));

        /**
         * MediaController控制视频播放
         */
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }
}
