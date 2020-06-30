package com.example.hotel;

import com.example.hotel.blImpl.hotel.HotelServiceImpl;
import com.example.hotel.blImpl.hotel.RoomServiceImpl;
import com.example.hotel.blImpl.order.OrderServiceImpl;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.HotelRoom;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)

public class RoomServiceImplTest {

    @Autowired
    RoomServiceImpl roomService;

    @Autowired
    RoomMapper roomMapper;

    @Test
    public void retrieveHotelRoomInfo(){
        int[] expect = new int[] {20, 30, 1060, 7};
        List<HotelRoom> list = roomService.retrieveHotelRoomInfo(1);
        int[] acctual = new int[list.size()];
        for(int i = 0; i < list.size(); ++i){
            acctual[i] = list.get(i).getCurNum();
        }
        Assert.assertArrayEquals(expect, acctual);
    }

    @Test
    public void insertRoomInfo(){
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setCurNum(50);
        hotelRoom.setHotelId(2);
        hotelRoom.setPrice(2000.0);
        hotelRoom.setRoomType(RoomType.Family);
        hotelRoom.setTotal(100);
        roomService.insertRoomInfo(hotelRoom);
    }

}
