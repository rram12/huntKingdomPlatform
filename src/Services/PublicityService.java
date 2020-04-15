/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Publicity;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class PublicityService {
    Connection cnx2;
    public PublicityService(){
    cnx2 = MyConnection.getInstance().getCnx();
    }  
    public List<Publicity> afficher()
    {
         List<Publicity> myList= new ArrayList();
        try {
           
            String requete = "SELECT * FROM Publicity ";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Publicity p = new Publicity();
                p.setId(rs.getInt(1));
                p.setDateDebut(rs.getDate(2));
                p.setDateFin(rs.getDate(3));
                p.setCompagnie(rs.getString(4));
                p.setPrix(rs.getFloat(5));
                p.setDescription(rs.getString(6));
                p.setImage(rs.getString(7));
                p.setTitre(rs.getString(8));
                 
                myList.add(p);
                
            }
                    
                   
        } catch (SQLException ex) {
            System.err.println("error d affichage");
          
        }    
             return myList;
    }
     public boolean addPublicity(Publicity o){
        try {
            String requete="INSERT INTO publicity(dateDebut,dateFin,compagnie,prix,description,image,titre) "
                    + "values(?,?,?,?,?,?,?)";
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setDate(1, (Date) o.getDateDebut());
            pst.setDate(2, (Date) o.getDateFin());
            pst.setString(3, o.getCompagnie());
            pst.setFloat(4, o.getPrix());
            pst.setString(5, o.getDescription());
            pst.setString(6, o.getImage());
            pst.setString(7, o.getTitre());
            
          
            pst.executeUpdate();
            System.out.println("Publicity added succesfully");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
      public boolean deletePublicity(int id) {
        try {
            String requete = "DELETE FROM publicity WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("publicity deleted succesfully ! ");
            return true;
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
          return false;
        }

    }
             public void updatePublicity(Publicity p) {
        try {
            String requete = "UPDATE publicity SET dateDebut = ?, dateFin = ?, compagnie = ?, prix= ?, description= ?, image = ?, titre = ?  WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDate(1, (Date) p.getDateDebut());
            pst.setDate(2, (Date) p.getDateFin());
            pst.setString(3, p.getCompagnie());
            pst.setFloat(4, p.getPrix());
            pst.setString(5, p.getDescription());
            pst.setString(6, p.getImage());
            pst.setString(7, p.getTitre());
            pst.setInt(8, p.getId());
            pst.executeUpdate();
            System.out.println("Publicity updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }
       
    
}
