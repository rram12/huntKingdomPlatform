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
public class Produits {
    private int id;
    private int promotion_id=-1;
    private String lib_prod;
    private double prix;
    private double prixFinale;
    private String description;
    private int qte_prod;
    private Date date_ajout;
    private String image;
    private String type;
    private String marque;
    private int qtt;

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public Produits() {
    }

    public Produits(int id, int promotion_id, String lib_prod, double prix, double prixFinale, String description, int qte_prod, Date date_ajout, String image, String type, String marque) {
        this.id = id;
        this.promotion_id = promotion_id;
        this.lib_prod = lib_prod;
        this.prix = prix;
        this.prixFinale = prixFinale;
        this.description = description;
        this.qte_prod = qte_prod;
        this.date_ajout = date_ajout;
        this.image = image;
        this.type = type;
        this.marque = marque;
    }

    @Override
    public String toString() {
        return "Produits{" + "id=" + id + ", promotion_id=" + promotion_id + ", lib_prod=" + lib_prod + ", prix=" + prix + ", prixFinale=" + prixFinale + ", description=" + description + ", qte_prod=" + qte_prod + ", date_ajout=" + date_ajout + ", image=" + image + ", type=" + type + ", marque=" + marque + '}';
    }
     public Produits(int id, String lib_prod, double prix, String description, int qte_prod, String image, String type, String marque) {
        this.id = id;
        this.lib_prod = lib_prod;
        this.prix = prix;
        this.description = description;
        this.qte_prod = qte_prod;
        this.image = image;
        this.type = type;
        this.marque = marque;
    }
      public Produits( int promotion_id ,String lib_prod, double prix,double prixFinale, String description, int qte_prod, Date date_ajout, String image, String type, String marque) {
        this.promotion_id = promotion_id;
        this.lib_prod = lib_prod;
        this.prix = prix;
        this.prixFinale = prixFinale;
        this.description = description;
        this.qte_prod = qte_prod;
        this.date_ajout = date_ajout;
        this.image = image;
        this.type = type;
        this.marque = marque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getLib_prod() {
        return lib_prod;
    }

    public void setLib_prod(String lib_prod) {
        this.lib_prod = lib_prod;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getPrixFinale() {
        return prixFinale;
    }

    public void setPrixFinale(double prixFinale) {
        this.prixFinale = prixFinale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQte_prod() {
        return qte_prod;
    }

    public void setQte_prod(int qte_prod) {
        this.qte_prod = qte_prod;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
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
    

    
}
