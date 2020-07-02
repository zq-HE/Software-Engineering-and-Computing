package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final static String DELETE_ERROR = "删除失败";

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomService roomService;

    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if (manager == null || !manager.getUserType().equals(UserType.HotelManager)) {
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        //将数据从VO转换到PO
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomService.updateRoomInfo(hotelId, roomType, rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        //return 0;
        return roomService.getRoomCurNum(hotelId, roomType);
    }

    @Override
    public List<HotelVO> retrieveHotels(String _userId) {
        //return null;
        try {
            int userId = Integer.parseInt(_userId);
            List<HotelVO> hotels = hotelMapper.selectAllHotel();
            List<Order> orders = orderService.getUserOrders(userId);
            //从数据库中读取出列表时对酒店预订状态进行初始化
            for(HotelVO hotelVO: hotels){
                hotelVO.setHasBooked("未曾预订");
                hotelVO.setHasCanceled("未曾撤销");
                hotelVO.setHasExeced("未曾执行");
            }
            //若该顾客有相应订单，则对相应酒店状态进行赋值
            for (int i = 0; i < hotels.size(); i++) {
                HotelVO hotelVO = hotels.get(i);
                for (int j = 0; j < orders.size(); j++) {
                    Order order = orders.get(j);
                    if (order.getHotelId().equals(hotelVO.getId()) &&
                            (order.getOrderState().compareTo("已预订") == 0
                                    || order.getOrderState().compareTo("已执行") == 0)) {
                        hotelVO.setHasBooked("曾预订");
                    }
                    if (order.getHotelId().equals(hotelVO.getId()) &&
                            (order.getOrderState().compareTo("已撤销") == 0)) {
                        hotelVO.setHasCanceled("曾撤销");
                    }
                    if (order.getHotelId().equals(hotelVO.getId())
                            && order.getOrderState().compareTo("已执行") == 0) {
                        hotelVO.setHasExeced("曾执行");
                    }
                    hotels.set(i,hotelVO);
                }
            }
            return hotels;
        } catch (Exception e) {
            return hotelMapper.selectAllHotel();
        }

    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        //return null;
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public ResponseVO deleteHotel(int hotelId) {
        //return ResponseVO.buildSuccess(true);
        try {
            hotelMapper.deleteHotel(hotelId);
            roomService.deleteRoomByHotelId(hotelId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.buildFailure(DELETE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public void updateHotelInfo(Hotel hotel) {
        try {
            hotelMapper.updateHotel(hotel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //    /**
////     * @param hotelId
////     * @return
////     */
////    @Override
////    public List<Order> getHotelOrders(Integer hotelId) {
////        List<Order> orders = orderService.getAllOrders();
////        return orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
////    }
}
