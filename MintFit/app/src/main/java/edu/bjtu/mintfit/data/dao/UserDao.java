package edu.bjtu.mintfit.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import edu.bjtu.mintfit.data.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity ... userEntities);

    @Query("SELECT * FROM user")
    UserEntity [] loadCurrUser();
}
