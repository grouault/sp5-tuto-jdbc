package com.exo.dao.impl;

import com.exo.dao.BookShopDAO;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BookShopJdbcDAO extends NamedParameterJdbcDaoSupport implements BookShopDAO {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void purchase(String isbn, String username) {

        // recuperation du prix du livre
        String sql = "SELECT PRICE FROM BOOK WHERE ISBN = :isbn";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("isbn", isbn);
        int price = getNamedParameterJdbcTemplate().queryForObject(sql, parameters, Integer.class);

        // mise à jour du stock du livre.
        sql = "UPDATE BOOK_STOCK SET STOCK = STOCK - 1 WHERE ISBN = :isbn";
        getNamedParameterJdbcTemplate().update(sql, parameters);

        // ajustement du solde du compte.
        sql = "UPDATE ACCOUNT SET BALANCE = BALANCE - :price WHERE USERNAME = :username";
        parameters = new HashMap<>();
        parameters.put("price", price);
        parameters.put("username", username);
        getNamedParameterJdbcTemplate().update(sql, parameters);

    }

}
