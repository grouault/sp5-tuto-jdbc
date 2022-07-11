package com.exo.dao.impl;

import com.exo.dao.VehiculeDAO;
import com.exo.entity.Vehicule;
import com.exo.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcVehiculeDAO implements VehiculeDAO {

    private static final Logger LOG = LogManager.getLogger();

    private DataSource dataSource;

    @Override
    public void truncate() {
        String sql = "TRUNCATE TABLE vehicule";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                if (!conn.isClosed())
                    conn.close();
            } catch (SQLException e){};
        }
    }

    @Override
    public void insert(Vehicule vehicule) {
        String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
                "VALUES(?, ?, ?, ?, ?);";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
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
                if (!conn.isClosed())
                    conn.close();
            } catch (SQLException e){};
        }
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        String sql = "update vehicule " +
                "set date_immatriculation=?, immatriculation=?, couleur=?, marque=?, modele=? " +
                "where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, vehicule.getDateImmatricuation());
            ps.setString(2, vehicule.getImmatriculation());
            ps.setString(3, vehicule.getCouleur());
            ps.setString(4, vehicule.getMarque());
            ps.setString(5, vehicule.getModele());
            ps.setInt(6, vehicule.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            try{
                if (ps != null)
                    ps.close();
                if (!conn.isClosed()) {
                    conn.close();
                };
            }catch (SQLException ex){}
        }
        return vehicule;
    }

    @Override
    public void delete(Vehicule vehicule) {
        String sql = "DELETE FROM vehicule where id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, vehicule.getId());
            ps.execute();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (!conn.isClosed())
                    conn.close();
            } catch(SQLException ex){}
        }

    }

    @Override
    public Vehicule findById(int id) {
        String sql = "Select * from Vehicule where id = ?";
        Connection conn = null;
        Vehicule vehicule = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                vehicule = new Vehicule();
                vehicule.setId(rs.getInt("id"));
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
                if (!conn.isClosed())
                    conn.close();
            } catch (SQLException ex) {}
        }
        return vehicule;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
