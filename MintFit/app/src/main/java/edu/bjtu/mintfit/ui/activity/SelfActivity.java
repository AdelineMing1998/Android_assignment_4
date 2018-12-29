package edu.bjtu.mintfit.ui.activity;

import android.arch.persistence.room.Room;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.bjtu.mintfit.R;
import edu.bjtu.mintfit.data.database.CacheDatabase;
import edu.bjtu.mintfit.data.entity.UserEntity;

public class SelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        CacheDatabase cdb = Room.databaseBuilder(this, CacheDatabase.class, "bohe").allowMainThreadQueries().build();
        UserEntity[] userEntities = cdb.userDao().loadCurrUser();
        ((TextView)findViewById(R.id.tv_self_name)).setText(userEntities[0].getUsername());
        ((TextView)findViewById(R.id.tv_self_age)).setText(userEntities[0].getAge());
        ((TextView)findViewById(R.id.tv_self_gender)).setText(userEntities[0].getGender());
        ((TextView)findViewById(R.id.tv_self_phone)).setText(userEntities[0].getPhone());
        ((TextView)findViewById(R.id.tv_self_mail)).setText(userEntities[0].getEmail());

        cdb.close();
    }
}
