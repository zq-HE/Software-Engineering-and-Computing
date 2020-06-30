package com.example.hotel;

import com.example.hotel.blImpl.coupon.TargetRoomCouponStrategyImpl;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)

public class TargetRoomCouponStrategyImplTest {

    @Autowired
    TargetRoomCouponStrategyImpl targetRoomCouponStrategy;

    @Test
    public void isMatch(){
        int roomNum = new Random().nextInt();

        OrderVO orderVO = new OrderVO();
        orderVO.setRoomNum(roomNum);

        Coupon coupon = new Coupon();
        coupon.setCouponType(2);
        coupon.setTargetRoom(roomNum);

        Assert.assertTrue("TargetRoomCouponStrategyImpl.isMatch() ERROR!",
                targetRoomCouponStrategy.isMatch(orderVO, coupon));

        orderVO.setRoomNum(roomNum - 1);

        Assert.assertFalse("TargetRoomCouponStrategyImpl.isMatch() ERROR!",
                targetRoomCouponStrategy.isMatch(orderVO, coupon));
    }

}
