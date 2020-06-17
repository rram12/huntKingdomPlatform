/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Publication;
import Entities.User;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author L
 */
public class PublicationService {
    
    Connection cnx2;
    
    public PublicationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public boolean addPublication(Publication p){
            Profanity pr = new Profanity();
            Profanity.loadConfigs();

            if(Profanity.badWordsFound(p.getTitle()).size()>0){
                System.err.println("Bad Word detected in this publication's title");
                return false;
            }
            else{
                if(Profanity.badWordsFound(p.getDescription()).size()>0){
                System.err.println("Bad Word detected in this publication's description");
                return false;
            }
              
             else{
                 
             
        try {
            String requete="INSERT INTO publication (title, description, userId, image,closed, solved, views, pinned, publishedAt) "
                    + "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, p.getTitle());
            pst.setString(2, p.getDescription());
            pst.setInt(3, p.getUserId());
            pst.setString(4, p.getImage());
            pst.setInt(5, p.getClosed());
            pst.setInt(6, p.getSolved());
            pst.setInt(7, p.getViews());
            pst.setInt(8, p.getPinned());
            pst.setDate(9, p.getPublishedAt());
            
            
            pst.executeUpdate();
            System.out.println("publication added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
                }
    }
    
    public ArrayList<Publication> showAll(){
        ArrayList<Publication> pubsList = new ArrayList();
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM Publication";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Publication p = new Publication();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString(2));
                p.setDescription(rs.getString(3));
                p.setUserId(rs.getInt(4));
                p.setImage(rs.getString(5));
                p.setClosed(rs.getInt(6));
                p.setSolved(rs.getInt(7));
                p.setViews(rs.getInt(8));
                p.setPinned(rs.getInt(9));
                p.setPublishedAt(rs.getDate(10));
                
                pubsList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pubsList;
    }
    
    public void showPubDetails(Publication p){
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM Publication join fos_user on fos_user.id ='"+p.getUserId()+"' ";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getDate(10));
                System.out.println(rs.getInt(4));
                System.out.println(rs.getString(12));
                System.out.println(rs.getString(14));
                System.out.println(rs.getString(23));
                System.out.println(rs.getString(24));
                System.out.println(rs.getString(25));
                System.out.println(rs.getLong(26));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean deletePublication(Publication p){
        try {
            String requete="delete from Publication where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Publication deleted");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean updatePublication(Publication p){
        try {
            String requete="update Publication set title='"+p.getTitle()+"',description='"+p.getDescription()+"'"
                    + ",userId='"+p.getUserId()+"',image='"+p.getImage()+"',closed='"+p.getClosed()+"'"
                    + ",solved='"+p.getSolved()+"',views='"+p.getViews()+"',pinned='"+p.getPinned()+"'"
                    + ",publishedAt='"+p.getPublishedAt()+"'where Publication.id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Publication updated");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
}
