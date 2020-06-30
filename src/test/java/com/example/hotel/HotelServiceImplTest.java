package com.example.hotel;

import com.example.hotel.blImpl.hotel.HotelServiceImpl;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.po.Hotel;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.HotelVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class HotelServiceImplTest {

    @Autowired
    HotelMapper hotelMapper;

    @Autowired
    HotelServiceImpl hotelService;

    @Test
    public void addHotel(){
        HotelVO hotelVO = new HotelVO();
        hotelVO.setName("JTH (JUnit Test Hotel)");
        hotelVO.setDescription("This a JUnit test generated Hotel.");
        hotelVO.setAddress("Road");
        hotelVO.setPhoneNum("1729124321");
        hotelVO.setManagerId(400);
        hotelVO.setBizRegion("XiDan");
        hotelVO.setHotelStar("Four");
        try {
            hotelService.addHotel(hotelVO);
        }catch(ServiceException e){
            e.printStackTrace();
        }
    }

    @Test
    public void retrieveHotels(){
        hotelService.retrieveHotels("400");
    }

    @Test
    public void retrieveHotelDetails(){
        HotelVO hotelVO = hotelService.retrieveHotelDetails(2);
        Assert.assertEquals("儒家酒店", hotelVO.getName());
        Assert.assertEquals("南京市鼓楼区珠江路268号", hotelVO.getAddress());
        Assert.assertEquals("1829373819", hotelVO.getPhoneNum());
    }

    @Test
    public void deleteHotel(){
        Assert.assertTrue(hotelService.deleteHotel(6).getSuccess());
        Assert.assertNull(hotelMapper.selectById(6));
    }

    @Test
    public void updateHotelInfo(){
        hotelService.updateHotelInfo(new Hotel());
    }

}
