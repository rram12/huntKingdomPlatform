/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PiecesDefectueuses;
import Entities.Reparation;
import Entities.User;
import Services.PieceService;
import Services.ProduitService;
import Services.ReparationService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author asus_pc
 */
public class ReparateurController implements Initializable {
    @FXML
    private StackPane stack;

    @FXML
    private Tab reparePane;
    @FXML
    private Tab ListPane;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private JFXTextArea descriptionText;

    @FXML
    private DatePicker endDateText;

    @FXML
    private JFXTextField priceText;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private FlowPane flow;
   private int id;
   
User currentUser = LoginController.getInstance().getLoggedUser();
    
   
   void afficherPieces(){
        ArrayList<PiecesDefectueuses> pieces = new ArrayList<>();
        PieceService ps= new PieceService();
        pieces = (ArrayList) ps.afficherPieceNonReserved();
        ObservableList<PiecesDefectueuses> obsl = FXCollections.observableArrayList(pieces);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            if(pieces.get(i).isReserved()==false){
            try {    
                
                FXMLLoader loader = new FXMLLoader();
               
                Pane root = loader.load(getClass().getResource("/Gui/SinglePiece.fxml").openStream());
                 SinglePieceController single = (SinglePieceController)loader.getController();
                 single.getInfo(pieces.get(i));
                  int id1 = pieces.get(i).getId();
                 JFXButton button = single.getButton();
                 button.setText("Repare");
                 button.setOnAction(e->{
                     reparePane.setDisable(false);
                     /**vider les champs**/
                     descriptionText.setText("");
                     priceText.setText("");
                     endDateText.setValue(null);
                     /***********/
                     SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                     selectionModel.select(1); //select by index starting with 0
                     this.id = id1;
            
                 });
               
                 
                nodes[i]=root;
                flow.getChildren().add(nodes[i]);
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
       
   
   
   }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flow.getChildren().clear();
        afficherPieces();
    // ListPane.onSelectionChangedProperty();
     tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
        if(oldTab==reparePane){
        reparePane.setDisable(true);
        }
    });
        
    }
      
        
 
     @FXML
    void onConfirmAction(ActionEvent event) {
        if(validateFields()){
                    ReparationService ps = new ReparationService();
                      ZoneId defaultZoneId = ZoneId.systemDefault();
                    Date date = Date.from( endDateText.getValue().atStartOfDay(defaultZoneId).toInstant());
                    Double price = Double.parseDouble(priceText.getText());
                           int UserId =  currentUser.getId();
                    Reparation rep = new Reparation(new Date(),date,price,descriptionText.getText(),UserId,id);
                    ps.ajouterReparation(rep);
                    PieceService prs = new PieceService();
                    prs.updateReserved(id);
              flow.getChildren().clear();
            afficherPieces();
                    
                    
                  
                     reparePane.setDisable(true);
                     SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                     
                     selectionModel.select(0); //select by index starting with 0
                     
                     
                     
        }
    }
        public boolean validateFields() {
            boolean numeric = true;
        try {
            Double num = Double.parseDouble(priceText.getText());
        } catch (NumberFormatException e) {
            numeric = false;
        }
    
            
        if (descriptionText.getText().isEmpty() || priceText.getText().isEmpty() ||endDateText.getValue() == null) {
  
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
        if(numeric==false){
        
            JFXDialogLayout message = new JFXDialogLayout();
            message.setHeading(new Text("error!"));
            message.setBody(new Text("please enter a numeric value for price !"));
            JFXDialog msg = new JFXDialog(stack, message, JFXDialog.DialogTransition.CENTER);
            JFXButton button = new JFXButton("close");
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
    
}
