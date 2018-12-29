package edu.bjtu.mintfit.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import edu.bjtu.mintfit.data.entity.ExerciseEntity;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercises(ExerciseEntity... exerciseEntities);

    @Query("SELECT * FROM exercise")
    ExerciseEntity[] loadAllExercise();
}
