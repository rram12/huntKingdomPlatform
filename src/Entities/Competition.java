/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author User
 */
public class Competition {
   private int id;
   private String categorie ;
   private String nom;
   private String lieu;
   private int nbParticipants;
   private Date dateDebut;
   private Date dateFin;

    public Competition(String categorie, String nom, String lieu, int nbParticipants, Date dateDebut, Date dateFin) {
        this.categorie = categorie;
        this.nom = nom;
        this.lieu = lieu;
        this.nbParticipants = nbParticipants;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Competition(int id, String categorie, String nom, String lieu, int nbParticipants, Date dateDebut, Date dateFin) {
        this.id = id;
        this.categorie = categorie;
        this.nom = nom;
        this.lieu = lieu;
        this.nbParticipants = nbParticipants;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    

    public Competition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getNbParticipants() {
        return nbParticipants;
    }

    public void setNbParticipants(int nbParticipants) {
        this.nbParticipants = nbParticipants;
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

    @Override
    public String toString() {
        return "Competition{" + "id=" + id + ", categorie=" + categorie + ", nom=" + nom + ", lieu=" + lieu + ", nbParticipants=" + nbParticipants + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }

   

  
    
    
}
