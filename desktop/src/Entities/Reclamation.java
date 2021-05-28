/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author L
 */
public class Reclamation {
    int id;
    String descriptionRec;
    Date dateRec;
    String title;
    int userId;
    boolean handled;

    public Reclamation() {
    }

    public Reclamation(int id, String descriptionRec, Date dateRec, String title,int userId, boolean handled) {
        this.id = id;
        this.descriptionRec = descriptionRec;
        this.dateRec = dateRec;
        this.title = title;
        this.userId = userId;
        this.handled = handled;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", descriptionRec=" + descriptionRec + ", dateRec=" + dateRec + ", title=" + title + ", userId=" + userId + ", handled=" + handled + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptionRec() {
        return descriptionRec;
    }

    public void setDescriptionRec(String descriptionRec) {
        this.descriptionRec = descriptionRec;
    }

    public Date getDateRec() {
        return dateRec;
    }

    public void setDateRec(Date dateRec) {
        this.dateRec = dateRec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }
    
    
    
}
