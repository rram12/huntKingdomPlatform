/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Reclamation;
import Entities.User;
import Utils.EmailBody;
import Utils.MailSender;
import Services.ReclamationService;
import Utils.ShowNotification;
import Services.UserService;
import eu.bitm.NominatimReverseGeocoding.Address;
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
import javafx.scene.control.Pagination;
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
public class AdminReclamationsListController implements Initializable {

    
    //instance of AdminReclamationsListController
    private AdminReclamationsListController currentAdminReclamationsListController;
    
    private static AdminReclamationsListController instance;
    @FXML
    private ImageView bg;
    
    public AdminReclamationsListController() {
        instance = this;
    }
    
    public static AdminReclamationsListController getInstance() {
        return instance;
    }

    public AdminReclamationsListController getCurrentAdminReclamationsListController() {
        return currentAdminReclamationsListController;
    }
    public void setLoggedUser(AdminReclamationsListController arlc) {
        this.currentAdminReclamationsListController = arlc;
    }
    
    
    //global vars declaration
    ReclamationService rc = new ReclamationService();
    UserService us = new UserService();
    boolean titleRules = false;
    boolean descriptionRules = false;
    String orderCriteria = "id";
    String searchCriteria = "id";

    int maxItems = 9;
    int from = 0, to = maxItems;
    
    //fxml declaration

    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btBack;
    @FXML
    private TableView<Reclamation> table;
    @FXML
    private CheckBox cbOnlyActiveRec;
    @FXML
    private Label lErrorTitle;
    @FXML
    private Label lErrorDescription;
    @FXML
    private Label lID;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btDeleteRec;
    @FXML
    private ChoiceBox<String> cbCriteria;
    @FXML
    private TextField tfSearch;
    @FXML
    private ChoiceBox<String> cbCriteria1;
    @FXML
    private Button btHandle;
    @FXML
    private TextArea taResponse;
    @FXML
    private CheckBox cbResponse;
    @FXML
    private Button btClear;
    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            bg.setImage(new Image("images/hunting-and-fishing-wallpaper_2719072.jpg"));
        


        //vars declaration
        lID.setVisible(false);
        btHandle.setDisable(true);
        lErrorDescription.setTextFill(Color.web("#ff0000"));
        lErrorTitle.setTextFill(Color.web("#ff0000"));

        btUpdate.setDisable(!false);
        btDeleteRec.setDisable(!false);

        tfTitle.setEditable(false);
        taDescription.setEditable(false);

        //setting pagination
        pagination.setPageCount(calculateNbrPages());
        pagination.setPageFactory(this::createPage);

        //setting choice boxes
        cbCriteria1.getItems().add("ID");
        cbCriteria1.getItems().add("Title");
        cbCriteria1.getItems().add("Description");
        cbCriteria1.getItems().add("Date");
        cbCriteria1.getItems().add("UserID");
        cbCriteria1.setValue("ID");

        cbCriteria.getItems().add("ID");
        cbCriteria.getItems().add("Title");
        cbCriteria.getItems().add("Description");
        cbCriteria.getItems().add("Date");
        cbCriteria.getItems().add("UserID");
        cbCriteria.setValue("ID");

        //setting the tableView
        TableColumn<Reclamation, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setPrefWidth(75);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(100);
        titleColumn.setPrefWidth(175);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(100);
        descriptionColumn.setPrefWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionRec"));
        descriptionColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setPrefWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateRec"));
        dateColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, Integer> userIdColumn = new TableColumn<>("UserID");
        userIdColumn.setMinWidth(100);
        userIdColumn.setPrefWidth(130);
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userIdColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Reclamation, Boolean> handledColumn = new TableColumn<>("Handled");
        handledColumn.setMinWidth(100);
        handledColumn.setPrefWidth(130);
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
                Logger.getLogger(AdminReclamationsListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });

        table.getColumns().addAll(idColumn, titleColumn, descriptionColumn, dateColumn, handledColumn, userIdColumn);

