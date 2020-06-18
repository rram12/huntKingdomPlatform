/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.User;
import Utils.BCrypt;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author ramzi.raddaoui@esprit.tn
 */
public class UpdateProfileController implements Initializable {

    private WebEngine engine;
    JSONParser parser = new JSONParser();
    ShowNotification notif = new ShowNotification();

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
    private ChoiceBox<String> cbGender;
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

    boolean fnRules = true;
    boolean lnRules = true;
    boolean emRules = true;
    boolean unRules = true;
    boolean pwRules = false;
    boolean rpwRules = false;
    boolean adRules = true;
    boolean pnRules = true;

    User currentUser = LoginController.getInstance().getLoggedUser();

    String suggestions = "";
    String contract = currentUser.getContract();
    String picture = currentUser.getPicture();

    UserService us = new UserService();

    String ad = "";
    @FXML
    private WebView wbMap;
//    private Label pdfPath;
//    private ImageView avatar1;

    @FXML
    private PasswordField tfOldPw;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btBack;
    @FXML
    private AnchorPane mainpane;

    private Address onValueChange(String data) {

        NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI();
        System.out.println(Double.parseDouble(data.substring(data.indexOf(" ") + 1)));
        System.out.println(Double.parseDouble(data.substring(0, data.indexOf(" "))));
        System.out.println(nominatim1.getAdress(Double.parseDouble(data.substring(data.indexOf(" ") + 1)), Double.parseDouble(data.substring(0, data.indexOf(" ")))));
        return nominatim1.getAdress(Double.parseDouble(data.substring(data.indexOf(" ") + 1)), Double.parseDouble(data.substring(0, data.indexOf(" "))));

    }

    Address userAddress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        

        cbGender.getItems().add("Male");
        cbGender.getItems().add("Female");
        if (currentUser.getGender() == 1) {
            cbGender.setValue("Male");
        } else {
            cbGender.setValue("Female");
        }
        cbRole.getItems().add("Client");
        cbRole.getItems().add("Trainer");
        cbRole.getItems().add("Repairer");
        String role = "";
        boolean test = false;
        if (currentUser.getRoles().contains("lient")) {
            role = "Client";
            test = false;
        }
        if (currentUser.getRoles().contains("rainer")) {
            role = "Trainer";
            test = true;
        }
        if (currentUser.getRoles().contains("pairer")) {
            role = "Repairer";
            test = true;
        }

        cbRole.setValue(role);
//        avatar1.setVisible(test);
//        pdfPath.setVisible(test);

  //      cbRole.setVisible(false);

        tfAddress.setText(currentUser.getAddress());
        tfEmail.setText(currentUser.getEmail());
        tfFirstName.setText(currentUser.getFirstName());
        tfLastName.setText(currentUser.getLastName());
        tfUsername.setText(currentUser.getUsername());
        tfPhoneNumber.setText("00" + currentUser.getPhoneNumber());
        tfAddress.setText(currentUser.getAddress());
        try {
            avatar.setImage(new Image(new FileInputStream("res\\uploadedImages\\" + currentUser.getPicture())));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getCause());
            System.out.println(ex.getClass());
            System.out.println(ex.getMessage());
        }

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

//        cbRole.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
//            if (wasShowing) {
//                if (cbRole.getValue() == "Trainer" || cbRole.getValue() == "Repairer") {
////                    avatar1.setVisible(true);
//                    pdfPath.setVisible(true);
//                } else {
//                    avatar1.setVisible(false);
//                    pdfPath.setVisible(false);
//                }
//            }
//        });

        btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);

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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                } else if (us.verifyEmail(s2) && !currentUser.getEmail().equals(s2)) {
                    lErrorEmail.setText("This email is used");
                    emRules = false;
                } else if (currentUser.getEmail().equals(s2)) {
                    lErrorEmail.setText("");
                    emRules = true;
                }
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                if (s2.equals(currentUser.getUsername())) {
                    lErrorUsername.setText("");
                    lSuggestions.setText("");
                    unRules = true;
                }
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
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
                btUpdate.setDisable(!fnRules || !lnRules || !emRules || !unRules || !pwRules || !rpwRules || !adRules || !pnRules);
            }
        });

        cbRole.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (isNowShowing) {
                // choice box popup is now displayed
            } else {
                // choice box popup is now hidden
            }
        });

    }

    @FXML
    private void updateProfile(ActionEvent event) {
        if (!realEmailValidation.isAddressValid(tfEmail.getText())) {
            lErrorEmail.setText("FAKE email address!!!");
        } else if (BCrypt.checkpw(tfOldPw.getText(), currentUser.getPassword())) {
            String username = tfUsername.getText();
            String email = tfEmail.getText();
            String password = passwordField.getText();
            String roles = cbRole.getValue();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String address = tfAddress.getText();
            long phoneNumber = Long.parseLong(tfPhoneNumber.getText());
            String role = currentUser.getRoles();
            int confirmed = 1;
            int gender = 1;
            if ("Female".equals(cbGender.getValue())) {
                gender = 0;
            }
            User u = new User(currentUser.getId(), username, email, password, role, firstName,
                    lastName, address, phoneNumber, picture, gender, contract, confirmed);
            boolean res = us.updateUser(u);
            System.out.println(res);
            notif.show("Update confirmed", "Your profile was updated successfully");

            LoginController.getInstance().setLoggedUser(u);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Parent root = loader.load();
                HomeController sic = loader.getController();

                tfAddress.getScene().setRoot(root);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (!BCrypt.checkpw(tfOldPw.getText(), currentUser.getPassword())) {
            notif.showError("Error!", "Please verify your password");
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

    private void location(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientAddress.fxml"));
            Parent root1 = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Map");
            stage.setScene(new Scene(root1));
            stage.show();
//            ClientAddressController cac = new ClientAddressController();
//if(cac.getUserAddress()==null)
//    tfAddress.setText("Gafsa Tunisia");
//        
//else
//            tfAddress.setText(cac.getUserAddress().toString());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Stage stage;

    @FXML
    private Label imagePath;
    @FXML
    private ImageView avatar;

    @FXML
    public void openfile() {
        imagePath.setText(currentUser.getPicture());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            imagePath.setText(file.getPath());
            File f = new File(file.getPath());
            avatar.setImage(new Image(f.toURI().toString()));
            imagePath.setVisible(false);
            picture = generateImageName(f.toURI().toString());
            File source = new File(f.toURI().toString().substring(6));
            File dest = new File("res/uploadedImages/" + picture);
//            System.out.println(f.toURI().toString());
//            System.out.println(generatedName);

            try {
                Files.copy(source.toPath(), dest.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

//    private void openpdf() {
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose your license");
//        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
////            pdfPath.setText(file.getPath());
//            File f = new File(file.getPath());
////            pdfPath.setVisible(false);
//            contract = generateImageName(f.toURI().toString());
//            File source = new File(f.toURI().toString().substring(6));
//            File dest = new File("res/uploadedPDF/" + contract);
////            System.out.println(f.toURI().toString());
////            System.out.println(generatedName);
//
//            try {
//                Files.copy(source.toPath(), dest.toPath(),
//                        StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }
//
//        }
//
//    }

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
    private void backToHome(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Home.fxml"));
            mainpane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(UpdateProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
