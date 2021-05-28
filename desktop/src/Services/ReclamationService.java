/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author Ramzi
 */
public class ReclamationService {
    
    Connection cnx2;

    public ReclamationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public boolean addRec(Reclamation r) {
        try {
            String requete = "INSERT INTO reclamation (descriptionRec, dateRec, title, userId, handled) "
                    + "VALUES (? , ? , ? , ? , ? )";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, r.getDescriptionRec());
            pst.setDate(2, r.getDateRec());
            pst.setString(3, r.getTitle());
            pst.setInt(4, r.getUserId());
            if(r.isHandled()){
            pst.setInt(5, 1);}
            else{
                pst.setInt(5, 0);
            }

            pst.executeUpdate();
            System.out.println("reclamation added");
        } catch (SQLException ex) {
            System.out.println("addRec exception: "+ex.getMessage());
        }
        return true;
    }
    
    public boolean updateRec(Reclamation r) {
        try {
            String requete = "update reclamation set descriptionRec = ?, title = ? where id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, r.getDescriptionRec());
            pst.setString(2, r.getTitle());
            pst.setInt(3, r.getId());
            

            pst.executeUpdate();
            System.out.println("reclamation updated");
        } catch (SQLException ex) {
            System.out.println("updateRec exception: "+ex.getMessage());
        }
        return true;
    }
    public boolean handleRec(int id) {
        try {
            String requete = "update reclamation set handled=1 where id=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("handleRec exception: "+ex.getMessage());
        }
        return true;
    }
    
//    public ArrayList<Reclamation> showAll(String c, String cSearch, String sString) {
//        ArrayList<Reclamation> recList = new ArrayList<>();
//        try {
//            Statement st = cnx2.createStatement();
//            String requete = "SELECT * FROM reclamation where "+cSearch+" like '%"+sString+"%' order by "+c+"";
//            ResultSet rs = st.executeQuery(requete);
//            while (rs.next()) {
//                Reclamation r = new Reclamation();
//                r.setId(rs.getInt("id"));
//                r.setDescriptionRec(rs.getString(2));
//                r.setDateRec(rs.getDate(3));
//                r.setTitle(rs.getString(4));
//                r.setUserId(rs.getInt(5));
//                r.setHandled(rs.getInt(6)==1);
//                recList.add(r);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        System.out.println(recList);
//        return recList;
//    }
    
    public ArrayList<Reclamation> showAllByUser(int id, String c, String cSearch, String sString) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM reclamation where userId="+id+" and "+cSearch+" like '%"+sString+"%' order by "+c+"";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(recList);
        return recList;
    }
    
    public ArrayList<Reclamation> selectByID(int id) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM reclamation where id='"+id+"'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return recList;
    }
    
    public ArrayList<Reclamation> showNotHandledByUser(int id, String cSort, String cSearch, String sString) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM reclamation "
                    + "where userId="+id+" and handled = 0 and "+cSearch+" like '%"+sString+"%'"
                    + " order by "+cSort+"";
            System.out.println(requete);
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(recList);
        return recList;
    }
    
    public ArrayList<Reclamation> showNotHandled(String cSort, String cSearch, String sString, int from, int to) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete ="SELECT * FROM reclamation "
                    + "where handled = 0 and "+cSearch+" like '%"+sString+"%'"
                    + " order by "+cSort+" limit "+from+" , "+to+"";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("error showAllNotHandled+");
            System.out.println(ex.getMessage());
        }
        return recList;
    }
    public ArrayList<Reclamation> showAllNotHandled(String cSort, String cSearch, String sString) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete ="SELECT * FROM reclamation "
                    + "where handled = 0 and "+cSearch+" like '%"+sString+"%'"
                    + " order by "+cSort+"";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("error showAllNotHandled");
            System.out.println(ex.getMessage());
        }
        return recList;
    }
    
    public ArrayList<Reclamation> showAll(String cSort, String cSearch, String sString, int from, int to) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete ="SELECT * FROM reclamation "
                    + "where "+cSearch+" like '%"+sString+"%'"
                    + " order by "+cSort+" limit "+from+" , "+to+"";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("error showAll+");
            System.out.println(ex.getMessage());
        }
        return recList;
    }
    
    public ArrayList<Reclamation> showAll(String cSort, String cSearch, String sString) {
        ArrayList<Reclamation> recList = new ArrayList<>();
        try {
            Statement st = cnx2.createStatement();
            String requete ="SELECT * FROM reclamation "
                    + "where "+cSearch+" like '%"+sString+"%'"
                    + " order by "+cSort+"";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setId(rs.getInt("id"));
                r.setDescriptionRec(rs.getString(2));
                r.setDateRec(rs.getDate(3));
                r.setTitle(rs.getString(4));
                r.setUserId(rs.getInt(5));
                r.setHandled(rs.getInt(6)==1);
                recList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println("error showAll");
            System.out.println(ex.getMessage());
        }
        return recList;
    }
    
    public boolean deleteRec(int id) {
        String requete;
        PreparedStatement pst;
        try {
            requete = "delete from reclamation where reclamation.id = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("reclamation deleted");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
}
