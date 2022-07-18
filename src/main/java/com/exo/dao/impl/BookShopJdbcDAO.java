package com.exo.dao.impl;

import com.exo.dao.BookShopDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class BookShopJdbcDAO extends NamedParameterJdbcDaoSupport implements BookShopDAO {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void purchase(String isbn, String username) {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

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
        });

    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
