package com.example.hotel.bl.admin;

import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface AdminService {

    /**
     * 添加酒店管理人员账号
     * @param userForm
     * @return
     */
    ResponseVO addManager(UserForm userForm);

    /**
     * 更改管理人员账号信息
     * @param userForm
     * @param id
     * @return
     */
    ResponseVO updateManager(UserForm userForm,int id);

    /**
     * 更改用户账号信息
     * @param userForm
     * @return
     */
    ResponseVO updateUser(int id,UserForm userForm);

    /**
     * 添加用户账号
     * @param userForm
     * @return
     */
    ResponseVO addUser(UserForm userForm);

    /**
     * 添加信用值
     * @param id
     * @param credit
     * @return
     */
    ResponseVO updateCredit(int id,String credit);

    /**
     * 获得所有酒店管理人员信息
     * @return
     */
    List<User> getAllManagers();

    /**
     * 获得所有用户信息
     * @return
     */
    List<User> getAllUsers();

    /**
     * 删除用户账号
     * @param userid
     * @return
     */
    ResponseVO deleteUser(int userid);
}
