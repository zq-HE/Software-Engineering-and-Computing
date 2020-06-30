package com.example.hotel.bl.user;

import com.example.hotel.po.User;
import com.example.hotel.vo.*;
import com.example.hotel.po.CreditRecord;

import java.util.Date;
import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     *
     * @return
     */
    ResponseVO registerAccount(UserVO userVO);

    List<CreditRecord> getCreditRecord(int userId);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     *
     * @return
     */
    User login(UserForm userForm);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    User getUserInfo(int id);

    /**
     * 更新用户个人信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
    ResponseVO updateUserInfo(int id, String password, String username, String phonenumber, Date birthday);

    ResponseVO registerVip(int id,String vipType, String vipInfo);
}
