package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.CreditRecord;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    private final static String ANNUL_ERROR = "撤销失败";
    private final static String ANNUL_FAIL = "已过可撤销时间";
    private final static String DO_ERROR = "执行失败";
    private final static String DO_FAIL = "未到可执行时间";
    private final static String DELETE_ERROR= "删除失败";
    private final static String SCORE_ERROR= "评分失败";

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    HotelMapper hotelMapper;

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        //return ResponseVO.buildSuccess(true);
        int reserveRoomNum = orderVO.getRoomNum();
        int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType());
        if(reserveRoomNum>curNum){
            return ResponseVO.buildFailure(ROOMNUM_LACK);
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String curdate = sf.format(date);
            orderVO.setCreateDate(curdate);
            orderVO.setOrderState("已预订");
            User user = accountService.getUserInfo(orderVO.getUserId());
            orderVO.setClientName(user.getUserName());
            orderVO.setPhoneNumber(user.getPhoneNumber());
            Order order = new Order();
            //将数据从VO转换到PO
            BeanUtils.copyProperties(orderVO,order);
            orderMapper.addOrder(order);
            hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(RESERVE_ERROR);
        }
       return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        //return null;
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        //return null;
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //return ResponseVO.buildSuccess(true);
        try{
            Order order=orderMapper.getOrderById(orderid);

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String nowDateStr = sf.format(now);
            String inDateStr = sf.format(order.getCheckInDate());
            sf.applyPattern("HH");
            int nowHour=Integer.parseInt(sf.format(now));

            //如果超过预订入住当天下午6点则无法撤销
            if (nowDateStr.compareTo(inDateStr)>0 || (nowDateStr.compareTo(inDateStr)==0 && nowHour>=18))
                return ResponseVO.buildFailure(ANNUL_FAIL);

            int roomNums=-order.getRoomNum();
            hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),roomNums);
            orderMapper.annulOrder(orderid);

            //根据撤销时间判断减少的信用值为多少
            Integer userId = order.getUserId();
            User user = accountMapper.getAccountById(userId);
            double newCredit=nowDateStr.compareTo(inDateStr)==0?user.getCredit()-order.getPrice():user.getCredit()-order.getPrice()/2;
            newCredit= Math.max(newCredit, 0.0);
            accountMapper.updateCredit(userId,newCredit);

            //更新creditRecord
            CreditRecord creditRecord=new CreditRecord();
            creditRecord.setChangeTime(new Date());
            creditRecord.setChangeValue(nowDateStr.compareTo(inDateStr)==0?-order.getPrice():-order.getPrice()/2);
            creditRecord.setRestValue(newCredit);
            creditRecord.setUserId(user.getId());
            creditRecord.setReason("无故撤销订单");
            accountMapper.insertCreditRecord(creditRecord);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(ANNUL_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        //return null;
        List<Order> orders = getAllOrders();
        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
    }

    /**
     * 执行订单
     * @param orderid
     * @return
     */
    @Override
    public ResponseVO doOrder(int orderid) {
        //return ResponseVO.buildSuccess(true)
        try{
            Order order=orderMapper.getOrderById(orderid);

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            String nowDateStr = sf.format(now);
            String outDateStr = sf.format(order.getCheckOutDate());

            //只有退房后才能执行订单
            if (nowDateStr.compareTo(outDateStr)<0)
                return ResponseVO.buildFailure(DO_FAIL);

            int roomNums=-order.getRoomNum();
            hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),roomNums);
            orderMapper.doOrder(orderid);

            //更新creditRecord
            Integer userId = order.getUserId();
            User user = accountMapper.getAccountById(userId);
            CreditRecord creditRecord=new CreditRecord();
            creditRecord.setChangeTime(new Date());
            creditRecord.setChangeValue(order.getPrice());
            creditRecord.setRestValue(user.getCredit()+order.getPrice());
            creditRecord.setUserId(user.getId());
            creditRecord.setReason("完成订单增加信用值");
            accountMapper.insertCreditRecord(creditRecord);
            accountMapper.updateCredit(userId,user.getCredit()+order.getPrice());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DO_ERROR);
        }

        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO deleteOrder(int id){
        //return ResponseVO.buildSuccess(true);
        try{
            orderMapper.deleteOrder(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO scoreOrder(int value, int orderId) {
        //return ResponseVO.buildSuccess(true);
        try{
            Order order=orderMapper.getOrderById(orderId);
            int hotelId=order.getHotelId();
            HotelVO hotel=hotelMapper.selectById(hotelId);
            double rate=hotel.getRate();
            int scoreNum=hotel.getScoreNum();
            if(scoreNum==0){
                rate=value;
            }
            else{
                rate=(rate*scoreNum+value)/(scoreNum+1);
                rate = (double)(Math.round(rate*100)/10.00);
            }
            hotelMapper.updateScore(rate,hotelId);
            orderMapper.updateScore(orderId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(SCORE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }
}
