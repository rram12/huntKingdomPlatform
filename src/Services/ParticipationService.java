/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Participation;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ParticipationService {
    
     Connection cnx2;
    public ParticipationService(){
    cnx2 = MyConnection.getInstance().getCnx();}
    
    public boolean addParticipation(Participation p){
        try {
            String requete="INSERT INTO competition_user(competition_id,user_id) "
                    + "values(?,?)";
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(2, p.getUser_id());
            pst.setInt(1, p.getCompetition_id());
            
          
            pst.executeUpdate();
            System.out.println("Participation added succesfully");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    
    public boolean annulerParticipation(Participation p){
        try {
            String requete="DELETE FROM competition_user WHERE competition_id=? AND user_id=? ";
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setInt(2, p.getUser_id());
            pst.setInt(1, p.getCompetition_id());
            
          
            pst.executeUpdate();
            System.out.println("Participation added succesfully");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    public void decrementerParticipants(int id){
        try {
            String requete = "UPDATE competition SET nbParticipants = nbParticipants -1  WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1,id);   
            pst.executeUpdate();
            System.out.println("Number of participants updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
    public void incrementerParticipants(int id){
        try {
            String requete = "UPDATE competition SET nbParticipants = nbParticipants +1  WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1,id);   
            pst.executeUpdate();
            System.out.println("Number of participants updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }
    public int checkParticipation(int competition_id,int user_id) throws SQLException{
        int count = 0;
        try {
           
            String requete = "SELECT count(*) FROM competition_user Where competition_id=? and user_id=?  ";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1,competition_id);
            pst.setInt(2,user_id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            count = rs.getInt(1);
                    
                   
        } catch (SQLException ex) {
            System.err.println("error d affichage");
          
        }    
             return count;
    }
    
    
    
}
