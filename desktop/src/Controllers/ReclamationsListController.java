/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Reclamation;
import Entities.User;
import Utils.Profanity;
import Services.ReclamationService;
import Utils.ShowNotification;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ramzi.raddaoui@esprit.tn
 */
public class ReclamationsListController implements Initializable {

    ReclamationService rc = new ReclamationService();
    User currentUser = LoginController.getInstance().getLoggedUser();
    boolean titleRules = false;
    boolean descriptionRules = false;
    String orderCriteria = "id";
    String searchCriteria = "id";

    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btAdd;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btClear;
    @FXML
    private TableView<Reclamation> table;
    @FXML
    private Button btBack;
    @FXML
    private CheckBox cbOnlyActiveRec;
    @FXML
    private Label lErrorTitle;
    @FXML
    private Label lErrorDescription;
    @FXML
    private Label lID;
    @FXML
    private Button btDeleteRec;
    @FXML
    private ChoiceBox<String> cbCriteria;
    @FXML
    private TextField tfSearch;
    @FXML
    private ChoiceBox<String> cbCriteria1;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        try {
////            img.setImage(new javafx.scene.image.Image(new FileInputStream("res/images/signin.jpg")));
//        } catch (FileNotFoundException ex) {
//            System.out.println(ex.getMessage());
//        }

        cbCriteria1.getItems().add("ID");
        cbCriteria1.getItems().add("Title");
        cbCriteria1.getItems().add("Description");
        cbCriteria1.getItems().add("Date");
        cbCriteria1.setValue("ID");

        cbCriteria.getItems().add("ID");
        cbCriteria.getItems().add("Title");
        cbCriteria.getItems().add("Description");
        cbCriteria.getItems().add("Date");
        cbCriteria.setValue("ID");

        cbCriteria1.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbCriteria1.getValue() == "Title") {
                    orderCriteria = "title";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));
                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));
                    }
                } else if (cbCriteria1.getValue() == "ID") {
                    orderCriteria = "id";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                } else if (cbCriteria1.getValue() == "Description") {
                    orderCriteria = "descriptionRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                } else if (cbCriteria1.getValue() == "Date") {
                    orderCriteria = "dateRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                }

            }
        });

        cbCriteria.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbCriteria.getValue() == "Title") {
                    searchCriteria = "title";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));
                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));
                    }
                } else if (cbCriteria.getValue() == "ID") {
                    searchCriteria = "id";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                } else if (cbCriteria.getValue() == "Description") {
                    searchCriteria = "descriptionRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                } else if (cbCriteria.getValue() == "Date") {
                    searchCriteria = "dateRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    } else {
                        table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                    }
                }

            }
        });

        Profanity.loadConfigs();
        lID.setVisible(false);
        lID.setText("0");
        btAdd.setDisable(!titleRules || !descriptionRules);
        lErrorDescription.setTextFill(Color.web("#ff0000"));
        lErrorTitle.setTextFill(Color.web("#ff0000"));

        btUpdate.setDisable(!false);
        btDeleteRec.setDisable(!false);
        btAdd.setDisable(!false);
