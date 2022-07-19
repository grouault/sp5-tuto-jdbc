package com.exo;

import com.exo.dao.BookShopDAO;
import com.exo.dao.VehiculeDAO;
import com.exo.dao.impl.JdbcVehiculeDAO;
import com.exo.entity.Vehicule;
import com.exo.service.CashierService;
import com.exo.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/**
 * Exemple.
 */
public final class Main {
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Constructeur.
	 */
	private Main() {
		super();
		Main.LOG.error("Ne pas utiliser le constructeur");
	}

	/**
	 * Charge un fichier Spring.
	 *
	 * @param args
	 *            ne sert a rien
	 */
	public static void main(String[] args) {
		Main.LOG.info("-- Debut -- ");

		ClassPathXmlApplicationContext appContext = null;
		try {

			// Chargement des fichiers Spring
			appContext = new ClassPathXmlApplicationContext("spring/*-context.xml");

			BookShopDAO bookShopDAO = appContext.getBean("bookShopDAO", BookShopDAO.class);

			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						bookShopDAO.checkStock("D7G 7T9");
					} catch (RuntimeException e){}
				}
			}, "thread 1");

			Thread thread2 = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						bookShopDAO.increaseStock("D7G 7T9",50);
					} catch (RuntimeException e){}
				}
			}, "thread 2");

			thread1.start();
			try{
				Thread.sleep(5000);
			} catch (InterruptedException ex){}
			thread2.start();

		} catch (Exception e) {
			Main.LOG.fatal("Erreur", e);
			System.exit(-1);
		} finally {
			// Quoi qu'il arrive fermeture du context Spring
			if (appContext != null) {
			 	appContext.close();
			}
		}
		Main.LOG.info("-- Fin -- ");
		// System.exit(0);
	}
}