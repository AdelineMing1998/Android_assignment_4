package edu.bjtu.mintfit.data.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "trend", primaryKeys = "trends_name")
public class TrendEntity {
    public @NonNull String getTrends_name() {
        return trends_name;
    }

    public void setTrends_name(@NonNull String trends_name) {
        this.trends_name = trends_name;
    }

    public TrendEntity(@NonNull String trends_name, String trends_content, int trends_head_portrait, int trends_image) {
        this.trends_content = trends_content;
        this.trends_head_portrait = trends_head_portrait;
        this.trends_image = trends_image;
        this.trends_name = trends_name;
    }

    public String getTrends_content() {
        return trends_content;
    }

    public void setTrends_content(String trends_content) {
        this.trends_content = trends_content;
    }

    public int getTrends_head_portrait() {
        return trends_head_portrait;
    }

    public void setTrends_head_portrait(int trends_head_portrait) {
        this.trends_head_portrait = trends_head_portrait;
    }

    public int getTrends_image() {
        return trends_image;
    }

    public void setTrends_image(int trends_image) {
        this.trends_image = trends_image;
    }

    private String trends_content;
    private int trends_head_portrait;
    private int trends_image;
    @NonNull
    private String trends_name;
}
