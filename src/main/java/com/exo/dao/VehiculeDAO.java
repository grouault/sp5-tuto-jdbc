package com.exo.dao;

import com.exo.entity.Vehicule;
import java.util.List;

public interface VehiculeDAO {

    void truncate();

    void insertWithPreparedStatementCreator(Vehicule vehicule);

    void insertWithPreparedStatementSetter(Vehicule vehicule);

    void insertWithParameter(Vehicule vehicule);

    Vehicule update(Vehicule vehicule);

    void delete(Vehicule vehicule);

    Vehicule findById_withRowCallBackHandler(int id);

    Vehicule findById_withRowMapper(int id);

    List<Vehicule> findAll_withQueryList();

    List<Vehicule> findAll_withQueryRowMapper();

    List<Vehicule> findByMarque_withRowCallBackHandler(String marque);

    List<Vehicule> findByMarque_withQueryRowMapper(String marque);

    void insertBatch(List<Vehicule> vehicules);

    void insertBatch_NameParameter(List<Vehicule> vehicules);

    String getColor(int id);

    int countAll();

}
