package com.melih.spring.E7;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getUserById(int userId) {
        // Simüle edilmiş iş mantığı
        return "User_" + userId;
    }

    public void saveUser(String username) {
        // Simüle edilmiş kaydetme işlemi
        System.out.println("User saved: " + username);
    }
}
