package Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asus_pc
 */
public class Repairer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;

    public Repairer() {
    }

    public Repairer(int id, String firstName, String lastName, String email, int phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
   
    
     @Override
    public String toString() {
        return "Repairer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + '}';
    }
    
}
