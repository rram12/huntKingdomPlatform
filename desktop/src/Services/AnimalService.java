/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Interfaces.IServiceAnimal;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tibh
 */
public class AnimalService implements IServiceAnimal {

    Connection cnx;

    public AnimalService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void addAnimal(Animal a) throws SQLException {
         Statement stm = cnx.createStatement();
        String query = "INSERT INTO `animal` ( `categorie`, `nom`, `description`, `image_animal`, `debutSaison`,`finSaison`)"
                + "     VALUES ('"+a.getCategorie()+"', '"+a.getNom()+"', '"+a.getDescription()+"','"+a.getImage_animal()+"','"+a.getDebutSaison()+"','"+a.getFinSaison()+"')";
        stm.executeUpdate(query);      
    }

   
       @Override
    public List<Animal> getAnimals(){
        List<Animal> myList = new ArrayList();
        try {
            Statement st = cnx.createStatement();
            String requete = "SELECT * FROM animal";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Animal a = new Animal();
                 a.setId(rs.getInt(1));
            a.setCategorie(rs.getString(2));
            a.setNom(rs.getString(3));
            a.setDescription(rs.getString(4));
            a.setImage_animal(rs.getString(5));
            a.setDebutSaison(rs.getInt(6));
            a.setFinSaison(rs.getInt(7));
                myList.add(a);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }
    @Override
    public List<Animal> TrierAnimals(int i) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Animal getById(int id) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select id,nom,image_animal from `animal` where id= '" + id + "'";
        ResultSet rst = stm.executeQuery(query);

        Animal a = new Animal();

        while (rst.next()) {

            a.setId(rst.getInt(1));
            a.setNom(rst.getString(2));
            a.setImage_animal(rst.getString(3));
            

        }
        return a;
    }

    public List<String> getAnimal() throws SQLException {
        Statement pst = cnx.createStatement();
        String requete = "SELECT nom FROM animal";
        ResultSet rs = pst.executeQuery(requete);
        List<String> myList = new ArrayList();
        String a = "";
        while (rs.next()) {

            a = rs.getString(1);
            myList.add(a);
        }
        return myList;
    }

    public int getAnimalTraining(String nom) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select id  from `animal` where nom='"+nom+"'";
        ResultSet rst = stm.executeQuery(query);
        int id = 0;
        while (rst.next()) {
            id = rst.getInt("id");
        }
        return id;
    }

    @Override
    public void deleteAnimal(Animal p) throws SQLException {
        
    }

    @Override
    public void deleteAnimal(int id) throws SQLException {
       
        try {
            String requete = "DELETE FROM animal WHERE id = ?";
               PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("animal deleted succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void updateAnimal(Animal a, int id)  {
       try {
            String requete = "UPDATE animal SET categorie = ?, nom = ?,  description = ?, image_animal = ?,debutSaison=?,finSaison=? WHERE id = ?";
               PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, a.getCategorie());
            pst.setString(2, a.getNom());
            pst.setString(3, a.getDescription());
            pst.setString(4, a.getImage_animal());
            pst.setInt(5, a.getDebutSaison()); 
            pst.setInt(6, a.getFinSaison()); 
            pst.setInt(7, a.getId());
            pst.executeUpdate();
            System.out.println("Animal updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void SearchAnimals(String character) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Animal> getAnimalsCategory(String categorie){
        List<Animal> myList = new ArrayList();
        try {
            Statement st = cnx.createStatement();
            String requete = "SELECT * FROM animal where categorie='"+categorie+"' ";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Animal a = new Animal();
                 a.setId(rs.getInt(1));
            a.setCategorie(rs.getString(2));
            a.setNom(rs.getString(3));
            a.setDescription(rs.getString(4));
            a.setImage_animal(rs.getString(5));
            a.setDebutSaison(rs.getInt(6));
            a.setFinSaison(rs.getInt(7));
                myList.add(a);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }
    
      public List<Animal> getAnimalsOfMonth(int month){
        List<Animal> myList = new ArrayList();
        try {
            Statement st = cnx.createStatement();
            String requete = "SELECT nom,image_animal FROM animal where debutSaison='"+month+"'";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Animal a = new Animal();
                 
            a.setNom(rs.getString(1));
            
            a.setImage_animal(rs.getString(2));
            
                myList.add(a);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

}
