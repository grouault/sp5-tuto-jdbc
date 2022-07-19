package com.exo.dao.impl;

import com.exo.dao.BookShopDAO;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Isolation;
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

    @Override
    @Transactional
    public void increaseStock(String isbn, int stock) {
        String threadName = Thread.currentThread().getName();
        LOG.info(" {} - prêt à augmenter le stock du livre", threadName);

        String sql = "UPDATE BOOK_STOCK SET STOCK = STOCK + :stock WHERE ISBN = :isbn";
        Map<String, Object> parameters = new HashMap<String , Object>();
        parameters.put("isbn", isbn);
        parameters.put("stock", stock);
        getNamedParameterJdbcTemplate().update(sql, parameters);
        LOG.info(" {} - stock du livre augmenté de {}", threadName,stock);

        sleep(threadName);

        LOG.info(" {} - augmentation du stock annulé", threadName);
        throw new RuntimeException("Augmentation par erreur");

    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int checkStock(String isbn) {
        String threadName = Thread.currentThread().getName();
        LOG.info(" {} - prêt à vérifier le stock du livre", threadName);

        int stock = 0;
        String sql = "Select stock from BOOK_STOCK where isbn=:isbn";
        Map<String, Object> parameters = new HashMap<String , Object>();
        parameters.put("isbn", isbn);
        stock = getNamedParameterJdbcTemplate().queryForObject(sql, parameters, Integer.class);
        LOG.info("{} - Le stock du livre est de : {}", threadName, stock);

        sleep(threadName);
        stock = getNamedParameterJdbcTemplate().queryForObject(sql, parameters, Integer.class);
        LOG.info("{} - Le stock du livre après relecture est de : {}", threadName, stock);
        return stock;
    }

    private void sleep(String threadName) {
        LOG.info(" {} - En sommeil", threadName);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {}
        LOG.info(" {} - Réveillé", threadName);

    }

}
