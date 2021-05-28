/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.PiecesDefectueuses;
import Entities.Produit;
import Entities.Reparation;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String requete = "INSERT INTO reparation (dateDebut,dateFin,prixRep,description,userId,Piecesdefectueuses_id) values(?,?,?,?,?,?)";
        try {
            java.sql.Date rDateFin = new java.sql.Date(r.getDateFin().getTime());
            java.sql.Date rDateDebut = new java.sql.Date(r.getDateDebut().getTime());
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDate(1, rDateDebut);
            pst.setDate(2, rDateFin);
            pst.setDouble(3, r.getPrixRep());
            pst.setString(4, r.getDescription());
            pst.setInt(5, r.getUserId());
            pst.setInt(6, r.getPiecesdefectueuses_id());
            pst.executeUpdate();
            System.out.println("reparation added succesfully ! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Date getDateFin(int id_piece) {
        Date d = new Date();
        try {
            Statement pst = cnx2.createStatement();
            String requete = "SELECT dateFin FROM reparation where Piecesdefectueuses_id ='" + id_piece + "'";

            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                d = rs.getDate(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        java.util.Date utilDate = new java.util.Date(d.getTime());
        return utilDate;
    }

    
    
    public Date getDateDebut(int id_piece) {
        Date d = new Date();
        try {
            Statement pst = cnx2.createStatement();
            String requete = "SELECT dateDebut FROM reparation where Piecesdefectueuses_id ='" + id_piece + "'";

            ResultSet rs = pst.executeQuery(requete);

            while (rs.next()) {

                d = rs.getDate(1);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        java.util.Date utilDate = new java.util.Date(d.getTime());
        return utilDate;
    }

    public Double getPrixReparation(int id_piece) {
        Double d = 0.0;
        try {
            Statement pst = cnx2.createStatement();
            String requete = "SELECT prixRep FROM reparation where Piecesdefectueuses_id ='" + id_piece + "'";

            ResultSet rs = pst.executeQuery(requete);

            while (rs.next()) {

                d = rs.getDouble(1);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return d;
    }

    public void deleteReparation(int id_piece) {
        try {
            String requete = "delete FROM reparation where Piecesdefectueuses_id ='" + id_piece + "'";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("reparation deleted succesfully ! ");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

}
