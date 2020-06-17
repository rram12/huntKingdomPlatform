/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Animal;
import Services.AnimalService;
import Utils.MyConnection;
import com.sun.prism.impl.Disposer;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
 



/**
 * FXML Controller class
 *
 * @author tibh
 */
public class ListAnimalAdminController implements Initializable {

    @FXML
    private TableView<Animal> tableAnimal;
    @FXML
    private TableColumn<Animal, String> categorie;
    @FXML
    private TableColumn<Animal, String> description;
    @FXML
    private TableColumn<Animal, String> image;
    @FXML
    private TableColumn<Animal, Integer> debut_saison;
    @FXML
    private TableColumn<Animal, Integer> fin_saison;
    
    @FXML
    private Button btnAddAnimal;
    @FXML
    private TableColumn<Animal, String> nom;
    //TableColumn<Animal, Void> tenthCol = new TableColumn<>("Action");
    @FXML
    private TableColumn action;
    
    
    AnimalService SA = new AnimalService();
    List<Animal> mylist = new ArrayList();
    public ObservableList<Animal> list = FXCollections.observableArrayList(
            SA.getAnimals()
    );
    @FXML
    private Button editButton;
    @FXML
    private Button chooserFile;
    @FXML
    private TextField nomtxt;
    @FXML
    private ComboBox<String> categorietxt;
    //ObservableList<String> listcategorie = FXCollections.observableArrayList("Hunting", "Fishing");
    @FXML
    private TextField imagetxt;
    @FXML
    private TextArea descriptiontxt;
    @FXML
    private TextField debut_saisontxt;
    @FXML
    private TextField fin_saisontxt;
    private FileChooser fileChooser;
    private File file;
    private Stage stage;
    @FXML
    private AnchorPane anchorPane;
    private Image imagev;
    private int current_id;
    
    @FXML
    private TextField search;
    
    
    
    
    
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorietxt.setItems(FXCollections.observableArrayList("Hunting","Fishing"));
        AnimalService SA= new AnimalService();
        ArrayList<Animal> a = new ArrayList<>();
        Animal an=new Animal();
            a=(ArrayList<Animal>) SA.getAnimals();
       
        ObservableList<Animal> obsl = FXCollections.observableArrayList(a);
       
      
     
        tableAnimal.setItems(obsl);
      
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        image.setCellValueFactory(new PropertyValueFactory<>("image_animal"));
        
        debut_saison.setCellValueFactory(new PropertyValueFactory<>("debutSaison"));
        fin_saison.setCellValueFactory(new PropertyValueFactory<>("finSaison"));
        
