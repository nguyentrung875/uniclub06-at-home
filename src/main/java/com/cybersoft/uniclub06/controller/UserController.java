package com.cybersoft.uniclub06.controller;

import com.cybersoft.uniclub06.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUser(){
        BaseResponse baseResponse = new BaseResponse();

        return new ResponseEntity<>("Hello get All User", HttpStatus.OK);
    }
}
