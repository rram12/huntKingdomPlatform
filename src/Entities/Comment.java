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
public class Comment {
    int id;
    String content;
    Date postedAt;
    int checked;
    int postId;
    int userId;

    public Comment() {
    }

    public Comment(int id, String content, Date postedAt, int checked,
            int postId, int userId) {
        this.id = id;
        this.content = content;
        this.postedAt = postedAt;
        this.checked = checked;
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", content=" + content + ", postedAt=" + postedAt + ", checked=" + checked + ", postId=" + postId + ", userId=" + userId + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    
}
