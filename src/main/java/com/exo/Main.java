package com.exo;

import com.exo.dao.VehiculeDAO;
import com.exo.dao.impl.JdbcVehiculeDAO;
import com.exo.entity.Vehicule;
import com.exo.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

		VehiculeDAO vehiculeDAO = new JdbcVehiculeDAO();

		try {

			// Test d'insert.
			Vehicule vehicule = new Vehicule();
			vehicule.setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()))
					.setImmatriculation("405-PM-78")
					.setCouleur("rouge")
					.setMarque("Peugeot")
					.setModele("3007");
			vehiculeDAO.insert(vehicule);
			Main.LOG.info("Véhicule inséré");

		} catch (Exception e) {
			Main.LOG.fatal("Erreur", e);
			System.exit(-1);
		}
		Main.LOG.info("-- Fin -- ");
		System.exit(0);
	}
}