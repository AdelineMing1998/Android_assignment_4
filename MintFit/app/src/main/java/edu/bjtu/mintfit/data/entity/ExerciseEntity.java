package edu.bjtu.mintfit.data.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "exercise", primaryKeys = "exe_name")
public class ExerciseEntity {
    @NonNull
    private String exe_name;
    private int exe_image;

    public ExerciseEntity(@NonNull String exe_name, int exe_image) {
        this.exe_name = exe_name;
        this.exe_image = exe_image;
    }

    public void setExe_name(@NonNull String exe_name)
    {
        this.exe_name = exe_name;
    }
    public @NonNull String getExe_name()
    {
        return this.exe_name;
    }

    public void setExe_image(int exe_image)
    {
        this.exe_image = exe_image;
    }
    public int getExe_image()
    {
        return this.exe_image;
    }
}
