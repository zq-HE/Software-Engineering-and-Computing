package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.blImpl.user.AccountServiceImpl;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.User;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BirthdayCouponStrategyImpl implements CouponMatchStrategy {

    @Autowired
    AccountService accountService;

    User user;

    @Override

    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //return true;
        user = accountService.getUserInfo(orderVO.getUserId());
        try{

            Date birthday = user.getBirthday();
            Date in = orderVO.getCheckInDate();
            Date out = orderVO.getCheckOutDate();

            if (coupon.getCouponType() == 1) {
                if(in.getMonth()==out.getMonth() && birthday.getMonth()==in.getMonth() && birthday.getDate()>=in.getDate() && birthday.getDate()<=out.getDate()){
                    return true;
                }
                if(in.getMonth()<out.getMonth() && birthday.getMonth()==in.getMonth() && birthday.getDate()>=in.getDate()){
                    return true;
                }
                if(in.getMonth()<out.getMonth() && birthday.getMonth()==out.getMonth() && birthday.getDate()<=out.getDate()) {
                    return true;
                }
                if(in.getMonth()<out.getMonth() && birthday.getMonth()>in.getMonth() && birthday.getMonth()<out.getMonth()){
                    return true;
                }
            }

            return false;
        }catch (Exception e){
            return false;
        }
    }
}
