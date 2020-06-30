package com.example.hotel;

import com.example.hotel.blImpl.coupon.TargetMoneyCouponStrategyImpl;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class TargetMoneyCouponStrategyImplTest {

    @Autowired
    TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    @Test
    public void isMatch(){
        OrderVO orderVO = new OrderVO();
        orderVO.setPrice(2000.0);

        Coupon coupon = new Coupon();
        coupon.setCouponType(3);
        coupon.setTargetMoney(1999.9);

        Assert.assertTrue("TargetMoneyCouponStrategyImpl.isMatch() ERROR",
                targetMoneyCouponStrategy.isMatch(orderVO, coupon));

        orderVO.setPrice(1999.8);

        Assert.assertFalse("TargetMoneyCouponStrategyImpl.isMatch() ERROR",
                targetMoneyCouponStrategy.isMatch(orderVO, coupon));
    }

}
