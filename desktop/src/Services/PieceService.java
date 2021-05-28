/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.PiecesDefectueuses;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author asus_pc
 */
public class PieceService {

    Connection cnx2;

    public PieceService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public void ajouterPiece(PiecesDefectueuses p) {
        String requete = "INSERT INTO piecesdefectueuses (etat,reserved,nom,categorie,description,image,userid) values(false,false,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, p.getNom());//index starts with 1 for the first value
            pst.setString(2, p.getCategorie());
            pst.setString(3, p.getDescription());
            pst.setString(4, p.getImage());
            pst.setInt(5, p.getUserId());
            pst.executeUpdate();
            System.out.println("piece added succesfully ! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List<PiecesDefectueuses> afficherPiece() {
        List<PiecesDefectueuses> myList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM piecesdefectueuses";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                PiecesDefectueuses p = new PiecesDefectueuses();
                p.setId(rs.getInt(1));
                p.setEtat(rs.getBoolean(2));
                p.setReserved(rs.getBoolean(3));
                p.setNom(rs.getString(4));
                p.setCategorie(rs.getString(5));
                p.setDescription(rs.getString(6));
                p.setImage(rs.getString(7));
                p.setUserId(rs.getInt(8));
                myList.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

    public List<PiecesDefectueuses> afficherPieceNonReserved() {
        List<PiecesDefectueuses> myList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM piecesdefectueuses where reserved=false";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                PiecesDefectueuses p = new PiecesDefectueuses();
                p.setId(rs.getInt(1));
                p.setEtat(rs.getBoolean(2));
                p.setReserved(rs.getBoolean(3));
                p.setNom(rs.getString(4));
                p.setCategorie(rs.getString(5));
                p.setDescription(rs.getString(6));
                p.setImage(rs.getString(7));
                p.setUserId(rs.getInt(8));
                myList.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }

    public void updateReserved(int id) {
        try {
            String requete = "UPDATE piecesdefectueuses SET reserved = true WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("product reserved succesfully ! ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }
     public void updateEtat() {
        try {
            String requete = "UPDATE piecesdefectueuses SET etat = true where reserved = true and id in (select Piecesdefectueuses_id from reparation where dateFin<?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            Date current_date = new Date();
            java.sql.Date dateFin = new java.sql.Date(current_date.getTime());
            pst.setDate(1, dateFin);
              int nb =  pst.executeUpdate();
           if (nb>0)
            System.out.println("piecesdefectueuses UPDATE etat succesfully ! ");

        } catch (SQLException ex) {
            System.out.println("error : "+ex.getMessage());

        }

    }
    public List<PiecesDefectueuses> afficherPieceReserved() {
        List<PiecesDefectueuses> myList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM piecesdefectueuses where reserved=true";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                PiecesDefectueuses p = new PiecesDefectueuses();
                p.setId(rs.getInt(1));
                p.setEtat(rs.getBoolean(2));
                p.setReserved(rs.getBoolean(3));
                p.setNom(rs.getString(4));
                p.setCategorie(rs.getString(5));
                p.setDescription(rs.getString(6));
                p.setImage(rs.getString(7));
                p.setUserId(rs.getInt(8));
                myList.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }
       public int countPieceReady() {
            int nb = 0;
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT count(*)as count FROM piecesdefectueuses where etat=true";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
              nb =  rs.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       

        return nb;
    }
        public void deletePiece(int id_piece){
         try {
            String requete = "delete FROM piecesdefectueuses where id ='"+id_piece+"'";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            int nb = pst.executeUpdate();
            if (nb > 0) {
            System.out.println("piece deleted succesfully ! ");}
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        
    }
        
       public boolean exists(PiecesDefectueuses p ) {
            int nb = 0;
        try {
            String requete = "SELECT count(*)as count FROM piecesdefectueuses where etat=? and reserved=? and nom=? and categorie=? and description=? and image=? and userId=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setBoolean(1, p.isEtat());
            pst.setBoolean(2, p.isReserved());
            pst.setString(3, p.getNom());
            pst.setString(4, p.getCategorie());
            pst.setString(5, p.getDescription());
            pst.setString(6, p.getImage());
            pst.setInt(7, p.getUserId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                nb =  rs.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return nb>0;
    }  
        
        
        
}