//listener to sort checkbox
        cbCriteria1.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbCriteria1.getValue() == "Title") {
                    orderCriteria = "title";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria1.getValue() == "ID") {
                    orderCriteria = "id";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria1.getValue() == "Description") {
                    orderCriteria = "descriptionRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria1.getValue() == "Date") {
                    orderCriteria = "dateRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria1.getValue() == "UserID") {
                    orderCriteria = "userId";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                }

            }
        });

        //listener to search checkbox
        cbCriteria.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbCriteria.getValue() == "Title") {
                    searchCriteria = "title";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria.getValue() == "ID") {
                    searchCriteria = "id";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria.getValue() == "Description") {
                    searchCriteria = "descriptionRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria.getValue() == "Date") {
                    searchCriteria = "dateRec";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                } else if (cbCriteria.getValue() == "UserID") {
                    searchCriteria = "userId";
                    if (cbOnlyActiveRec.isSelected()) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(this::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
                    }
                }

            }
        });

        //listener to onlyActive checkbox
        cbOnlyActiveRec.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                        pagination.setPageCount(calculateNbrPages());
                        pagination.setPageFactory(currentAdminReclamationsListController.getInstance()::createPage);
                    } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(currentAdminReclamationsListController.getInstance()::createPageAll);
                    }
            }
        });

        //listener to response checkbox
        cbResponse.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    taResponse.setEditable(true);
                } else {
                    taResponse.setEditable(false);
                    taResponse.clear();
                }
            }
        });

        //listener to mouse click on one row
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Reclamation selectedRec = table.getSelectionModel().getSelectedItem();
                    tfTitle.setText(selectedRec.getTitle());
                    taDescription.setText(selectedRec.getDescriptionRec());
                    lID.setText("" + selectedRec.getId());
                    btUpdate.setDisable(!titleRules || !descriptionRules);
                    btDeleteRec.setDisable(!true);
                    btHandle.setDisable(!true);
                    tfTitle.setEditable(!false);
                    taDescription.setEditable(!false);
                }
            }
        });

        //listener on deselecting 
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
                            btUpdate.setDisable(true);
                            btDeleteRec.setDisable(true);
                            btHandle.setDisable(true);

                            tfTitle.setEditable(false);
                            taDescription.setEditable(false);
                        }
                    }
                });
                return row;
            }
        });

        //buttons on action
        btUpdate.setOnAction(e -> {
            updateRec(e);
            if (cbOnlyActiveRec.isSelected()) {
                pagination.setPageCount(calculateNbrPages());
                pagination.setPageFactory(this::createPage);
            } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
            }
        });

        btDeleteRec.setOnAction(e -> {
            deleteRec();
            if (cbOnlyActiveRec.isSelected()) {
                pagination.setPageCount(calculateNbrPages());
                pagination.setPageFactory(this::createPage);
            } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
            }
        });

        btHandle.setOnAction(e -> {
            handleRec(e);
            if (cbOnlyActiveRec.isSelected()) {
                pagination.setPageCount(calculateNbrPages());
                pagination.setPageFactory(this::createPage);
            } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(this::createPageAll);
            }
        });

        //listener on textfields and textareas
        tfTitle.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {

                if (!s2.isEmpty()) {
                    if (s2.length() < 10) {
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
                btHandle.setDisable(true);
                btUpdate.setDisable(!titleRules || !descriptionRules);
                btDeleteRec.setDisable(true);
            }
        });

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                if (cbOnlyActiveRec.isSelected()) {
                    pagination.setPageCount(calculateNbrPages());
                    pagination.setPageFactory(currentAdminReclamationsListController.getInstance()::createPage);
                } else {
                        pagination.setPageCount(calculateNbrPagesAll());
                        pagination.setPageFactory(currentAdminReclamationsListController.getInstance()::createPageAll);
                }
            }
        });

        taDescription.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {

                if (!s2.isEmpty()) {
                    if (s2.length() < 30) {
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
                btHandle.setDisable(true);
                btUpdate.setDisable(!titleRules || !descriptionRules);
                btDeleteRec.setDisable(true);
            }
        });

    }

    //buttons functions
    
    @FXML
    private void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));

        try {
            Parent root = loader.load();
            btBack.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void handleRec(ActionEvent event) {
        Reclamation r = new Reclamation();
        r = rc.selectByID(Integer.parseInt(lID.getText())).get(0);
        if (r.isHandled()) {
            ShowNotification notif = new ShowNotification();
            notif.showError("Error!!", "this reclamation is already handled!!");
        } else {
            rc.handleRec(r.getId());
            if (cbResponse.isSelected()) {
                MailSender mailer = new MailSender();
                // SMTP server information
                String host = "smtp.gmail.com";
                String port = "587";
                String mailFrom = "huntkingdomteam@gmail.com";
                String passwordMail = "hk2020..";

                // outgoing message information
                User u = new User(r.getUserId());
                u = us.getUserById(u);
                System.out.println("******************");
                System.out.println("user" + u);
                String mailTo = u.getEmail();
                String subject = "Reclamation update";

                // message contains HTML markups
                EmailBody eb = new EmailBody();

                try {
                    String newPw = mailer.genererCle();
                    String message = eb.createEmailBody("Reclamation update ", "", "Hi " + u.getUsername() + ",",
                            taResponse.getText());
                    mailer.sendHtmlEmail(host, port, mailFrom, passwordMail, mailTo, subject, message);
                    System.out.println("Email sent.");
                } catch (Exception e) {
                    System.out.println("Failed to sent email.");
                    e.printStackTrace();
                    System.out.println(e.getCause());
                    System.out.println(e.getClass());
                    System.out.println(e.getMessage());
                }
                taResponse.clear();

            }
            clear();
            ShowNotification notif = new ShowNotification();
            notif.show("Success!!", "Reclamation has been successfully handled!");
        }
    }

    

    @FXML
    private void updateRec(ActionEvent event) {
        System.out.println("button update pressed");
        Reclamation r = new Reclamation();
        r = rc.selectByID(Integer.parseInt(lID.getText())).get(0);
        System.out.println("handle: " + r);
        if (r.isHandled()) {
            ShowNotification notif = new ShowNotification();
            notif.showError("Error!!", "A handled reclamation can't be edited!");
        } else {
            r.setDescriptionRec(taDescription.getText());
            r.setTitle(tfTitle.getText());
            rc.updateRec(r);
            clear();
            ShowNotification notif = new ShowNotification();
            notif.show("Success!!", "Reclamation has been successfully updated!");
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        clear();
    }

    public void clear() {
        tfTitle.clear();
        taDescription.clear();
        btUpdate.setDisable(!false);
        btDeleteRec.setDisable(!false);
        btHandle.setDisable(!true);
        lErrorDescription.setText("");
        lErrorTitle.setText("");
    }

    @FXML
    private void deleteRec() {
        rc.deleteRec(Integer.parseInt(lID.getText()));
        clear();
        ShowNotification notif = new ShowNotification();
        notif.show("Success!!", "Reclamation has been successfully deleted!");
    }

    
    //pagination functions
    
    public Node createPage(int pageIndex) {
        from = pageIndex * maxItems;
        to = maxItems;
        table.setItems(FXCollections.observableArrayList(rc.showNotHandled(orderCriteria, searchCriteria, tfSearch.getText(), from, to)));
        return table;
    }

    public Node createPageAll(int pageIndex) {
        from = pageIndex * maxItems;
        to = maxItems;
        table.setItems(FXCollections.observableArrayList(rc.showAll(orderCriteria, searchCriteria, tfSearch.getText(), from, to)));
        return table;
    }
    
    private int calculateNbrPages() {
        int n = 1;
        int nItems = rc.showAllNotHandled(orderCriteria, searchCriteria, tfSearch.getText()).size();
        while (nItems > maxItems) {
            n++;
            nItems -= maxItems;
        }
        return n;
    }

    private int calculateNbrPagesAll() {
        int n = 1;
        int nItems = rc.showAll(orderCriteria, searchCriteria, tfSearch.getText()).size();
        while (nItems > maxItems) {
            n++;
            nItems -= maxItems;
        }
        return n;
    }

}
