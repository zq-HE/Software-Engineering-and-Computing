package com.example.hotel.blImpl.user;

import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.User;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private final static String UPDATE_ERROR = "修改失败";
    private final static String VIP_ERROR="VIP注册失败";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserVO userVO) {
        //return ResponseVO.buildSuccess(true);
        User user = new User();
        //将数据从VO转换到PO
        user.setBirthday(userVO.getBirthday());
        user.setPhoneNumber(userVO.getPhoneNumber());
        user.setUserName(userVO.getUserName());
        user.setPassword(userVO.getPassword());
        user.setEmail(userVO.getEmail());
        user.setCredit(userVO.getCredit());
        user.setUserType(userVO.getUserType());
        System.out.println(accountMapper.getAccountByName(userVO.getEmail()));
        if(accountMapper.getAccountByName(userVO.getEmail())!=null){
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        else {
            try {
                accountMapper.createNewAccount(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(ACCOUNT_EXIST);
            }
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public User login(UserForm userForm) {
        //return null;
        User user = accountMapper.getAccountByName(userForm.getEmail());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public List<CreditRecord> getCreditRecord(int userId) {
        //return null;
        return accountMapper.getCreditRecord(userId);
    }


    @Override
    public User getUserInfo(int id) {
        //return null;
        User user = accountMapper.getAccountById(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public ResponseVO updateUserInfo(int id, String password, String username, String phonenumber, Date birthday) {
        //return ResponseVO.buildSuccess(true);
        User userInfo = getUserInfo(id);
        if (birthday==null) birthday=userInfo.getBirthday();
        try {
            accountMapper.updateAccount(id, password, username, phonenumber,birthday);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(UPDATE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO registerVip(int id, String vipType, String vipInfo) {
        //return ResponseVO.buildSuccess(true);
        try {
            accountMapper.registerVip(id, vipType,vipInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(VIP_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
