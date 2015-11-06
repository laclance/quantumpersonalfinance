package com.quantumsoftwaresolutions.quantumfinance.repository.database;

import com.quantumsoftwaresolutions.quantumfinance.model.User;

import java.util.List;

public interface UserDAO {
    void createUser(String username, String password) ;
    void updateUser(User user) ;
    User findUserById(long id) ;
    User findUserByUsername(String username) ;
    void deleteUser(User user) ;
    List<User> getUserList() ;
}
