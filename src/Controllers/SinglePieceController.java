package Controllers;

import Entities.PiecesDefectueuses;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SinglePieceController implements Initializable{

 
    @FXML
    private Label idPiece;

    @FXML
    private ImageView img_piece;


    @FXML
    private Label labelCategory;

    @FXML
    private Label labelNom;
    
   private PiecesDefectueuses piece;
  @FXML
    private JFXButton showBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }
    
    
 /*    @FXML
    void showProgress(ActionEvent event) {
        
        

    }*/
    void getInfo(PiecesDefectueuses p){
        labelCategory.setText(p.getCategorie());
        labelNom.setText(p.getNom());
        piece = p;
        try {
            Image image = new Image(new FileInputStream(p.getImage()));
            img_piece.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
    }
    JFXButton getButton(){
        return showBtn;
    }
    int getCurrentId(){
        return this.piece.getId();
    }
    
    

}
