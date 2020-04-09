/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PiecesDefectueuses;
import Services.JavaMail;
import Services.PieceService;
import Services.ReparationService;
import Utils.MyConnection;
import Utils.copyFiles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;
import com.stripe.model.Token;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class ReparationController implements Initializable {

    @FXML
    private JFXTextField moix_exp;
    @FXML
    private StackPane stackPay;
    @FXML
    private JFXTextField annee_exp;
    @FXML
    private JFXTextField cvc;

    @FXML
    private JFXTextField num_carte;

    @FXML
    private Tab progressPane;
    @FXML
    private Tab readyPane;

    @FXML
    private Label LabelHours;

    @FXML
    private Label labelMonth;

    @FXML
    private Label labelDays;

    @FXML
    private Label labelYear;
    @FXML
    private StackPane stack;

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXComboBox<String> categorie;
    @FXML
    private JFXTextArea description;
    @FXML
    private ImageView imageView;
    @FXML
    private ListView listView;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private AnchorPane anchor;

    private String absolutePath = "";

    @FXML
    private Pane listPane;
    private PiecesDefectueuses current_piece;
    ObservableList<String> list = FXCollections.observableArrayList("Hunting", "Fishing");
    ArrayList<PiecesDefectueuses> pieces = new ArrayList<>();

    public ReparationController() {
        PieceService ps = new PieceService();
        this.pieces = (ArrayList) ps.afficherPieceReserved();
    }

    /**
     * Initializes the controller class.
     */
    void refresh() {
        PieceService ps = new PieceService();
        this.pieces = (ArrayList) ps.afficherPieceReserved();
        ObservableList<PiecesDefectueuses> obsl = FXCollections.observableArrayList(pieces);
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination = new Pagination();
        // System.out.println("nbPieceReserved : "+pieces.size());

        if (pieces.size() % 3 > 0) {
            pagination.setPageCount((pieces.size() / 3) + 1);
        } else {
            pagination.setPageCount(pieces.size() / 3);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage(pageIndex, pieces);
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().add(pagination);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorie.setItems(list);
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (oldTab == readyPane) {
                readyPane.setDisable(true);
                /**
                 * clearing the fields *
                 */
                num_carte.setText("");
                moix_exp.setText("");
                annee_exp.setText("");
                cvc.setText("");

            }
        });
        ObservableList<PiecesDefectueuses> obsl = FXCollections.observableArrayList(pieces);
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination = new Pagination();
        // System.out.println("nbPieceReserved : "+pieces.size());

        if (pieces.size() % 3 > 0) {
            pagination.setPageCount((pieces.size() / 3) + 1);
        } else {
            pagination.setPageCount(pieces.size() / 3);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage(pageIndex, pieces);
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().add(pagination);

    }

    public FlowPane createPage(int index, ArrayList<PiecesDefectueuses> pieces) {
        FlowPane flow = new FlowPane();
        Node[] nodes = new Node[4];
        int nbNode = -1;
        index++;
        int n = index + (index - 1) * 2 + 1;
        // System.out.println("n : "+n);
        int deb = n - 2;
        for (int i = deb; i < deb + 3 && i != pieces.size(); i++) {
            nbNode++;
            if (pieces.get(i).isReserved()) {
                try {

                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/Gui/SinglePiece.fxml").openStream());
                    SinglePieceController single = (SinglePieceController) loader.getController();
                    single.getInfo(pieces.get(i));
                    PiecesDefectueuses p1 = pieces.get(i);
                    JFXButton button = single.getButton();

                    if (pieces.get(i).isEtat()) {

                        button.setText("Ready");
                        button.setStyle("-fx-background-color: green;");

                        button.setOnAction(e -> {

                            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(2); //select by index starting with 0
                            readyPane.setDisable(false);
                            this.current_piece = p1;

                        });
                    } else {
                        button.setOnAction(e -> {
                            this.current_piece = p1;
                            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(3); //select by index starting with 0
                            System.out.println("not ready");
                            //String str1 = this.current_piece.getNom();
                            // labelProgress.setText(labelProgress.getText() + str1);
                            System.out.println(p1.getNom());
                            Date currentDate1 = new Date();

                            LocalDate currentDate = currentDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            ReparationService rs = new ReparationService();
                            Date dateFin1 = rs.getDateFin(p1.getId());

                            LocalDate dateFin = dateFin1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            System.out.println("current_date : " + currentDate + "datefin : " + dateFin);

                            Period diff = Period.between(currentDate, dateFin);
                            System.out.printf("Difference is %d years, %d months and %d days old",
                                    diff.getYears(), diff.getMonths(), diff.getDays());
                            labelYear.setText(Integer.toString(diff.getYears()));
                            labelMonth.setText(Integer.toString(diff.getMonths()));
                            labelDays.setText(Integer.toString(diff.getDays()));

                        });

                    }
                    nodes[nbNode] = root;
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/Gui/SinglePiece.fxml"));
                    flow.getChildren().add(nodes[nbNode]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        /*  Label lab = new Label("test");
           
        flow.getChildren().add(lab);     */
        return flow;
    }

  

    @FXML
    void chooseImage(ActionEvent event) {
        
        System.out.println("choose image...");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.getItems().add(selectedFile.getName());
            String oldPath = selectedFile.getAbsolutePath();
            
            absolutePath = "E:\\roadToInfini\\java\\projet\\final\\git\\HuntKingdom\\src\\Uploads\\" + selectedFile.getName() ;
            copyFiles.deplacerVers( oldPath, "E:\\roadToInfini\\java\\projet\\final\\git\\HuntKingdom\\src\\Uploads\\");

        } else {
            System.out.println("file is not valid !");
        }
        try {
            Image image = new Image(new FileInputStream(absolutePath));
            imageView.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public boolean validateFields() {
        if (nom.getText().isEmpty() || description.getText().isEmpty() || absolutePath.equals("") || categorie.getValue() == null) {
            JFXDialogLayout message = new JFXDialogLayout();
            message.setHeading(new Text("error!"));
            message.setBody(new Text("please enter all the information !"));
            JFXDialog msg = new JFXDialog(stack, message, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("close");
            button.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 14px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: red;"
                    + "    -fx-pref-width: 100;"
                    + "    -fx-text-fill: WHITE;");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msg.close();
                }
            });
            message.setActions(button);
            msg.show();

            return false;
        }

        return true;
    }

    @FXML
    void addPiece(ActionEvent event) {
        if (validateFields()) {
            MyConnection mc = MyConnection.getInstance();
            PieceService ps = new PieceService();
            PiecesDefectueuses p = new PiecesDefectueuses(nom.getText(), categorie.getValue(), description.getText(), absolutePath, 1);
            ps.ajouterPiece(p);
            JFXDialogLayout message = new JFXDialogLayout();
            Label label = new Label("Piece added seccessfully !");
            label.setStyle("-fx-font-size: 12px;"
                    + "    -fx-font-weight: bold;"
                    + "    -fx-text-fill: green;");
            message.setHeading(label);
            JFXDialog msg = new JFXDialog(stack, message, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("ok");
            button.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 14px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: blue;"
                    + "    -fx-pref-width: 100;"
                    + "    -fx-text-fill: WHITE;");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msg.close();
                }
            });
            message.setActions(button);
            msg.show();
            nom.setText("");
            categorie.setValue(null);
            description.setText("");
            listView.getItems().clear();
            imageView.setImage(null);

        }
    }

    public boolean validateFieldsPay() {
        if (num_carte.getText().isEmpty() || moix_exp.getText().isEmpty() || annee_exp.getText().isEmpty() || cvc.getText().isEmpty()) {
            JFXDialogLayout message = new JFXDialogLayout();
            message.setHeading(new Text("error!"));
            message.setBody(new Text("please enter all the information !"));
            JFXDialog msg = new JFXDialog(stackPay, message, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("close");
            button.setStyle("-fx-padding: 0.7em 0.57em;"
                    + "    -fx-font-size: 14px;"
                    + "    -jfx-button-type: RAISED;"
                    + "    -fx-background-color: red;"
                    + "    -fx-pref-width: 100;"
                    + "    -fx-text-fill: WHITE;");
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    msg.close();
                }
            });
            message.setActions(button);
            msg.show();

            return false;
        }

        return true;
    }

    @FXML
    void onPayAction(ActionEvent event) throws StripeException {
        if (validateFieldsPay()) {

            ReparationService rs = new ReparationService();
            Double prixRep = rs.getPrixReparation(current_piece.getId());
            int prix = prixRep.intValue();
            //String price = Double.toString(prix);
            /* add a customer */
            String EmailStatique = "khalil.tourabi10@gmail.com";
            Stripe.apiKey = "sk_test_0sHcDNkGs9s7se1ifivPl6Vb00WBmw7K82";
            try {
                Map<String, Object> options = new HashMap<>();
                options.put("email", EmailStatique);
                List<Customer> customers = Customer.list(options).getData();
                Customer customer;
                if (customers.size() > 0) {
                    customer = customers.get(0);
                    System.out.println("customer is already exist..");
                } else {

                    Map<String, Object> customerParameter = new HashMap<String, Object>();
                    customerParameter.put("email", EmailStatique);
                    customer = Customer.create(customerParameter);
                    System.out.println("customer ajouté");
                }
                String idcas = customer.getId();
                Customer a = Customer.retrieve(idcas);
                /* add a card */
                ExternalAccountCollection allcardDetails = a.getSources();
                Map<String, Object> cardParam = new HashMap<String, Object>();
                cardParam.put("number", num_carte.getText());
                cardParam.put("exp_month", moix_exp.getText());
                cardParam.put("exp_year", annee_exp.getText());
                cardParam.put("cvc", cvc.getText());
                Map<String, Object> tokenParam = new HashMap<String, Object>();//more secure
                tokenParam.put("card", cardParam);
                Token token = Token.create(tokenParam);
                boolean cardIsNotExist = true;
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String current_cardID = "";
                for (int i = 0; i < allcardDetails.getData().size(); i++) {
                    String c = allcardDetails.getData().get(i).toJson();
                    Card card = gson.fromJson(c, Card.class);
                    if (card.getFingerprint().equals(token.getCard().getFingerprint())) {
                        cardIsNotExist = false;
                        current_cardID = card.getId();
                    }
                }

                if (cardIsNotExist) {
                    Map<String, Object> source = new HashMap<String, Object>();
                    source.put("source", token.getId());
                    Card newCard = (Card) a.getSources().create(source);
                    current_cardID = newCard.getId();
                    System.out.println("card created .");
                } else {
                    System.out.println("card is already exist..");
                }

                /**
                 * charge customer
                 *
                 */
                Map<String, Object> chargeParam = new HashMap<String, Object>();
                System.out.println("price : " + prix);
                chargeParam.put("amount", prix);
                chargeParam.put("currency", "eur");
                chargeParam.put("customer", a.getId());
                chargeParam.put("source", current_cardID);
                Charge charge = Charge.create(chargeParam);
                System.out.println("payment is done !");
                /**
                 * clearing the fields *
                 */
                num_carte.setText("");
                moix_exp.setText("");
                annee_exp.setText("");
                cvc.setText("");
                /* seccuss dialog **/
                JFXDialogLayout message = new JFXDialogLayout();
                Label label = new Label("payment is seccessfully done !");
                label.setStyle("-fx-font-size: 12px;"
                        + "    -fx-font-weight: bold;"
                        + "    -fx-text-fill: green;");
                message.setHeading(label);
                JFXDialog msg = new JFXDialog(stackPay, message, JFXDialog.DialogTransition.CENTER);
                JFXButton button = new JFXButton("ok");
                button.setStyle("-fx-padding: 0.7em 0.57em;"
                        + "    -fx-font-size: 14px;"
                        + "    -jfx-button-type: RAISED;"
                        + "    -fx-background-color: blue;"
                        + "    -fx-pref-width: 100;"
                        + "    -fx-text-fill: WHITE;");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        msg.close();
                        rs.deleteReparation(current_piece.getId());
                        PieceService ps = new PieceService();
                        ps.deletePiece(current_piece.getId());
                        anchor.getChildren().clear();
                        refresh();
                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(1); //select by index starting with 0
                        readyPane.setDisable(true);
                        try {
                            JavaMail.sendMail(EmailStatique, 3);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                });
                message.setActions(button);
                msg.show();

            } catch (StripeException e) {
                String m = e.getMessage();
                System.out.println(m);
                JFXDialogLayout message = new JFXDialogLayout();
                message.setHeading(new Text("error!"));
                message.setBody(new Text(m));
                JFXDialog msg = new JFXDialog(stackPay, message, JFXDialog.DialogTransition.CENTER);
                JFXButton button = new JFXButton("close");
                button.setStyle("-fx-padding: 0.7em 0.57em;"
                        + "    -fx-font-size: 14px;"
                        + "    -jfx-button-type: RAISED;"
                        + "    -fx-background-color: red;"
                        + "    -fx-pref-width: 100;"
                        + "    -fx-text-fill: WHITE;");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        msg.close();
                    }
                });
                message.setActions(button);
                msg.show();

            }

        }
    }

}
