/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huntkingdom;

import Controllers.AdminHomeController;
import Controllers.AdminUsersListController;
import Controllers.SignUpController;
import Entities.User;
import Services.UserService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author asus_pc
 */
public class HuntKingdom extends Application {

    public static Boolean isSplasheded = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
        primaryStage.setTitle("HuntKingdom");
        Image ico = new Image("Uploads/logo2.png");
        primaryStage.getIcons().add(ico);
        primaryStage.setScene(scene);
        primaryStage.show();
        //stage.setFullScreen(true);
    }

       @Override
    public void stop() {
        System.out.println("Stage is closing");
        if (AdminHomeController.getInstance() != null) {
            AdminHomeController.getInstance().getTimer().cancel();
            System.out.println("timer admin home canceled");
        }
        if (AdminUsersListController.getInstance() != null) {
            AdminUsersListController.getInstance().getTimer().cancel();
            System.out.println("timer admin usersList  canceled");
        }
        if (SignUpController.getInstance() != null) {
            if(!SignUpController.getInstance().getContract().isEmpty()){
                    File file = new File("res/uploadedPDF/" +SignUpController.getInstance().getContract());
                    file.delete();
            }
            if(!SignUpController.getInstance().getPicture().isEmpty()){
                    File file = new File( "res/uploadedImages/" + SignUpController.getInstance().getPicture());
                    file.delete();
            }
        }
        
deleteTmpFiles();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void deleteTmpFiles(){
        UserService us = new UserService();
        final File folderImages = new File("res/uploadedImages/");
        final File folderPDFs = new File("res/uploadedPDF/");
        ArrayList<String> pics = new ArrayList();
        ArrayList<String> pdfs = new ArrayList();
        for(User i : us.showAll()){
            pics.add(i.getPicture());
            pdfs.add(i.getContract());
        }
        System.out.println(pics);
        for (final File fileEntry : folderImages.listFiles()){
            if (!fileEntry.isDirectory() && !pics.contains(fileEntry.getPath().substring(fileEntry.getPath().lastIndexOf("\\")+1))) {
                System.out.println(fileEntry+" deleted");
                fileEntry.delete();
            }
        }
        for (final File fileEntry : folderPDFs.listFiles()){
            if (!fileEntry.isDirectory() && !pics.contains(fileEntry.getPath().substring(fileEntry.getPath().lastIndexOf("\\")+1))) {
                System.out.println(fileEntry+" deleted");
                fileEntry.delete();
            }
        }
        
    }

}
