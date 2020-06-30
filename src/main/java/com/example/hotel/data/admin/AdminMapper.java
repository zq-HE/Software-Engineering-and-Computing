package com.example.hotel.data.admin;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Mapper
@Repository
public interface AdminMapper {

    int addManager(User user);

    int updateManager(User user);

    int addUser(User user);

    int deleteUser(int userid);

    int updateCredit(int id,double credit);

    int updateUser(User user);

    List<User> getAllManagers();

    List<User> getAllUsers();
}
