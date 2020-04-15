/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author User
 */
public class Participation {
    private int user_id;
    private int competition_id;

    public Participation(int user_id, int competition_id) {
        this.user_id = user_id;
        this.competition_id = competition_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(int competition_id) {
        this.competition_id = competition_id;
    }

    @Override
    public String toString() {
        return "Participation{" + "user_id=" + user_id + ", competition_id=" + competition_id + '}';
    }
    
    
    
    
}
