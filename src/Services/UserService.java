/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import edu.huntkingdom.entities.User;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author L
 */
public class UserService {
    Connection cnx2;
    
    public UserService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public boolean addUser(User u){
        if(verifyUsername(u.getUsername())){
            System.err.println("This username is taken! You can choose from these usernames");
            System.err.println(suggestUsername(u.getUsername()));
            return false;
        }
        try {
            String encryptedPW = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12));
            String requete="INSERT INTO fos_user (username, username_canonical, email, email_canonical, enabled,"
                    + " password, roles, firstName, lastName, address, phoneNumber, picture, gender, contract) "
                    + "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, u.getUsername());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getEmail());
            pst.setInt(5, 1);
            pst.setString(6, encryptedPW);
            pst.setString(7, u.getRoles());
            pst.setString(8, u.getFirstName());
            pst.setString(9, u.getLastName());
            pst.setString(10, u.getAddress());
            pst.setLong(11, u.getPhoneNumber());
            pst.setString(12, u.getPicture());
            pst.setInt(13, u.getGender());
            pst.setString(14, u.getContract());
            
            pst.executeUpdate();
            System.out.println("user added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
    
    public ArrayList<User> showAll(){
        ArrayList<User> usersList = new ArrayList();
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(8));
                u.setLast_login(rs.getString(9));
                u.setRoles(rs.getString(12));
                u.setFirstName(rs.getString(13));
                u.setLastName(rs.getString(14));
                u.setAddress(rs.getString(15));
                u.setPhoneNumber(rs.getLong(16));
                u.setPicture(rs.getString(17));
                u.setGender(rs.getInt(18));
                u.setContract(rs.getString(19));
                u.setConfirmed(rs.getInt(20));
                usersList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usersList;
    }
    
    public boolean verifyUsername(String username){
        ArrayList<User> usersList = new ArrayList();
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.username = '"+username+"'";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(8));
                u.setLast_login(rs.getString(9));
                u.setRoles(rs.getString(12));
                u.setFirstName(rs.getString(13));
                u.setLastName(rs.getString(14));
                u.setAddress(rs.getString(15));
                u.setPhoneNumber(rs.getLong(16));
                u.setPicture(rs.getString(17));
                u.setGender(rs.getInt(18));
                u.setContract(rs.getString(19));
                u.setConfirmed(rs.getInt(20));
                usersList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(usersList.size()>0)
            return true;
        return false;
    }
    
    public ArrayList suggestUsername(String username){
        ArrayList<String> suggestList = new ArrayList();
        Random generator=new Random();
        int n=0;
        int suffixe=0;
        while(n<3){
            suffixe = generator.nextInt(100);
            if(verifyUsername(username+Integer.toString(suffixe))==false && !suggestList.contains(username+Integer.toString(suffixe))){
                suggestList.add(username+Integer.toString(suffixe));
                n+=1;
            }
        }
        return suggestList;
    }
    
    public boolean deleteUser(User u){
        try {
            String requete="delete from fos_user where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, u.getId());
            pst.executeUpdate();
            System.out.println("User deleted");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public User getUserById(User u1) {
        User u = new User();
        try {
            String req = "select * from utilisateur join adresse on utilisateur.cin=adresse.cin where utilisateur.cin='" + u1.getId() + "'";
            Statement st = cnx2.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(8));
                u.setLast_login(rs.getString(9));
                u.setRoles(rs.getString(12));
                u.setFirstName(rs.getString(13));
                u.setLastName(rs.getString(14));
                u.setAddress(rs.getString(15));
                u.setPhoneNumber(rs.getLong(16));
                u.setPicture(rs.getString(17));
                u.setGender(rs.getInt(18));
                u.setContract(rs.getString(19));
                u.setConfirmed(rs.getInt(20));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    
    public boolean updateUser(User u){
        try {
            String requete="update fos_user set username='"+u.getUsername()+"',username_canonical='"+u.getUsername()+"'"
                    + ",email='"+u.getEmail()+"',email_canonical='"+u.getEmail()+"',roles='"+u.getRoles()+"'"
                    + ",firstName='"+u.getFirstName()+"',lastName='"+u.getLastName()+"',address='"+u.getAddress()+"'"
                    + ",phoneNumber='"+u.getPhoneNumber()+"',picture='"+u.getPicture()+"',gender='"+u.getGender()+"'where fos_user.id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, u.getId());
            pst.executeUpdate();
            System.out.println("User updated");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
}
