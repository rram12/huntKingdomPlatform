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
public class MoyenDeTransport {
    private int id;
    
    private String type;
    
    private float prixParJour;
    
    private  String image;
    
    private String marque;
    
    private String categorie;

    public MoyenDeTransport() {
    }

    public MoyenDeTransport(String type, float prixParJour, String image, String marque, String categorie) {
        this.type = type;
        this.prixParJour = prixParJour;
        this.image = image;
        this.marque = marque;
        this.categorie = categorie;
    }

    public MoyenDeTransport(int id, String type, float prixParJour, String image, String marque, String categorie) {
        this.id = id;
        this.type = type;
        this.prixParJour = prixParJour;
        this.image = image;
        this.marque = marque;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "MoyenDeTransport{" + "id=" + id + ", type=" + type + ", prixParJour=" + prixParJour + ", image=" + image + ", marque=" + marque + ", categorie=" + categorie + '}';
    }
    
}
