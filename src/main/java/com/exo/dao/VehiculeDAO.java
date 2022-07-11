package com.exo.dao;

import com.exo.entity.Vehicule;

public interface VehiculeDAO {

    void truncate();

    void insert(Vehicule vehicule);

    Vehicule update(Vehicule vehicule);

    void delete(Vehicule vehicule);

    Vehicule findById(int id);

}
