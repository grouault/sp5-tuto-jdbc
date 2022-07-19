package com.exo.service;

import java.util.List;

public interface CashierService {

    void checkout(List<String> isbns, String username);

}
