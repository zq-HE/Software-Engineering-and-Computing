package com.example.hotel.blImpl.coupon;

import com.example.hotel.bl.coupon.CouponMatchStrategy;
import com.example.hotel.po.Coupon;
import com.example.hotel.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public class TargetRoomCouponStrategyImpl implements CouponMatchStrategy {
    //return true;
    @Override
    public boolean isMatch(OrderVO orderVO, Coupon coupon) {
        try {
            if (coupon.getCouponType() == 2 && orderVO.getRoomNum() >= coupon.getTargetRoom()) {
                return true;
            }

            return false;
        }catch (Exception e){
            return false;
        }

    }
}
