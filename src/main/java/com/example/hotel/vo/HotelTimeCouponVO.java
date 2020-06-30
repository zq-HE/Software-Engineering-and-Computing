package com.example.hotel.vo;

import java.time.LocalDateTime;
import java.util.Date;

public class HotelTimeCouponVO extends CouponVO{
    private Integer hotelId;

    private Date startTime;

    private Date endTime;

    private Integer discountMoney;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Date getStartTime(){return this.startTime;}

    public void setStartTime(Date startTime){this.startTime=startTime;}

    public Date getEndTime(){return this.endTime;}

    public void setEndTime(Date endTime){this.endTime=endTime;}

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

}
