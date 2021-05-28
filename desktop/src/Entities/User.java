/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author L
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String roles;
    private String firstName;
    private String lastName;
    private String address;
    private long phoneNumber;
    private String picture;
    private int gender;
    private String contract;
    private int confirmed;
    private String last_login;
    private int enabled=1;

    public User() {
    }

    public User(int id, String username, String email, String password,
            String roles, String firstName, String lastName, String address,
            long phoneNumber, String picture, int gender, String contract) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.gender = gender;
        this.contract = contract;
    }
    
    public User(String username, String email, String password, String roles, String firstName, String lastName, String address, long phoneNumber, String picture, int gender, String contract, int confirmed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.gender = gender;
        this.contract = contract;
        this.confirmed = confirmed;
    }
    
    public User(int id,String username, String email, String password, String roles, String firstName, String lastName, String address, long phoneNumber, String picture, int gender, String contract, int confirmed) {
        this.id=id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.gender = gender;
        this.contract = contract;
        this.confirmed = confirmed;
    }

    public User(int id) {
        this.id = id;
    }
    
    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", roles=" + roles + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phoneNumber=" + phoneNumber + ", picture=" + picture + ", gender=" + gender + ", contract=" + contract + ", confirmed=" + confirmed + ", last_login=" + last_login + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }
    
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    
}
