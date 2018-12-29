package edu.bjtu.mintfit.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.bjtu.mintfit.data.entity.TrendEntity;

@Dao
public interface TrendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrends(TrendEntity... trendsEntities);

    @Query("SELECT * FROM trend")
    TrendEntity[] loadAllTrends();
}
