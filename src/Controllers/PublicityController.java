/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Publicity;
import Services.PublicityService;
import Utils.MyConnection;
import com.sun.prism.impl.Disposer;
import com.sun.prism.impl.Disposer.Record;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PublicityController implements Initializable {

    @FXML
    private TableColumn<Publicity, Integer> price;

    @FXML
    private TableColumn<Publicity, Date> ends;

    @FXML
    private TableColumn<Publicity, String> description;

    @FXML
    private AnchorPane mainpane;
    @FXML
    private TextField search;

    @FXML
    private TableColumn<Publicity, String> company;

    @FXML
    private TableColumn<Publicity, Integer> id;

    @FXML
    private TableColumn<Publicity, Date> starts;

    @FXML
    private TableColumn<Publicity, String> title;
    
    @FXML
    TableColumn action;

    @FXML
    private TableView<Publicity> table;   
    
     @FXML
    private TextField ss;

     @FXML
    private TextField descrip;

    @FXML
    private TextField titr;
    
    @FXML
    private ImageView imageView;

    @FXML
    private Button update;
    
     @FXML
    private Group modif;

    @FXML
    private TextField es;
    
    @FXML
    private TextField compagnie;

    @FXML
    private Button chooserFile;

    @FXML
    private TextField pe;
    
    @FXML
    private TextField listView;
    
    private int current_id;
    
    private String absolutePath;
    
    
     public void chooseFileAction() {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.clear();
            listView.setText(selectedFile.getName());
            absolutePath = selectedFile.getAbsolutePath();
            /**
             * update the imageView*
             */
            try {
                Image images = new Image(new FileInputStream(absolutePath));
                imageView.setImage(images);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }

        } else {
            System.out.println("file is not valid !");
        }
    }
 
     MyConnection mc = MyConnection.getInstance();
    PublicityService ps = new PublicityService();
    List<Publicity> mylist = new ArrayList();
    public ObservableList<Publicity> pub = FXCollections.observableArrayList(
            ps.afficher()
             );
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         FilteredList<Publicity> filteredData = new FilteredList<>(pub, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Publicity>) Publicity -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (Publicity.getTitre().toLowerCase().contains(lower)||Publicity.getCompagnie().toLowerCase().contains(lower)||String.valueOf(Publicity.getPrix()).contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Publicity> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
          table.setItems(pub);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        starts.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        ends.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        company.setCellValueFactory(new PropertyValueFactory<>("compagnie"));
          price.setCellValueFactory(new PropertyValueFactory<>("prix"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        title.setCellValueFactory(new PropertyValueFactory<>("titre"));
        
        action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        action.setCellFactory(
                new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        
        table.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                Publicity rowData = table.getSelectionModel().getSelectedItem();
                /**
                 * fill the fields with the selected data *
                 */

                ss.setText(rowData.getDateDebut().toString());
                es.setText(rowData.getDateFin().toString());
                compagnie.setText(rowData.getDescription());
                pe.setText(Float.toString(rowData.getPrix()));
                descrip.setText(rowData.getDescription());
                titr.setText(rowData.getTitre());
                absolutePath = rowData.getImage();
                listView.clear();
                listView.setText(absolutePath.substring(absolutePath.lastIndexOf(("\\")) + 1));
                current_id = rowData.getId();
                try {
                    Image image = new Image(new FileInputStream(absolutePath));
                    imageView.setImage(image);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                }
                modif.setVisible(true);

            }
        });
        /**
         * ** double click event **
         */
        table.setRowFactory(tv -> {
            TableRow<Publicity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Publicity rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    ss.setText(rowData.getDateDebut().toString());
                    es.setText(rowData.getDateFin().toString());
                    compagnie.setText(rowData.getDescription());
                    pe.setText(Float.toString(rowData.getPrix()));
                    descrip.setText(rowData.getDescription());
                    titr.setText(rowData.getTitre());
                    absolutePath = rowData.getImage();
                    listView.clear();
                    listView.setText(absolutePath.substring(absolutePath.lastIndexOf(("\\")) + 1));
                    current_id = rowData.getId();
                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        imageView.setImage(image);
                    } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                    }
                    modif.setVisible(true);
                }
            });
            return row;
        }); 
        
      
    }    
    
     public void goToAddP(ActionEvent event) throws IOException {

          AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addPublicity.fxml"));
          mainpane.getChildren().setAll(pane);
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
                    alert.setTitle("Validate Fields");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        // get Selected Item
                        Publicity currentPub = (Publicity) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the tableView
                        pub.remove(currentPub);
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        PublicityService ps = new PublicityService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        ps.deletePublicity(currentPub.getId());
         
                        

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
      public void updatePublicity(ActionEvent event) {
        MyConnection mc = MyConnection.getInstance();
        PublicityService ps = new PublicityService();
        float price=Float.parseFloat(pe.getText());
        Date dateDs=Date.valueOf(ss.getText());
        Date dateFe=Date.valueOf(es.getText());
        Publicity p = new Publicity(current_id,dateDs,dateFe,compagnie.getText(),titr.getText(),Float.parseFloat(pe.getText()), descrip.getText(),absolutePath);
        ps.updatePublicity(p);
        pub.clear();
        pub= FXCollections.observableArrayList(ps.afficher());
        table.setItems(pub);

    } 
      
      

    
}
