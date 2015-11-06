package com.quantumsoftwaresolutions.quantumfinance.service.impl;

import com.quantumsoftwaresolutions.quantumfinance.model.User;
import com.quantumsoftwaresolutions.quantumfinance.repository.impl.RestUserAPIImpl;
import com.quantumsoftwaresolutions.quantumfinance.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    final RestUserAPIImpl rest = new RestUserAPIImpl();
    @Override
    public User findById(Long id) {
        return rest.get(id);
    }

    @Override
    public User findByUsername(String username) {
        return rest.get(username);
    }

    @Override
    public String save(User entity) {
        return rest.post(entity);
    }

    @Override
    public String update(User entity) {
        return rest.put(entity);
    }

    @Override
    public void delete(User entity) {
        rest.delete(entity);
    }

    @Override
    public List<User> findAll() {
        return rest.findAll();
    }
}
