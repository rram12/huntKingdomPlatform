/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Competition;
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
public class CompetitionService {

    Connection cnx2;

    public CompetitionService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public List<Competition> afficher() {
        List<Competition> myList = new ArrayList();
        try {

            String requete = "SELECT * FROM Competition where dateDebut > SYSDATE() ";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Competition c = new Competition();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString(2));
                c.setDateDebut(rs.getDate(3));
                c.setDateFin(rs.getDate(4));
                c.setCategorie(rs.getString(5));
                c.setLieu(rs.getString(6));
                c.setNbParticipants(rs.getInt(7));
                myList.add(c);

            }

        } catch (SQLException ex) {
            System.err.println("error d affichage");

        }
        return myList;
    }

    public List<Competition> MyCompetitions(int idu) {
        List<Competition> myList = new ArrayList();
        try {

            String requete = "SELECT * FROM Competition where id IN ( SELECT competition_id from competition_user where user_id=? ) ";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, idu);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Competition c = new Competition();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString(2));
                c.setDateDebut(rs.getDate(3));
                c.setDateFin(rs.getDate(4));
                c.setCategorie(rs.getString(5));
                c.setLieu(rs.getString(6));
                c.setNbParticipants(rs.getInt(7));
                myList.add(c);

            }

        } catch (SQLException ex) {
            System.err.println("error d affichage");

        }
        return myList;
    }

    public boolean addCompetition(Competition o) {
        try {
            String requete = "INSERT INTO competition(nom,dateDebut,dateFin,categorie,lieu,nbParticipants) "
                    + "values(?,?,?,?,?,?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, o.getNom());
            pst.setDate(2, (Date) o.getDateDebut());
            pst.setDate(3, (Date) o.getDateFin());
            pst.setString(4, o.getCategorie());
            pst.setString(5, o.getLieu());
            pst.setInt(6, o.getNbParticipants());

            pst.executeUpdate();
            System.out.println("Competition added succesfully");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateCompetition(Competition c) {
        try {
            String requete = "UPDATE competition SET nom = ?, dateDebut = ?, dateFin = ?, categorie = ?, lieu = ?, nbParticipants = ? WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setDate(2, (Date) c.getDateDebut());
            pst.setDate(3, (Date) c.getDateFin());
            pst.setString(4, c.getCategorie());
            pst.setString(5, c.getLieu());
            pst.setInt(6, c.getNbParticipants());
            pst.setInt(7, c.getId());
            pst.executeUpdate();
            System.out.println("competition updated succesfully ! ");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;

        }

    }

    public void deleteCompetition(int id) {
        try {
            String requete = "DELETE FROM competition WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("competition deleted succesfully ! ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

}
