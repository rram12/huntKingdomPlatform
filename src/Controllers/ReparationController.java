/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PiecesDefectueuses;
import Services.PieceService;
import Utils.MyConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private Tab progressPane;
    @FXML
    private Tab readyPane;
    @FXML
    private Label labelReady;

    @FXML
    private Label labelProgress;
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
    private int current_id;
    ObservableList<String> list = FXCollections.observableArrayList("Hunting", "Fishing");
    ArrayList<PiecesDefectueuses> pieces = new ArrayList<>();

    public ReparationController() {
        PieceService ps = new PieceService();
        ps.updateEtat();
        this.pieces = (ArrayList) ps.afficherPieceReserved();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorie.setItems(list);
        
        
        
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

        /*Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            if(pieces.get(i).isReserved()){
            try {    
                
                FXMLLoader loader = new FXMLLoader();
               
                Pane root = loader.load(getClass().getResource("/Gui/SinglePiece.fxml").openStream());
                 SinglePieceController single = (SinglePieceController)loader.getController();
                 single.getInfo(pieces.get(i));
                 if(pieces.get(i).isEtat()){
                 JFXButton button = single.getButton();
                 button.setText("Ready");
                 button.setStyle("-fx-background-color: green;");
                 
                 button.setOnAction(e->{
                     
                    // System.out.println("test");
                 
                 });
                 }
                nodes[i]=root;
                //nodes[i] = FXMLLoader.load(getClass().getResource("/Gui/SinglePiece.fxml"));
                flow.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }*/
    }

    public FlowPane createPage(int index, ArrayList<PiecesDefectueuses> pieces) {
        FlowPane flow = new FlowPane();
        Node[] nodes = new Node[4];
        int nbNode = -1;
        index++;
        int n = index + (index - 1) * 2 + 1;
       // System.out.println("n : "+n);
        int deb = n - 2;
        for (int i = deb; i < deb + 3 && i!=pieces.size(); i++) {
            nbNode++;
            if (pieces.get(i).isReserved()) {
                try {

                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/Gui/SinglePiece.fxml").openStream());
                    SinglePieceController single = (SinglePieceController) loader.getController();
                    single.getInfo(pieces.get(i));
                    int id1 = single.getCurrentId();
                    JFXButton button = single.getButton();
                    
                    if (pieces.get(i).isEtat()) {

                        button.setText("Ready");
                        button.setStyle("-fx-background-color: green;");

                        button.setOnAction(e -> {
                            this.current_id = id1;
                            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(2); //select by index starting with 0
                            System.out.println("ready");
                            String str1 = Integer.toString(this.current_id);
                            labelReady.setText(labelReady.getText() + str1);
                        });
                    } else {
                        button.setOnAction(e -> {
                            this.current_id = id1;
                            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(3); //select by index starting with 0
                            System.out.println("not ready");
                            String str1 = Integer.toString(this.current_id);
                            labelProgress.setText(labelProgress.getText() + str1);
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

    private void copyFile(File file) {
        try {
            File dest = new File("E:\\roadToInfini\\java\\projet\\final\\HuntKingdom\\src\\Uploads\\" + file.getName()); //any location
            Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fc = new FileChooser();
        fc.setTitle("Select an image");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(imageFilter);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            listView.getItems().add(selectedFile.getName());
            copyFile(selectedFile);
            absolutePath = selectedFile.getAbsolutePath();
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
            /*Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("please enter all the information ! ");
        alert.showAndWait();*/
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

}
