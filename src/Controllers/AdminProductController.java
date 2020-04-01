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
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author asus_pc
 */
public class AdminProductController implements Initializable {
 
    @FXML
    private JFXTextField search;
    @FXML
    TableView<Produits> table;
    @FXML
    TableColumn<Produits, Integer> id;
    @FXML
    TableColumn<Produits, String> lib_prod;
    @FXML
    TableColumn<Produits, Double> prix;
    @FXML
    TableColumn<Produits, Double> prixFinale;
    @FXML
    private ImageView imageView;
    @FXML
    TableColumn<Produits, String> description;
    @FXML
    TableColumn<Produits, Integer> qte_prod;
    @FXML
    TableColumn<Produits, Date> date_ajout;
    @FXML
    TableColumn<Produits, String> type;
    @FXML
    TableColumn<Produits, String> marque;
    private String absolutePath;
      @FXML
    private TextField rate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Button reductionBtn;
    @FXML
    private AnchorPane mainPane;
    MyConnection mc = MyConnection.getInstance();
    ProduitService ps = new ProduitService();
    List<Produits> mylist = new ArrayList();
    public ObservableList<Produits> list;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(
            ps.afficherProduits()
        );
                FilteredList<Produits> filteredData = new FilteredList<>(list, e -> true);
         search.setOnKeyReleased(e -> {
            search.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Produits>) produits -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lower = newValue.toLowerCase();
                    if (produits.getLib_prod().toLowerCase().contains(lower)) {
                        return true;
                    }

                    return false;
                });
            });
            SortedList<Produits> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
        });
        /*** delete ended reductions **/
        ProduitService ps = new ProduitService();
        ps.deletePromotionFini();
        PromotionService promos = new PromotionService();
        promos.supprimerPromotionFini();
        list = FXCollections.observableArrayList(
            ps.afficherProduits()
    );
        
        id.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("id"));
        lib_prod.setCellValueFactory(new PropertyValueFactory<Produits, String>("lib_prod"));
        prix.setCellValueFactory(new PropertyValueFactory<Produits, Double>("prix"));
        prixFinale.setCellValueFactory(new PropertyValueFactory<Produits, Double>("prixFinale"));
        description.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
        qte_prod.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("qte_prod"));
        date_ajout.setCellValueFactory(new PropertyValueFactory<Produits, Date>("date_ajout"));
        type.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        marque.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        table.setItems(list);
        table.setRowFactory(tv -> {
            TableRow<Produits> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Produits rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    absolutePath = rowData.getImage();

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
    public boolean validateFields(){
       boolean numeric = true;
        try {
            Double num = Double.parseDouble(rate.getText());
        } catch (NumberFormatException e) {
            numeric = false;
        }
        if(numeric){
            Double num = Double.parseDouble(rate.getText());
            if(num>1||num<0)numeric = false;
        }
    if(rate.getText().isEmpty()||endDate.getValue()==null){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validate Fields");
        alert.setHeaderText(null);
        alert.setContentText("please enter all the information ! ");
        alert.showAndWait();
        return false;
    }else{
    if(numeric==false){
    Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incorrect field");
        alert.setHeaderText(null);
        alert.setContentText("please enter a number for rate(Between 0 and 1)  ");
        alert.showAndWait();
        return false;
    }       
    
}
    return true;
    }
    public void OnConfirmAction(){
        Produits p = table.getSelectionModel().getSelectedItem();
        if(p==null){
        Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate selection");
            alert.setHeaderText(null);
            alert.setContentText("please select a product ! ");
            alert.showAndWait();
            
        }
        else{
            if(validateFields()){
                boolean ok=true;
                if(p.getPromotion_id()!=-1){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("overwrite reduction");
                alert.setHeaderText(null);
                alert.setContentText("product already has a reduction, do you want to overwite it?");
                
                Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() != ButtonType.OK) {
                        ok = false;
                    }
                } 
                    
                    if(ok){
                
                
                
               
                Date current_date = new Date();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date finaDate = Date.from(endDate.getValue().atStartOfDay(defaultZoneId).toInstant());
                Promotion promo = new Promotion(Double.parseDouble(rate.getText()),current_date,finaDate);
                PromotionService Spromo = new PromotionService();
                Spromo.ajouterPromotion(promo,p.getId());
                ProduitService ps = new ProduitService();
                ps.ReductPiece(p.getId(),promo.getTaux());
                list.clear();
                list = FXCollections.observableArrayList(
                    ps.afficherProduits()
                );
                table.setItems(list);
                }
            }
        
        }
    }
    
    public void OnShowReductionAction()throws IOException{
      AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/ListReductionsAdmin.fxml"));
        mainPane.getChildren().setAll(pane);
       
    }
    
    
}
