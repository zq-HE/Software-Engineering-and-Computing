package com.example.hotel;

import com.example.hotel.blImpl.coupon.TimeCouponStrategyImpl;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)

public class TimeCouponStrategyImplTest {

    @Autowired
    TimeCouponStrategyImpl timeCouponStrategy;

    @Test
    public void isMatch(){
        OrderVO orderVO = new OrderVO();

        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        checkInDate.setYear(checkInDate.getYear() - 1);
        checkOutDate.setYear(checkOutDate.getYear() + 1);

        orderVO.setCheckInDate(checkInDate);
        orderVO.setCheckOutDate(checkOutDate);

        Coupon coupon = new Coupon();
        coupon.setCouponType(4);

        Date startTime = new Date();
        Date endTime = new Date();
        startTime.setYear(startTime.getYear() - 2);
        endTime.setYear(endTime.getYear() + 2);

        coupon.setStartTime(startTime);
        coupon.setEndTime(endTime);

        Assert.assertTrue("TimeCouponStrategyImpl.isMatch() ERROR",
                timeCouponStrategy.isMatch(orderVO, coupon));

        endTime.setYear(startTime.getYear() + 1);

        Assert.assertFalse("TimeCouponStrategyImpl.isMatch() ERROR",
                timeCouponStrategy.isMatch(orderVO, coupon));
    }

}
