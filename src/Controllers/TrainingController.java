/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.AddTrainingController.showAlert;
import Entities.Entrainement;
import Services.AnimalService;
import Services.TrainingService;
import Services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author tibh
 */
public class TrainingController implements Initializable {

    @FXML
    private Pane last;
    @FXML
    private Label catlast;
    @FXML
    private Label hnlast;
    @FXML
    private Label pricelast;
    @FXML
    private Label tdlast;
    @FXML
    private Label tplast;
    @FXML
    private ImageView acceptlast;
    @FXML
    private ImageView animallast;
    @FXML
    private ImageView produitlast;

    private Entrainement globalCurrent;

    private List<Entrainement> entr = new ArrayList<Entrainement>();

    @FXML
    private HBox tcontainer;
    @FXML
    private Pane examplePane;
    @FXML
    private Button Add;
    @FXML
    private AnchorPane main;
    @FXML
    private ImageView imageAdd;
    @FXML
    private ScrollPane scrol;
    @FXML
    private Label trainerLast;
    @FXML
    private Pane secondMain;
    @FXML
    private Pane exemple2Pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();
        Services.UserService SU = new UserService();

        globalCurrent = new Entrainement();
        examplePane.setVisible(false);
        exemple2Pane.setVisible(false);
        trainerLast.setVisible(false);
        imageAdd.setImage(new Image("/Uploads/blog.png"));
        try {

            Entrainement elast = SEntr.getById(SEntr.getLastTraining());

            String username = SU.getUsername(elast.getEntraineurId());
            catlast.setText("Training For " + elast.getCategorie());
            hnlast.setText("Hours Number : " + elast.getNbHeures());
            pricelast.setText("Price : " + elast.getPrix() + "DT");
            tdlast.setText("Training Date : " + elast.getDateEnt());
            tplast.setText("Training Place : " + elast.getLieu());

            if ("encours".equals(elast.getAccepter())) {
                acceptlast.setImage(new Image("/Uploads/loading.gif"));
            } else {
                trainerLast.setVisible(true);
                trainerLast.setText("Trainer : " + username);
                acceptlast.setImage(new Image("/Uploads/checked.png"));
            }
            Image animalImage = new Image("Uploads/" + SA.getById(elast.getAnimalId()).getImage_animal());
            animallast.setImage(animalImage);
            produitlast.setImage(new Image("/Uploads/image.jpg"));
            ImageView supp = new ImageView();
            supp.setFitHeight(20);
            supp.setFitWidth(20);
            supp.setPreserveRatio(false);
            Image sup = new Image("Uploads/bin.png");
            supp.setImage(sup);
            Button supprimer = new Button("", supp);
            supprimer.setStyle("-fx-background-color: transparent");

            supprimer.setLayoutX(440);
            supprimer.setLayoutY(0);

            supprimer.setOnMouseClicked((MouseEvent e) -> {
                Services.TrainingService ST = new TrainingService();

                try {
                    showAlert(Alert.AlertType.CONFIRMATION, "", "", "Are You Sure !");
                    ST.deleteTraining(elast.getId());
                    AnchorPane redirected;
                    redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                    main.getChildren().setAll(redirected);
                } catch (SQLException ex) {
                    Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            String format = "dd/MM/yyyy";

                java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
                Date date = new Date();
                Date d = formater.parse(formater.format(date));
                System.out.println(formater.format(d));
                Date datee;
                datee = elast.getDateEnt();
                System.out.println("elast : "+elast);
                System.out.println("datee : "+datee);
                System.out.println("d : "+d);
                if(d.compareTo(datee)>0)
                {

                    Button Like = new Button();
                    ImageView HeartImage = new ImageView();
                        HeartImage.setFitHeight(40);
                        HeartImage.setFitWidth(40);
                        HeartImage.setPreserveRatio(false);
                        Image h = new Image("Uploads/heart.png");
                        HeartImage.setImage(h);
                        Like = new Button("", HeartImage);
                    Like.setStyle("-fx-background-color: transparent");

                        Like.setLayoutX(350);
                        Like.setLayoutY(220);
                         if(SEntr.Test(elast.getId())==0)
                     last.getChildren().add(Like);
                     else if(SEntr.Test(elast.getId())==1)
                     {
                         Like.setVisible(false);
                     ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/hearted.png");
                        heartImage.setImage(hh);
                        

                        heartImage.setLayoutX(370);
                        heartImage.setLayoutY(225);
                         last.getChildren().add(heartImage);
                     }
                      else if(SEntr.Test(elast.getId())==2)
                     {
                         Like.setVisible(false);
                     ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/broken-heart.png");
                        heartImage.setImage(hh);
                        

                        heartImage.setLayoutX(370);
                        heartImage.setLayoutY(225);
                         last.getChildren().add(heartImage);
                     }

                        
                         Like.setOnMouseClicked((MouseEvent e) -> {
                        Pane flou = new Pane();
                        flou.setPrefHeight(200);
                        flou.setPrefWidth(300);
                        flou.setLayoutX(670);
                        flou.setLayoutY(21);
                        //flou.setStyle("-fx-background-color: Black;-fx-opacity: 0.37");

                        ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/hearted.png");
                        heartImage.setImage(hh);
                        Button Liked = new Button("", heartImage);
                        Liked.setStyle("-fx-background-color: transparent");

                        Liked.setLayoutX(85);
                        Liked.setLayoutY(128);

                        flou.getChildren().add(Liked);
                        Liked.setOnMouseClicked((MouseEvent v) -> {
                            showAlert(Alert.AlertType.INFORMATION, "", "", "Your Opinion Is Added Successfully !");
                            try {
                                 
                                Services.TrainingService ST = new TrainingService();
                                
                                ST.updateLike("heart", elast.getId());
                                flou.setVisible(false);
                                 
                                AnchorPane redirected;
                                redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                                main.getChildren().setAll(redirected);
                            } catch (IOException ex) {
                                Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                    
                        ImageView unheartImage = new ImageView();
                        unheartImage.setFitHeight(40);
                        unheartImage.setFitWidth(40);
                        unheartImage.setPreserveRatio(false);
                        Image hhh = new Image("Uploads/broken-heart.png");
                        unheartImage.setImage(hhh);
                        Button unLiked = new Button("", unheartImage);
                        unLiked.setStyle("-fx-background-color: transparent");

                        unLiked.setLayoutX(175);
                        unLiked.setLayoutY(128);
                        flou.getChildren().add(unLiked);
                        unLiked.setOnMouseClicked((MouseEvent b) -> {
                            showAlert(Alert.AlertType.INFORMATION, "", "", "Your Opinion Is Added Successfully !");
                            try {
                                Services.TrainingService ST = new TrainingService();

                                ST.updateLike("unheart", elast.getId());
                                flou.setVisible(false);

                                AnchorPane redirected;
                                redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                                main.getChildren().setAll(redirected);
                            } catch (IOException ex) {
                                Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        Label question = new Label();
                        question.setTextFill(Color.WHITE);
                        question.setStyle("-fx-font-size: 14px;-fx-font-weight: bold");
                        //question.setStyle("-fx-font-weight: bold");
                        question.setText("Are you Like or dislike the trainer : " + username);
                        question.setLayoutX(15);
                        question.setLayoutY(31);

                        flou.getChildren().add(question);

                        Label TrainingDate = new Label();
                        TrainingDate.setTextFill(Color.WHITE);
                        TrainingDate.setStyle("-fx-font-weight: bold");
                        TrainingDate.setText("Training Date: " + elast.getDateEnt());
                        TrainingDate.setLayoutX(37);
                        TrainingDate.setLayoutY(83);
                        flou.getChildren().add(TrainingDate);

                        secondMain.getChildren().add(flou);

                    });
                }
            last.getChildren().add(supprimer);
            List<Entrainement> e = SEntr.getTrainingsUser(LoginController.getInstance().getLoggedUser().getId());
            System.out.println(e);
            afficher(e);

        } catch (SQLException ex) {
            Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void afficher(List<Entrainement> entrs) throws SQLException, ParseException {

        Services.TrainingService SEntr = new TrainingService();
        Services.AnimalService SA = new AnimalService();
        Services.UserService SU = new UserService();

        tcontainer.getChildren().clear();

        for (int i = 0; i < entrs.size(); i++) {
            Entrainement current = entrs.get(i);
            String username = SU.getUsername(current.getEntraineurId());
            Pane entpane = new Pane();
            entpane.setPrefHeight(256);
            entpane.setPrefWidth(200);

            if (current.getId() == SEntr.getLastTraining()) {
                entpane.getChildren().clear();
            } else {

                Label categorie = new Label();
                categorie.setTextFill(Color.WHITE);
                categorie.setStyle("-fx-font-weight: bold");
                //categorie.setStyle("-fx-font-size: 20px");
                categorie.setText("Training For " + current.getCategorie());
                categorie.setLayoutX(1);
                categorie.setLayoutY(1);
                entpane.getChildren().add(categorie);

                final ImageView animalImage = new ImageView();

                animalImage.setLayoutX(13);
                animalImage.setLayoutY(19);
                animalImage.setFitHeight(70);
                animalImage.setFitWidth(70);

                Image animal = new Image("Uploads/" + SA.getById(current.getAnimalId()).getImage_animal());
                animalImage.setImage(animal);

                entpane.getChildren().add(animalImage);

                final ImageView produitImage = new ImageView();

                produitImage.setLayoutX(110);
                produitImage.setLayoutY(19);
                produitImage.setFitHeight(70);
                produitImage.setFitWidth(70);

                produitImage.setImage(new Image("/Uploads/image.jpg"));

                entpane.getChildren().add(produitImage);

                Label hn = new Label();
                hn.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                hn.setText("Hours Number : " + current.getNbHeures());
                hn.setLayoutX(21);
                hn.setLayoutY(112);
                entpane.getChildren().add(hn);

                Label price = new Label();
                price.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                price.setText("Price : " + current.getPrix() + "DT");
                price.setLayoutX(21);
                price.setLayoutY(139);
                entpane.getChildren().add(price);

                Label dateT = new Label();
                dateT.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                dateT.setText("Training Date : " + current.getDateEnt());
                dateT.setLayoutX(21);
                dateT.setLayoutY(166);
                entpane.getChildren().add(dateT);

                Label place = new Label();
                place.setTextFill(Color.WHITE);
                //categorie.setStyle("-fx-font-weight: bold");
                place.setText("Training Place : " + current.getLieu());
                place.setLayoutX(21);
                place.setLayoutY(196);
                entpane.getChildren().add(place);

                final ImageView acceptImage = new ImageView();

                acceptImage.setLayoutX(183);
                acceptImage.setLayoutY(226);
                acceptImage.setFitHeight(45);
                acceptImage.setFitWidth(45);

                if ("encours".equals(current.getAccepter())) {
                    acceptImage.setImage(new Image("/Uploads/loading.gif"));
                } else {
                    Label trainer = new Label();
                    trainer.setTextFill(Color.WHITE);
                    trainer.setText("Trainer : " + username);
                    trainer.setLayoutX(21);
                    trainer.setLayoutY(222);
                    entpane.getChildren().add(trainer);

                    acceptImage.setImage(new Image("/Uploads/checked.png"));
                }

                entpane.getChildren().add(acceptImage);

                ImageView supp = new ImageView();
                supp.setFitHeight(20);
                supp.setFitWidth(20);
                supp.setPreserveRatio(false);
                Image sup = new Image("Uploads/bin.png");
                supp.setImage(sup);
                Button supprimer = new Button("", supp);
                supprimer.setStyle("-fx-background-color: transparent");

                supprimer.setLayoutX(183);
                supprimer.setLayoutY(190);

                supprimer.setOnMouseClicked((MouseEvent e) -> {
                    Services.TrainingService ST = new TrainingService();
                    try {
                        ST.deleteTraining(current.getId());
                        AnchorPane redirected;
                        redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                        main.getChildren().setAll(redirected);
                    } catch (SQLException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                entpane.getChildren().add(supprimer);

                String format = "dd/MM/yyyy";

                java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
                Date date = new Date();
                Date d = formater.parse(formater.format(date));
                System.out.println(formater.format(d));
                Date datee;
                datee = current.getDateEnt();
                if (d.compareTo(datee) == 1) {

                    Button Like = new Button();
                    ImageView HeartImage = new ImageView();
                        HeartImage.setFitHeight(40);
                        HeartImage.setFitWidth(40);
                        HeartImage.setPreserveRatio(false);
                        Image h = new Image("Uploads/heart.png");
                        HeartImage.setImage(h);
                        Like = new Button("", HeartImage);
                    Like.setStyle("-fx-background-color: transparent");

                        Like.setLayoutX(135);
                        Like.setLayoutY(226);
                  

                   
                        
                      
                    
                    Like.setOnMouseClicked((MouseEvent e) -> {
                        Pane flou = new Pane();
                        flou.setPrefHeight(200);
                        flou.setPrefWidth(300);
                        flou.setLayoutX(670);
                        flou.setLayoutY(21);
                        //flou.setStyle("-fx-background-color: Black;-fx-opacity: 0.37");

                        ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/hearted.png");
                        heartImage.setImage(hh);
                        Button Liked = new Button("", heartImage);
                        Liked.setStyle("-fx-background-color: transparent");

                        Liked.setLayoutX(85);
                        Liked.setLayoutY(128);

                        flou.getChildren().add(Liked);
                        Liked.setOnMouseClicked((MouseEvent v) -> {
                            showAlert(Alert.AlertType.INFORMATION, "", "", "Your Opinion Is Added Successfully !");
                            try {
                                 
                                Services.TrainingService ST = new TrainingService();
                                
                                ST.updateLike("heart", current.getId());
                                flou.setVisible(false);
                                 
                                AnchorPane redirected;
                                redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                                main.getChildren().setAll(redirected);
                            } catch (IOException ex) {
                                Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                    
                        ImageView unheartImage = new ImageView();
                        unheartImage.setFitHeight(40);
                        unheartImage.setFitWidth(40);
                        unheartImage.setPreserveRatio(false);
                        Image hhh = new Image("Uploads/broken-heart.png");
                        unheartImage.setImage(hhh);
                        Button unLiked = new Button("", unheartImage);
                        unLiked.setStyle("-fx-background-color: transparent");

                        unLiked.setLayoutX(175);
                        unLiked.setLayoutY(128);
                        flou.getChildren().add(unLiked);
                        unLiked.setOnMouseClicked((MouseEvent b) -> {
                            showAlert(Alert.AlertType.INFORMATION, "", "", "Your Opinion Is Added Successfully !");
                            try {
                                Services.TrainingService ST = new TrainingService();

                                ST.updateLike("unheart", current.getId());
                                flou.setVisible(false);

                                AnchorPane redirected;
                                redirected = FXMLLoader.load(getClass().getResource("/Gui/Training.fxml"));
                                main.getChildren().setAll(redirected);
                            } catch (IOException ex) {
                                Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        Label question = new Label();
                        question.setTextFill(Color.WHITE);
                        question.setStyle("-fx-font-size: 14px;-fx-font-weight: bold");
                        //question.setStyle("-fx-font-weight: bold");
                        question.setText("Are you Like or dislike the trainer : " + username);
                        question.setLayoutX(15);
                        question.setLayoutY(31);

                        flou.getChildren().add(question);

                        Label TrainingDate = new Label();
                        TrainingDate.setTextFill(Color.WHITE);
                        TrainingDate.setStyle("-fx-font-weight: bold");
                        TrainingDate.setText("Training Date: " + current.getDateEnt());
                        TrainingDate.setLayoutX(37);
                        TrainingDate.setLayoutY(83);
                        flou.getChildren().add(TrainingDate);

                        secondMain.getChildren().add(flou);

                    });
                     
                     if(SEntr.Test(current.getId())==0)
                     entpane.getChildren().add(Like);
                     else if(SEntr.Test(current.getId())==1)
                     {
                         Like.setVisible(false);
                     ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/hearted.png");
                        heartImage.setImage(hh);
                        

                        heartImage.setLayoutX(135);
                        heartImage.setLayoutY(226);
                    
                         entpane.getChildren().add(heartImage);
                     }
                      else if(SEntr.Test(current.getId())==2)
                     {
                         Like.setVisible(false);
                     ImageView heartImage = new ImageView();
                        heartImage.setFitHeight(40);
                        heartImage.setFitWidth(40);
                        heartImage.setPreserveRatio(false);
                        Image hh = new Image("Uploads/broken-heart.png");
                        heartImage.setImage(hh);
                        

                        heartImage.setLayoutX(135);
                        heartImage.setLayoutY(226);
                         entpane.getChildren().add(heartImage);
                     }

                }
            }

            tcontainer.getChildren().add(entpane);
            tcontainer.setStyle("-fx-background-color: transparent;");
            scrol.setStyle("-fx-background-color: transparent;");

        }
    }

    @FXML
    private void btnAddAction(ActionEvent event) {
        try {
            AnchorPane addpane = FXMLLoader.load(getClass().getResource("/Gui/AddTraining.fxml"));
            main.getChildren().setAll(addpane);
        } catch (IOException ex) {
            Logger.getLogger(TrainingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    
}
