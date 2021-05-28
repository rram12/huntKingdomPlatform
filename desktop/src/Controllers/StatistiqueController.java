/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.MyConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;


/**
 * FXML Controller class
 *
 * @author tibh
 */
public class StatistiqueController implements Initializable {

    @FXML
    private PieChart pieChart;
    Connection cnx;
    Statement ste;
       ObservableList < PieChart.Data > piechartdata;
    ArrayList < String > p = new ArrayList <  > ();
    ArrayList < Double > c = new ArrayList <> ();
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
                
  pieChart.setData(piechartdata);
   
    }
    
    public void loadData() {
     
   
    String query="Select entrainement.categorie ,CONVERT(SUM(entrainement.id)*100/Tot.total,decimal(10,2)) as pourcentage from entrainement ,(select SUM(id) as total from entrainement) as Tot Group By entrainement.categorie";
 
    piechartdata = FXCollections.observableArrayList();

      cnx = MyConnection.getInstance().getCnx();
 
    try {
      
      ResultSet rs = cnx.createStatement().executeQuery(query);
  
      while (rs.next()) {
       
        piechartdata.add(new PieChart.Data(rs.getString("categorie")+"("+Double.toString(rs.getDouble("pourcentage"))+")", rs.getDouble("pourcentage")));
        String pour=Double.toString(rs.getDouble("pourcentage"));
        p.add(rs.getString("categorie")+pour);
        c.add(rs.getDouble("pourcentage"));
        
        
      }
      
    } catch (SQLException e) {
  System.out.println(e.getMessage());
    }}
    
    
    
}
