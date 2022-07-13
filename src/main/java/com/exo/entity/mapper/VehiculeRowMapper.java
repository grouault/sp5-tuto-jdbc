package com.exo.entity.mapper;

import com.exo.entity.Vehicule;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class VehiculeRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vehicule vehicule = new Vehicule();
        vehicule.setId(rs.getInt("id"));
        vehicule.setDateImmatricuation(rs.getTimestamp("date_immatriculation"));
        vehicule.setImmatriculation(rs.getString("immatriculation"));
        vehicule.setCouleur(rs.getString("couleur"));
        vehicule.setMarque(rs.getString("marque"));
        vehicule.setModele(rs.getString("modele"));
        return vehicule;
    }

}
