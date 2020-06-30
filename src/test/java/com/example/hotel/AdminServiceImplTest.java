package com.example.hotel;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.user.AccountServiceImpl;
import com.example.hotel.vo.UserForm;
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
//@Component
public class AdminServiceImplTest {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    AdminService adminService;

    @Test
    public void test(){
        System.out.printf("1111111111111");
    }

    @Autowired
    @Test
    public void addManager() {
        for(int i = 0; i < 50; ++i) {
            UserForm userForm = getRandomUserForm();
            Assert.assertTrue("ERROR!",
                    adminService.addManager(userForm).getSuccess());
        }
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

    public static UserForm getRandomUserForm(){
        UserForm userForm = new UserForm();
        Random random = new Random();
        String email = getRandomString(random.nextInt(5) + 1) +
                "@" + getRandomString(random.nextInt(5) + 1) +
                "." + getRandomString(random.nextInt(5) + 1);


        String password = getRandomString(random.nextInt(10) + 1);
        String username = getRandomString(random.nextInt(20) + 1);

        String phonenumber = getRandomPhoneNumber();

        Date birthday = new Date();
        double credit = random.nextDouble() * 100;
        userForm.setEmail(email);
        userForm.setPassword(password);
        userForm.setUsername(username);
        userForm.setPhonenumber(phonenumber);
        userForm.setBirthday(birthday);
        userForm.setCredit(credit);
        return userForm;
    }

    @Test
    public void updateManager() {
        for(int id = 10; id < 60; ++id){
            UserForm userForm = getRandomUserForm();
            Assert.assertTrue("AdminServiceImpl.updateManager() ERROR!",
                    adminService.updateManager(userForm, id).getSuccess());
        }
    }

    @Test
    public void addUser() {
        UserForm userForm = getRandomUserForm();
        Assert.assertTrue("AdminServiceImpl.addUser() ERROR!",
                adminService.addUser(userForm).getSuccess());
    }

    @Test
    public void updateCredit() {
        Random random = new Random();
        for(int id = 10; id < 60; ++id){
            Assert.assertTrue("AdminServiceImpl.updateCredit() ERROR!",
                    adminService.updateCredit(id, Integer.toString(random.nextInt(1000))).getSuccess());
        }
    }

    @Test
    public void deleteUser() {
        Assert.assertTrue("AdminServiceImpl.deleteUser() ERROR!",
                adminService.deleteUser(100).getSuccess());
    }

    @Test
    public void updateUser() {
        for(int id = 10; id < 60; ++id) {
            Assert.assertTrue("AdminServiceImpl.updateUser() ERROR!",
                    adminService.updateUser(id, getRandomUserForm()).getSuccess());
        }
    }
}