/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
public final class UserSession {

    private static UserSession instance;

    private String userName;
    private int id;
    private String email;
    private String roles;
    private String address;
    private long phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     private UserSession(String username,int id,String email,String roles,String address,long phoneNumber) {
        this.id = id;
        this.userName = username;
        this.email = email;
        this.roles = roles;   
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static UserSession getInstace(String userName,int id,String email,String roles,String address,long phoneNumber) {
        if(instance == null) {
            instance = new UserSession(userName,id,email,roles,address,phoneNumber);
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

   

    public void cleanUserSession() {
        instance = null;
    }

    @Override
    public String toString() {
        return "UserSession{" + "userName=" + userName + ", id=" + id + ", email=" + email + ", roles=" + roles + ", address=" + address + ", phoneNumber=" + phoneNumber + '}';
    }

  
}