/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Utils.EmailBody;
import Utils.MailSender;
import Utils.ShowNotification;
import Utils.realEmailValidation;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import Services.UserService;
import Utils.MyConnection;
import eu.bitm.NominatimReverseGeocoding.Address;
import eu.bitm.NominatimReverseGeocoding.NominatimReverseGeocodingJAPI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author ramzi.raddaoui@esprit.tn
 */
public class SignUpController implements Initializable {

    private WebEngine engine;
    JSONParser parser = new JSONParser();

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private CheckBox ckbTerms;
    @FXML
    private ChoiceBox<String> cbGender;
    @FXML
    private Button btSignUp;
    @FXML
    private ChoiceBox<String> cbRole;
    @FXML
    private Label lErrorFirstName;
    @FXML
    private Label lErrorEmail;
    @FXML
    private Label lErrorLastName;
    @FXML
    private Label lErrorUsername;
    @FXML
    private Label lMatchedPw;
    @FXML
    private Label lUpperCase;
    @FXML
    private Label lLowerCase;
    @FXML
    private Label lSymbol;
    @FXML
    private Label lNumber;
    @FXML
    private Label lErrorPhoneNumber;
    @FXML
    private Label lErrorAddress;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordFieldRepeat;
    @FXML
    private Label lErrorPw;
    @FXML
    private Label lSuggestions;

    boolean fnRules = false;
    boolean lnRules = false;
    boolean emRules = false;
    boolean unRules = false;
    boolean pwRules = false;
    boolean rpwRules = false;
    boolean adRules = false;
    boolean pnRules = false;
    boolean ckbRules = false;

    String suggestions = "";
    String contract = "";
    String picture = "";

    UserService us = new UserService();
    ObservableList<String> cities = FXCollections.observableArrayList();

    String ad = "";
    @FXML
    private WebView wbMap;
    @FXML
    private Label pdfPath;
    @FXML
    private ImageView avatar1;

    private FileChooser fc;
    @FXML
    private Button btCancel;
    
    private static SignUpController instance;
    
    
    public SignUpController() {
        instance = this;
    }
    
    public static SignUpController getInstance() {
        return instance;
    }

    public String getPicture() {
        return picture;
    }
    public String getContract() {
        return contract;
    }

    private Address onValueChange(String data) {

        NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI();
//        System.out.println(Double.parseDouble(data.substring(data.indexOf(" ") + 1)));
//        System.out.println(Double.parseDouble(data.substring(0, data.indexOf(" "))));
//        System.out.println(nominatim1.getAdress(Double.parseDouble(data.substring(data.indexOf(" ") + 1)), Double.parseDouble(data.substring(0, data.indexOf(" ")))));
//        try {
//            PrintWriter out = new PrintWriter("coord.txt");
//            out.println(Double.parseDouble(data.substring(data.indexOf(" ") + 1)));
//            out.println(Double.parseDouble(data.substring(0, data.indexOf(" "))));
//            out.println(nominatim1.getAdress(Double.parseDouble(data.substring(data.indexOf(" ") + 1)), Double.parseDouble(data.substring(0, data.indexOf(" ")))).toString());
//            out.close();
//        } catch (FileNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }
        return nominatim1.getAdress(Double.parseDouble(data.substring(data.indexOf(" ") + 1)), Double.parseDouble(data.substring(0, data.indexOf(" "))));

//        System.out.println(data);
//       
    }

    Address userAddress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        

