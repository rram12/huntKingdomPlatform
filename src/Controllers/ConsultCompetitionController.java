/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Competition;
import Entities.Participation;
import Entities.User;
import Services.CompetitionService;
import Services.ParticipationService;
import Services.UserService;
import Utils.Mailing;
import Utils.MyConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ConsultCompetitionController implements Initializable {

    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor;

    @FXML
    private Label errorparticipants;

    @FXML
    private JFXButton particip;

    @FXML
    private Tab participate;

    @FXML
    private Label ends;

    @FXML
    private Label name;

    @FXML
    private Tab competition;

    @FXML
    private JFXButton cancel;

    @FXML
    private Label errorcancel;

    @FXML
    private Label place;

    @FXML
    private Label starts;

    @FXML
    private Label category;

    @FXML
    private Label participant;

    @FXML
    private JFXTabPane tabCompetition;
    private int id;
    Competition cm;
    private int idu;
    
    User currentUser = LoginController.getInstance().getLoggedUser();
    ArrayList<Competition> competitions = new ArrayList<>();
    ArrayList<Competition> Mycompetitions = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {



        tabCompetition.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (oldTab == participate) {
                participate.setDisable(true);
            }
        });
        CompetitionService cs = new CompetitionService();
        competitions = (ArrayList<Competition>) cs.afficher();
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination = new Pagination();

        if (competitions.size() % 3 > 0) {
            pagination.setPageCount((competitions.size() / 3) + 1);
        } else {
            pagination.setPageCount(competitions.size() / 3);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage(pageIndex, competitions);
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().add(pagination);

        /////////////////////////////////////////////////////////////
        Mycompetitions = (ArrayList<Competition>) cs.MyCompetitions(currentUser.getId());
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination1 = new Pagination();

        if (Mycompetitions.size() % 3 > 0) {
            pagination1.setPageCount((Mycompetitions.size() / 3) + 1);
        } else {
            pagination1.setPageCount(Mycompetitions.size() / 3);
        }

        pagination1.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage1(pageIndex, Mycompetitions);
            }
        });

        AnchorPane.setTopAnchor(pagination1, 10.0);
        AnchorPane.setBottomAnchor(pagination1, 10.0);
        AnchorPane.setRightAnchor(pagination1, 10.0);
        AnchorPane.setLeftAnchor(pagination1, 10.0);
        anchor1.getChildren().add(pagination1);

    }

    public FlowPane createPage(int index, ArrayList<Competition> competitions) {
        FlowPane flow = new FlowPane();
        Node[] nodes = new Node[3];
        int nbNode = -1;
        index++;
        int n = index + (index - 1) * 2 + 1;
        // System.out.println("n : "+n);
        int deb = n - 2;
        for (int i = deb; i < deb + 3 && i != competitions.size(); i++) {
            nbNode++;

            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/singleCompetition.fxml").openStream());
                SingleCompetitionController single = (SingleCompetitionController) loader.getController();
                single.getInfo(competitions.get(i));
                int id1 = single.getCurrentId();
                Competition m = competitions.get(i);
                
                JFXButton button = single.getButton();

                button.setOnAction(e -> {
                    cancel.setVisible(false);
                    errorcancel.setVisible(false);
                    particip.setVisible(true);
                    cm = m;
                    participate.setDisable(false);
                    name.setText(m.getNom());
                    starts.setText(m.getDateDebut().toString());
                    ends.setText((m.getDateFin().toString()));
                    category.setText(m.getCategorie());
                    place.setText(m.getLieu());
                    participant.setText(Integer.toString(m.getNbParticipants()));
                    int participated = 0;
                    try {
                        participated = checkparticipations(cm.getId(), currentUser.getId());
                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultCompetitionController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (participated != 0) {
                        particip.setOnAction(null);
                        particip.setStyle("-fx-background-color: red ; -fx-text-fill: white;");
                        particip.setText("Already Participated");

                    } else if (m.getNbParticipants() == 0) {
                        particip.setOnAction(null);
//                       particip.setDisable(true);
                        particip.setStyle("-fx-background-color: red ; -fx-text-fill: white;");
                        particip.setText("No more places");
                    } else {
                        particip.setStyle("-fx-background-color: green ; -fx-text-fill: white;");
                        particip.setText("Participate");
                        particip.setOnAction(a -> {
                            ParticipationService pl = new ParticipationService();
                            Participation c = new Participation(currentUser.getId(), id);
                            if (pl.addParticipation(c)) {
                                participant.setText(Integer.toString(Integer.parseInt(participant.getText()) - 1));
                                pl.decrementerParticipants(id);
//                                String maill = currentUser.getEmail();
                                Mailing ma = new Mailing();
                                try {
                                    ma.envoyer(currentUser, cm);
                                } catch (UnsupportedEncodingException ex) {
                                    Logger.getLogger(ConsultCompetitionController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                refresh();
                            }
                        });

                    }
                    SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
                    selectionModel.select(2); //select by index starting with 0
                    this.id = cm.getId();
                });

                nodes[nbNode] = root;
                flow.getChildren().add(nodes[nbNode]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return flow;
    }

    public FlowPane createPage1(int index, ArrayList<Competition> Mycompetitions) {
        FlowPane flow = new FlowPane();
        Node[] nodes = new Node[4];
        int nbNode = -1;
        index++;
        int n = index + (index - 1) * 2 + 1;
        // System.out.println("n : "+n);
        int deb = n - 2;
        for (int i = deb; i < deb + 3 && i != Mycompetitions.size(); i++) {
            nbNode++;

            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/singleCompetition.fxml").openStream());
                SingleCompetitionController single = (SingleCompetitionController) loader.getController();
                single.getInfo(Mycompetitions.get(i));
                Competition m = Mycompetitions.get(i);
                JFXButton button = single.getButton();

                button.setOnAction(e -> {
                    particip.setVisible(false);
                    cm = m;
                    participate.setDisable(false);
                    name.setText(m.getNom());
                    starts.setText(m.getDateDebut().toString());
                    ends.setText((m.getDateFin().toString()));
                    category.setText(m.getCategorie());
                    place.setText(m.getLieu());
                    participant.setText(Integer.toString(m.getNbParticipants()));
                    Date date = new Date();
                    long diff = m.getDateDebut().getTime() - date.getTime();
                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    if (diffDays > 4) {
                        cancel.setVisible(true);
                        errorcancel.setVisible(false);
                    } else {
                        cancel.setVisible(false);
                        errorcancel.setVisible(true);
                    }

                    SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
                    selectionModel.select(2); //select by index starting with 0
                    this.id = cm.getId();
                });
                nodes[nbNode] = root;
                flow.getChildren().add(nodes[nbNode]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return flow;
    }

    void refresh() {
        anchor.getChildren().clear();
        CompetitionService cs = new CompetitionService();
        competitions = (ArrayList<Competition>) cs.afficher();
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination = new Pagination();
        // System.out.println("nbPieceReserved : "+pieces.size());

        if (competitions.size() % 3 > 0) {
            pagination.setPageCount((competitions.size() / 3) + 1);
        } else {
            pagination.setPageCount(competitions.size() / 3);
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage(pageIndex, competitions);
            }
        });

        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().add(pagination);
    }

    void refresh1() {
        anchor1.getChildren().clear();
        CompetitionService cs = new CompetitionService();

        Mycompetitions = (ArrayList<Competition>) cs.MyCompetitions(currentUser.getId());
        /**
         * ****** PAGINATION **********
         */
        Pagination pagination1 = new Pagination();

        if (Mycompetitions.size() % 3 > 0) {
            pagination1.setPageCount((Mycompetitions.size() / 3) + 1);
        } else {
            pagination1.setPageCount(Mycompetitions.size() / 3);
        }

        pagination1.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {  // every time when you click pagination button this method will be called

                return createPage1(pageIndex, Mycompetitions);
            }
        });

        AnchorPane.setTopAnchor(pagination1, 10.0);
        AnchorPane.setBottomAnchor(pagination1, 10.0);
        AnchorPane.setRightAnchor(pagination1, 10.0);
        AnchorPane.setLeftAnchor(pagination1, 10.0);
        anchor1.getChildren().add(pagination1);
    }

    @FXML
    void cancelParticipation(ActionEvent event) throws UnsupportedEncodingException {
        ParticipationService ps = new ParticipationService();
        Participation c = new Participation(id, currentUser.getId());
        if(ps.addParticipation(c)){
        participant.setText(Integer.toString(Integer.parseInt(participant.getText())-1));
        ps.decrementerParticipants(id);
        String maill = currentUser.getEmail();
        Mailing ma = new Mailing();
        ma.envoyer(currentUser,cm);
        System.out.println(maill);
        }
        
        System.out.println(id);
        if (ps.annulerParticipation(c)) {
            participant.setText(Integer.toString(Integer.parseInt(participant.getText()) + 1));
            ps.incrementerParticipants(id);
            refresh();
            refresh1();
            participate.setDisable(true);
            SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
            selectionModel.select(1); //select by index starting with 0
        }

    }

    public int checkparticipations(int idcompetition, int iduser) throws SQLException {
        ParticipationService pss = new ParticipationService();
        return pss.checkParticipation(idcompetition, iduser);
    }
}
