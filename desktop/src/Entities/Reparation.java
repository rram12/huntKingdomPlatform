/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author asus_pc
 */
public class Reparation {
    private int id;
    private Date dateFin;
    private Date dateDebut;

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    private Double prixRep;
    private String description;
    private int userId;
    private int Piecesdefectueuses_id;

    public Reparation() {
    }

    public Reparation(int id,Date dateDebut, Date dateFin, Double prixRep, String description, int userId, int Piecesdefectueuses_id) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixRep = prixRep;
        this.description = description;
        this.userId = userId;
        this.Piecesdefectueuses_id = Piecesdefectueuses_id;
    }
       public Reparation( Date dateDebut,Date dateFin, Double prixRep, String description, int userId, int Piecesdefectueuses_id) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prixRep = prixRep;
        this.description = description;
        this.userId = userId;
        this.Piecesdefectueuses_id = Piecesdefectueuses_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Double getPrixRep() {
        return prixRep;
    }

    public void setPrixRep(Double prixRep) {
        this.prixRep = prixRep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPiecesdefectueuses_id() {
        return Piecesdefectueuses_id;
    }

    public void setPiecesdefectueuses_id(int Piecesdefectueuses_id) {
        this.Piecesdefectueuses_id = Piecesdefectueuses_id;
    }
    
}
