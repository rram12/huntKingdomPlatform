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
public class Promotion {
    
    private int id;
    private double taux;
    private Date dateDebut;
    private Date dateFin;

    public Promotion() {
    }

    public Promotion( int id,double taux, Date dateDebut, Date dateFin) {
        this.id=id;
        this.taux = taux;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    public Promotion( int id,double taux,Date dateFin) {
        this.id=id;
        this.taux = taux;
        this.dateFin = dateFin;
    }
    public Promotion( double taux, Date dateDebut, Date dateFin) {
        this.taux = taux;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
     

     public int getId() {
        return id;
    }

     public void setId(int id) {
        this.id = id;
    }

    public double getTaux() {
        return taux;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", taux=" + taux + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
}
