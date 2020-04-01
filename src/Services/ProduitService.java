/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Produit;
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
 * @author asus_pc
 */

public class ProduitService {

    Connection cnx2;

    public ProduitService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

public List<Produit> getProduit() throws SQLException {
        Statement pst = cnx2.createStatement();
        String requete = "SELECT * FROM produit ";
        ResultSet rs = pst.executeQuery(requete);
        List<Produit> myList = new ArrayList();
        
        while (rs.next()) {
            Produit p = new Produit();
            p.setId(rs.getInt(1));
            p.setLib_prod(rs.getString(2));
            p.setPrix(rs.getDouble(3));
            p.setDescription(rs.getString(4));
            p.setQte_prod(rs.getInt(5));
            p.setDate_ajout(rs.getDate(6));
            p.setImage(rs.getString(7));
            p.setType(rs.getString(8));
           
            myList.add(p);

        }

        return myList;
    }

 public int getProduitTraining(String nom) throws SQLException {
        Statement stm = cnx2.createStatement();
        String query = "select id  from `produit` where lib_prod='"+nom+"'";
        ResultSet rst = stm.executeQuery(query);
        int id = 0;
        while (rst.next()) {
            id = rst.getInt("id");
        }
        return id;
    }
}

