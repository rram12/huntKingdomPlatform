/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Services.UserService;
import Utils.MyConnection;
import Utils.BCrypt;
import Utils.EmailBody;
import Utils.MailSender;
import Utils.Session;
import Utils.ShowNotification;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private ImageView imglogin;

    UserService us = new UserService();
    ShowNotification notif = new ShowNotification();

    private User loggedUser;
    private static LoginController instance;
    @FXML
    private CheckBox cbRememberMe;
    @FXML
    private Button btSignUp;

    public LoginController() {
        instance = this;
    }

    public static LoginController getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User u) {
        this.loggedUser = u;
    }

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        cbRememberMe.setSelected(true);

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("res/registredCredentials.txt"));
            String line = br.readLine();

            txtusername.setText(line);
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        ArrayList<String> allEmails = new ArrayList();

        try {
            br = new BufferedReader(new FileReader("res/registredEmails.txt"));
            String line = br.readLine();

            while (line != null) {
                allEmails.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        TextFields.bindAutoCompletion(txtusername, allEmails);

    }

    @FXML
    private void btnLoginAction(ActionEvent event) {

        User u = us.selectUserByEmail(txtusername.getText());
        System.out.println(u);
        if (u.getId() == 0) {

            notif.showError("Not existing account!", "Not existing account! Please verify the entred credentials ! ");

            System.out.println("email wrong");
        } else if (BCrypt.checkpw(txtpassword.getText(), u.getPassword()) && u.getConfirmed() == 1) {

            notif.show("Welcome back", "It's good to see you again " + u.getUsername());

            loggedUser = u;

            addEmail(cbRememberMe.isSelected(), u.getEmail());

            rememberCredentials(cbRememberMe.isSelected(), u.getEmail());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/Home.fxml"));

            if (u.getRoles().contains("ADMIN")) {
                loader = new FXMLLoader(getClass().getResource("/Gui/AdminHome.fxml"));
            }

            try {
                Parent root = loader.load();

                txtusername.getScene().setRoot(root);

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.err.println(ex.getCause());
                System.err.println(ex.getClass());
            }
        } else if (u.getConfirmed() == 0) {

            try {
//                notif.showError("Error!", "This account is not confirmed yet");
                /* AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Wait.fxml"));
                mainPane.getChildren().setAll(pane);    */
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                // do what you have to do

                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Gui/Wait.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/Style/bootstrap3.css").toExternalForm());
                primaryStage.setTitle("not Confirmed Account !");
                primaryStage.setTitle("HuntKingdom");
                Image ico = new Image("Uploads/logo2.png");
                primaryStage.getIcons().add(ico);
                primaryStage.setScene(scene);
                primaryStage.show();
                stage.close();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            notif.showError("Error!", "Please verify your password");
        }
    }

    public static void clearTheFile(String fileName) throws IOException {
        FileWriter fwOb = new FileWriter(fileName, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

//    @FXML
//    private void signUp(ActionEvent event) {
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
//
//        try {
//            Parent root = loader.load();
//            SignUpController suc = loader.getController();
//
//            tfUsername.getScene().setRoot(root);
//
//        } catch (IOException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
    @FXML
    private void passwordRecovery(ActionEvent event) {
        User u = us.selectUserByEmail(txtusername.getText());
        MailSender mailer = new MailSender();
        ShowNotification notif = new ShowNotification();
        // SMTP server information
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "huntkingdomteam@gmail.com";
        String passwordMail = "hk2020..";

        // outgoing message information
        String mailTo = u.getEmail();
        String subject = "Password Recovery";

        // message contains HTML markups
        EmailBody eb = new EmailBody();
        if (u.getId() == 0) {
            notif.showError("Error!", "Email does not exist");
        } else {
            try {
                String newPw = mailer.genererCle();
                String message = eb.createEmailBody("Password Recovery ", "", "Hi " + u.getUsername() + ",",
                        "Your new Password is: " + newPw + "<br>Please disregard this e-mail if you did not request a password reset.");
                mailer.sendHtmlEmail(host, port, mailFrom, passwordMail, mailTo, subject, message);
                System.out.println("Email sent.");
//            u.setPassword(BCrypt.hashpw(newPw, BCrypt.gensalt(10)));
                us.updatePassword(u.getId(), newPw);
                notif.show("Password Reset", "An email with your new password have been sent.");
            } catch (Exception ex) {
                System.out.println("Failed to sent email.");
                ex.printStackTrace();
            }
        }

    }

    public void setTextEmail(String message) {
        this.txtusername.setText(message);
    }

    public void rememberCredentials(boolean test, String email) {
        if (test) {

            try {
                PrintWriter out = new PrintWriter("res/registredCredentials.txt");
                out.println(email);
                out.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                clearTheFile("res/registredCredentials.txt");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void addEmail(boolean test1, String email) {
        if (test1) {
            Writer output;
            List<String> emails = new ArrayList<>();
            try {
                File myObj = new File("res/registredEmails.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    emails.add(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                output = new BufferedWriter(new FileWriter("res/registredEmails.txt", true));
//                System.out.println(emails.contains(email));
//                System.out.println("************"+emails);
                if (!emails.contains(email)) {
                    output.append("\n" + email);
                }
                output.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void signUp(ActionEvent event) {

        try {
//            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/AdminHome.fxml"));
//            mainPane.getChildren().setAll(pane);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/SignUp.fxml"));
//
//
//            try {
            Parent root = loader.load();

            txtusername.getScene().setRoot(root);
//
//            } catch (IOException ex) {
//                System.err.println(ex.getMessage());
//                System.err.println(ex.getCause());
//                System.err.println(ex.getClass());
//            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
