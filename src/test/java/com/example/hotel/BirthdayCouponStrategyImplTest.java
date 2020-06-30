package com.example.hotel;

import com.example.hotel.blImpl.coupon.BirthdayCouponStrategyImpl;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)

public class BirthdayCouponStrategyImplTest {

    @Autowired
    BirthdayCouponStrategyImpl birthdayCouponStrategy;

    @Test
    public void isMatch(){
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(300);

        Date inDate = new Date();
        inDate.setYear(2020);
        inDate.setMonth(Calendar.JUNE);
        inDate.setDate(28);

        Date outDate = (Date)inDate.clone();
        outDate.setYear(outDate.getYear() + 1);

        orderVO.setCheckInDate(inDate);
        orderVO.setCheckOutDate(outDate);

        Coupon coupon = new Coupon();
        coupon.setCouponType(1);

        Assert.assertTrue("BirthdayCouponStrategyImply.isMatch() ERROR",
                birthdayCouponStrategy.isMatch(orderVO, coupon));

        inDate.setMonth(Calendar.JULY);
        outDate.setMonth(Calendar.AUGUST);
        orderVO.setCheckInDate(inDate);
        orderVO.setCheckOutDate(outDate);

        Assert.assertFalse("BirthdayCouponStrategyImply.isMatch() ERROR",
                birthdayCouponStrategy.isMatch(orderVO, coupon));
    }

}
