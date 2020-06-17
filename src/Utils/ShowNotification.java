/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author L
 */
public class ShowNotification {
    
    public void show(String titre, String texte){
        try {
            Image img = new Image(new FileInputStream("res/images/tick.png"));
            Notifications notificationBuilder = Notifications.create().title(titre).text(texte)
                    .graphic(new ImageView(img)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).hideCloseButton().darkStyle();
            notificationBuilder.show();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showConfirm(String titre, String texte){
        try {
            Image img = new Image(new FileInputStream("res/images/tick.png"));
            Notifications notificationBuilder = Notifications.create().title(titre).text(texte)
                    .graphic(new ImageView(img)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).hideCloseButton().darkStyle();
            notificationBuilder.showConfirm();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showError(String titre, String texte){
        try {
            Image img = new Image(new FileInputStream("res/images/tick.png"));
            Notifications notificationBuilder = Notifications.create().title(titre).text(texte)
                    .graphic(new ImageView(img)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).hideCloseButton().darkStyle();
            notificationBuilder.showError();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showInformation(String titre, String texte){
        try {
            Image img = new Image(new FileInputStream("res/images/tick.png"));
            Notifications notificationBuilder = Notifications.create().title(titre).text(texte)
                    .graphic(new ImageView(img)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).hideCloseButton().darkStyle();
            notificationBuilder.showInformation();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showWarning(String titre, String texte){
        try {
            Image img = new Image(new FileInputStream("res/images/tick.png"));
            Notifications notificationBuilder = Notifications.create().title(titre).text(texte)
                    .graphic(new ImageView(img)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).hideCloseButton().darkStyle();
            notificationBuilder.showWarning();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ShowNotification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
