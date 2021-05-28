/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.itextpdf.text.Document;
import Entities.Reclamation;
import Entities.User;
import Utils.EmailBody;
import Utils.MailSender;
import Utils.ShowNotification;
import Utils.ShowNotificationsAdmin;
import Services.UserService;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javax.mail.MessagingException;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author ramzi.raddaoui@esprit.tn
 */
public class AdminUsersListController implements Initializable {

    UserService us = new UserService();
    boolean titleRules = false;
    boolean descriptionRules = false;
    String orderCriteria = "id";
    String searchCriteria = "id";
    int currentSize = 0;

    WebView pdfViewer;
    WebEngine engine;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
//            System.out.println(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size());
//            System.out.println(currentSize);
            if (currentSize != us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size()) {
                table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));

            }
        }

    };
    

    @FXML
    private TableView<User> table;
    @FXML
    private Label lID;
    @FXML
    private Button btDeleteUser;
    @FXML
    private Button btBack;
    @FXML
    private ChoiceBox<String> cbCriteria;
    @FXML
    private TextField tfSearch;
    @FXML
    private ChoiceBox<String> cbCriteria1;
    @FXML
    private ImageView bg;

    private static AdminUsersListController instance;
    @FXML
    private TableView<User> tableToReviewUsers;
    @FXML
    private Button btOpenLisence;
    @FXML
    private Button btConfirm;
    @FXML
    private Button btRefuse;
    @FXML
    private TabPane tp;
    @FXML
    private Tab confirmedTab;
    @FXML
    private Tab unconfirmedTab;
    


    public AdminUsersListController() {
        instance = this;
    }

    public static AdminUsersListController getInstance() {
        return instance;
    }

    public Timer getTimer() {
        return timer;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btConfirm.setVisible(false);
        btBack.setVisible(!false);
        btDeleteUser.setVisible(!false);
        btOpenLisence.setVisible(false);
        btRefuse.setVisible(false);

        btBack.setDisable(false);
        btDeleteUser.setDisable(true);
        btConfirm.setDisable(true);
        btOpenLisence.setDisable(true);
        btRefuse.setDisable(true);

//        try {
//            bg.setImage(new Image(new FileInputStream("src/Uploads/dash.jpg")));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
//        }

        cbCriteria1.getItems().add("ID");
        cbCriteria1.getItems().add("Username");
        cbCriteria1.getItems().add("Email");
        cbCriteria1.getItems().add("Roles");
        cbCriteria1.getItems().add("firstName");
        cbCriteria1.getItems().add("lastName");
        cbCriteria1.getItems().add("address");
        cbCriteria1.getItems().add("phoneNumber");
        cbCriteria1.setValue("ID");

        cbCriteria.getItems().add("ID");
        cbCriteria.getItems().add("Username");
        cbCriteria.getItems().add("Email");
        cbCriteria.getItems().add("Roles");
        cbCriteria.getItems().add("firstName");
        cbCriteria.getItems().add("lastName");
        cbCriteria.getItems().add("address");
        cbCriteria.getItems().add("phoneNumber");
        cbCriteria.setValue("ID");

        cbCriteria1.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
                currentSize = us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size();
            }
        });

        cbCriteria.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
                currentSize = us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size();
            }
        });

        lID.setVisible(false);

        table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
        currentSize = us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size();

        TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(50);
        idColumn.setPrefWidth(75);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> unColumn = new TableColumn<>("Username");
        unColumn.setMinWidth(100);
        unColumn.setPrefWidth(175);
        unColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        unColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> fnColumn = new TableColumn<>("First Name");
        fnColumn.setMinWidth(100);
        fnColumn.setPrefWidth(175);
        fnColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fnColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> lnColumn = new TableColumn<>("Last Name");
        lnColumn.setMinWidth(100);
        lnColumn.setPrefWidth(175);
        lnColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lnColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setPrefWidth(230);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, Long> pnColumn = new TableColumn<>("Phone Number");
        pnColumn.setMinWidth(100);
        pnColumn.setPrefWidth(130);
        pnColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        pnColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> adColumn = new TableColumn<>("Address");
        adColumn.setMinWidth(300);
        adColumn.setPrefWidth(350);
        adColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        adColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> rColumn = new TableColumn<>("Role");
        rColumn.setMinWidth(100);
        rColumn.setPrefWidth(130);
        rColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));
        rColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> picColumn = new TableColumn<>("Picture");
        picColumn.setMinWidth(100);
        picColumn.setPrefWidth(130);
        picColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
        picColumn.setStyle("-fx-alignment: CENTER;");

        picColumn.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String active, boolean empty) {
                    super.updateItem(active, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        try {
                            imageView.setImage(new Image(new FileInputStream("res\\uploadedImages\\" + active)));
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(80);
                            imageView.setPreserveRatio(false);
                            setGraphic(imageView);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            return cell;
        });

        table.getColumns().addAll(idColumn, unColumn, picColumn, fnColumn, lnColumn, emailColumn, adColumn, pnColumn, rColumn);

        btDeleteUser.setDisable(true);

        timer.scheduleAtFixedRate(task, 1000, 1000);

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    User selectedUser = table.getSelectionModel().getSelectedItem();
                    lID.setText("" + selectedUser.getId());
                    btDeleteUser.setDisable(!true);
                }
            }
        });

        table.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> tableView2) {
                final TableRow<User> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index > table.getItems().size()) {
                            table.getSelectionModel().clearSelection();
                            btDeleteUser.setDisable(true);
                        }
                    }
                });
                return row;
            }
        });

        btDeleteUser.setOnAction(e -> {
            deleteUser(e);
            table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
            currentSize = us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size();

        });

        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            public void changed(
                    ObservableValue<? extends String> observableValue, String s,
                    String s2) {
                table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
                currentSize = us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText()).size();
            }
        });

        TableColumn<User, Integer> idColumn1 = new TableColumn<>("ID");
        idColumn1.setMinWidth(50);
        idColumn1.setPrefWidth(50);
        idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> unColumn1 = new TableColumn<>("Username");
        unColumn1.setMinWidth(100);
        unColumn1.setPrefWidth(175);
        unColumn1.setCellValueFactory(new PropertyValueFactory<>("username"));
        unColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> fnColumn1 = new TableColumn<>("First Name");
        fnColumn1.setMinWidth(100);
        fnColumn1.setPrefWidth(175);
        fnColumn1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fnColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> lnColumn1 = new TableColumn<>("Last Name");
        lnColumn1.setMinWidth(100);
        lnColumn1.setPrefWidth(175);
        lnColumn1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lnColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> emailColumn1 = new TableColumn<>("Email");
        emailColumn1.setMinWidth(200);
        emailColumn1.setPrefWidth(230);
        emailColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, Long> pnColumn1 = new TableColumn<>("Phone Number");
        pnColumn1.setMinWidth(100);
        pnColumn1.setPrefWidth(130);
        pnColumn1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        pnColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> adColumn1 = new TableColumn<>("Address");
        adColumn1.setMinWidth(300);
        adColumn1.setPrefWidth(350);
        adColumn1.setCellValueFactory(new PropertyValueFactory<>("address"));
        adColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> rColumn1 = new TableColumn<>("Role");
        rColumn1.setMinWidth(100);
        rColumn1.setPrefWidth(130);
        rColumn1.setCellValueFactory(new PropertyValueFactory<>("roles"));
        rColumn1.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> picColumn1 = new TableColumn<>("Picture");
        picColumn1.setMinWidth(100);
        picColumn1.setPrefWidth(130);
        picColumn1.setCellValueFactory(new PropertyValueFactory<>("picture"));
        picColumn1.setStyle("-fx-alignment: CENTER;");

        picColumn1.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                private ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String active, boolean empty) {
                    super.updateItem(active, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        try {
                            imageView.setImage(new Image(new FileInputStream("res\\uploadedImages\\" + active)));
                            imageView.setFitHeight(50);
                            imageView.setFitWidth(80);
                            imageView.setPreserveRatio(false);
                            setGraphic(imageView);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            return cell;
        });

        tableToReviewUsers.getColumns().addAll(idColumn1, unColumn1, picColumn1, fnColumn1, lnColumn1, emailColumn1, adColumn1, pnColumn1, rColumn1);
        tableToReviewUsers.setItems(FXCollections.observableArrayList(us.showUnconfirmed()));

        tableToReviewUsers.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                if (tableToReviewUsers.getSelectionModel().getSelectedItem() != null) {
                    User selectedUser = tableToReviewUsers.getSelectionModel().getSelectedItem();
                    lID.setText("" + selectedUser.getId());
                    btConfirm.setDisable(!true);
                    btRefuse.setDisable(!true);
                    btOpenLisence.setDisable(!true);
                    System.out.println("lID" + lID.getText());
                }
            }
        });

        tableToReviewUsers.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> tableView2) {
                final TableRow<User> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index > tableToReviewUsers.getItems().size()) {
                            tableToReviewUsers.getSelectionModel().clearSelection();
                            btConfirm.setDisable(true);
                            btRefuse.setDisable(true);
                            btOpenLisence.setDisable(true);
                        }
                    }
                });
                return row;
            }
        });
        tp.getSelectionModel().select(confirmedTab);

        tp.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                if (t1 == unconfirmedTab) {
                    btDeleteUser.setVisible(false);
                    btConfirm.setVisible(!false);
                    btOpenLisence.setVisible(!false);
                    btRefuse.setVisible(!false);
                } else if (t1 == confirmedTab) {
                    btDeleteUser.setVisible(!false);
                    btConfirm.setVisible(false);
                    btOpenLisence.setVisible(false);
                    btRefuse.setVisible(false);
                }
            }
        }
        );

    }

    @FXML
    private void deleteUser(ActionEvent event) {
        us.deleteUser(Integer.parseInt(lID.getText()));
        ShowNotification notif = new ShowNotification();
        notif.show("Success!!", "This user has been successfully deleted!");
    }

    @FXML
    private void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/AdminHome.fxml"));

        try {
            Parent root = loader.load();
            btBack.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @FXML
    private void confirmedUsersTab(Event event) {

    }

    @FXML
    private void UsersToReviewTab(Event event) {

    }

    @FXML
    private void openLisence(ActionEvent event) {
        try {
            File file = new File("res/uploadedPDF/" + us.selectUserByID(Integer.parseInt(lID.getText())).getContract());

            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void confirmUser(ActionEvent event) {
        User u = us.selectUserByID(Integer.parseInt(lID.getText()));
        u.setConfirmed(1);
        us.confirmUser(u);
        ShowNotificationsAdmin notif = new ShowNotificationsAdmin();
        notif.show("Success!!", "User confirmed");
        tableToReviewUsers.setItems(FXCollections.observableArrayList(us.showUnconfirmed()));
        try {
            // SMTP server information
            String host = "smtp.gmail.com";
            String port = "587";
            String mailFrom = "huntkingdomteam@gmail.com";
            String passwordMail = "hk2020..";

            // outgoing message information
            String mailTo = u.getEmail();
            String subject = "Application Update";

            // message contains HTML markups
            EmailBody eb = new EmailBody();

            String message = eb.createEmailBody("WELCOME " + u.getUsername() + " TO OUR FAMILY, YOUR APPLICATION HAS BEEN ACCEPTED", "We are delignted to have you among us", "YOUR ACCOUNT IS NOW ACTIVE", "Your "
                    + "huntkingdom registration was successful! You can now log into huntkingdom with your email address and password through our website, "
                    + "desktop application or android application");

            MailSender mailer = new MailSender();

            mailer.sendHtmlEmail(host, port, mailFrom, passwordMail, mailTo,
                    subject, message);
        } catch (MessagingException ex) {
            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Email sent.");
                tableToReviewUsers.setItems(FXCollections.observableArrayList(us.showUnconfirmed()));
            table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));

    }

    @FXML
    private void refuseUser(ActionEvent event) {
        User u = us.selectUserByID(Integer.parseInt(lID.getText()));
        String s = "res/uploadedImages/"+u.getPicture();
        us.deleteUser(u.getId());
        
//        File file = new File(s);
//        file.delete();
        File file = new File("res/uploadedPDF/"+u.getContract());
        file.delete();
        ShowNotificationsAdmin notif = new ShowNotificationsAdmin();
        notif.show("Success!!", "User removed");
        try {
            // SMTP server information
            String host = "smtp.gmail.com";
            String port = "587";
            String mailFrom = "huntkingdomteam@gmail.com";
            String passwordMail = "hk2020..";

            // outgoing message information
            String mailTo = u.getEmail();
            String subject = "Application Update";

            // message contains HTML markups
            EmailBody eb = new EmailBody();

            String message = eb.createEmailBody("Hello " + u.getUsername() + ", YOUR APPLICATION HAS BEEN REVIEWED", "We are sorry to tell you that your application has been declined", 
                    "All your data has been removed but you can resubmit another application.", "If you think there is a mistake please contact the support team by reply to this email "
                    + "\nThank you.");

            MailSender mailer = new MailSender();

            mailer.sendHtmlEmail(host, port, mailFrom, passwordMail, mailTo,
                    subject, message);
        } catch (MessagingException ex) {
            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Email sent.");
                tableToReviewUsers.setItems(FXCollections.observableArrayList(us.showUnconfirmed()));
            table.setItems(FXCollections.observableArrayList(us.showAllByCriteria(cbCriteria1.getValue(), cbCriteria.getValue(), tfSearch.getText())));
Path imagesPath = Paths.get("res/uploadedImages/"+u.getPicture());
        try {
            Files.delete(imagesPath);
        } catch (IOException ex) {
            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
