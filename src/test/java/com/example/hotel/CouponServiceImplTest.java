package com.example.hotel;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.blImpl.coupon.*;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)

public class CouponServiceImplTest {

    @Autowired
    TargetMoneyCouponStrategyImpl targetMoneyCouponStrategy;

    @Autowired
    TargetRoomCouponStrategyImpl targetRoomCouponStrategy;

    @Autowired
    TimeCouponStrategyImpl timeCouponStrategy;

    @Autowired
    BirthdayCouponStrategyImpl birthdayCouponStrategy;

    @Autowired
    CouponServiceImpl couponService;

    @Autowired
    CouponMapper couponMapper;

    @Test
    public void getMatchOrderCoupon(){
        OrderVO orderVO = new OrderVO();

        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        checkInDate.setDate(25);

        orderVO.setCheckInDate(checkInDate);
        orderVO.setCheckOutDate(checkOutDate);

        Random random = new Random();

        orderVO.setRoomNum(Math.abs(random.nextInt()));

        orderVO.setHotelId(random.nextInt(5) + 1);

        orderVO.setPrice(Math.abs(random.nextDouble() * random.nextInt()));

        List<CouponMatchStrategy> strategyList = new ArrayList<>();

        strategyList.add(targetMoneyCouponStrategy);
        strategyList.add(targetRoomCouponStrategy);
        strategyList.add(timeCouponStrategy);
        strategyList.add(birthdayCouponStrategy);

        orderVO.setUserId(400);

        List<Coupon> hotelCoupons = couponMapper.selectByHotelId(orderVO.getHotelId());

        List<Coupon> availAbleCoupons = new ArrayList<>();

        for (int i = 0; i < hotelCoupons.size(); i++) {
            for (CouponMatchStrategy strategy : strategyList) {
                if (strategy.isMatch(orderVO, hotelCoupons.get(i))) {
                    availAbleCoupons.add(hotelCoupons.get(i));
                }
            }
        }

        int[] expectArr = new int[availAbleCoupons.size()];

        for(int i = 0; i < availAbleCoupons.size(); ++i){
            expectArr[i] = availAbleCoupons.get(i).getId();
        }

        availAbleCoupons = couponService.getMatchOrderCoupon(orderVO);

        int[] acctualArr = new int[availAbleCoupons.size()];

        for(int i = 0; i < availAbleCoupons.size(); ++i){
            acctualArr[i] = availAbleCoupons.get(i).getId();
        }

        Assert.assertArrayEquals(expectArr, acctualArr);
    }

    @Test
    public void addHotelTargetMoneyCoupon(){
        Random random = new Random();
        HotelTargetMoneyCouponVO couponVO = new HotelTargetMoneyCouponVO();
        couponVO.setName("TestCoupon");
        couponVO.setDescription("This is a TestCoupon");
        couponVO.setType(3);
        int randomMoney = Math.abs(random.nextInt());
        couponVO.setTargetMoney(randomMoney);
        couponVO.setHotelId(random.nextInt(4) + 1);
        couponVO.setDiscountMoney(randomMoney - 1);
        couponVO.setStatus(1);

        CouponVO cv = new CouponVO();

        cv = couponService.addHotelTargetMoneyCoupon(couponVO);

        Assert.assertEquals(1, couponMapper.selectByCouponMoney(randomMoney - 1).size());
    }

    @Test
    public void addHotelTimeCoupon(){
        Random random = new Random();
        HotelTimeCouponVO couponVO = new HotelTimeCouponVO();
        couponVO.setName("TestCoupon");
        couponVO.setDescription("This is a TestCoupon");
        couponVO.setType(4);
        int randomMoney = Math.abs(random.nextInt());
        Date startTime = new Date();
        Date endTime = new Date();
        startTime.setDate(random.nextInt(30) + 1);
        endTime.setDate(startTime.getDate() + random.nextInt(30 - startTime.getDate() + 1));
        couponVO.setStartTime(startTime);
        couponVO.setStartTime(endTime);
        couponVO.setHotelId(random.nextInt(4) + 1);
        couponVO.setDiscountMoney(randomMoney - 1);
        couponVO.setStatus(1);

        CouponVO cv = new CouponVO();

        cv = couponService.addHotelTimeCoupon(couponVO);

        Assert.assertEquals(1, couponMapper.selectByCouponMoney(randomMoney - 1).size());
    }

    @Test
    public void addHotelTargetRoomCoupon(){
        Random random = new Random();
        HotelTargetRoomCouponVO couponVO = new HotelTargetRoomCouponVO();
        couponVO.setName("TestCoupon");
        couponVO.setDescription("This is a TestCoupon");
        couponVO.setType(2);
        int randomMoney = Math.abs(random.nextInt());
        couponVO.setTargetRoom(Math.abs(random.nextInt()));
        couponVO.setHotelId(random.nextInt(4) + 1);
        couponVO.setDiscountMoney(randomMoney - 1);
        couponVO.setStatus(1);

        CouponVO cv = new CouponVO();

        cv = couponService.addHotelTargetRoomCoupon(couponVO);

        Assert.assertEquals(1, couponMapper.selectByCouponMoney(randomMoney - 1).size());
    }

    @Test
    public void addHotelBirthdayCoupon(){
        Random random = new Random();
        HotelBirthdayCouponVO couponVO = new HotelBirthdayCouponVO();
        couponVO.setName("TestCoupon");
        couponVO.setDescription("This is a TestCoupon");
        couponVO.setType(2);
        int randomMoney = Math.abs(random.nextInt());
        couponVO.setHotelId(random.nextInt(4) + 1);
        couponVO.setDiscountMoney(randomMoney - 1);
        couponVO.setStatus(1);

        CouponVO cv = new CouponVO();

        cv = couponService.addHotelBirthdayCoupon(couponVO);

        Assert.assertEquals(1, couponMapper.selectByCouponMoney(randomMoney - 1).size());
    }

    @Test
    public void deleteCoupon(){
        Assert.assertTrue(couponService.deleteCoupon(5).getSuccess());
    }

}
