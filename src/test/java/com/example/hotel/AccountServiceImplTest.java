package com.example.hotel;

import com.example.hotel.blImpl.order.OrderServiceImpl;
import com.example.hotel.blImpl.user.AccountServiceImpl;
import com.example.hotel.enums.UserType;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.UserForm;
import com.example.hotel.vo.UserVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)

public class AccountServiceImplTest {

    Random random = new Random();

    @Autowired
    AccountServiceImpl accountService;

    @Test
    public void registerAccount(){
        UserVO userVO = new UserVO();
        userVO.setBirthday(new Date());
        userVO.setPhoneNumber(getRandomPhoneNumber());
        userVO.setUserName(getRandomString(25));
        userVO.setCredit(Math.abs(random.nextDouble() * random.nextInt()));
        userVO.setEmail(getRandomString(10) + "@" + getRandomString(10) + "." + getRandomString(10));
        userVO.setPassword(getRandomString(10));
        userVO.setUserType(UserType.Client);
        Assert.assertTrue(accountService.registerAccount(userVO).getSuccess());
    }

    public static String getRandomPhoneNumber(){
        // Eleven-digit phone number
        String src = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 11; ++i){
            int x = random.nextInt(10);
            sb.append(src.charAt(x));
        }
        return sb.toString();
    }

    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Test
    public void login(){
        UserForm userForm = new UserForm();
        userForm.setEmail("333@qq.com");
        userForm.setPassword("12");
        Assert.assertEquals(new Integer(6), accountService.login(userForm).getId());
    }

    @Test
    public void updateUserInfo(){
        Assert.assertTrue(
                accountService.updateUserInfo(7, getRandomString(10), getRandomString(25), getRandomPhoneNumber(), new Date()).getSuccess()
        );
    }

    @Test
    public void registerVip(){
        Assert.assertTrue(
                accountService.registerVip(8, "企业会员", "SMY").getSuccess()
        );
    }

}
