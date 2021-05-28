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
public class Publicity {
   private int id;
   private Date dateDebut ;
   private Date dateFin;
   private String compagnie;
   private String titre;
   private float prix ;
   private String description;
   private String image;

    public Publicity(int id, Date dateDebut, Date dateFin, String compagnie, String titre, float prix, String description, String image) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.compagnie = compagnie;
        this.titre = titre;
        this.prix = prix;
        this.description = description;
        this.image = image;
    }

    public Publicity(Date dateDebut, Date dateFin, String compagnie, String titre, float prix, String description, String image) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.compagnie = compagnie;
        this.titre = titre;
        this.prix = prix;
        this.description = description;
        this.image = image;
    }
    public Publicity(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
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

    @Override
    public String toString() {
        return "Publicity{" + "id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", compagnie=" + compagnie + ", titre=" + titre + ", prix=" + prix + ", description=" + description + ", image=" + image + '}';
    }
    
    
   
    
}
