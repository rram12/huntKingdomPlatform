/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Hebergement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public interface IServiceHebergement {
    public void addHebergement(Hebergement h) throws SQLException;

    public List<Hebergement> getHebergements() throws SQLException;
    
    public List<Hebergement> TrierHebergement(int i) throws SQLException;

    public Hebergement getById(int id) throws SQLException;

    public void deleteHebergement(Hebergement p) throws SQLException;

    public void deleteHebergement(int id) throws SQLException;

    public void updateHebergement(Hebergement p,int id) throws SQLException; 
    
    public void SearchHebergements(String character) ;
    
}
