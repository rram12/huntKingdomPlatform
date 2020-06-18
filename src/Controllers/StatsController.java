/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import Services.UserService;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author ramzi.raddaoui@esprit.tn
 */
public class StatsController implements Initializable {

    int num = 1;
    
    UserService us = new UserService();

    @FXML
    private PieChart pchart;
    @FXML
    private ChoiceBox<String> cbCriteria;
    @FXML
    private Button btExportPDF;
    @FXML
    private Button btBack;
    @FXML
    private AnchorPane pchartNode;
    @FXML
    private ImageView bg;
    @FXML
    private AnchorPane apMain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        try {
//           // bg.setImage(new javafx.scene.image.Image(new FileInputStream("res/images/hunting-and-fishing-wallpaper_2719072.jpg")));
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(AdminUsersListController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        cbCriteria.getItems().addAll("Country", "Gender");
        cbCriteria.setValue("Gender");

        ObservableList<PieChart.Data> pDataGender = FXCollections.observableArrayList();

        int nMale = (int) us.countGender().get("Male");
        int nFemale = (int) us.countGender().get("Female");
        System.out.println(nMale);
        System.out.println(nFemale);
        pDataGender.add(new PieChart.Data("Male", nMale));
        pDataGender.add(new PieChart.Data("Female", nFemale));

                ObservableList<PieChart.Data> pDataCoutry = FXCollections.observableArrayList();
        HashMap<String, Integer> hm = new HashMap();
        hm = us.countCountries();
        System.out.println("hmC"+hm);
        int n;
        for(String key : hm.keySet()){
            n = hm.get(key);
            pDataCoutry.add(new PieChart.Data(key, n));
        }
        
        

        pchart.setData(pDataGender);

        cbCriteria.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
            if (wasShowing) {
                if (cbCriteria.getValue() == "Gender") {
                    pchart.setData(pDataGender);
                } else if (cbCriteria.getValue() == "Country") {
                    pchart.setData(pDataCoutry);
                }
            }
            pchart.getData().forEach(data -> {
            String num = ""+(int)data.getPieValue() ;
            Tooltip toolTip = new Tooltip(num);
            Tooltip.install(data.getNode(), toolTip);
        
        });
        });

        pchart.getData().forEach(data -> {
            String num = ""+(int)data.getPieValue() ;
            Tooltip toolTip = new Tooltip(num);
            Tooltip.install(data.getNode(), toolTip);
        });

    }

    @FXML
    private void exportToPDF(ActionEvent event) {
        try {
            
            Document doc = new Document();
               FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
 
            Stage stage = (Stage) apMain.getScene().getWindow();
            File saveToFile = fileChooser.showSaveDialog(stage);
            //Show save file dialog return null if no selection(cancel) was made ==> nullPointerExcep
            while(saveToFile == null){
                saveToFile = fileChooser.showSaveDialog(stage);
            }
            System.out.println(saveToFile);
            WritableImage wimg = pchartNode.snapshot(new SnapshotParameters(), null);
            File file = new File("pchart"+num+".png");
            ImageIO.write(SwingFXUtils.fromFXImage(wimg, null), "png", file);
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(saveToFile));
            doc.open();
            Image img = Image.getInstance("pchart"+num+".png");
            doc.add(img);
            doc.close();
            
         
            
 
            
            Desktop.getDesktop().open(saveToFile);
            writer.close();
            file.delete();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getCause());
        }
    }

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

}
