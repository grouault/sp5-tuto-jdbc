package com.exo;

import com.exo.dao.VehiculeDAO;
import com.exo.dao.impl.JdbcVehiculeDAO;
import com.exo.entity.Vehicule;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestVehiculeDAO {

    private static final Logger LOG = LogManager.getLogger();

    private static VehiculeDAO vehiculeDAO;

    @BeforeClass
    public static void setup(){
        LOG.info("startup - creating DAO");
        vehiculeDAO = new JdbcVehiculeDAO();
    }

    @AfterClass
    public static void tearDown(){
        LOG.info("end");
    }

    @Test
    public void insertVehicule(){
        Vehicule vehicule = new Vehicule();
        vehicule.setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        vehicule.setImmatriculation("998-TE-98");
        vehicule.setModele("Clio").setMarque("Renault").setCouleur("bleu");
        vehiculeDAO.insert(vehicule);
        LOG.info("Test insert ok");
    }

    @Test
    public void updateVehicule() {
        Vehicule vehicule = vehiculeDAO.findById(1);
        Assert.assertNotNull(vehicule);
        vehicule.setCouleur("rouge");
        vehiculeDAO.update(vehicule);
        LOG.info("Test update ok");
    }

    @Test
    public void findById() {
        Vehicule vehicule = vehiculeDAO.findById(1);
        if (vehicule != null)
            LOG.info(vehicule.toString());
        Assert.assertNotNull(vehicule);
    }

    @Test
    public void delete() {
        Vehicule vehicule = vehiculeDAO.findById(8);
        if (vehicule == null) {
            Assert.fail("Impossible de supprimer un véhicule non existant.");
        }
        vehiculeDAO.delete(vehicule);
        LOG.info("Test delete ok");
    }

}
