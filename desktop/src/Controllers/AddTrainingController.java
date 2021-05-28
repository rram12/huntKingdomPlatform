/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Animal;
import Entities.Entrainement;
import Entities.Produit;
import Services.AnimalService;
import Services.ProduitService;
import Services.TrainingService;
import Services.UserService;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class AddTrainingController implements Initializable {

    @FXML
    private ComboBox<String> categorie;
    @FXML
    private TextField nh;
    @FXML
    private DatePicker dT;
    @FXML
    private TextField prix;
    private TextField lieu;
    @FXML
    private ComboBox<String> animal;
    @FXML
    private ComboBox<String> produit;

    //private List<Animal> animals = new ArrayList<Animal>();
    final ObservableList options = FXCollections.observableArrayList();
    final ObservableList produits = FXCollections.observableArrayList();
    @FXML
    private Button addbtn;
    @FXML
    private AnchorPane form;
    Date actuelle = new Date();
//         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String dat = dateFormat.format(actuelle);
//       Calendar c= Calendar.getInstance();
//       String dpick=dT.toString();

    @FXML
    private ScrollPane scrollAnimals;
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<String> comboPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();
        Services.ProduitService SP = new ProduitService();
        categorie.setItems(FXCollections.observableArrayList("Hunting", "Fishing"));
        comboPlace.setItems(FXCollections.observableArrayList("Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba", "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"));

        categorie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                options.clear();
                List<Animal> animals = SA.getAnimalsCategory(categorie.getValue());
                
                for (int i = 0; i < animals.size(); i++) {
                    options.add(animals.get(i).getNom());
                }
            }
        });
        try {
            List<Produit> produitt = SP.getProduit();
            for (int i = 0; i < produitt.size(); i++) {
                produits.add(produitt.get(i).getLib_prod());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vbox.getChildren().clear();
        animal.setItems(options);
        produit.setItems(produits);
        prix.setVisible(false);
        Date today = new Date();
        Calendar cal = Calendar.getInstance();

        int m = cal.get(Calendar.MONTH);
        //System.out.println(m);
        List<Animal> animals = SA.getAnimalsOfMonth(m + 1);

        afficher(animals);
    }

    @FXML
    private void AddTrainingAction(ActionEvent event) throws SQLException, IOException, ParseException {

        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();
        Services.ProduitService SP = new ProduitService();
        //Services.UserService SU = new UserService();
        int UserId =  LoginController.getInstance().getLoggedUser().getId();

        //String categorieComb=categorie.valueProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %s", categorie.getValue()).println());
        if (controleDeSaisi()) {
            String categorieComb = categorie.getValue();
            String animalComb = animal.getValue();
            String produitComb = produit.getValue();

            int idAnimal = SA.getAnimalTraining(animalComb);
            int idProduit = SP.getProduitTraining(produitComb);

            int p = 200;
            String answer = String.valueOf(p * (Integer.parseInt(nh.getText().trim())));
            prix.setText(answer);

            Entrainement e = new Entrainement(categorieComb, parseInt(nh.getText()), java.sql.Date.valueOf(dT.getValue().toString()), Double.parseDouble(prix.getText()), comboPlace.getValue(), UserId, idAnimal, idProduit);
            showAlert(Alert.AlertType.INFORMATION, "", "", "Training Added Successfully !");
            try {
                SEntr.addTraining(e);
                AnchorPane main = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                form.getChildren().setAll(main);

            } catch (SQLException ex) {
                Logger.getLogger(AddTrainingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    private boolean controleDeSaisi() throws ParseException {
        String format = "dd/MM/yyyy";

        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
        Date date = new Date();

        
       
        if (nh.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Please fill in all the fields !");
            return false;
        } else {

            if (!Pattern.matches("^[0-9]*$", nh.getText())) {
                showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Check number of hours !");
                nh.requestFocus();
                nh.selectEnd();
                return false;
            }
             try {
            Date d= formater.parse(formater.format(date));
            System.out.println(formater.format(d));
              Date datee;
            datee = java.sql.Date.valueOf(dT.getValue());
            if(d.compareTo(datee)==1) {
                 showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Date > System Date !");
                dT.requestFocus();
                
                return false;
            } 
            }
catch (ParseException ex) {
            Logger.getLogger(AddTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
//            if(dT.getValue().==1)
//            {
//                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez data  !");
//                nh.requestFocus();
//                nh.selectEnd();
//                return false;
//            } 
        }

        return true;
    }

    public void afficher(List<Animal> animals) {

//        Date aujourdhui = new Date();
//SimpleDateFormat formater = null;
//    formater = new SimpleDateFormat("dd/MM/yy");
//    LocalDate currentDate = LocalDate.now();
//    Month m = currentDate.getMonth();
//    System.out.println(m);
        for (int i = 0; i < animals.size(); i++) {
            Animal current = animals.get(i);
            AnimalService SA = new AnimalService();
//            int DebutSaison = current.getDebutSaison() - 1;
//            if (DebutSaison == m) {
            //System.out.println("Animals OF this Month : " + animals.get(i).getNom());
            Pane pane = new Pane();
            pane.setPrefWidth(200);
            pane.setPrefHeight(200);
            pane.setLayoutX(0);
            pane.setLayoutY(0);

            final ImageView animalImage = new ImageView();

            animalImage.setLayoutX(52);
            animalImage.setLayoutY(21);
            animalImage.setFitHeight(157);
            animalImage.setFitWidth(251);
                                       
            Image animal = new Image("file:E:/roadToInfini/java/projet/FinalGit/HuntKingdom/src/Uploads/" + current.getImage_animal());
            animalImage.setImage(animal);

            pane.getChildren().add(animalImage);

            Label nom = new Label();
            nom.setTextFill(Color.WHITE);
            nom.setStyle("-fx-font-size: 15px;-fx-background-color:#fea526");
            //categorie.setStyle("-fx-font-size: 20px");
            nom.setText(current.getNom());
            nom.setLayoutX(36);
            nom.setLayoutY(33);
            nom.setRotate(-39.8);
            pane.getChildren().add(nom);

            vbox.getChildren().add(pane);
            vbox.setStyle("-fx-background-color: transparent;");
            scrollAnimals.setStyle("-fx-background-color: transparent;");
        }

    }

}
