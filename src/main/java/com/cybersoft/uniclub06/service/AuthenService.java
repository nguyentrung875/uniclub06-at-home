package com.cybersoft.uniclub06.service;

import com.cybersoft.uniclub06.repository.UserRepository;
import com.cybersoft.uniclub06.request.AuthenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface AuthenService {
    public boolean checkLogin(AuthenRequest request);
    public String login(AuthenRequest request);
}
