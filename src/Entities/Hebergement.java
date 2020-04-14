/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ASUS1
 */
public class Hebergement {
    
    private int id;
    
    private String type;
    
    private float prixParJour;
    
    private  String image;
    
    private String adresse;
    
    private int nbChambre;
    
    private int nbLits;
    
    private int capacite;
    
    private String description;

    public Hebergement() {
    }

    public Hebergement(int id, String type, float prixParJour, String image, String adresse, int nbChambre, int nbLits, int capacite, String description) {
        this.id = id;
        this.type = type;
        this.prixParJour = prixParJour;
        this.image = image;
        this.adresse = adresse;
        this.nbChambre = nbChambre;
        this.nbLits = nbLits;
        this.capacite = capacite;
        this.description = description;
    }

    
    
    
    public Hebergement(String type, float prixParJour, String image, String adresse, int nbChambre, int nbLits, int capacite,String description) {
        this.type = type;
        this.prixParJour = prixParJour;
        this.image = image;
        this.adresse = adresse;
        this.nbChambre = nbChambre;
        this.nbLits = nbLits;
        this.capacite = capacite;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hebergement{" + "id=" + id + ", type=" + type + ", prixParJour=" + prixParJour + ", image=" + image + ", adresse=" + adresse + ", nbChambre=" + nbChambre + ", nbLits=" + nbLits + ", capacite=" + capacite + '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrixParJour() {
        return prixParJour;
    }

    public void setPrixParJour(float prixParJour) {
        this.prixParJour = prixParJour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbChambre() {
        return nbChambre;
    }

    public void setNbChambre(int nbChambre) {
        this.nbChambre = nbChambre;
    }

    public int getNbLits() {
        return nbLits;
    }

    public void setNbLits(int nbLits) {
        this.nbLits = nbLits;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    
    
    
}
