/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Produits;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class SingleCartController implements Initializable {

    @FXML
    private Label typeTxt;

    @FXML
    private Label marqueTxt;

    @FXML
    private Button removeBtn;

    @FXML
    private Label libelleTxt;

    @FXML
    private Label totalTxt;

    @FXML
    private Label priceTxt;

    @FXML
    private TextField quatityText;

    @FXML
    private ImageView img_produit;
    private Double totale;

    /**
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* quatityText.focusedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasFocused, Boolean isNowFocused) -> {
            if (!isNowFocused) {
                // text field has lost focus...
                if (validateQuantity()) {
                    int qtt = Integer.parseInt(quatityText.getText());
                    Double prix = Double.parseDouble(priceTxt.getText());
                    totale = prix * qtt;
                    totalTxt.setText(Double.toString(totale));
                    System.out.println("infocusednow : " + quatityText.getText());
                    ProductsFrontController.totalee += totale - prix;
                    System.out.println("totalee = " + ProductsFrontController.totalee);
                    ProductsFrontController fc = new ProductsFrontController();
                    Label l = new Label();
                    l.setText(Double.toString(ProductsFrontController.totalee));
                    fc.setLabelTotalPrice(l);
                   
                    System.out.println("getLabelTotalPrice : "+fc.getLabelTotalPrice().getText());*/
                    // d+=totale;
                    //fc.setTotalee(d);
                    //Label l = new Label();
                    //l.setText(Double.toString(d));
                    // fc.setLabelTotalPrice(l);
                    /*  Double prec = Double.parseDouble(ProductsFrontController.LabelTotalPrice.getText());
        totale+=prec;
        ProductsFrontController.LabelTotalPrice.setText(Double.toString(totale));*/
 /*FXMLLoader loader = new FXMLLoader();
            try {
                Pane root = loader.load(getClass().getResource("/Gui/ProductsFront.fxml").openStream());
                ProductsFrontController single = (ProductsFrontController) loader.getController();
                Label l = single.getLabelTotalPrice();
                System.out.println("getLabelTotalPrice : " + l.getText());
            } catch (IOException ex) {
                System.out.println("error"+ex.getMessage());
            }
                     */
                    // ProductsFrontController.LabelTotalPrice.setText(Double.toString(totale));
                    //       ProductsFrontController.addTotal(totale);
              //  }
            //}
      //  });
        // TODO
    }

    void getInfo(Produits p) {
        typeTxt.setText(p.getType());
        marqueTxt.setText(p.getMarque());
        libelleTxt.setText(p.getLib_prod());
        priceTxt.setText(Double.toString(p.getPrix()));
        try {
            Image image = new Image(new FileInputStream(p.getImage()));
            img_produit.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        quatityText.setText("1");
        totalTxt.setText(Double.toString(p.getPrix()));
        this.totale = Double.parseDouble(totalTxt.getText());
    }

    Button getButton() {
        return removeBtn;
    }

    TextField getTextField(){
    return quatityText ;
    }
    
     Label getTotalTxt(){
     return   totalTxt;
     }
     Label getPriceTxt(){
     return  priceTxt;
     }
    
    Double getTotale() {
        return totale;
    }

    public void showNotif(String message) {
        Image img = new Image("/Uploads/error.png");
        ImageView imgV = new ImageView(img);
        imgV.setFitHeight(50);
        imgV.setFitWidth(50);

        Notifications notif = Notifications.create()
                .title("  erreur")
                .text(message)
                .graphic(imgV)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_CENTER)
                .onAction(s -> {
                    // System.out.println("notif clicked");
                });
        notif.show();

    }

    boolean validateQuantity() {

        try {
            int qtt = Integer.parseInt(quatityText.getText());
            if (qtt < 0) {
                showNotif("quantity > 0");
                return false;
            }
        } catch (NumberFormatException nfe) {
            showNotif("quantity numerique !");
            return false;

        }
        return true;
    }

}
