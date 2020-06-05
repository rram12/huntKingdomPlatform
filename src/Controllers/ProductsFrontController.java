/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PiecesDefectueuses;
import Entities.Produits;
import Services.JavaMail;
import Services.JavaMailPayment;
import Services.PieceService;
import Services.ProduitService;
import Services.ReparationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;
import com.stripe.model.Token;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author asus_pc
 */
public class ProductsFrontController implements Initializable {
      @FXML
    private JFXTextField annee_exp;
 @FXML
    private JFXTextField cvc;
   @FXML
    private Label labelPirx;
      @FXML
    private JFXTextField num_carte;
     @FXML
    private StackPane stackPay;

    @FXML
    private JFXTextField moix_exp;
     @FXML
    private Tab paymentPane;
    
    @FXML
    private JFXTabPane tabPane;
       @FXML
        public  Label LabelTotalPrice;
       @FXML
    private VBox vBox;
    @FXML
    private Label labelCart;
    @FXML
    private AnchorPane anchor;
    @FXML
    private AnchorPane anchorCart;
    private Node[] nodes ;
    private    int n;
    String filePath;
   Double tot;
 ArrayList<Produits> produits = new ArrayList<>();
 ArrayList<Produits> cart = new ArrayList<>();
 public static Double totalee;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalee = 0.0;
        nodes = new Node[50];
        n= -1;
         ProduitService ps = new ProduitService();
        this.produits = (ArrayList) ps.afficherProduits();
        ObservableList<Produits> obsl = FXCollections.observableArrayList(produits);
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination = new Pagination();
        // System.out.println("nbPieceReserved : "+pieces.size());

