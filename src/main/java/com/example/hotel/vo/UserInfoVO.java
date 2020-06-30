package com.example.hotel.vo;

import java.util.Date;

/**
 * @Author: chenyizong
 * @Date: 2020-03-03
 */
public class UserInfoVO {
    private String password;
    private String userName;
    private String phoneNumber;
    private Date birthday;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
