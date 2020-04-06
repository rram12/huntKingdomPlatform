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
public class Publication {
    int id;
    String title;
    String description;
    int userId;
    String image;
    int closed;
    int solved;
    int views;
    int pinned;
    Date publishedAt;

    public Publication() {
    }

    public Publication(int id, String title, String description, int userId,
            String image, int closed, int solved, int views, int pinned,
            Date publishedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.image = image;
        this.closed = closed;
        this.solved = solved;
        this.views = views;
        this.pinned = pinned;
        this.publishedAt = publishedAt;
    }

    

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", title=" + title + ", description=" + description + ", userId=" + userId + ", image=" + image + ", closed=" + closed + ", solved=" + solved + ", views=" + views + ", pinned=" + pinned + ", publishedAt=" + publishedAt + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    
    
}
