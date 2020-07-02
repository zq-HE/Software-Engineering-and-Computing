package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.po.HotelRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<HotelRoom> retrieveHotelRoomInfo(Integer hotelId) {
        //return null;
        return roomMapper.selectRoomsByHotelId(hotelId);
    }

    @Override
    public void insertRoomInfo(HotelRoom hotelRoom) {
        System.out.println(hotelRoom.getRoomType().getRawString());
        try {
            int roomCurNum = roomMapper.getRoomCurNum(hotelRoom.getHotelId(), hotelRoom.getRoomType().getRawString());
            //如果房型已经存在，则只做添加操作而非重新插入字段
            if (roomCurNum > 0)
                roomMapper.addRoomTotalNum(hotelRoom.getHotelId(), hotelRoom.getRoomType().getRawString(), hotelRoom.getTotal());
        } catch (Exception e){roomMapper.insertRoom(hotelRoom);}
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms) {
        roomMapper.updateRoomInfo(hotelId,roomType,rooms);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType) {
        //return 0;
        return roomMapper.getRoomCurNum(hotelId,roomType);
    }

    @Override
    public void deleteRoomByHotelId(Integer hotelId) {
        roomMapper.deleteRoomByHotelId(hotelId);
    }
}
