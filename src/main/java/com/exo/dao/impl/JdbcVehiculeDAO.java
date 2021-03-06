package com.exo.dao.impl;

import com.exo.dao.VehiculeDAO;
import com.exo.entity.Vehicule;
import com.exo.entity.mapper.VehiculeRowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


public class JdbcVehiculeDAO extends NamedParameterJdbcDaoSupport implements VehiculeDAO {

    private static final Logger LOG = LogManager.getLogger();


    @Override
    public void truncate() {
        String sql = "TRUNCATE TABLE vehicule";
        getJdbcTemplate().update(sql);
    }

    @Override
    public void insertWithPreparedStatementCreator(Vehicule vehicule) {

        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
                        "VALUES(?, ?, ?, ?, ?);";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, vehicule.getDateImmatriculation());
                ps.setString(2, vehicule.getImmatriculation());
                ps.setString(3, vehicule.getCouleur());
                ps.setString(4, vehicule.getMarque());
                ps.setString(5, vehicule.getModele());
                return ps;
            }
        });
    }

    @Override
    public void insertWithPreparedStatementSetter(Vehicule vehicule) {

        String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
            "VALUES(?, ?, ?, ?, ?);";

        getJdbcTemplate().update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setTimestamp(1, vehicule.getDateImmatriculation());
                ps.setString(2, vehicule.getImmatriculation());
                ps.setString(3, vehicule.getCouleur());
                ps.setString(4, vehicule.getMarque());
                ps.setString(5, vehicule.getModele());
            }
        });

    }

    @Override
    public void insertWithParameter(Vehicule vehicule) {
        String sql = "INSERT INTO vehicule (id, date_immatriculation, immatriculation, couleur, marque, modele) " +
                "VALUES(:id, :dateImmatriculation, :immatriculation, :couleur, :marque, :modele);";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(vehicule);
        getNamedParameterJdbcTemplate().update(sql, parameterSource);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        String sql = "update vehicule " +
                "set date_immatriculation=:dateImmatriculation, immatriculation=:immatriculation, " +
                "couleur=:couleur, marque=:marque, modele=:modele " +
                "where id = :id";

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("dateImmatriculation", vehicule.getDateImmatriculation());
        parameters.put("immatriculation", vehicule.getImmatriculation());
        parameters.put("couleur", vehicule.getCouleur());
        parameters.put("marque", vehicule.getMarque());
        parameters.put("modele", vehicule.getModele());
        parameters.put("id", vehicule.getId());

        getNamedParameterJdbcTemplate().update(sql, parameters);

        return vehicule;
    }

    @Override
    public void delete(Vehicule vehicule) {
        String sql = "DELETE FROM vehicule where id = ?";
        getJdbcTemplate().update(sql, vehicule.getId());
    }

    @Override
    public Vehicule findById_withRowCallBackHandler(int id) {
        String sql = "Select * from Vehicule where id = ?";
        final Vehicule vehicule = new Vehicule();
        getJdbcTemplate().query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                vehicule.setId(rs.getInt("id"));
                vehicule.setDateImmatricuation(rs.getTimestamp("date_immatriculation"));
                vehicule.setImmatriculation(rs.getString("immatriculation"));
                vehicule.setCouleur(rs.getString("couleur"));
                vehicule.setMarque(rs.getString("marque"));
                vehicule.setModele(rs.getString("modele"));
            }
        }, id);
        return vehicule;
    }

    @Override
    public Vehicule findById_withRowMapper(int id) {
        String sql = "Select * from Vehicule where id = ?";
        Vehicule vehicule = (Vehicule) getJdbcTemplate().queryForObject(sql, new VehiculeRowMapper(), id);
        return vehicule;
    }

    @Override
    public List<Vehicule> findAll_withQueryList() {
        String sql = "Select * from Vehicule";
        List<Vehicule> vehicules = new ArrayList<Vehicule>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        if (rows != null && !rows.isEmpty()) {
            for (Map<String, Object> row : rows) {
                Vehicule vehicule = new Vehicule();
                vehicule.setId((Integer) row.get("id"));
                vehicule.setDateImmatricuation(new Timestamp(((Date) row.get("date_immatriculation")).getTime()));
                vehicule.setImmatriculation((String)row.get("immatriculation"));
                vehicule.setCouleur((String)row.get("couleur"));
                vehicule.setMarque((String)row.get("marque"));
                vehicule.setModele((String)row.get("modele"));
                vehicules.add(vehicule);
            }
        }
        return vehicules;
    }

    @Override
    public List<Vehicule> findAll_withQueryRowMapper() {
        String sql = "Select * from Vehicule";
        List<Vehicule> vehicules = getJdbcTemplate().query(sql, new VehiculeRowMapper());
        return vehicules;
    }

    @Override
    public List<Vehicule> findByMarque_withRowCallBackHandler(String marque) {
        String sql = "Select * from Vehicule where marque = ?";
        List<Vehicule> vehicules = new ArrayList<>();
        getJdbcTemplate().query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Vehicule vehicule = new Vehicule();
                vehicule.setId(rs.getInt("id"));
                vehicule.setDateImmatricuation(rs.getTimestamp("date_immatriculation"));
                vehicule.setImmatriculation(rs.getString("immatriculation"));
                vehicule.setCouleur(rs.getString("couleur"));
                vehicule.setMarque(rs.getString("marque"));
                vehicule.setModele(rs.getString("modele"));
                vehicules.add(vehicule);
            }
        }, marque);
        return vehicules;
    }

    @Override
    public List<Vehicule> findByMarque_withQueryRowMapper(String marque) {
        String sql = "Select * from Vehicule where marque = ?";
        List<Vehicule> vehicules =  getJdbcTemplate().query(sql, new VehiculeRowMapper(), marque);
        return vehicules;
    }

    @Override
    public void insertBatch(List<Vehicule> vehicules) {
        String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
                "VALUES(?, ?, ?, ?, ?);";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Vehicule vehicule = vehicules.get(i);
                ps.setTimestamp(1, vehicule.getDateImmatriculation());
                ps.setString(2, vehicule.getImmatriculation());
                ps.setString(3, vehicule.getCouleur());
                ps.setString(4, vehicule.getMarque());
                ps.setString(5, vehicule.getModele());
            }

            @Override
            public int getBatchSize() {
                return vehicules.size();
            }
        });
    }

    @Override
    public void insertBatch_NameParameter(List<Vehicule> vehicules) {
        String sql = "INSERT INTO vehicule (date_immatriculation, immatriculation, couleur, marque, modele) " +
                "VALUES(:dateImmatriculation, :immatriculation, :couleur, :marque, :modele);";
        List<SqlParameterSource> parameters = new ArrayList<SqlParameterSource>();
        for (Vehicule vehicule: vehicules) {
            parameters.add(new BeanPropertySqlParameterSource(vehicule));
        }
        getNamedParameterJdbcTemplate().batchUpdate(sql, parameters.toArray(new SqlParameterSource[0]));
    }

    @Override
    public String getColor(int id) {
        String sql = "Select couleur from Vehicule where id=?";
        String color = getJdbcTemplate().queryForObject(sql, String.class, id);
        return color;
    }

    @Override
    public int countAll() {
        String sql = "Select count(*) from Vehicule";
        int count = getJdbcTemplate().queryForObject(sql, Integer.class);
        return count;
    }

}
