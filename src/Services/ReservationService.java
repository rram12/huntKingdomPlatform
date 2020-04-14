/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reservation;
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
public class ReservationService {

    Connection cnx2;

    public ReservationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public boolean ajouterReservation(Reservation r) {
        String requete = "INSERT INTO Reservation (nbJours,prixTot,dateArrivee,userId,HebergementId) values(?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, r.getNbJours());
            pst.setDouble(2, r.getPrixTot());
            pst.setDate(3, (Date) r.getDateArrivee());
            pst.setInt(4, r.getUserId());
            pst.setInt(5, r.getHebergementId());
            pst.executeUpdate();
            System.out.println("Rent succesfully added ! ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public List<Reservation> afficherReservations(int id) {
        List<Reservation> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM Reservation where Hebergementid=? and dateArrivee > SYSDATE() order by dateArrivee";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();

                r.setId(rs.getInt(1));
                r.setNbJours(rs.getInt(2));
                r.setDateArrivee(rs.getDate(3));
                r.setPrixTot(rs.getFloat(4));
                r.setUserId(rs.getInt(5));
                r.setHebergementId(rs.getInt(6));
                myList.add(r);

            }

        } catch (SQLException ex) {
            System.err.println("error d affichage");

        }
        System.out.println(myList);
        return myList;
    }
}
