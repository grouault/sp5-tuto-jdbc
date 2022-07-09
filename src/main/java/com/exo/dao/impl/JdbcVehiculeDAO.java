package com.exo.dao.impl;

import com.exo.dao.VehiculeDAO;
import com.exo.entity.Vehicule;
import com.exo.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcVehiculeDAO implements VehiculeDAO {

    @Override
    public void insert(Vehicule vehicule) {
        String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
                "VALUES(?, ?, ?, ?, ?);";
        Connection conn = null;
        try {
            conn = ConnectionUtil.getInstance().etablirConnexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, vehicule.getDateImmatricuation());
            ps.setString(2, vehicule.getImmatriculation());
            ps.setString(3, vehicule.getCouleur());
            ps.setString(4, vehicule.getMarque());
            ps.setString(5, vehicule.getModele());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                ConnectionUtil.getInstance().fermerConnexion();
            } catch (SQLException e){};
        }
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        return null;
    }

    @Override
    public void delete(Vehicule vehicule) {

    }

    @Override
    public Vehicule findById(int id) {
        String sql = "Select * from Vehicule where id = ?";
        Connection conn;
        Vehicule vehicule = null;
        try {
            conn = ConnectionUtil.getInstance().etablirConnexion();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                vehicule = new Vehicule();
                vehicule.setDateImmatricuation(rs.getTimestamp("date_immatriculation"));
                vehicule.setImmatriculation(rs.getString("immatriculation"));
                vehicule.setCouleur(rs.getString("couleur"));
                vehicule.setMarque(rs.getString("marque"));
                vehicule.setModele(rs.getString("modele"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        } finally {
            try {
                ConnectionUtil.getInstance().fermerConnexion();
            } catch (SQLException ex) {}
        }
        return vehicule;
    }

}
