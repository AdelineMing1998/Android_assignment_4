package edu.bjtu.mintfit.data.entity;

/**
 * author : ByteSpider
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/09
 * desc   : 动态实体类
 * version: 1.0
 */
public class Trends {
    private String trends_name;
    private String trends_content;
    private int trends_head_portrait;
    private int trends_image;

    public Trends(String trends_name, String trends_content, int trends_head_portrait, int trends_image) {
        this.trends_name = trends_name;
        this.trends_content = trends_content;
        this.trends_head_portrait = trends_head_portrait;
        this.trends_image = trends_image;
    }

    public String getTrends_name() {
        return trends_name;
    }

    public String getTrends_content() {
        return trends_content;
    }

    public int getTrends_head_portrait() {
        return trends_head_portrait;
    }

    public int getTrends_image() {
        return trends_image;
    }
}