        try {
            avatar.setImage(new Image(new FileInputStream("res/images/uploadImage.png")));
        avatar1.setImage(new Image(new FileInputStream("res/images/uploadPDF.png")));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

//        String[] locales1 = Locale.getISOCountries();
//        for (String countrylist : locales1) {
//            Locale obj = new Locale("", countrylist);
//            String[] city = {obj.getDisplayCountry()};
//            for (int x = 0; x < city.length; x++) {
//                cities.add(obj.getDisplayCountry());
//            }
//        }
//        country.setItems(cities);
        cbGender.getItems().add("Male");
        cbGender.getItems().add("Female");
        cbGender.setValue("Male");
        cbRole.getItems().add("Client");
        cbRole.getItems().add("Trainer");
        cbRole.setValue("Client");
        cbRole.getItems().add("Repairer");
        avatar1.setVisible(false);
        pdfPath.setVisible(false);

        lErrorAddress.setTextFill(Color.web("#ff0000"));
        lErrorEmail.setTextFill(Color.web("#ff0000"));
        lErrorLastName.setTextFill(Color.web("#ff0000"));
        lErrorUsername.setTextFill(Color.web("#ff0000"));
        lMatchedPw.setTextFill(Color.web("#ff0000"));
        lUpperCase.setTextFill(Color.web("#ff0000"));
        lLowerCase.setTextFill(Color.web("#ff0000"));
        lSymbol.setTextFill(Color.web("#ff0000"));
        lNumber.setTextFill(Color.web("#ff0000"));
        lErrorPhoneNumber.setTextFill(Color.web("#ff0000"));
        lErrorPw.setTextFill(Color.web("#ff0000"));
        lErrorFirstName.setTextFill(Color.web("#ff0000"));

        engine = wbMap.getEngine();
        engine.load(getClass().getResource("map.html").toString());
        engine.getLoadWorker().stateProperty().addListener((ov, o, n) -> {
            if (Worker.State.SUCCEEDED == n) {
                engine.setOnStatusChanged(webEvent -> {

                    //Call value change
                    userAddress = onValueChange(webEvent.getData());
                    tfAddress.setText(userAddress.toString());
                    try {
                        Object obj = parser.parse(new FileReader("res/countriesCode.json"));
                        JSONObject jobj = (JSONObject) obj;
                        String code = (String) jobj.get(userAddress.getCountryCode().toString().toUpperCase());
                        System.out.println(code);
                        System.out.println(userAddress.getCountryCode());
                        tfPhoneNumber.setText("00" + code);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
        });

//        try{
//        File file = new File("coord.txt"); 
//  
//  BufferedReader br = new BufferedReader(new FileReader(file)); 
//            try {
//                System.out.println(br.readLine());
//                tfAddress.setText(userAddress.toString());
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
//        } catch (FileNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }
        cbRole.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbRole.getValue() == "Trainer" || cbRole.getValue() == "Repairer") {
                    avatar1.setVisible(true);
                    pdfPath.setVisible(true);
                } else {
                    try {
                        avatar1.setVisible(false);
                        pdfPath.setVisible(false);
                        File fileToDelete = new File("res/uploadedPDF/"+contract);
                        fileToDelete.delete();
                        contract = "";
                        pdfPath.setText("upload your license");
                        avatar1.setImage(new Image(new FileInputStream("res\\images\\uploadPDF.png")));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
        tfFirstName.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (checkAllLetters(s2)) {
                        if (s2.length() >= 3) {
                            lErrorFirstName.setText("");
                            fnRules = true;
                        } else {
                            lErrorFirstName.setText("at least 3 characters");
                            fnRules = false;
                        }
                    } else {
                        lErrorFirstName.setText("only letters accepted");
                        fnRules = false;
                    }
                } else {
                    lErrorFirstName.setText("required field!!");
                    fnRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        tfLastName.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (checkAllLetters(s2)) {
                        if (s2.length() >= 3) {
                            lErrorLastName.setText("");
                            lnRules = true;
                        } else {
                            lErrorLastName.setText("at least 3 characters");
                            lnRules = false;
                        }
                    } else {
                        lErrorLastName.setText("only letters accepted");
                        lnRules = false;
                    }
                } else {
                    lErrorLastName.setText("required field!!");
                    lnRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        tfEmail.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!us.verifyEmail(s2)) {
                    if (!s2.isEmpty()) {
                        if (checkEmail(s2)) {
                            lErrorEmail.setText("");
                            emRules = true;
                        } else {
                            lErrorEmail.setText("enter a valid email");
                            emRules = false;
                        }

                    } else {
                        lErrorEmail.setText("required field!!");
                        emRules = false;
                    }
                } else {
                    lErrorEmail.setText("This email is used");
                    emRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        tfUsername.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (!us.verifyUsername(s2)) {
                        lErrorUsername.setText("");
                        lSuggestions.setText("");
                        unRules = true;
                    } else {
                        lErrorUsername.setText("Username taken");
                        for (Object un : us.suggestUsername(s2)) {
                            suggestions += un;
                            suggestions += " ";
                        }
                        lSuggestions.setText(suggestions);
                        unRules = false;
                    }
                } else {
                    lErrorUsername.setText("required field!!");
                    unRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        passwordField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if ((boolean) checkPw(s2).get(0) && (boolean) checkPw(s2).get(1) && (boolean) checkPw(s2).get(2) && (boolean) checkPw(s2).get(3)) {
//                        lErrorPw.setText("");
                        pwRules = true;
                    }
                } else {
                    checkPw(s2);
                    lErrorPw.setTextFill(Color.web("#ff0000"));
                    lErrorPw.setText("required field!!");
                    pwRules = false;
                }
                if (!s2.equals(passwordFieldRepeat.getText())) {
                    lMatchedPw.setTextFill(Color.web("#ff0000"));
                    lMatchedPw.setText("Passwords don't match");
                    rpwRules = false;
                } else {
                    lMatchedPw.setTextFill(Color.web("#1500ff"));
                    lMatchedPw.setText("Passwords match");
                    rpwRules = true;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        passwordFieldRepeat.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (s2.equals(passwordField.getText())) {
                        lMatchedPw.setText("Passwords match");
                        lMatchedPw.setTextFill(Color.web("#1500ff"));
                        rpwRules = true;
                    } else {
                        lMatchedPw.setTextFill(Color.web("#ff0000"));
                        lMatchedPw.setText("Passwords don't match");
                        rpwRules = false;
                    }

                } else {
                    lMatchedPw.setText("required field!!");
                    rpwRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        tfAddress.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (s2.length() > 5) {
                        lErrorAddress.setText("");
                        adRules = true;
                    } else {
                        lErrorAddress.setText("Please enter a valid address");
                        adRules = false;
                    }
                } else {
                    lErrorAddress.setText("required field!!");
                    adRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        tfPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (!s2.isEmpty()) {
                    if (checkAllDigits(s2)) {
                        if (s2.length() >= 12 && s2.length() <= 20) {
                            lErrorPhoneNumber.setText("");
                            pnRules = true;
                        } else {
                            lErrorPhoneNumber.setText("enter a valid phone number");
                            pnRules = false;
                        }
                    } else {
                        lErrorPhoneNumber.setText("only digits accepted");
                        pnRules = false;
                    }
                } else {
                    lErrorPhoneNumber.setText("required field!!");
                    pnRules = false;
                }
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);
            }
        });

        //ckbRules=ckbTerms.isSelected();
        ckbTerms.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                ckbRules = newValue;
                btSignUp.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules || !ckbRules);

            }
        });

        tfAddress.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
