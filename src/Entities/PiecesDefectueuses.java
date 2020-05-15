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
public class PiecesDefectueuses {
    private int id;
    private boolean etat;
    private boolean reserved;
    private String nom;
    private String categorie;
    private String description;
    private String image;
    private int userId;

    public PiecesDefectueuses() {

    }

    public PiecesDefectueuses(  String nom, String categorie, String description, String image, int userId) {
        

        this.nom = nom;
        this.categorie = categorie;
        this.description = description;
        this.image = image;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PiecesDefectueses{" + "id=" + id + ", etat=" + etat + ", reserved=" + reserved + ", nom=" + nom + ", categorie=" + categorie + ", description=" + description + ", image=" + image + ", userId=" + userId + '}';
    }
    
    
}
