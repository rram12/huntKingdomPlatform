/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Competition;
import Services.CompetitionService;
import Utils.MyConnection;
import com.sun.prism.impl.Disposer;
import com.sun.prism.impl.Disposer.Record;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class EventController implements Initializable {
    
    @FXML
    private AnchorPane mainpane;
    
    @FXML
    private TextField search;
    
    @FXML
    private TableColumn<Competition, String> Categorie;

    @FXML
    private TableColumn<Competition, Date> DateFin;

    @FXML
    private TableColumn<Competition, Integer> nbParticipants;

    @FXML
    private TableColumn<Competition, String> Lieu;

    @FXML
    private TableColumn<Competition, Integer> Id;

    @FXML
    private TableColumn<Competition, Date> DateDebut;

    @FXML
    private TableColumn<Competition, String> Nom;
    @FXML
    TableColumn action;

    @FXML
    private TableView<Competition> table;
     @FXML
    private Button addCompetition;
     
     
    @FXML
    private TextField np;

    @FXML
    private Button edit;

    @FXML
    private TextField lu;

    @FXML
    private TextField es;

    @FXML
    private TextField dt;

    @FXML
    private ComboBox<String> cy;
    ObservableList<String>list = FXCollections.observableArrayList("Hunting","Fishing");

    @FXML
    private TextField nm;
    private int current_id;

    MyConnection mc = MyConnection.getInstance();
    CompetitionService ps = new CompetitionService();
    List<Competition> mylist = new ArrayList();
    public ObservableList<Competition> c = FXCollections.observableArrayList(
            ps.afficher()
    );
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FilteredList<Competition> filteredData = new FilteredList<>(c, e -> true);
        search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Competition>) Competition -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (Competition.getLieu().toLowerCase().contains(lower)||Competition.getNom().toLowerCase().contains(lower)||String.valueOf(Competition.getNbParticipants()).contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Competition> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        
        cy.setItems(list);
//        CompetitionService ps= new CompetitionService();
//        ArrayList<Competition> c = new ArrayList<>();
//        c=(ArrayList<Competition>) ps.afficher();
//        ObservableList<Competition> obsl = FXCollections.observableArrayList(c);
       
        table.setItems(c);
        Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        DateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        DateFin.setCellValueFactory(new PropertyValueFactory<>("DateFin"));
        Lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        nbParticipants.setCellValueFactory(new PropertyValueFactory<>("nbParticipants"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        
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
                TableRow<Competition> row = new TableRow<>();
                Competition rowData = row.getItem();
                /**
                 * fill the fields with the selected data *
                 */
                
                nm.setText(rowData.getNom());
                dt.setText(rowData.getDateDebut().toString());
                es.setText(rowData.getDateFin().toString());
                cy.setValue(rowData.getCategorie());
                lu.setText(rowData.getLieu());
                String nbPart=Integer.toString(rowData.getNbParticipants());
                np.setText(nbPart);
                current_id = rowData.getId();
                 edit.setVisible(true);

            }
        });
        /**
         * ** double click event **
         */
        table.setRowFactory(tv -> {
            TableRow<Competition> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Competition rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    nm.setText(rowData.getNom());
                dt.setText(rowData.getDateDebut().toString());
                es.setText(rowData.getDateFin().toString());
                cy.setValue(rowData.getCategorie());
                lu.setText(rowData.getLieu());
                String nbPart=Integer.toString(rowData.getNbParticipants());
                np.setText(nbPart);
                current_id = rowData.getId();
                 edit.setVisible(true);
                }
            });
            return row;
        });
    }    
      public void goToAdd(ActionEvent event) throws IOException {
//          Stage primaryStage = new Stage();
//          Parent root = FXMLLoader.load(getClass().getResource("/Gui/AdminHome.fxml"));
//           Scene scene = new Scene(root);
//           primaryStage.setTitle("Add Competition");
//           primaryStage.setScene(scene);
//           primaryStage.show();
//            Stage stage = (Stage) addCompetition.getScene().getWindow();
//        stage.close();
          AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/addCompetition.fxml"));
          mainpane.getChildren().setAll(pane);
    }

      public void updateCompetition(ActionEvent event) {
          MyConnection mc = MyConnection.getInstance();
        CompetitionService ps = new CompetitionService();
        Date dateD=Date.valueOf(dt.getText());
        Date dateF=Date.valueOf(es.getText());
        Competition p = new Competition(current_id, cy.getValue(), nm.getText(), lu.getText(), parseInt(np.getText()),dateD,dateF);
        ps.updateCompetition(p);
        /**
         * refreshing the table view *
         */
        
         c.clear();
        c = FXCollections.observableArrayList(
                ps.afficher()
        );
        table.setItems(c);

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
                        Competition currentComp = (Competition) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the tableView
                        c.remove(currentComp);
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        CompetitionService ps = new CompetitionService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        ps.deleteCompetition(currentComp.getId());
         
                        

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
      

      
    }    
    

