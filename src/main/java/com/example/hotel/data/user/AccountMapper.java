package com.example.hotel.data.user;

import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
     int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
     User getAccountByName(@Param("email") String email);

     User getAccountById(@Param("id") int id);

    int insertCreditRecord(CreditRecord creditRecord);

    List<CreditRecord> getCreditRecord(@Param("userId") int userId);


    /**
     * 更新用户信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @param birthday
     * @return
     */
     int updateAccount(@Param("id") int id, @Param("password") String password, @Param("userName") String username, @Param("phoneNumber") String phonenumber, @Param("birthday")Date birthday);

    /**
     * 注册VIP
     * @param id
     * @param vipType
     * @param vipInfo
     * @return
     */
     int registerVip(@Param("id") int id, @Param("vipType") String vipType, @Param("vipInfo") String vipInfo);

    /**
     * 更新用户信用值
     * @param id
     * @param credit
     * @return
     */
     int updateCredit(@Param("id") int id,@Param("credit") double credit);

}
