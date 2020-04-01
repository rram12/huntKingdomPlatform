/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Entrainement;
import Interfaces.IServiceTraining;
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
public class TrainingService implements IServiceTraining {

    Connection cnx;

    public TrainingService() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public List<Entrainement> getTrainings() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from `entrainement` where categorie='Hunting' ";

        ResultSet rst = stm.executeQuery(query);
        List<Entrainement> entr = new ArrayList<>();
        while (rst.next()) {
            Entrainement e = new Entrainement();

            e.setId(rst.getInt(1)); 
            e.setCategorie(rst.getString(2));
            e.setNbHeures(rst.getInt(3));
            e.setDateEnt(rst.getDate(4));
            e.setPrix(rst.getDouble(5));
            e.setLieu(rst.getString(6));
            e.setUserId(rst.getInt(7));
            e.setEntraineurId(rst.getInt(8));
            e.setAnimalId(rst.getInt(9));
            e.setProduitId(rst.getInt(10));
            e.setAccepter(rst.getString(11));

            entr.add(e);
        }
        return entr;
    }
    
    @Override
    public Entrainement getById(int id) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from `entrainement` where id= '"+id+"'";
        ResultSet rst = stm.executeQuery(query);
        
        Entrainement e = new Entrainement();
        
        while (rst.next()) {
            
            e.setId(rst.getInt(1));
            e.setCategorie(rst.getString(2));
            e.setNbHeures(rst.getInt(3));
            e.setDateEnt(rst.getDate(4));
            e.setPrix(rst.getDouble(5));
            e.setLieu(rst.getString(6));
            e.setUserId(rst.getInt(7));
            e.setEntraineurId(rst.getInt(8));
            e.setAnimalId(rst.getInt(9));
            e.setProduitId(rst.getInt(10));
            e.setAccepter(rst.getString(11));
        }
     return e;
    }
    
    public int getLastTraining() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select max(id) as idLast from `entrainement` where categorie='hunting'";
        ResultSet rst = stm.executeQuery(query);
        int id=0;
        while (rst.next()) {            
            id=rst.getInt("idLast");
        }
     return id;
    }
    
    @Override
    public void addTraining(Entrainement e) throws SQLException {
         Statement stm = cnx.createStatement();
        String query = "INSERT INTO `entrainement` ( `categorie`, `nbHeures`, `dateEnt`, `prix`, `lieu`,`userId`,`entraineurId`,`animalId`,`produitId`,`accepter`)"
                + "     VALUES ('"+e.getCategorie()+"', '"+e.getNbHeures()+"', '"+e.getDateEnt()+"','"+e.getPrix()+"','"+e.getLieu()+"','"+e.getUserId()+"',NULL,'"+e.getAnimalId()+"','"+e.getProduitId()+"', 'encours')";
        stm.executeUpdate(query);      
    }
    
    public void deleteTraining(int id) throws SQLException {
       
        try {
            String requete = "DELETE FROM entrainement WHERE id = ?";
               PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("training deleted succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

}
