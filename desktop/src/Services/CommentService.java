/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Entities.Publication;
import Entities.Comment;
import Entities.User;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author L
 */
public class CommentService {
    Connection cnx2;
    
    public CommentService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public boolean addComment(Comment c){
            Profanity pr = new Profanity();
            Profanity.loadConfigs();
            if(Profanity.badWordsFound(c.getContent()).size()>0){
                System.err.println("Bad Word detected in this comment");
                return false;
            }
        try {
            String requete="INSERT INTO comment (content, postedAt, checked, postId,userId) "
                    + "VALUES (? , ? , ? , ? ,?)";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, c.getContent());
            pst.setDate(2, c.getPostedAt());
            pst.setInt(3, c.getChecked());
            pst.setInt(4, c.getPostId());
            pst.setInt(5, c.getUserId());
            pst.executeUpdate();
            System.out.println("comment added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
                
    
    
    public ArrayList<Comment> getPubsComments(int pubId){
        ArrayList<Comment> commentsList = new ArrayList();
        try {            
            Statement st = cnx2.createStatement();
            String requete = "SELECT * FROM comment where comment.postId='"+Integer.toString(pubId)+"'";
            ResultSet rs = st.executeQuery(requete);
            while(rs.next()){
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setContent(rs.getString(2));
                c.setPostedAt(rs.getDate(3));
                c.setChecked(rs.getInt(4));
                c.setPostId(rs.getInt(5));
                c.setUserId(rs.getInt(6));
                
                commentsList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return commentsList;
    }
    
    public boolean deleteComment(Comment c){
        try {
            String requete="delete from Comment where id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            System.out.println("Comment deleted");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean updateComment(Comment c){
        try {
            String requete="update Comment set content='"+c.getContent()+"',postedAt='"+c.getPostedAt()+"'"
                    + ",checked='"+c.getChecked()+"',postId='"+c.getPostId()+"',userId='"+c.getUserId()+"'where Comment.id = ?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            System.out.println("Comment updated");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
