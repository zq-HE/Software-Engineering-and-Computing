package com.example.hotel.blImpl.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class AdminServiceImpl implements AdminService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String DELETE_ERROR= "删除失败";
    private final static String UPDATE_ERROR= "更新失败";
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public ResponseVO addManager(UserForm userForm) {
        //return ResponseVO.buildSuccess(true);
        User user = new User();
        //将数据从VO转换到PO
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserName(userForm.getUsername());
        user.setPhoneNumber(userForm.getPhonenumber());
        user.setBirthday(userForm.getBirthday());
        user.setUserType(UserType.HotelManager);
        try {
            adminMapper.addManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateManager(UserForm userForm,int id) {
        //return ResponseVO.buildSuccess(true);
        User user = accountService.getUserInfo(id);
        //将数据从VO转换到PO
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserName(userForm.getUsername());
        user.setPhoneNumber(userForm.getPhonenumber());
        user.setBirthday(userForm.getBirthday());
        try {
            adminMapper.updateManager(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO addUser(UserForm userForm) {
        //return ResponseVO.buildSuccess(true);
        User user = new User();
        //将数据从VO转换到PO
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setUserName(userForm.getUsername());
        user.setPhoneNumber(userForm.getPhonenumber());
        user.setBirthday(userForm.getBirthday());
        user.setCredit(userForm.getCredit());
        user.setUserType(UserType.Client);
        try {
            adminMapper.addUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateCredit(int id,String credit) {
        //return ResponseVO.buildSuccess(true);
        try {
            User user = accountService.getUserInfo(id);
            //将数据从VO转换到PO
            CreditRecord creditRecord=new CreditRecord();
            creditRecord.setUserId(id);
            creditRecord.setRestValue(user.getCredit()+Double.parseDouble(credit));
            creditRecord.setChangeValue(Double.parseDouble(credit));
            creditRecord.setChangeTime(new Date());
            creditRecord.setReason("管理员添加");
            accountMapper.insertCreditRecord(creditRecord);
            adminMapper.updateCredit(id,Double.parseDouble(credit)+user.getCredit());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<User> getAllManagers() {
//        List<User> stub = null;
//        return stub;
        return adminMapper.getAllManagers();
    }

    @Override
    public List<User> getAllUsers() {
//        List<User> stub = null;
//        return stub;
        return adminMapper.getAllUsers();
    }

    @Override
    public ResponseVO deleteUser(int userid){
        //return ResponseVO.buildSuccess(true);
        try{
            adminMapper.deleteUser(userid);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateUser(int id,UserForm userForm) {
        //return ResponseVO.buildSuccess(true);
        User user = accountService.getUserInfo(id);
        //将数据从VO转换到PO，同时判定前端是否传入数据，如果未传入数据则不做更改
        user.setEmail(userForm.getEmail()==null||userForm.getEmail().compareTo("")==0?
                user.getEmail():userForm.getEmail());
        user.setPassword(userForm.getPassword()==null||userForm.getPassword().compareTo("")==0?
                user.getPassword():userForm.getPassword());
        user.setUserName(userForm.getUsername()==null||userForm.getUsername().compareTo("")==0?
                user.getUserName():userForm.getUsername());
        user.setPhoneNumber(userForm.getPhonenumber()==null||userForm.getPhonenumber().compareTo("")==0?
                user.getPhoneNumber():userForm.getPhonenumber());
        user.setBirthday(userForm.getBirthday()==null?user.getBirthday():userForm.getBirthday());
        try {
            adminMapper.updateUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

}
