package com.example.hotelmanager.service;

import com.example.hotelmanager.Domain.User;
import java.util.List;
public interface UserService {
    void registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
