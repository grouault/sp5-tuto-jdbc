package com.exo.dao;

public interface BookShopDAO {

    void purchase(String isbn, String username);

    void increaseStock(String isbn, int stock);

    int checkStock(String isbn);

}
