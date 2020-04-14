/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Entities.Hebergement;
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
 * @author ASUS1
 */
public class HebergementService {
    Connection cnx2;
    public HebergementService(){
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public List<Hebergement> afficher()
    {
         List<Hebergement> myList= new ArrayList();
        try {
           
            String requete = "SELECT * FROM Hebergement ";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Hebergement a = new Hebergement();
                a.setId(rs.getInt(1));
                a.setType(rs.getString(2));
                a.setPrixParJour(rs.getFloat(3));
                a.setImage(rs.getString(4));
                a.setAdresse(rs.getString(5));
                a.setNbChambre(rs.getInt(6));
                a.setNbLits(rs.getInt(7));
                a.setCapacite(rs.getInt(8));
                a.setDescription(rs.getString(9));
                myList.add(a);
                
            }
                    
                   
        } catch (SQLException ex) {
            System.err.println("error d affichage");
          
        }    
             return myList;
    }
    public boolean addHebergement(Hebergement h){
        try {
            String requete="INSERT INTO hebergement(type,prixParJour,image,adresse,nbChambre,nbLits,capacite,description) "
                    + "values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst=cnx2.prepareStatement(requete);
            pst.setString(1, h.getType());
            pst.setFloat(2, h.getPrixParJour());
            pst.setString(3, h.getImage());
            pst.setString(4, h.getAdresse());
            pst.setInt(5, h.getNbChambre());
            pst.setInt(6, h.getNbLits());
            pst.setInt(7, h.getCapacite());
            pst.setString(8, h.getDescription());
            pst.executeUpdate();
            System.out.println("Accommodation added succesfully");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean updateHebergement(Hebergement h) {
        try {
            String requete = "UPDATE Hebergement SET type = ?, prixParJour = ?, image = ?, adresse = ?, NbChambre = ?, nbLits = ?, capacite = ?, description = ? WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, h.getType());
            pst.setFloat(2, h.getPrixParJour()); 
            pst.setString(3, h.getImage());
            pst.setString(4, h.getAdresse());
            pst.setInt(5, h.getNbChambre());
            pst.setInt(6, h.getNbLits());
            pst.setInt(7, h.getCapacite());
            pst.setString(8, h.getDescription());
            pst.setInt(9, h.getId());
            pst.executeUpdate();
            System.out.println("Accommodation succesfully updated ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }
    public boolean deleteHebergement(int id) {
        try {
            String requete = "DELETE FROM Hebergement WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("Accommodation succesfully deleted ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }
}
