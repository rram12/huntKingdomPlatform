/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Produits;
import Entities.Promotion;
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
public class PromotionService {
    Connection cnx2;

    public PromotionService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    public void ajouterPromotion(Promotion p,int id) {
           String requete = "INSERT INTO promotion (id,taux,dateDebut,dateFin) values(?,?,?,?)ON DUPLICATE KEY UPDATE id = ?,taux=?,dateDebut=?,dateFin=? ";
        try {
            PreparedStatement pst = cnx2.prepareStatement(requete);
            //index starts with 1 for the first value
            pst.setInt(1,id);//index starts with 1 for the first value
            pst.setDouble(2, p.getTaux());
            java.sql.Date sDateDebut = new java.sql.Date(p.getDateDebut().getTime());
            java.sql.Date sDatefin = new java.sql.Date(p.getDateFin().getTime());
            pst.setDate(3, sDateDebut);
            pst.setDate(4, sDatefin);
            pst.setInt(5,id);
            pst.setDouble(6, p.getTaux());
            pst.setDate(7, sDateDebut);
            pst.setDate(8, sDatefin);
            pst.executeUpdate();
            System.out.println("reduction added succesfully ! ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
     public List<Promotion> afficherPromotions() {
        List<Promotion> myList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM promotion";
            //executeQuery seulement pour select 
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Promotion p = new Promotion();
                p.setId(rs.getInt(1));
                p.setTaux(rs.getDouble(2));
                p.setDateDebut(rs.getDate(3));
                p.setDateFin(rs.getDate(4));
                
                myList.add(p);
            }
            System.out.println(myList);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }
     public void supprimerPromotion(int id) {
        try {
            String requete = "DELETE FROM promotion WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);//index starts with 1 for the first value
            pst.executeUpdate();
            System.out.println("promotion deleted succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }
     public void supprimerPromotionFini() {
         Date current_date = new Date();
         java.sql.Date sqlDate = new java.sql.Date(current_date.getTime());
        try {
            String requete = "DELETE FROM promotion WHERE dateFin < ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDate(1, sqlDate);//index starts with 1 for the first value
           int nb =  pst.executeUpdate();
           if (nb>0)
            System.out.println("ended promotion deleted succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }
      public void modifierPromotion(Promotion p) {
        try {
            java.sql.Date sqlDate = new java.sql.Date(p.getDateFin().getTime());
            String requete = "UPDATE promotion SET taux = ?, dateFin = ? WHERE id = ?";
               PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setDouble(1, p.getTaux());
            pst.setDate(2, sqlDate); 
            pst.setInt(3, p.getId());
            int nb =  pst.executeUpdate();
           if (nb>0)
            System.out.println("promotion updated succesfully ! ");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }
     

}
