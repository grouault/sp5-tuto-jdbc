package com.exo.service.impl;

import com.exo.dao.BookShopDAO;
import com.exo.service.CashierService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class BookShopCashier implements CashierService {

    private BookShopDAO bookShopDAO;

    @Override
    @Transactional
    public void checkout(List<String> isbns, String username) {
        for (String isbn: isbns) {
            bookShopDAO.purchase(isbn, username);
        }
    }

    public void setBookShopDAO(BookShopDAO bookShopDAO) {
        this.bookShopDAO = bookShopDAO;
    }

}
