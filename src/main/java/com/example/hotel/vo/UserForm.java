package com.example.hotel.vo;

import java.util.Date;

/**
 * @author huwen
 * @date 2019/3/23
 */
public class UserForm {
    /**
     * 用户邮箱，不可重复
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户电话号码
     */
    private String phonenumber;
    /**
     * 用户生日
     */
    private Date birthday;
    /**
     * 用户信用值
     */
    private double credit;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername(){return username;}

    public void setUsername(String username){this.username = username;}

    public String getPhonenumber(){return phonenumber;}

    public void setPhonenumber(String phonenumber){this.phonenumber = phonenumber;}

    public Date getBirthday(){return birthday;}

    public void setBirthday(Date birthday){this.birthday = birthday;}

    public double getCredit(){return credit;}

    public void setCredit(double credit){this.credit = credit;}
}
