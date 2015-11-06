package com.quantumsoftwaresolutions.quantumfinance.service;

import com.quantumsoftwaresolutions.quantumfinance.model.User;

public interface UserService extends Services<User,Long>  {
    User findByUsername(String username);
}
