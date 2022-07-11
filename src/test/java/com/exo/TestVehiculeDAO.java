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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVehiculeDAO {

    private static final Logger LOG = LogManager.getLogger();

    private static VehiculeDAO vehiculeDAO;

    private static ClassPathXmlApplicationContext appContext;

    @BeforeClass
    public static void setup(){
        LOG.info("startup - begin");
        appContext = new ClassPathXmlApplicationContext("spring/*-context.xml");
        vehiculeDAO = appContext.getBean("vehiculeDAO", VehiculeDAO.class);
        LOG.info("startup - creating DAO");
        vehiculeDAO.truncate();
        LOG.info("startup - TRUNCATE Vehicule");
    }

    @AfterClass
    public static void tearDown(){
        if (appContext != null)
            appContext.close();
        LOG.info("end");
    }

    @Test
    public void a1_insertVehicule(){
        LOG.info("Test insert Vehicule");
        Vehicule vehicule = new Vehicule();
        vehicule.setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        vehicule.setImmatriculation("998-TE-98");
        vehicule.setModele("Clio").setMarque("Renault").setCouleur("bleu");
        vehiculeDAO.insert(vehicule);
        LOG.info("Test insert ok");
    }

    @Test
    public void a2_updateVehicule() {
        LOG.info("Test update Vehicule");
        Vehicule vehicule = vehiculeDAO.findById(1);
        Assert.assertNotNull(vehicule);
        vehicule.setCouleur("rouge");
        vehiculeDAO.update(vehicule);
        LOG.info("Test update ok");
    }

    @Test
    public void a3_findById() {
        LOG.info("Test find Vehicule");
        Vehicule vehicule = vehiculeDAO.findById(1);
        if (vehicule != null)
            LOG.info(vehicule.toString());
        Assert.assertNotNull(vehicule);
    }

    @Test
    public void a4_delete() {
        LOG.info("Test delete Vehicule");
        Vehicule vehicule = vehiculeDAO.findById(1);
        if (vehicule == null) {
            Assert.fail("Impossible de supprimer un véhicule non existant.");
        }
        vehiculeDAO.delete(vehicule);
        LOG.info("Test delete ok");
    }

}
