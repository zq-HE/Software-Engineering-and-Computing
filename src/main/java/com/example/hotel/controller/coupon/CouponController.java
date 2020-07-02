package com.example.hotel.controller.coupon;

import com.example.hotel.bl.coupon.CouponService;
import com.example.hotel.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/coupon")
@ResponseBody
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/hotelTargetMoney")
    public ResponseVO addHotelTargetMoneyCoupon(@RequestBody HotelTargetMoneyCouponVO hotelTargetMoneyCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetMoneyCoupon(hotelTargetMoneyCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelTime")
    public ResponseVO addHotelTimeCoupon(@RequestBody HotelTimeCouponVO hotelTimeCouponVO) {

        CouponVO couponVO = couponService.addHotelTimeCoupon(hotelTimeCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelBirthday")
    public ResponseVO addHotelBirthdayCoupon(@RequestBody HotelBirthdayCouponVO hotelBirthdayCouponVO) {

        CouponVO couponVO = couponService.addHotelBirthdayCoupon(hotelBirthdayCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }

    @PostMapping("/hotelTargetRoom")
    public ResponseVO addHotelTargetRoomCoupon(@RequestBody HotelTargetRoomCouponVO hotelTargetRoomCouponVO) {

        CouponVO couponVO = couponService.addHotelTargetRoomCoupon(hotelTargetRoomCouponVO);

        return ResponseVO.buildSuccess(couponVO);
    }


    @GetMapping("/hotelAllCoupons")
    public ResponseVO getHotelAllCoupons(@RequestParam Integer hotelId) {
        return ResponseVO.buildSuccess(couponService.getHotelAllCoupon(hotelId));
    }

    @GetMapping("/orderMatchCoupons")
    public ResponseVO getOrderMatchCoupons(@RequestParam Integer userId,
                                           @RequestParam Integer hotelId,
                                           @RequestParam Double orderPrice,
                                           @RequestParam Integer roomNum,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        OrderVO requestOrderVO = new OrderVO();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        requestOrderVO.setUserId(userId);
        requestOrderVO.setHotelId(hotelId);
        requestOrderVO.setPrice(orderPrice);
        requestOrderVO.setRoomNum(roomNum);
        try{
            //将输入的String格式的日期转换为Date格式
            requestOrderVO.setCheckInDate(sf.parse(checkIn));
            requestOrderVO.setCheckOutDate(sf.parse(checkOut));
        }catch (Exception e){
            //如果输入不合法就设置为null
            requestOrderVO.setCheckInDate(null);
            requestOrderVO.setCheckOutDate(null);
        }
        return ResponseVO.buildSuccess(couponService.getMatchOrderCoupon(requestOrderVO));
    }


    @GetMapping("/deleteCoupon")
    public ResponseVO deleteCoupon(@RequestParam Integer couponID){
        System.out.print("ID: ");
        System.out.println(couponID);
        return couponService.deleteCoupon(couponID);
    }
}
