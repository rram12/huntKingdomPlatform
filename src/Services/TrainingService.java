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

    
    public List<Entrainement> getTrainingsUser(int id) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from `entrainement` where userid='"+id+"' ";

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
        String query = "select max(id) as idLast from `entrainement` where userid=15";
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
    public List<Entrainement> getTrainings() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from `entrainement` where accepter='encours' ";

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
    public List<Entrainement> getTrainingsAccepted() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select * from `entrainement` where accepter='oui' ";

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
    
    public void updateTraining(int idE,int idT)  {
       try {
            String requete = "UPDATE `entrainement` SET `entraineurId`='"+idE+"',`accepter`='oui' WHERE id='"+idT+"'";
               PreparedStatement pst = cnx.prepareStatement(requete);
            
            pst.executeUpdate();
            System.out.println("Training updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
    
     public void updateLike(String note,int idT)  {
       try {
            String requete = "UPDATE `entrainement` SET `likeTrainer`='"+note+"' WHERE id='"+idT+"'";
               PreparedStatement pst = cnx.prepareStatement(requete);
            
            pst.executeUpdate();
            System.out.println("Trainer Liked  succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
 
  public int Test(int idT){
        int test = 0;
          try {
          String req="select likeTrainer  from `entrainement` where id='"+idT+"'";
          
             Statement stm = cnx.createStatement();
              
          ResultSet rs=  stm.executeQuery(req);

          while (rs.next() && (test==0)) {
            if ("heart".equals((rs.getString("likeTrainer")))  ){
                test=1;
                 System.out.println(test);
            }
            
            else if ("unheart".equals((rs.getString("likeTrainer")))  ){
                test=2;
                 System.out.println(test);
            }
            else{
            test=0;
            }
           
         
    
         
          }
    }   catch (SQLException ex) {
              System.out.println("erreur de test");
        }
        return test;
        }
  
  
        public int getNnLike(int idu) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select COUNT(entrainement.likeTrainer) as nb , entrainement.entraineurId FROM entrainement,fos_user WHERE entrainement.entraineurId=fos_user.id and entrainement.likeTrainer='heart' and fos_user.id='"+idu+"' GROUP BY entrainement.entraineurId";
        ResultSet rst = stm.executeQuery(query);
        int nb=0;
        while (rst.next()) {            
            nb=rst.getInt("nb");
        }
     return nb;
    }
        
        public int getNnDisLike(int idu) throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "select COUNT(entrainement.likeTrainer) as nb , entrainement.entraineurId FROM entrainement,fos_user WHERE entrainement.entraineurId=fos_user.id and entrainement.likeTrainer='unheart' and fos_user.id='"+idu+"' GROUP BY entrainement.entraineurId";
        ResultSet rst = stm.executeQuery(query);
        int nb=0;
        while (rst.next()) {            
            nb=rst.getInt("nb");
        }
     return nb;
    }
        

}
