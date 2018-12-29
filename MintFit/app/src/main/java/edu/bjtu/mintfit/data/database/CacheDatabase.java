package edu.bjtu.mintfit.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import edu.bjtu.mintfit.data.dao.ExerciseDao;
import edu.bjtu.mintfit.data.dao.TrendDao;
import edu.bjtu.mintfit.data.dao.UserDao;
import edu.bjtu.mintfit.data.entity.ExerciseEntity;
import edu.bjtu.mintfit.data.entity.TrendEntity;
import edu.bjtu.mintfit.data.entity.UserEntity;

@Database(entities = {ExerciseEntity.class, TrendEntity.class, UserEntity.class}, version = 1,exportSchema = false)
public abstract class CacheDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TrendDao trendDao();
    public abstract ExerciseDao exerciseDao();

}
