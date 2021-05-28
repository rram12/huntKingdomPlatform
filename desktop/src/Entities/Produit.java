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
public class Produit {
    private int id;
    private String lib_prod;
    private Double prix;
    private String description;
    private int qte_prod;
     private Date date_ajout;
    private String image;
    private String type;
    
    
    public Produit(){}
    
    public Produit(int id, String lib_prod, Double prix, String description, int qte_prod, Date date_ajout, String image, String type) {
        this.id = id;
        this.lib_prod = lib_prod;
        this.prix = prix;
        this.description = description;
        this.qte_prod = qte_prod;
        this.date_ajout = date_ajout;
        this.image = image;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLib_prod() {
        return lib_prod;
    }

    public void setLib_prod(String lib_prod) {
        this.lib_prod = lib_prod;
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

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", lib_prod=" + lib_prod + ", prix=" + prix + ", description=" + description + ", qte_prod=" + qte_prod + ", date_ajout=" + date_ajout + ", image=" + image + ", type=" + type + '}';
    }
   
}
