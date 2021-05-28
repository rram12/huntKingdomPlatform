/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author asus_pc
 */
public class Products {
   private int id;
   private Double taux;
   private Double prixFinale;
   private Double prix;
   private String libProd;
   private String description;
   private String image;
   private String type;
   private String marque;
   private int qteProd;

    public Products() {
    }

    public Products(int id, Double taux, Double prixFinale, Double prix,String libProd, String description, String image, String type, String marque, int qteProd) {
        this.id = id;
        this.taux = taux;
        this.prixFinale = prixFinale;
        this.prix = prix;
        this.libProd = libProd;
        this.description = description;
        this.image = image;
        this.type = type;
        this.marque = marque;
        this.qteProd = qteProd;
    }

    public String getLibProd() {
        return libProd;
    }

    public void setLibProd(String libProd) {
        this.libProd = libProd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public Double getPrixFinale() {
        return prixFinale;
    }

    public void setPrixFinale(Double prixFinale) {
        this.prixFinale = prixFinale;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getQteProd() {
        return qteProd;
    }

    public void setQteProd(int qteProd) {
        this.qteProd = qteProd;
    }

    @Override
    public String toString() {
        return "Products{" + "id=" + id + ", taux=" + taux + ", prixFinale=" + prixFinale + ", prix=" + prix + ", description=" + description + ", libProd=" + libProd  +", image=" + image + ", type=" + type + ", marque=" + marque + ", qteProd=" + qteProd + '}';
    }
   
   
}
