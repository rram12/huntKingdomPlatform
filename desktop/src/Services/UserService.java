/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.User;
import Utils.MyConnection;
import Utils.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public String getUsername(int id) throws SQLException {
       Statement stm = cnx2.createStatement();
        String query = "select username  from `fos_user` where id='"+id+"'";
        ResultSet rst = stm.executeQuery(query);
        String username = "";
       
        while (rst.next()) {
            username= rst.getString("username");
            
            
        }
        return username;
    }

    public boolean addUser(User u) {
        if (verifyUsername(u.getUsername())) {
            System.err.println("This username is taken! You can choose from these usernames");
            System.err.println(suggestUsername(u.getUsername()));
            return false;
        }
        try {
            String encryptedPW = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10));
            String requete = "INSERT INTO fos_user (username, username_canonical, email, email_canonical, confirmed,"
                    + " password, roles, firstName, lastName, address, phoneNumber, picture, gender, contract, enabled) "
                    + "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?,?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, u.getUsername());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getEmail());
            pst.setInt(5, u.getConfirmed());
            pst.setString(6, encryptedPW);
            pst.setString(7, u.getRoles());
            pst.setString(8, u.getFirstName());
            pst.setString(9, u.getLastName());
            pst.setString(10, u.getAddress());
            pst.setLong(11, u.getPhoneNumber());
            pst.setString(12, u.getPicture());
            pst.setInt(13, u.getGender());
            pst.setString(14, u.getContract());
            pst.setInt(15, 1);

            pst.executeUpdate();
            System.out.println("user added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public ArrayList<User> showAll() {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
    public ArrayList<User> showUnconfirmed() {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where confirmed=0";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
    public ArrayList<User> showAllByCriteria(String sortC , String searchC, String searchString) {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where confirmed=1 and "+searchC+" like '%"+searchString+"%' order by "+sortC;
            
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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

    public boolean verifyUsername(String username) {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.username = '" + username + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
        if (usersList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean verifyEmail(String email) {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.email = '" + email + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
        if (usersList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean verifyGeneratedImageName(String name) {
        ArrayList<User> usersList = new ArrayList();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.picture = '" + name + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
        if (usersList.size() > 0) {
            return true;
        }
        return false;
    }

    public User selectUserByEmail(String email) {
        User u = new User();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.email = '" + email + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
    public User selectUserByID(int id) {
        User u = new User();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.id = '" + id + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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

    public User getUserById(User u1) {
        User u = new User();
        try {
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where fos_user.id = '" + u1.getId() + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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

    public User logIn(String email, String pass) {

        try {
            User u = new User();
            Statement st = cnx2.createStatement();
            String hash = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12));
            String requete = "SELECT * FROM fos_user where fos_user.email = '" + email + "' and fos_user.password='" + hash + "'";
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
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
                return u;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ArrayList suggestUsername(String username) {
        ArrayList<String> suggestList = new ArrayList();
        Random generator = new Random();
        int n = 0;
        int suffixe = 0;
        while (n < 3) {
            suffixe = generator.nextInt(100);
            if (verifyUsername(username + Integer.toString(suffixe)) == false && !suggestList.contains(username + Integer.toString(suffixe))) {
                suggestList.add(username + Integer.toString(suffixe));
                n += 1;
            }
        }
        return suggestList;
    }

    public boolean deleteUser(int id) {
        String requete;
        PreparedStatement pst;
        try {
            requete = "delete from comment where comment.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from publication where publication.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from reclamation where reclamation.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from competition_user where competition_user.user_id = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from participationcompetition where participationcompetition.user_id = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from reservation where reservation.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from location where location.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from entrainement where entrainement.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from piecesdefectueuses where piecesdefectueuses.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from reparation where reparation.userId = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            requete = "delete from fos_user where id = ?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("User deleted");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateUser(User u) {

        try {
            String requete = "UPDATE fos_user SET username=? ,username_canonical=? "
                    + ",email=? ,email_canonical=? ,"
                    + "firstName= ?,lastName=? ,address=? "
                    + ",phoneNumber=? ,picture=? ,gender=? , password=?"
                    + "WHERE fos_user.id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, u.getUsername());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getFirstName());
            pst.setString(6, u.getLastName());
            pst.setString(7, u.getAddress());
            pst.setLong(8, u.getPhoneNumber());
            pst.setString(9, u.getPicture());
            pst.setInt(10, u.getGender());
            pst.setString(11, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
            pst.setInt(12, u.getId());
            pst.executeUpdate();
            System.out.println("User updated");
            return true;
        } catch (Exception ex) {
            System.out.println("updateUser exception" + ex.getMessage());
            return false;
        }
    }
    public boolean confirmUser(User u) {

        try {
            String requete = "UPDATE fos_user SET confirmed = 1 WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
          
            
            pst.setInt(1, u.getId());
            pst.executeUpdate();
            System.out.println(requete);
            return true;
        } catch (Exception ex) {
            System.out.println("confirmUser exception" + ex.getMessage());
            return false;
        }
    }

    public boolean desactivateUser(User u) {

        try {
            String requete = "UPDATE fos_user SET username=? ,username_canonical=? "
                    + ",email=? ,email_canonical=? ,"
                    + "firstName= ?,lastName=? ,address=? "
                    + ",phoneNumber=? ,picture=? ,gender=? , password=?"
                    + "WHERE fos_user.id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, u.getUsername());
            pst.setString(2, u.getUsername());
            pst.setString(3, u.getEmail());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getFirstName());
            pst.setString(6, u.getLastName());
            pst.setString(7, u.getAddress());
            pst.setLong(8, u.getPhoneNumber());
            pst.setString(9, u.getPicture());
            pst.setInt(10, u.getGender());
            pst.setString(11, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(10)));
            pst.setInt(12, u.getId());
//System.out.println("before executeUpdate");
// pst.setInt(1, u.getId());
            pst.executeUpdate();
//            System.out.println("after executeUpdate");
            //System.out.println(pst.executeUpdate());
            System.out.println("User updated");
            return true;
        } catch (Exception ex) {
            System.out.println("updateUser exception" + ex.getMessage());
            return false;
        }
    }

    public boolean updatePassword(int id, String pw) {
        try {
            String requete = "UPDATE fos_user SET password= ? WHERE id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, BCrypt.hashpw(pw, BCrypt.gensalt(10)));
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("password updated ");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;

        }
    }

    public HashMap countGender() {
        HashMap<String, Integer> hm = new HashMap();
        ArrayList<User> usersList = new ArrayList<>();
        UserService us = new UserService();
        usersList = us.showAll();
        String gender = "";
        hm.put("Male", 0);
        hm.put("Female", 0);
        for (User u : usersList) {
            if (u.getGender() == 1) {
                gender = "Male";
            } else {
                gender = "Female";
            }
            hm.put(gender, hm.get(gender) + 1);

        }
        
        return hm;
    }
    
    public HashMap countCountries() {
        HashMap<String, Integer> hm = new HashMap();
        ArrayList<User> usersList = new ArrayList<>();
        UserService us = new UserService();
        usersList = us.showAll();
        String country = "";
        for (User u : usersList) {
            country = u.getAddress().substring(u.getAddress().lastIndexOf(',')+1, u.getAddress().length()).replaceAll(" ", "");
            if (hm.containsKey(country)){
                hm.put(country, hm.get(country)+1);
            }else{
                hm.put(country,1);
            }

        }
        
        return hm;
    }
    
    public ArrayList<User> showNotConfirmed(){
        ArrayList<User> usersList = new ArrayList();
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM fos_user where confirmed = false";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(8));
                u.setLast_login(rs.getString(9));
                String role = rs.getString(12);
                role = role.substring(20,role.length());
                u.setRoles(role.substring(0, role.length()-3));
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

}
