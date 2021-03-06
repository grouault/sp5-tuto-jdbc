package com.exo;

import com.exo.dao.VehiculeDAO;
import com.exo.dao.impl.JdbcVehiculeDAO;
import com.exo.entity.Vehicule;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.xml.validation.ValidatorHandler;
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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVehiculeDAO {

    private static final Logger LOG = LogManager.getLogger();

    private static VehiculeDAO vehiculeDAO;

    private static ClassPathXmlApplicationContext appContext;

    private static Vehicule vehiculeTest = null;

    @BeforeClass
    public static void setup(){
        LOG.info("startup - begin");
        appContext = new ClassPathXmlApplicationContext("spring/*-context.xml");
        vehiculeDAO = appContext.getBean("vehiculeDAO", VehiculeDAO.class);
        LOG.info("startup - creating DAO");
        vehiculeDAO.truncate();
        LOG.info("startup - TRUNCATE Vehicule");
        // creation du vehicule de test
        vehiculeTest = new Vehicule();
        vehiculeTest.setId(1);
        vehiculeTest.setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        vehiculeTest.setImmatriculation("998-TE-98");
        vehiculeTest.setModele("Clio").setMarque("Renault").setCouleur("bleu");
        LOG.info("startup - Création du véhicule de test : " + vehiculeTest);
    }

    @AfterClass
    public static void tearDown(){
        if (appContext != null)
            appContext.close();
        LOG.info("end");
    }

    @Test
    public void a01_insertVehicule(){
        LOG.info("Test insert Vehicule");
        // vehiculeDAO.insert(vehiculeTest);
        // vehiculeDAO.insert_v1(vehiculeTest);
        vehiculeDAO.insertWithParameter(vehiculeTest);
    }

    @Test
    public void a02_insertBatchVehicule() {
        Vehicule vehicule1 = new Vehicule()
                .setImmatriculation("MOP-65-EE")
                .setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .setCouleur("rouge").setModele("clio").setMarque("Renault");
        Vehicule vehicule2 = new Vehicule()
                .setImmatriculation("MOP-66-EE")
                .setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .setCouleur("rouge").setModele("clio").setMarque("Renault");
        vehiculeDAO.insertBatch_NameParameter(Arrays.asList(new Vehicule[]{vehicule1, vehicule2}));
    }

    @Test
    public void a03_insertVehicule_fail() {
        LOG.info("Test insertion véhicule en double");
          try {
            Vehicule vehicule1 = new Vehicule()
                    .setId(1)
                    .setImmatriculation("MOP-65-EE")
                    .setDateImmatricuation(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                    .setCouleur("rouge").setModele("clio").setMarque("Renault");
            vehiculeDAO.insertWithParameter(vehicule1);
            vehiculeDAO.insertWithParameter(vehicule1);
            Assert.fail("Le test aurait dû provoquer une exception de contrainte d'intégrité");
          } catch (DataAccessException ex) {
              LOG.info("exception declenchée : "+ ex.getClass());
              LOG.info("message : " + ex.getMessage());
              SQLException sqle = (SQLException) ex.getCause();
              LOG.info("Etat SQL : " + sqle.getSQLState());
              LOG.info("Code erreur SQL : " + sqle.getErrorCode());
          }
    }

    @Test
    public void b01_updateVehicule() {
        LOG.info("Test update Vehicule");
        Vehicule vehicule = vehiculeDAO.findById_withRowCallBackHandler(1);
        if (vehicule == null || vehicule.getId() == 0) {
            vehiculeDAO.insertWithParameter(vehiculeTest);
            LOG.info("insertion du véhicule");
        }
        vehicule = vehiculeDAO.findById_withRowCallBackHandler(1);
        Assert.assertNotNull(vehicule);
        vehicule.setCouleur("rouge");
        vehiculeDAO.update(vehicule);
    }

    @Test
    public void c01_findById() {
        LOG.info("Test find Vehicule by id");
        Vehicule vehicule = null;
        try {
            vehicule = vehiculeDAO.findById_withRowMapper(1);
        } catch(EmptyResultDataAccessException ex) {
            vehiculeDAO.insertWithParameter(vehiculeTest);
            LOG.info("insertion du véhicule");
        }
        vehicule = vehiculeDAO.findById_withRowMapper(1);
        LOG.info(vehicule.toString());
        Assert.assertNotNull(vehicule);
    }

    @Test
    public void c02_findByMarque() {
        LOG.info("Test find Vehicule by marque");
        // List<Vehicule> vehicules = vehiculeDAO.findByMarque_withRowCallBackHandler("Renault");
        List<Vehicule> vehicules = vehiculeDAO.findByMarque_withQueryRowMapper("Renault");
        if (vehicules != null && vehicules.size() > 0) {
            Assert.assertEquals(3, vehicules.size());
        } else {
            LOG.info("Test find Vehicule by marque : aucun véhicule trouvé");
        }
    }

    @Test
    public void c03_findAll() {
        LOG.info("Test find All Vehicule");
        // List<Vehicule> vehicules = vehiculeDAO.findAll_withQueryList();
        List<Vehicule> vehicules = vehiculeDAO.findAll_withQueryRowMapper();
        Assert.assertNotNull(vehicules);
        Assert.assertEquals(3, vehicules.size());
    }

    @Test
    public void z01_delete() {
        LOG.info("Test delete Vehicule");
        Vehicule vehicule = vehiculeDAO.findById_withRowCallBackHandler(1);
        if (vehicule == null) {
            vehiculeDAO.insertWithParameter(vehiculeTest);
            LOG.info("insertion du véhicule");
            vehicule = vehiculeDAO.findById_withRowCallBackHandler(1);
        }
        vehiculeDAO.delete(vehicule);
    }

    @Test
    public void g01_getColor() {
        LOG.info("Test get color");
        String color = vehiculeDAO.getColor(1);
        Assert.assertEquals("rouge", color);
    }

    @Test
    public void g02_countAll() {
        LOG.info("Test get count");
        int count = vehiculeDAO.countAll();
        Assert.assertTrue(count > 0);
    }

}