        action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        action.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }
            

        });
       
        fileChooser= new FileChooser();
          fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files","*.*"),
                new FileChooser.ExtensionFilter("images","*.png","*.jpg","*.gif"),
                new FileChooser.ExtensionFilter("Text File","*.txt")
                );
          
           tableAnimal.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                Animal rowData = tableAnimal.getSelectionModel().getSelectedItem();
                /**
                 * fill the fields with the selected data *
                 */
                categorietxt.setValue(rowData.getCategorie());
                nomtxt.setText(rowData.getNom());
                descriptiontxt.setText(rowData.getDescription());
                imagetxt.setText(rowData.getImage_animal());
                String dsaison=Integer.toString(rowData.getDebutSaison());
                String fsaison=Integer.toString(rowData.getFinSaison());
                debut_saisontxt.setText(dsaison);
                fin_saisontxt.setText(fsaison);
                
               
                
                current_id = rowData.getId();
               
                editButton.setVisible(true);
                btnAddAnimal.setVisible(false);

            }
        });
               FilteredList<Animal> filteredData = new FilteredList<>(list, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Animal>) animal -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (animal.getCategorie().toLowerCase().contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Animal> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableAnimal.comparatorProperty());
            tableAnimal.setItems(sortedData);
        });
        
        
    }

   
    @FXML
    public void EditAnimal(ActionEvent event) throws IOException {
        

            MyConnection mc = MyConnection.getInstance();
            AnimalService ps = new AnimalService();
            //Animal a = new Animal(current_id, categorietxt.getValue(),nomtxt.getText(),descriptiontxt.getText(), imagetxt.getText(),debut_saisontxt.getText(),fin_saisontxt.getText());
            Animal a =new Animal(current_id, categorietxt.getValue(), nomtxt.getText(),descriptiontxt.getText(),imagetxt.getText(),parseInt(debut_saisontxt.getText()),parseInt(fin_saisontxt.getText()));
             showAlert(Alert.AlertType.INFORMATION, "", "", "Animal Updated Successfully !");
            ps.updateAnimal(a,current_id);
            AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml")); 
                        anchorPane.getChildren().setAll(redirected);
            /**
             * refreshing the table view *
             */
          
            tableAnimal.setItems(list);

        
    }
    

    @FXML
    private void chooseFileAction(ActionEvent event) {
        stage=(Stage) anchorPane.getScene().getWindow();
        file=fileChooser.showOpenDialog(stage);
        /*try {
            deskTop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(FXMLajouterUserController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        if(file != null)
        {
            //System.out.println(""+file.getAbsolutePath());
            //imagev= new Image(file.getAbsoluteFile().toURI().toString());
           
            imagetxt.setText(file.getAbsolutePath());
        }
    }

    
     private class ButtonCell extends TableCell<Disposer.Record, Boolean> {

        final Button cellButton = new Button("Delete");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    //confirmation alert 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        // get Selected Item
                        Animal currentAnimal = (Animal) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the tableView
                        list.remove(currentAnimal);
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        AnimalService ps = new AnimalService();
                       try {
                        ps.deleteAnimal(currentAnimal.getId()); //hedhi tjarbi3a mokhek yekef hhhhh hel el fxml belehi
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml")); 
                        anchorPane.getChildren().setAll(redirected);
                    } catch (SQLException ex) {
                        Logger.getLogger(ListAnimalAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ListAnimalAdminController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    private void AddAnimalAction(ActionEvent event) throws IOException {
         if(controleDeSaisi())
             {
        Services.AnimalService SA = new AnimalService();
        String categorieComb=categorietxt.getValue();
       
        Animal a =new Animal(categorietxt.getValue(), nomtxt.getText(),descriptiontxt.getText(),imagetxt.getText(),parseInt(debut_saisontxt.getText()),parseInt(fin_saisontxt.getText()));
                  showAlert(Alert.AlertType.INFORMATION, "", "", "Animal Added Successfully !");
                 try {
             
            SA.addAnimal(a);
            
            AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/ListAnimalAdmin.fxml")); 
                        anchorPane.getChildren().setAll(redirected);
            
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

        if (nomtxt.getText().isEmpty() || descriptiontxt.getText().isEmpty() || imagetxt.getText().isEmpty()
                || debut_saisontxt.getText().isEmpty() || fin_saisontxt.getText().isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Please fill in all the fields !");
            return false;
        } else {

           if (!Pattern.matches("^[\\p{L} .'-]+$", nomtxt.getText())) {
               showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Check the animal's name ! ");
                nomtxt.requestFocus();
                nomtxt.selectEnd();
                return false;
            }

            if (!Pattern.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$", descriptiontxt.getText())) {
                showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Check Animal's Description  ! ");
                descriptiontxt.requestFocus();
                descriptiontxt.selectEnd();
                return false;
            }

            if (!Pattern.matches("^[0-9]*$", debut_saisontxt.getText())) {
                showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Check early season  !");
                debut_saisontxt.requestFocus();
                debut_saisontxt.selectEnd();
                return false;
            }
             if (!Pattern.matches("^[0-9]*$", fin_saisontxt.getText())) {
                showAlert(Alert.AlertType.ERROR, "Wrong Data", "Check the data", "Check end of season !");
                fin_saisontxt.requestFocus();
                fin_saisontxt.selectEnd();
                return false;
            }
        }
        return true;
    }
    

    
    
     
    
}
