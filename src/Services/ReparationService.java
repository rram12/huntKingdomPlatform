/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.PiecesDefectueuses;
import Entities.Reparation;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author asus_pc
 */
public class ReparationService {
    Connection cnx2;

    public ReparationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
    
    public void ajouterReparation(Reparation r) {
           String requete = "INSERT INTO reparation (dateFin,prixRep,description,userId,Piecesdefectueuses_id) values(?,?,?,?,?)";
        try {
            java.sql.Date rDateFin = new java.sql.Date(r.getDateFin().getTime());
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDate(1,rDateFin );
            pst.setDouble(2, r.getPrixRep());
            pst.setString(3,r.getDescription() );
            pst.setInt(4,r.getUserId());
            pst.setInt(5,r.getPiecesdefectueuses_id() );
            pst.executeUpdate();
            System.out.println("reparation added succesfully ! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
    
}
