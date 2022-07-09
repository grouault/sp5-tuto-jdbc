package com.exo.entity;

import java.sql.Timestamp;
import java.util.StringJoiner;

public class Vehicule {

    private int id;

    private Timestamp dateImmatricuation;

    private String immatriculation;

    private String couleur;

    private String marque;

    private String modele;

    public int getId() {
        return id;
    }

    public Vehicule setId(int id) {
        this.id = id;
        return this;
    }

    public Timestamp getDateImmatricuation() {
        return dateImmatricuation;
    }

    public Vehicule setDateImmatricuation(Timestamp dateImmatricuation) {
        this.dateImmatricuation = dateImmatricuation;
        return this;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public Vehicule setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
        return this;
    }

    public String getCouleur() {
        return couleur;
    }

    public Vehicule setCouleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public String getMarque() {
        return marque;
    }

    public Vehicule setMarque(String marque) {
        this.marque = marque;
        return this;
    }

    public String getModele() {
        return modele;
    }

    public Vehicule setModele(String modele) {
        this.modele = modele;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vehicule.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("dateImmatricuation=" + dateImmatricuation)
                .add("immatriculation='" + immatriculation + "'")
                .add("couleur='" + couleur + "'")
                .add("marque='" + marque + "'")
                .add("modele='" + modele + "'")
                .toString();
    }

}
