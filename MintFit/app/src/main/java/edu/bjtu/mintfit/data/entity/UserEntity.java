package edu.bjtu.mintfit.data.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "user", primaryKeys = "email")
public class UserEntity {

    public UserEntity(@NonNull String email, String username, String gender, String age, String phone) {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    private String email;

    private String username;
    private String gender;
    private String age;


    private String phone;
}
