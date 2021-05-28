/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ASUS1
 */
public class Location {

    private int id;

    private int nbJours;

    private float prixTot;

    private Date dateArrivee;

    private int userId;

    private int MoyenDeTransportId;

    public Location() {
    }

    public Location(int nbJours, float prixTot, Date dateArrivee, int userId, int MoyenDeTransportId) {
        this.nbJours = nbJours;
        this.prixTot = prixTot;
        this.dateArrivee = dateArrivee;
        this.userId = userId;
        this.MoyenDeTransportId = MoyenDeTransportId;
    }

    public Location(int id, int nbJours, float prixTot, Date dateArrivee) {
        this.id = id;
        this.nbJours = nbJours;
        this.prixTot = prixTot;
        this.dateArrivee = dateArrivee;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbJours() {
        return nbJours;
    }

    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    public float getPrixTot() {
        return prixTot;
    }

    public void setPrixTot(float prixTot) {
        this.prixTot = prixTot;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoyenDeTransportId() {
        return MoyenDeTransportId;
    }

    public void setMoyenDeTransportId(int MoyenDeTransportId) {
        this.MoyenDeTransportId = MoyenDeTransportId;
    }

    public Date finaldate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.dateArrivee);
        //Incrementing the date by 1 day
        c.add(Calendar.DAY_OF_MONTH, this.nbJours);
        Date date = c.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String date1 = format1.format(date);

        Date inActiveDate = null;

        try {

            inActiveDate = format1.parse(date1);

        } catch (ParseException e1) {

            // TODO Auto-generated catch block
            e1.printStackTrace();

        }
        return inActiveDate;
    }

    public Date Arrivaldate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.dateArrivee);
        Date date = c.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String date1 = format1.format(date);

        Date inActiveDate = null;

        try {

            inActiveDate = format1.parse(date1);

        } catch (ParseException e1) {

            // TODO Auto-generated catch block
            e1.printStackTrace();

        }
        return inActiveDate;
    }

    @Override
    public String toString() {
        return "   Starts=" + Arrivaldate() + "\n Ends=" + finaldate();
    }
}
