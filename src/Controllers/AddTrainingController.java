/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.ListAnimalAdminController.showAlert;
import Entities.Animal;
import Entities.Entrainement;
import Entities.Produit;
import Services.AnimalService;
import Services.ProduitService;
import Services.TrainingService;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    private TextField lieu;
    @FXML
    private ComboBox<String> animal;
    @FXML
    private ComboBox<String> produit;
    
    //private List<Animal> animals = new ArrayList<Animal>();
    final ObservableList options=FXCollections.observableArrayList();
    final ObservableList produits=FXCollections.observableArrayList();
    @FXML
    private Button addbtn;
    @FXML
    private AnchorPane form;
//    Date actuelle = new Date();
//         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String dat = dateFormat.format(actuelle);
//       Calendar c= Calendar.getInstance();
//       String dpick=dT.toString();
       
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Services.TrainingService SEntr = new TrainingService();
         Services.AnimalService SA = new AnimalService();
         Services.ProduitService SP = new ProduitService();
         
        try {
            List<Animal> animals = SA.getAnimals();
           
            List<Produit> produitt = SP.getProduit();
            
            for(int i=0;i<animals.size();i++)
         {
             options.add(animals.get(i).getNom());
         }
            for(int i=0;i<produitt.size();i++)
         {
             produits.add(produitt.get(i).getLib_prod());
         }
        } catch (SQLException ex) {
            Logger.getLogger(AddTrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        categorie.setItems(FXCollections.observableArrayList("Hunting","Fishing"));
         animal.setItems(options);
         produit.setItems(produits);
//         prix.setEditable(false);
//         double tot = 200 * Integer.parseInt(nh.getText());
//         prix.setText(Double.toString(tot));
        
         
        
        
        
    }   

    @FXML
    private void AddTrainingAction(ActionEvent event) throws SQLException, IOException {
      
        Services.TrainingService SEntr = new TrainingService();
         Services.AnimalService SA = new AnimalService();
         Services.ProduitService SP = new ProduitService();
         //String categorieComb=categorie.valueProperty().addListener(observable -> System.out.printf("Valeur sélectionnée: %s", categorie.getValue()).println());
           if(controleDeSaisi())
             {
         String categorieComb=categorie.getValue();
         String animalComb=animal.getValue();
         String produitComb=produit.getValue();
        
            int idAnimal=SA.getAnimalTraining(animalComb);
            int idProduit=SP.getProduitTraining(produitComb);
       
         
        //categorieComb = categorie.getSelectionModel().selectedIndexProperty().addListener(observable -> System.out.printf("Indice sélectionné: %d", categorie.getSelectionModel().getSelectedIndex()).println());
         LocalDate dTrainiing=dT.getValue();
         //Entrainement e = new Entrainement(categorieComb,nh.getText().,dTrainiing,prix.getText(),lieu.getText(),5,1,2);
         Entrainement e = new Entrainement(categorieComb, parseInt(nh.getText()), java.sql.Date.valueOf(dT.getValue().toString()), Double.parseDouble(prix.getText()),lieu.getText(), 5, idAnimal, idProduit);
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
    private boolean controleDeSaisi() {
        
        if (nh.getText().isEmpty() 
                || lieu.getText().isEmpty()  ) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

           if (!Pattern.matches("^[\\p{L} .'-]+$", lieu.getText())) {
               showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez champ lieu ! ");
                lieu.requestFocus();
                lieu.selectEnd();
                return false;
            }

            

            if (!Pattern.matches("^[0-9]*$", nh.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez nombre des heures  !");
                nh.requestFocus();
                nh.selectEnd();
                return false;
            }
            /*if(dpick.compareTo(dat)==0)
            {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez data  !");
                nh.requestFocus();
                nh.selectEnd();
                return false;
            } */
        }
        
        return true;
    }
         
         
    
}
