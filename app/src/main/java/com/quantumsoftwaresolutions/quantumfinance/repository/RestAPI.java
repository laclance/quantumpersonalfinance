package com.quantumsoftwaresolutions.quantumfinance.repository;

import java.util.List;

public interface RestAPI<S, ID> {
    String BASE_URL="http://quantumfinanceapi-coesolutions.rhcloud.com/api/";

    S get(ID id);

    String post(S entity);

    String put(S entity);

    void delete(S entity);

    List<S> findAll();
}
