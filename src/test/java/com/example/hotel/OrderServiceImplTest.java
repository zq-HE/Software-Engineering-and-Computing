package com.example.hotel;

import com.example.hotel.blImpl.order.OrderServiceImpl;
import com.example.hotel.vo.OrderVO;
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

public class OrderServiceImplTest {

    Random random = new Random();

    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void addOrder(){
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(400);
        orderVO.setHotelId(1);
        orderVO.setPrice(Math.abs(random.nextDouble() * random.nextInt()));
        orderVO.setRoomNum(10);
        orderVO.setCheckInDate(new Date());
        orderVO.setCheckOutDate(new Date());
        orderVO.setClientName("dh918d1jkwnq");
        orderVO.setOrderState("已预订");
        orderVO.setHaveChild(false);
        orderVO.setRoomType("Family");
        orderVO.setPhoneNumber("132132134");
        orderVO.setPeopleNum(12);
        Assert.assertTrue(orderService.addOrder(orderVO).getSuccess());
        orderVO.setRoomNum(100000);
        Assert.assertFalse(orderService.addOrder(orderVO).getSuccess());
    }

    @Test
    public void annulOrder(){
        Assert.assertFalse(orderService.annulOrder(15).getSuccess());
    }

    @Test
    public void doOrder(){
        Assert.assertTrue(orderService.doOrder(17).getSuccess());
    }

    @Test
    public void deleteOrder(){
        Assert.assertTrue(orderService.doOrder(17).getSuccess());
    }

    @Test
    public void scoreOrder(){
        Assert.assertFalse(orderService.scoreOrder(4, 18).getSuccess());
    }

}
