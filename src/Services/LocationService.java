/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Location;
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
 * @author ASUS1
 */
public class LocationService {

    Connection cnx2;

    public LocationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public boolean ajouterLocation(Location l) {
        String requete = "INSERT INTO Location (nbJours,prixTot,dateArrivee,userId,MoyenDeTransportId) values(?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, l.getNbJours());
            pst.setFloat(2, l.getPrixTot());
            pst.setDate(3, (Date) l.getDateArrivee());
            pst.setInt(4, l.getUserId());
            pst.setInt(5, l.getMoyenDeTransportId());
            pst.executeUpdate();
            System.out.println("Rent succesfully added ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public boolean updateLocation(Location l) {
        try {
            String requete = "UPDATE Location SET nbJours = ?, prixTot = ?, dateArrivee = ? WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, l.getNbJours());
            pst.setFloat(2, l.getPrixTot());
            pst.setDate(3, (Date) l.getDateArrivee());
            pst.setInt(4, l.getId());
            pst.executeUpdate();
            System.out.println("Rent succesfully updated ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public List<Location> afficherLocations(int id) {
        List<Location> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM Location where MoyenDeTransportId=? and DATE_ADD(dateArrivee, INTERVAL nbJours DAY)> SYSDATE() order by dateArrivee";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Location r = new Location();

                r.setId(rs.getInt(1));
                r.setNbJours(rs.getInt(2));
                r.setDateArrivee(rs.getDate(3));
                r.setPrixTot(rs.getFloat(4));
                r.setUserId(rs.getInt(5));
                r.setMoyenDeTransportId(rs.getInt(6));
                myList.add(r);

            }

        } catch (SQLException ex) {
            System.err.println("error d affichage");

        }
        System.out.println(myList);
        return myList;
    }
    public List<Location> afficherMesLocations(int id) {
        List<Location> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM Location where UserId=? order by dateArrivee";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Location r = new Location();

                r.setId(rs.getInt(1));
                r.setNbJours(rs.getInt(2));
                r.setDateArrivee(rs.getDate(3));
                r.setPrixTot(rs.getFloat(4));
                r.setUserId(rs.getInt(5));
                r.setMoyenDeTransportId(rs.getInt(6));
                myList.add(r);

            }

        } catch (SQLException ex) {
            System.err.println("error d affichage");

        }
        System.out.println(myList);
        return myList;
    }
    public boolean supprimerLocation(int idl) {
        String requete = "DELETE FROM Location where id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, idl);
            pst.executeUpdate();
            System.out.println("Rent succesfully cancelled ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }
}
