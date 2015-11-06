package com.quantumsoftwaresolutions.quantumfinance.repository.impl;

import com.quantumsoftwaresolutions.quantumfinance.model.User;
import com.quantumsoftwaresolutions.quantumfinance.repository.RestUserAPI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RestUserAPIImpl implements RestUserAPI {

    final HttpHeaders requestHeaders = RestMethods.getHeaders();
    final RestTemplate restTemplate = RestMethods.getRestTemplate();

    @Override
    public User get(String username) {
        final String url = BASE_URL+"user/username/"+username;
        HttpEntity<User> requestEntity = new HttpEntity<User>(requestHeaders);
        ResponseEntity<User> responseEntity = null;
        try{
             responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class);
        }
        catch(org.springframework.web.client.HttpStatusCodeException e){
            System.out.println(e.getStackTrace());
            return null;
        }
        return responseEntity.getBody();
    }

    @Override
    public User get(Long id) {
        final String url = BASE_URL+"user/"+id;
        HttpEntity<User> requestEntity = new HttpEntity<User>(requestHeaders);
        ResponseEntity<User> responseEntity = null;
        try {
             responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User.class);
        }
        catch(org.springframework.web.client.HttpStatusCodeException e){
            System.out.println(e.getStackTrace());
            return null;
        }
        return responseEntity.getBody();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        final String url = BASE_URL+"users/";
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
        ResponseEntity<User[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, User[].class);
        User[] results = responseEntity.getBody();

        for (User user : results) {
            users.add(user);
        }
        return users;
    }

    @Override
    public String post(User entity) {
        final String url = BASE_URL+"user/create";
        HttpEntity<User> requestEntity = new HttpEntity<User>(entity, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public String put(User entity) {
        return null;
    }

    @Override
    public void delete(User entity) {

    }
}
