package com.example.hotel.controller.admin;

import com.example.hotel.bl.admin.AdminService;
import com.example.hotel.blImpl.admin.AdminServiceImpl;
import com.example.hotel.data.admin.AdminMapper;
import com.example.hotel.vo.ResponseVO;
import com.example.hotel.vo.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addManager")
    public ResponseVO addManager(@RequestBody UserForm userForm){

        return adminService.addManager(userForm);
    }

    @PostMapping("/{id}/userForm/updateManager")
    public ResponseVO updateManager(@RequestBody UserForm userForm,@PathVariable int id){

        return adminService.updateManager(userForm,id);
    }

    @PostMapping("/addUser")
    public ResponseVO addUser(@RequestBody UserForm userForm){

        return adminService.addUser(userForm);
    }

    @GetMapping("/{id}/{credit}/addCredit")
    public ResponseVO addCredit(@PathVariable int id,@PathVariable String credit) {
        ResponseVO responseVO = adminService.updateCredit(id,credit);
        return responseVO;
    }

    @PostMapping("/getAllManagers")
    public ResponseVO getAllManagers(){
        return ResponseVO.buildSuccess(adminService.getAllManagers());
    }

    @PostMapping("/getAllUsers")
    public ResponseVO getAllUsers(){
        return ResponseVO.buildSuccess(adminService.getAllUsers());
    }

    @GetMapping("/{userid}/deleteUser")
    public ResponseVO deleteOrder(@PathVariable int userid){
        return adminService.deleteUser(userid);
    }

    @PostMapping("/{id}/userForm/updateUser")
    public ResponseVO updateInfo(@RequestBody UserForm userForm,@PathVariable int id){
        return adminService.updateUser(id,userForm);

    }
}
