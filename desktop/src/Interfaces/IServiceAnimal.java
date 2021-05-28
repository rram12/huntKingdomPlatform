/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;
import java.sql.SQLException;
import java.util.List;
import Entities.Animal;
/**
 *
 * @author tibh
 */
public interface IServiceAnimal {
  public void addAnimal(Animal p) throws SQLException;

    public List<Animal> getAnimals();
    
    public List<Animal> TrierAnimals(int i) throws SQLException;

    public Animal getById(int id) throws SQLException;

    public void deleteAnimal(Animal p) throws SQLException;

    public void deleteAnimal(int id) throws SQLException;

    public void updateAnimal(Animal p,int id); 
    
    public void SearchAnimals(String character) ;  
}
