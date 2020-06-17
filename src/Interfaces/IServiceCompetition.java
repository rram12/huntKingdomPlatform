/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Competition;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author User
 */
public interface IServiceCompetition {
     public void addCompetition(Competition o) throws SQLException;

    public List<Competition> getCompetitions() throws SQLException;
    
    public List<Competition> TrierCompetitions(int i) throws SQLException;

    public Competition getById(int id) throws SQLException;

    public void deleteCompetition(Competition o) throws SQLException;

    public void deleteCompetition(int id) throws SQLException;

    public void updateCompetition(Competition o,int id) throws SQLException; 
    
}
