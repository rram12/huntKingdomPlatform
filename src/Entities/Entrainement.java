/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author tibh
 */
public class Entrainement {

    private int id;
    private String categorie;
    private int nbHeures;
    private Date dateEnt;
    private Double prix;
    private String lieu;
    private int userId;
    private int entraineurId;
    private int animalId;
    private int produitId;
    private String accepter;
    
    public Entrainement(){}
    public Entrainement(int id, String categorie, int nbHeures, Date dateEnt, Double prix, String lieu, int userId, int entraineurId, int animalId, int produitId, String accepter) {
        this.id = id;
        this.categorie = categorie;
        this.nbHeures = nbHeures;
        this.dateEnt = dateEnt;
        this.prix = prix;
        this.lieu = lieu;
        this.userId = userId;
        this.entraineurId = entraineurId;
        this.animalId = animalId;
        this.produitId = produitId;
        this.accepter = accepter;
    }
    
    public Entrainement(String categorie, int nbHeures, Date dateEnt, Double prix, String lieu,int userId ,int animalId,int produitId  )
    {
        this.id=0;
        this.categorie = categorie;
        this.nbHeures = nbHeures;
        this.dateEnt = dateEnt;
        this.prix = prix;
        this.lieu = lieu;
        this.userId = userId;
        this.entraineurId = 0;
        this.animalId = animalId;
        this.produitId = produitId;
        this.accepter = "encours";
        
        
    
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

    public int getNbHeures() {
        return nbHeures;
    }

    public void setNbHeures(int nbHeures) {
        this.nbHeures = nbHeures;
    }

    public Date getDateEnt() {
        return dateEnt;
    }

    public void setDateEnt(Date dateEnt) {
        this.dateEnt = dateEnt;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntraineurId() {
        return entraineurId;
    }

    public void setEntraineurId(int entraineurId) {
        this.entraineurId = entraineurId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    @Override
    public String toString() {
        return "Entrainement{" + "id=" + id + ", categorie=" + categorie + ", nbHeures=" + nbHeures + ", dateEnt=" + dateEnt + ", prix=" + prix + ", lieu=" + lieu + ", userId=" + userId + ", entraineurId=" + entraineurId + ", animalId=" + animalId + ", produitId=" + produitId + ", accepter=" + accepter + '}';
    }
    

}