//            try {
////                FXMLLoader loader = new FXMLLoader(getClass().getResource("clientAddress.fxml"));
////            Parent root1 = (Parent) loader.load();
////    Stage stage = new Stage();
////    stage.initModality(Modality.APPLICATION_MODAL);
////    stage.initStyle(StageStyle.UNDECORATED);
////    stage.setTitle("MAP");
////    stage.setScene(new Scene(root1));  
////    stage.show();
//            } catch (IOException ex) {
//                Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
//            }
                } else {
                    System.out.println("Textfield out focus");
                }
            }
        });
//        ClientAddressController cac = new ClientAddressController();
//        tfAddress.setText(cac.getUserAddress().toString());

        cbRole.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                // choice box popup is now displayed
            } else {
                // choice box popup is now hidden
            }
        });

    }

    @FXML
    private void register(ActionEvent event) {

        if (!realEmailValidation.isAddressValid(tfEmail.getText())) {
            lErrorEmail.setText("FAKE email address!!!");
            ShowNotification notif = new ShowNotification();
            notif.showError("Fake Email Address!!", "Please enter a real Email Address!!");
        } else if (cbRole.getValue() != "Client" && contract.isEmpty()) {
            ShowNotification notif = new ShowNotification();
            notif.showError("ERROR !", "As a " + cbRole.getValue() + " you have to upload a lisence!");
        } else {

            ShowNotification notif = new ShowNotification();
            String username = tfUsername.getText();
            String email = tfEmail.getText();
            String password = passwordField.getText();
            String roles = cbRole.getValue();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String address = tfAddress.getText();
            long phoneNumber = Long.parseLong(tfPhoneNumber.getText());
            String role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";
            int confirmed = 1;
            int gender = 1;
            if ("Female".equals(cbGender.getValue())) {
                gender = 0;
            }
            if (cbRole.getValue().equalsIgnoreCase("Trainer")) {
                role = "a:1:{i:0;s:12:\"ROLE_TRAINER\";}";
                confirmed = 0;
            }
            if (cbRole.getValue().equalsIgnoreCase("Repairer")) {
                role = "a:1:{i:0;s:13:\"ROLE_REPAIRER\";}";
                confirmed = 0;
            }

            try {

                User u = new User(username, email, password, role, firstName, lastName, address, phoneNumber, picture, gender, contract, confirmed);
                us.addUser(u);
                picture ="";
                contract="";
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/Login.fxml"));
                Parent root = loader.load();
                LoginController sic = loader.getController();
                sic.setTextEmail(email);

                tfAddress.getScene().setRoot(root);

                // SMTP server information
                String host = "smtp.gmail.com";
                String port = "587";
                String mailFrom = "huntkingdomteam@gmail.com";
                String passwordMail = "hk2020..";

                // outgoing message information
                String mailTo = email;
                String subject = "Welcome";

                // message contains HTML markups
                EmailBody eb = new EmailBody();
                
                String message = eb.createEmailBody("WELCOME " + tfUsername.getText() + " TO OUR FAMILY", "We are delignted to have you among us", "YOUR ACCOUNT IS NOW ACTIVE", "Your "
                        + "huntkingdom registration was successful! You can now log into huntkingdom with your email address and password through our website, "
                        + "desktop application or android application");
                
                if(!cbRole.getValue().equals("Client")){
                    message = eb.createEmailBody("Thank you " + tfUsername.getText() + " for your application", "We will be delignted to have you among us", "YOUR APPLICATION IS UNDER REVIEW NOW",
                            "An Addmin will review your application in the next 24 hours. Keep in touch");
                }
                MailSender mailer = new MailSender();
                mailer.sendHtmlEmail(host, port, mailFrom, passwordMail, mailTo,
                        subject, message);
                System.out.println("Email sent.");

                notif.show("Welcome", "Welcome to our family " + tfUsername.getText());
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        }

    }

    boolean checkAllLetters(String s) {
        if (s == null) {
            return false;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if ((Character.isLetter(s.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    boolean checkAllDigits(String s) {
        if (s == null) {
            return false;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if ((Character.isDigit(s.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (mat.matches()) {
            return true;
        }
        return false;
    }

    ArrayList checkPw(String pw) {
        char ch;
        ArrayList<Boolean> result = new ArrayList();

        result.add(false);
        result.add(false);
        result.add(false);
        result.add(false);

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pw);
        result.set(3, m.find());
        if (m.find()) {
            lSymbol.setTextFill(Color.web("#1500ff"));
        } else {
            lSymbol.setTextFill(Color.web("#ff0000"));
        }

        for (int i = 0; i < pw.length(); i++) {
            ch = pw.charAt(i);
            if (Character.isDigit(ch)) {
                result.set(2, true);
//                lNumber.setTextFill(Color.web("#3ef85a"));

            } else {
//                lNumber.setTextFill(Color.web("#ff0000"));
            }
            if (Character.isUpperCase(ch)) {
                result.set(0, true);
//                lUpperCase.setTextFill(Color.web("#3ef85a"));

            } else {
//                lUpperCase.setTextFill(Color.web("#ff0000"));
            }
            if (Character.isLowerCase(ch)) {
                result.set(1, true);
//                lLowerCase.setTextFill(Color.web("#3ef85a"));
            } else {
//                lLowerCase.setTextFill(Color.web("#ff0000"));
            }
        }

        if (result.get(0)) {
            lUpperCase.setTextFill(Color.web("#1500ff"));
        } else {
            lUpperCase.setTextFill(Color.web("#ff0000"));
        }
        if (result.get(1)) {
            lLowerCase.setTextFill(Color.web("#1500ff"));
        } else {
            lLowerCase.setTextFill(Color.web("#ff0000"));
        }
        if (result.get(2)) {
            lNumber.setTextFill(Color.web("#1500ff"));
        } else {
            lNumber.setTextFill(Color.web("#ff0000"));
        }

        if (pw.length() < 5) {
            lErrorPw.setTextFill(Color.web("#ff0000"));
            lErrorPw.setText("short password");
        }
        if (pw.length() > 4) {
            lErrorPw.setTextFill(Color.web("#1500ff"));
            lErrorPw.setText("good password");
        }

        return result;
    }

//    private void location(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientAddress.fxml"));
//            Parent root1 = (Parent) loader.load();
//            Stage stage = new Stage();
//            stage.setTitle("Map");
//            stage.setScene(new Scene(root1));
//            stage.show();
//            tfAddress.setText(cac.getUserAddress().toString());
//
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
    private Stage stage;

    @FXML
    private Label imagePath;
    @FXML
    private ImageView avatar;

    @FXML
    public void openfile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (!picture.isEmpty()) {
                File fileToDelete = new File("res/uploadedImages/" + picture);
                fileToDelete.delete();
            }
            imagePath.setText(file.getPath().substring(file.getPath().lastIndexOf("\\") + 1, file.getPath().length()));
            File f = new File(file.getPath());

            avatar.setImage(new Image(f.toURI().toString()));
            //imagePath.setVisible(false);
            picture = generateImageName(f.toURI().toString());
            File source = new File(f.toURI().toString().substring(6)); //6 to delete file:
            File dest = new File("res/uploadedImages/" + picture);

            try {
                Files.copy(source.toPath(), dest.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                ShowNotification notif = new ShowNotification();
                notif.showInformation("Success!", "File has been uploaded successfully ");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    @FXML
    private void openpdf() throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your license");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (!contract.isEmpty()) {
                File fileToDelete = new File("res/uploadedPDF/" + contract);
                fileToDelete.delete();
            }
            Image imageAvatar = new Image(new FileInputStream("res\\images\\pdf.png"));
            avatar1.setImage(imageAvatar);
            avatar1.setPreserveRatio(false);
            pdfPath.setText(file.getPath().substring(file.getPath().lastIndexOf("\\") + 1, file.getPath().length()));
            System.out.println("path: " + file.getPath());
            File f = new File(file.getPath());
            contract = generateImageName(f.toURI().toString());

            File source = new File(f.toURI().toString().substring(6));//6 to delete file:
            File dest = new File("res/uploadedPDF/" + contract);

            try {
                Files.copy(source.toPath(), dest.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                ShowNotification notif = new ShowNotification();
                notif.showInformation("Success!", "File has been uploaded successfully ");
            } catch (IOException ex) {
                System.out.println("error uploading pdf file" + ex.getMessage());
            }

        }

    }

    public String generateImageName(String location) {

        boolean test = true;
        StringBuilder sb = new StringBuilder(30);
        while (test) {

            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            for (int i = 0; i < 30; i++) {

                // generate a random number between 
                // 0 to AlphaNumericString variable length 
                int index
                        = (int) (AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb 
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            if (us.verifyGeneratedImageName(sb.toString())) {
                test = true;
                sb = new StringBuilder(30);
            } else {
                test = false;
            }
        }

        return sb.toString() + location.substring(location.length() - 4);
    }

    @FXML
    private void cancel(ActionEvent event) {
        
        if (!contract.isEmpty()) {
                File fileToDelete = new File("res/uploadedPDF/" + contract);
                fileToDelete.delete();
            }
        if (!picture.isEmpty()) {
                File fileToDelete = new File("res/uploadedImages/" + picture);
                fileToDelete.delete();
            }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/Login.fxml"));
            Parent root = loader.load();

            tfAddress.getScene().setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getCause());
            System.out.println(ex.getClass());
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void dragImage(DragEvent event) {
    }

    @FXML
    private void dragPFD(DragEvent event) {
    }

}
