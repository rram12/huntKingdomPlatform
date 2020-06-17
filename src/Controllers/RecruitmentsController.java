/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Produits;
import Entities.Promotion;
import Services.UserService;
import Entities.User;
import Services.JavaMail;
import Services.ProduitService;
import Services.PromotionService;
import Utils.MyConnection;
import com.jfoenix.controls.JFXButton;
import com.sun.prism.impl.Disposer;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 *
 * @author asus_pc
 */
public class RecruitmentsController implements Initializable {

    @FXML
    TableView<User> table;
    @FXML
    TableColumn<User, String> first_name;
    @FXML
    TableColumn<User, String> last_name;
    @FXML
    TableColumn<User, String> email;
    @FXML
    TableColumn<User, String> role;
    @FXML
    private ImageView imageView;
    @FXML
    TableColumn<User, String> address;
    @FXML
    TableColumn<User, Long> phone;
    @FXML
    TableColumn<User, Date> date_ajout;
    @FXML
    TableColumn licence;
    @FXML
    TableColumn action1;
    @FXML
    TableColumn action2;

    private String absolutePath;

    MyConnection mc = MyConnection.getInstance();
    UserService ps = new UserService();
    List<User> mylist = new ArrayList();
    public ObservableList<User> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(
                ps.showNotConfirmed()
        );
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        role.setCellValueFactory(new PropertyValueFactory<>("roles"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        action1.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        action1.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        action2.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        action2.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell2();
            }

        });

        licence.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        licence.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell1();
            }

        });

        table.setItems(list);
        table.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    User rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    absolutePath = rowData.getPicture();

                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        imageView.setImage(image);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }

                }
            });
            return row;
        });

    }

    private class ButtonCell extends TableCell<Disposer.Record, Boolean> {

        final JFXButton cellButton = new JFXButton("reject");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    //confirmation alert 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("reject Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        User currentUser = (User) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        UserService us = new UserService();
                        us.deleteUser(currentUser.getId());
                        /**refresh table **/
                        list.clear();
                        list = FXCollections.observableArrayList(
                                ps.showNotConfirmed()
                        );
                        table.setItems(list);
                        try {
                            JavaMail.sendMail(currentUser.getEmail(), 2);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
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
                cellButton.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 10px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: red;"
                    + "    -fx-pref-width: 50;"
                    + "    -fx-text-fill: WHITE;");
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    private class ButtonCell2 extends TableCell<Disposer.Record, Boolean> {

        final JFXButton cellButton2 = new JFXButton("accept");

        ButtonCell2() {

            //Action when the button is pressed
//            cellButton2.setOnAction(new EventHandler<ActionEvent>() {
//
//                @Override
//                public void handle(ActionEvent t) {
//                        User currentUser = (User) ButtonCell2.this.getTableView().getItems().get(ButtonCell2.this.getIndex());
//                        UserService us = new UserService();
//                        us.UpdateConfirmer(currentUser.getId());
//                        /**refresh table **/
//                        list.clear();
//                        list = FXCollections.observableArrayList(
//                                ps.showNotConfirmed()
//                        );
//                        table.setItems(list);
//                        try {
//                            JavaMail.sendMail(currentUser.getEmail(), 1);
//                        } catch (Exception ex) {
//                            System.out.println(ex.getMessage());
//                        }
//                }
//            });

        }
        //Display button if the row is not empty

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton2.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 10px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: green;"
                    + "    -fx-pref-width: 50;"
                    + "    -fx-text-fill: WHITE;");
                setGraphic(cellButton2);
            } else {
                setGraphic(null);
            }
        }
    }

    private class ButtonCell1 extends TableCell<Disposer.Record, Boolean> {

        final JFXButton cellButton1 = new JFXButton("show");

        ButtonCell1() {

            //Action when the button is pressed
            cellButton1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    try {
                        User currentUser = (User) ButtonCell1.this.getTableView().getItems().get(ButtonCell1.this.getIndex());
                        String FilePath = currentUser.getContract();
                        Desktop.getDesktop().open(new java.io.File(FilePath));
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            });

        }
        //Display button if the row is not empty

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                cellButton1.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 10px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: blue;"
                    + "    -fx-pref-width: 50;"
                    + "    -fx-text-fill: WHITE;");
                setGraphic(cellButton1);
            } else {
                setGraphic(null);
            }
        }
    }

}
