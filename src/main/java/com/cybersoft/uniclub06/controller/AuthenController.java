package com.cybersoft.uniclub06.controller;

import com.cybersoft.uniclub06.request.AuthenRequest;
import com.cybersoft.uniclub06.response.BaseResponse;
import com.cybersoft.uniclub06.service.AuthenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/authen")
public class AuthenController {

    @Autowired
    private AuthenService authenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenRequest request){

//        SecretKey key = Jwts.SIG.HS256.key().build(); //or HS384.key() or HS512.key()
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("key: " + secretString);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(authenService.login(request));

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
