/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author tibh
 */
public class Animal {
   private int id;
   private String categorie ;
   private String nom;
   private String description;
   private String image_animal;
   private int debutSaison;
   private int finSaison;

    public Animal() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_animal() {
        return image_animal;
    }

    public void setImage_animal(String image_animal) {
        this.image_animal = image_animal;
    }

    public int getDebutSaison() {
        return debutSaison;
    }

    public void setDebutSaison(int debutSaison) {
        this.debutSaison = debutSaison;
    }

    public int getFinSaison() {
        return finSaison;
    }

    public void setFinSaison(int finSaison) {
        this.finSaison = finSaison;
    }

    public Animal(int id, String categorie, String nom, String description, String image_animal, int debutSaison, int finSaison) {
        this.id = id;
        this.categorie = categorie;
        this.nom = nom;
        this.description = description;
        this.image_animal = image_animal;
        this.debutSaison = debutSaison;
        this.finSaison = finSaison;
    }
    public Animal( String categorie, String nom, String description, String image_animal, int debutSaison, int finSaison) {
       
        this.categorie = categorie;
        this.nom = nom;
        this.description = description;
        this.image_animal = image_animal;
        this.debutSaison = debutSaison;
        this.finSaison = finSaison;
    }
    
     public Animal(String nom) {
        
        this.nom = nom;
        
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", categorie=" + categorie + ", nom=" + nom + ", description=" + description + ", image_animal=" + image_animal + ", debutSaison=" + debutSaison + ", finSaison=" + finSaison + '}';
    }
    public String toStringName() {
        return "Nom{" +  nom+"\n"+'}';
    }
   
   
}
