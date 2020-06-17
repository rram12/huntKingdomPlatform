/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Produits;
import Services.ProduitService;
import Utils.MyConnection;
import Utils.copyFiles;
import com.jfoenix.controls.JFXButton;
import com.sun.prism.impl.Disposer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class AjoutProduitAdminController implements Initializable {
  @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView imageView;

    @FXML
    private TextField marqueTxt;

    @FXML
    private TableColumn<Produits, Double> prix;

    @FXML
    private TableColumn<Produits, String> lib_prod;

    @FXML
    private TextField libelleTxt;

    @FXML
    private Button choseBtn;

    @FXML
    private TableColumn<Produits, String> description;

    @FXML
    private TableColumn<Produits, String> type;

    @FXML
    private TextField QttTxt;

    @FXML
    private TableColumn<Produits, String> marque;
    @FXML
    public ComboBox<String> typeTxt;
    ObservableList<String> listCombo = FXCollections.observableArrayList("Hunting", "Fishing");

    @FXML
    private TableColumn action_col;

    @FXML
    private TableColumn<Produits, Integer> qte_prod;

    @FXML
    private TableColumn<Produits, Date> date_ajout;

    @FXML
    private ListView listView;

    @FXML
    private TableColumn<Produits, Integer> id;

    @FXML
    private TextField prixTxt;

    @FXML
    private TableView<Produits> table;
    @FXML
    private JFXButton editBtn;

    @FXML
    private TextField descriptionTxt;
    private String absolutePath;
    private int current_id;
    /**
     * Initializes the controller class.
     */
    MyConnection mc = MyConnection.getInstance();
    ProduitService ps = new ProduitService();
    List<Produits> mylist = new ArrayList();
    public ObservableList<Produits> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList(
                ps.afficherProduits()
        );
        id.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("id"));
        lib_prod.setCellValueFactory(new PropertyValueFactory<Produits, String>("lib_prod"));
        prix.setCellValueFactory(new PropertyValueFactory<Produits, Double>("prix"));
        description.setCellValueFactory(new PropertyValueFactory<Produits, String>("description"));
        qte_prod.setCellValueFactory(new PropertyValueFactory<Produits, Integer>("qte_prod"));
        date_ajout.setCellValueFactory(new PropertyValueFactory<Produits, Date>("date_ajout"));
        type.setCellValueFactory(new PropertyValueFactory<Produits, String>("type"));
        marque.setCellValueFactory(new PropertyValueFactory<Produits, String>("marque"));
        action_col.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        //Adding the Button to the cell
        action_col.setCellFactory(
                new Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

            @Override
            public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                return new ButtonCell();
            }

        });
        typeTxt.setItems(listCombo);
        table.setItems(list);

        table.setRowFactory(tv -> {
            TableRow<Produits> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Produits rowData = row.getItem();
                    /**
                     * fill the fields with the selected data *
                     */
                    libelleTxt.setText(rowData.getLib_prod());
                    marqueTxt.setText(rowData.getMarque());
                    descriptionTxt.setText(rowData.getDescription());
                    prixTxt.setText(Integer.toString((int) rowData.getPrix()));
                    typeTxt.setValue(rowData.getType());
                    QttTxt.setText(Integer.toString(rowData.getQte_prod()));
                    absolutePath = rowData.getImage();
                    listView.getItems().clear();
                    listView.getItems().add(absolutePath.substring(absolutePath.lastIndexOf(("\\")) + 1));

                    current_id = rowData.getId();
                    try {
                        Image image = new Image(new FileInputStream(absolutePath));
                        imageView.setImage(image);
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex);
                    }
                    editBtn.setVisible(true);
                }
            });
            return row;
        });

    }

    private class ButtonCell extends TableCell<Disposer.Record, Boolean> {

        final Button cellButton = new Button("Delete");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    //confirmation alert 
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure ?");

                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK) {
                        // get Selected Item
                        Produits currentProduits = (Produits) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                        //remove it from the tableView
                        list.remove(currentProduits);
                        //remove it from the database
                        //MyConnection mc = MyConnection.getInstance();
                        ProduitService ps = new ProduitService();
                        //Piecesdefectueuses p = new Piecesdefectueuses(nom.getText(), combobox.getValue(), description.getText(), image.getText(), 1);
                        ps.deleteProduit(currentProduits.getId());

                    }
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    @FXML
    void ajouertAction(ActionEvent event) {
        if (validateFields()) {
MyConnection mc =  MyConnection.getInstance();
    ProduitService ps = new ProduitService();
     java.sql.Date dateajout = new java.sql.Date(new Date().getTime());
     Produits p = new Produits(-1,libelleTxt.getText(),Double.parseDouble(prixTxt.getText()),Double.parseDouble(prixTxt.getText()),descriptionTxt.getText(),Integer.parseInt(QttTxt.getText()),dateajout,absolutePath,typeTxt.getValue(),marqueTxt.getText());
    ps.ajouterProduit(p);
    /**
             * refreshing the table view *
             */
            list.clear();
            list = FXCollections.observableArrayList(
                    ps.afficherProduits()
            );
            table.setItems(list);
    }
    }

    @FXML
    void ChooseImageAction(ActionEvent event) {
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
            String DynamicPath =System.getProperty("user.dir");

            absolutePath = DynamicPath+"\\src\\Uploads\\" + selectedFile.getName();
            copyFiles.deplacerVers(oldPath, DynamicPath+"\\src\\Uploads\\");

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

    @FXML
    void returnAction(ActionEvent event) throws IOException {
      AnchorPane pane = FXMLLoader.load(getClass().getResource("/Gui/Shop.fxml"));
        mainPane.getChildren().setAll(pane);
    }

    public boolean validateFields(){
    if(libelleTxt.getText().isEmpty()||prixTxt.getText().isEmpty()||QttTxt.getText().isEmpty()||descriptionTxt.getText().isEmpty()||absolutePath.isEmpty()||marqueTxt.getText().isEmpty()||typeTxt.getValue()==null){
    Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("please enter all the information ! ");
            alert.showAndWait();
   return false;
    }
    
    try {  
    Double d= Double.parseDouble(prixTxt.getText());  
    if(d<0){
    Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText(" price > 0 !");
            alert.showAndWait();
    return false;}
    
  } catch(NumberFormatException e){  
      Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("price numeric ! ");
            alert.showAndWait();
    return false;  
  }  
    try {  
    int q =  Integer.parseInt(QttTxt.getText());  
    if(q<0){
    Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("quantity > 0 ! ");
            alert.showAndWait();
    
    
    return false;}
  } catch(NumberFormatException e){  
       Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Fields");
            alert.setHeaderText(null);
            alert.setContentText("quantity numeric ! ");
            alert.showAndWait();
    return false;  
  }  
    
    return true;
    }
    @FXML
    void ModifiertAction(ActionEvent event) {
  if (validateFields()) {
            MyConnection mc = MyConnection.getInstance();
            ProduitService ps = new ProduitService();
            
            Produits p = new Produits(current_id,libelleTxt.getText(),Double.parseDouble(prixTxt.getText()),descriptionTxt.getText(),Integer.parseInt(QttTxt.getText()),absolutePath,typeTxt.getValue(),marqueTxt.getText());
            ps.modifierProduit(p);
            
            /**
             * refreshing the table view *
             */
            list.clear();
            list = FXCollections.observableArrayList(
                    ps.afficherProduits()
            );
            table.setItems(list);
    }
    }
}
