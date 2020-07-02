package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeCouponStrategyImpl implements CouponMatchStrategy {


    /**
     * 判断某个订单是否满足某种限时优惠策略
     * @param orderVO
     * @return
     */
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        //return true;
        try{
//            String[] orderStartTime=orderVO.getCheckInDate().split("-");
//            String[] orderEndTime=orderVO.getCheckOutDate().split("-");
//            String[] couponStartTime = coupon.getStartTime().toString().split("-");
//            String[] couponEndTime = coupon.getEndTime().toString().split("-");
//            int flag=0;
//            if(orderStartTime[0].compareTo(couponStartTime[0])>=0 && orderEndTime[0].compareTo(couponEndTime[0])<=0){
//                if(orderStartTime[0].compareTo(couponStartTime[0])==0 && orderEndTime[0].compareTo(couponEndTime[0])==0){
//                    if(orderStartTime[1].compareTo(couponStartTime[1])>=0 && orderEndTime[1].compareTo(couponEndTime[1])<=0){
//                        if(orderStartTime[1].compareTo(couponStartTime[1])==0 && orderEndTime[1].compareTo(couponEndTime[1])==0){
//                            if(orderStartTime[2].compareTo(couponStartTime[2])>=0 && orderEndTime[2].compareTo(couponEndTime[2])<=0){
//                                flag=1;
//                            }
//                        }
//                        else {
//                            flag=1;
//                        }
//                    }
//                }
//                else{
//                    flag=1;
//                }
//            }
//            if (coupon.getCouponType() == 4 && flag==1) {
//                return true;
//            }
//            return false;
            if(coupon.getCouponType()==4 && orderVO.getCheckInDate().getTime()>=coupon.getStartTime().getTime() && orderVO.getCheckOutDate().getTime()<=coupon.getEndTime().getTime()){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
        }

}
