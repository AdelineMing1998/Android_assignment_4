package edu.bjtu.mintfit.data.entity;

/**
 * author : ByteSpider
 * e-mail : algorirhy@gmail.com
 * time   : 2018/10/09
 * desc   : 运动项目实体类
 * version: 1.0
 */
public class Exercises {
    private String exe_name;
    private int exe_image;

    public Exercises(String exe_name, int exe_image) {
        this.exe_name = exe_name;
        this.exe_image = exe_image;
    }

    public int getExe_image() {
        return exe_image;
    }

    public String getExe_name() {
        return exe_name;
    }
}