//        btAdd.setVisible(true);
        table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

        TableColumn<Reclamation, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(40);
        idColumn.setPrefWidth(40);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(100);
        titleColumn.setPrefWidth(120);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(100);
        descriptionColumn.setPrefWidth(390);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionRec"));
        descriptionColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(85);
        dateColumn.setPrefWidth(85);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateRec"));
        dateColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, Boolean> handledColumn = new TableColumn<>("Handled");
        handledColumn.setMinWidth(100);
        handledColumn.setPrefWidth(120);
        handledColumn.setCellValueFactory(new PropertyValueFactory<>("handled"));
        handledColumn.setStyle("-fx-alignment: CENTER;");

        handledColumn.setCellFactory(tc -> {
            try {
                final Image handledImage = new Image(new FileInputStream("res\\images\\done.png"));
                final Image notHandledImage = new Image(new FileInputStream("res\\images\\pending.gif"));
                TableCell<Reclamation, Boolean> cell = new TableCell<Reclamation, Boolean>() {
                    private ImageView imageView = new ImageView();
                    
                    @Override
                    protected void updateItem(Boolean active, boolean empty) {
                        super.updateItem(active, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            if (active) {
                                imageView.setImage(handledImage);
                            } else {
                                imageView.setImage(notHandledImage);
                            }
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(80);
                            imageView.setPreserveRatio(false);
                            setGraphic(imageView);
                        }
                    }
                };
                return cell;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ReclamationsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        table.getColumns().addAll(idColumn, titleColumn, descriptionColumn, dateColumn, handledColumn);

        cbOnlyActiveRec.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                } else {
                    table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                }
            }
        });

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Reclamation selectedRec = table.getSelectionModel().getSelectedItem();
                    tfTitle.setText(selectedRec.getTitle());
                    taDescription.setText(selectedRec.getDescriptionRec());
                    lID.setText("" + selectedRec.getId());
                    btUpdate.setDisable(!true);
                    btDeleteRec.setDisable(!true);
                    btAdd.setDisable(!false);
                }
            }
        });

        table.setRowFactory(new Callback<TableView<Reclamation>, TableRow<Reclamation>>() {
            @Override
            public TableRow<Reclamation> call(TableView<Reclamation> tableView2) {
                final TableRow<Reclamation> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index > table.getItems().size()) {
                            table.getSelectionModel().clearSelection();
                            clear();
                            lID.setText("0");
                        }
                    }
                });
                return row;
            }
        });

        btAdd.setOnAction(e -> {
            addRec(e);
            if (cbOnlyActiveRec.isSelected()) {
                table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            } else {
                table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            }
            table.getSelectionModel().select(table.getItems().size() - 1);
        });

        btUpdate.setOnAction(e -> {
            updateRec(e);
            if (cbOnlyActiveRec.isSelected()) {
                table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            } else {
                table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            }
        });

        btDeleteRec.setOnAction(e -> {
            deleteRec();
            if (cbOnlyActiveRec.isSelected()) {
                table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            } else {
                table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

            }
        });

        tfTitle.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {

                if (!s2.isEmpty()) {
                    if (Profanity.badWordsFound(s2).size() > 0) {
                        lErrorTitle.setText("Bad Word detected in the title");

                        titleRules = false;
                    } else if (s2.length() < 10) {
                        lErrorTitle.setText("The title have to be descriptive");

                        titleRules = false;
                    } else {
                        lErrorTitle.setText("");

                        titleRules = true;
                    }

                } else {
                    lErrorTitle.setText("required field!!");
                    titleRules = false;
                }
                if(rc.selectByID(Integer.parseInt(lID.getText())).size()>0){
                btAdd.setDisable(true);
                btUpdate.setDisable(!titleRules || ! descriptionRules);
                }else{
                    btAdd.setDisable(!titleRules || ! descriptionRules);
                btUpdate.setDisable(true);
                }
                btDeleteRec.setDisable(true);
            }
        });

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (cbOnlyActiveRec.isSelected()) {
                    table.setItems(FXCollections.observableArrayList(rc.showNotHandledByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                } else {
                    table.setItems(FXCollections.observableArrayList(rc.showAllByUser(currentUser.getId(), orderCriteria, searchCriteria, tfSearch.getText())));

                }
            }
        });

        taDescription.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {

                if (!s2.isEmpty()) {
                    if (Profanity.badWordsFound(s2).size() > 0) {
                        lErrorDescription.setText("Bad Word detected in the description");

                        descriptionRules = false;
                    } else if (s2.length() < 30) {
                        lErrorDescription.setText("Please describe the problem with more words");

                        descriptionRules = false;
                    } else {
                        lErrorDescription.setText("");

                        descriptionRules = true;
                    }

                } else {
                    lErrorDescription.setText("required field!!");
                    descriptionRules = false;
                }
                if(rc.selectByID(Integer.parseInt(lID.getText())).size()>0){
                btAdd.setDisable(true);
                btUpdate.setDisable(!titleRules || ! descriptionRules);
                }else{
                    btAdd.setDisable(!titleRules || ! descriptionRules);
                btUpdate.setDisable(true);
                }
                btDeleteRec.setDisable(true);
            }
        });

    }

    @FXML
    private void addRec(ActionEvent event) {
        System.out.println("button add pressed");
        Reclamation r = new Reclamation(0, taDescription.getText(),
                new java.sql.Date(System.currentTimeMillis()), tfTitle.getText(), currentUser.getId(), false);
        rc.addRec(r);
        clear();
        ShowNotification notif = new ShowNotification();
        notif.show("Success!!", "Your reclamation has been successfully added!"
                + " An admin will look through it ASAP");
        lErrorDescription.setText("");
        lErrorTitle.setText("");
    }

    @FXML
    public void clear() {
        tfTitle.clear();
        taDescription.clear();
        btUpdate.setDisable(!false);
        btDeleteRec.setDisable(!false);
        btAdd.setDisable(true);
        lErrorDescription.setText("");
        lErrorTitle.setText("");
    }

    private void clear(ActionEvent event) {
        clear();
    }

    @FXML
    private void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));

        try {
            Parent root = loader.load();
            btBack.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void updateRec(ActionEvent event) {
        System.out.println("button update pressed");
        Reclamation r = new Reclamation();
        r = rc.selectByID(Integer.parseInt(lID.getText())).get(0);
        if (r.isHandled()) {
            ShowNotification notif = new ShowNotification();
            notif.showError("Error!!", "A handled reclamation can't be edited!");
        } else {
            r.setDescriptionRec(taDescription.getText());
            r.setTitle(tfTitle.getText());
            rc.updateRec(r);
            clear();
            ShowNotification notif = new ShowNotification();
            notif.show("Success!!", "Your reclamation has been successfully updated!");
        }
    }

    private void deleteRec() {
        rc.deleteRec(Integer.parseInt(lID.getText()));
        clear();
        ShowNotification notif = new ShowNotification();
        notif.show("Success!!", "This reclamation has been successfully deleted!");
    }

}
