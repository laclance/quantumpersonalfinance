package com.quantumsoftwaresolutions.quantumfinance.repository;

import com.quantumsoftwaresolutions.quantumfinance.model.User;

public interface RestUserAPI extends RestAPI<User,Long> {
    User get(String username);
}
