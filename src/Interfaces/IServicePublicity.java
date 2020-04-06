/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Entities.Publicity;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author User
 */
public interface IServicePublicity {
     public void addPublicity(Publicity p) throws SQLException;

    public List<Publicity> getPublicitys() throws SQLException;

    public Publicity getById(int id) throws SQLException;

    public void deletePublicity(Publicity p) throws SQLException;

    public void deletePublicity(int id) throws SQLException;

    public void updateCompetition(Publicity p,int id) throws SQLException; 
    
    
}
