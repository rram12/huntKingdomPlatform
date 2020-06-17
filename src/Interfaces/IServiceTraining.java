/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;
import java.sql.SQLException;
import java.util.List;
import Entities.Entrainement;
/**
 *
 * @author tibh
 */
public interface IServiceTraining {
     public List<Entrainement> getTrainings() throws SQLException;
     public Entrainement getById(int id) throws SQLException;
     public void addTraining(Entrainement e) throws SQLException;
    
}