        if (produits.size() % 3 > 0) {
            pagination.setPageCount((produits.size() / 3) + 1);
        } else {
            pagination.setPageCount(produits.size() / 3);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage(pageIndex, produits);
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        
        anchor.getChildren().add(pagination);
    } 
     public FlowPane createPage(int index, ArrayList<Produits> produits) {
        FlowPane flow = new FlowPane();
        Node[] nodes = new Node[4];
        int nbNode = -1;
        index++;
        int n = index + (index - 1) * 2 + 1;
        // System.out.println("n : "+n);
        int deb = n - 2;
        for (int i = deb; i < deb + 3 && i != produits.size(); i++) {
            nbNode++;
            
                try {

                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/Gui/SingleProduct.fxml").openStream());
                    SingleProductController single = (SingleProductController) loader.getController();
                    single.getInfo(produits.get(i));
                    Produits p1 = produits.get(i);
                    JFXButton button = single.getButton();

                  
                        button.setOnAction(e -> {
                            cart.add(p1);
                            
                            labelCart.setText(Integer.toString(cart.size()));
                            //System.out.println("clicked"+p1);
                            AddToCart();
                        });

                   
                    nodes[nbNode] = root;
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/Gui/SinglePiece.fxml"));
                    flow.getChildren().add(nodes[nbNode]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        

        /*  Label lab = new Label("test");
           
        flow.getChildren().add(lab);     */
        return flow;
    }
     
     public void AddToCart(){
        //n++;
        
         try {
                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/Gui/SingleCart.fxml").openStream());
                    SingleCartController single = (SingleCartController) loader.getController();
                    single.getInfo(cart.get(cart.size()-1));
                    Produits p1 = cart.get(cart.size()-1);
                     tot = single.getTotale();
                    Double tot1 = single.getTotale();
                    System.out.println("totale = "+tot);
                    totalee += tot;
                    addTotal(totalee);
                    //LabelTotalPrice.setText(Double.toString(tot));
                    Button button = single.getButton();
                    TextField qtt = single.getTextField();
                     Label lTotale = single.getTotalTxt();
                     Label lPrice = single.getPriceTxt();
                     Double prix = Double.parseDouble(lPrice.getText());
                     int OldQtt = Integer.parseInt(qtt.getText());
                     System.out.println("OldQtt : "+OldQtt);
                    
                    qtt.focusedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasFocused, Boolean isNowFocused) -> {
            if (!isNowFocused) {
                // text field has lost focus...
                if (validateQuantity(qtt)) {
                    
                 lTotale.setText(Double.toString(prix*(Integer.parseInt(qtt.getText()))));   
                 
                  totalee -= (tot*OldQtt);
                    System.out.println("totalee -: "+totalee);
                  totalee+=Double.parseDouble(lTotale.getText());
                  System.out.println("totalee +: "+totalee);

                  addTotal(totalee); 
                  tot = Double.parseDouble(lTotale.getText() );
                }  
            }
        });
        // TODO
                    
                    button.setOnAction(e->{
                    System.out.println(" n = "+(cart.size()-1)+"cart : "+p1);
                    vBox.getChildren().clear();
                    totalee = 0.0;
                    addTotal(totalee);
                    cart.remove(p1);
                    labelCart.setText(Integer.toString(cart.size()));
                    //n--;
                    showAllCart();
                    });
                    
                    nodes[cart.size()-1] = root;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        // vBox.getChildren().clear();
         vBox.getChildren().add(nodes[cart.size()-1]);
         labelPirx.setText(LabelTotalPrice.getText());
             }
     public void showAllCart(){
         for(int i=0;i<cart.size();i++){
     try {

                    FXMLLoader loader = new FXMLLoader();

                    Pane root = loader.load(getClass().getResource("/Gui/SingleCart.fxml").openStream());
                    SingleCartController single = (SingleCartController) loader.getController();
                    single.getInfo(cart.get(i));
                    Produits p1 = cart.get(i);
                    Double tot = single.getTotale();
                    System.out.println("totale = "+tot);
                    totalee += tot;
                    addTotal(totalee);
                    Button button = single.getButton();
                    button.setOnAction(e->{
                    //System.out.println(" n = "+n+"cart : "+p1);
                    vBox.getChildren().clear();
                    totalee = 0.0;
                    addTotal(totalee);
                    cart.remove(p1);
                    showAllCart();
                    });
                    
                    nodes[i] = root;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        // vBox.getChildren().clear();
         vBox.getChildren().add(nodes[i]);
      
         }
         labelPirx.setText(LabelTotalPrice.getText());
     }
    public  void addTotal(Double tot){
    LabelTotalPrice.setText(Double.toString(tot));
    }
    
    
      public Label getLabelTotalPrice() {
        return LabelTotalPrice;
    }

    public void setLabelTotalPrice(Label LabelTotalPrice) {
        this.LabelTotalPrice = LabelTotalPrice;
    }

    public Double getTotalee() {
        return totalee;
    }

    public void setTotalee(Double totalee) {
        this.totalee = totalee;
    }
 boolean validateQuantity(TextField text) {

        try {
            int qtt = Integer.parseInt(text.getText());
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

    
    
    
    @FXML
    void checkOutAction(ActionEvent event) {
         SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                            selectionModel.select(2); //select by index starting with 0
                            paymentPane.setDisable(false);
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
            
            Double prixCart = Double.parseDouble(LabelTotalPrice.getText());
            int prix = prixCart.intValue();
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
                        vBox.getChildren().clear();
                        totalee = 0.0;
                        labelCart.setText("0");
                        LabelTotalPrice.setText("0.0");
                        labelPirx.setText("0.0");
                        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
                        selectionModel.select(0); //select by index starting with 0
                        paymentPane.setDisable(true);
                        String filePath = QRcode();
                        creatpdf();
                        System.out.println("cart : "+cart);
                        try {
                            JavaMailPayment.sendMail(EmailStatique,filePath);
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
    public String QRcode(){
             
   
     
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
 
        LocalDateTime today = LocalDateTime.now();
        String dateS = today.toString();
        dateS = dateS.replace(':','A');
        dateS+="Khalil";
        String myCodeText = "Thanks for using our application and your trust in us Your goods will reach you soon .. command Date : "+today.toString();
         filePath = "E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\"+dateS+".png";
      
        int size = 250;
        String fileType = "png";
        File myFile = new File(filePath);
        try {
            //chn7a4ar el hint map mte3i eli chnestoki fyha 
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            //hintMap.put(EncodeHintType.MARGIN, 1);
            /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            // creation qr
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
                    size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created QR Code.");
        return filePath;
    }
    
    private void creatpdf() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
 
        LocalDateTime today = LocalDateTime.now();
        String dateS = today.toString();
        String now = today.toString();
        dateS = dateS.replace(':','A');
        try {
            String out = "E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\bill"+dateS+".pdf";
            Document document = new Document();
            StringBuilder boxText = new StringBuilder();
            PdfWriter.getInstance(document, new FileOutputStream(out));
            document.open();
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\logo2.png");
            logo.scaleToFit(200, 70);
            document.add(logo);
            document.add(new Paragraph("\n\n\n"));
            document.add(new Paragraph("Bill :\n\n\nDate : "+now));
            PdfPTable table = new PdfPTable(4); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            PdfPCell cell1 = new PdfPCell(new Paragraph("Image"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Libelle"));
            cell2.setBorderColor(BaseColor.BLUE);
            cell2.setPaddingLeft(5);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Description"));
            cell3.setBorderColor(BaseColor.BLUE);
            cell3.setPaddingLeft(5);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell4 = new PdfPCell(new Paragraph("Prix"));
            cell3.setBorderColor(BaseColor.BLUE);
            cell3.setPaddingLeft(5);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            document.add(table);
            PdfPTable table1 = new PdfPTable(4); // 3 columns.
            table1.setWidthPercentage(100); //Width 100%
            table1.setSpacingBefore(10f); //Space before table
            table1.setSpacingAfter(10f); //Space after table
            for(Produits p : cart){
            com.itextpdf.text.Image x = com.itextpdf.text.Image.getInstance("E:\\roadToInfini\\java\\projet\\FinalGit\\huntkingdom\\src\\Uploads\\logo2.png");
               x.scaleToFit(100, 100);
            PdfPCell image = new PdfPCell(x);
            image.setBorderColor(BaseColor.BLUE);
            image.setPaddingLeft(10);
            image.setHorizontalAlignment(Element.ALIGN_CENTER);
            image.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_titre = new PdfPCell(new Paragraph(p.getLib_prod()));
            txt_titre.setBorderColor(BaseColor.BLUE);
            txt_titre.setPaddingLeft(5);
            txt_titre.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_titre.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_desc = new PdfPCell(new Paragraph(p.getDescription()));
            txt_desc.setBorderColor(BaseColor.BLUE);
            txt_desc.setPaddingLeft(5);
            txt_desc.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_desc.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell txt_prix = new PdfPCell(new Paragraph(Double.toString(p.getPrixFinale())));
            txt_prix.setBorderColor(BaseColor.BLUE);
            txt_prix.setPaddingLeft(5);
            txt_prix.setHorizontalAlignment(Element.ALIGN_CENTER);
            txt_prix.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(image);
            table1.addCell(txt_titre);
            table1.addCell(txt_desc);
            table1.addCell(txt_prix);
            }
            document.add(table1);
            com.itextpdf.text.Image qrc = com.itextpdf.text.Image.getInstance(filePath);
            qrc.scaleToFit(200, 200);
            document.add(qrc);
            document.close();
           
            System.out.println("Document '" + out + "' generated");
             try {
                        Desktop.getDesktop().open(new java.io.File(out));
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
        } catch (FileNotFoundException ex) {
        } catch (DocumentException ex) {
        } catch (IOException ex) {
        }

        
    }
    
    
}
