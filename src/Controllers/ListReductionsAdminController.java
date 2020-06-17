/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Produits;
import Entities.Promotion;
import Services.ProduitService;
import Services.PromotionService;
import Utils.MyConnection;
import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class ListReductionsAdminController implements Initializable {

    @FXML
    TableView<Promotion> table;
    @FXML
    TableColumn<Promotion, Integer> id;
    @FXML
    TableColumn<Promotion, Double> rate;
    @FXML
    TableColumn<Promotion, Date> start;
    @FXML
    TableColumn<Promotion, Date> end;
    @FXML
    TableColumn col_action;
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField rateText;
    @FXML
    private DatePicker endDateText;
    private int current_id;
    MyConnection mc = MyConnection.getInstance();
    PromotionService ps = new PromotionService();
    public ObservableList<Promotion> list = FXCollections.observableArrayList(
            ps.afficherPromotions()
    );

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        rate.setCellValueFactory(new PropertyValueFactory<>("taux"));
        start.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        end.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        col_action.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });

        table.setItems(list);

        table.setRowFactory(tv -> {
            TableRow<Promotion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Promotion rowData = row.getItem();
                    rateText.setText(Double.toString(rowData.getTaux()));

                    Instant instant = Instant.ofEpochMilli((rowData.getDateFin()).getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    current_id = rowData.getId();
                    endDateText.setValue(localDate);

                }
            });
            return row;
        });
        table.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {

                Promotion rowData = table.getSelectionModel().getSelectedItem();
                rateText.setText(Double.toString(rowData.getTaux()));
                current_id = rowData.getId();
                Instant instant = Instant.ofEpochMilli((rowData.getDateFin()).getTime());
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                LocalDate localDate = localDateTime.toLocalDate();

                endDateText.setValue(localDate);

            }
        });
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
                        Promotion currentPromotion = (Promotion) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the tableView
                        list.remove(currentPromotion);
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        PromotionService ps = new PromotionService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        ps.supprimerPromotion(currentPromotion.getId());
                        ProduitService pros = new ProduitService();
                        pros.deletePromotion(currentPromotion.getId());

                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton.getStyleClass().add("btn");
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    public void OnReturnAction() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Shop.fxml"));
        mainPane.getChildren().setAll(pane);

    }

    @FXML
    void editPromotionAction(ActionEvent event) {
        MyConnection mc = MyConnection.getInstance();
        PromotionService ps = new PromotionService();
        ZoneId defaultZoneId = ZoneId.systemDefault();
		
        
        //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from( endDateText.getValue().atStartOfDay(defaultZoneId).toInstant());
        Double taux = Double.parseDouble(rateText.getText());
        Promotion p = new Promotion(current_id,taux ,date);
        ps.modifierPromotion(p);
        /**
         * refreshing the table view *
         */
        list.clear();
        list = FXCollections.observableArrayList(
                ps.afficherPromotions()
        );
        table.setItems(list);
            ProduitService prs = new ProduitService();
            prs.ReductPiece(current_id,taux);
       

    }

}
