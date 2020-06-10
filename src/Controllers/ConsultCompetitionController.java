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
import Utils.UserSession;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ConsultCompetitionController implements Initializable {

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
    private FlowPane flow;

    @FXML
    private FlowPane flow1;

    @FXML
    private Label participant;

    @FXML
    private JFXTabPane tabCompetition;
    private int id;
    Competition cm;
    private int idu;
    UserSession currentUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Services.UserService SU = new UserService();
        currentUser = UserSession.getInstace("", 0, "", "", "", 0);
        flow.getChildren().clear();
        displayCompetition();
        flow1.getChildren().clear();
        displayMyCompetitions();

        tabCompetition.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (oldTab == participate) {
                participate.setDisable(true);
            }
        });

    }

    private void displayCompetition() {
//        ArrayList <Competition> trans = new ArrayList<>();
        ArrayList<Competition> trans = new ArrayList<>();
        CompetitionService ps = new CompetitionService();
        trans = (ArrayList) ps.afficher();
        ObservableList<Competition> obsl = FXCollections.observableArrayList(trans);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/singleCompetition.fxml").openStream());
                SingleCompetitionController single = (SingleCompetitionController) loader.getController();
                single.getInfo(trans.get(i));
                int id1 = single.getCurrentId();
                Competition m = trans.get(i);

                JFXButton button = single.getButton();
                button.setText("Consult");
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
                        participated = checkparticipations(id1, currentUser.getId());
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
//                                System.out.println(maill);
                                flow.getChildren().clear();
                                displayCompetition();
                                flow1.getChildren().clear();
                                displayMyCompetitions();
                            }
                        });
                        particip.setStyle("-fx-background-color: green ; -fx-text-fill: white;");
                        particip.setText("Participate");
                    }
                    SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
                    selectionModel.select(2); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayMyCompetitions() {
//        ArrayList <Competition> trans = new ArrayList<>();
//        ArrayList<Competition> trans = new ArrayList<>();
        CompetitionService ps = new CompetitionService();
        ArrayList<Competition> trans = (ArrayList) ps.MyCompetitions(currentUser.getId());
        ObservableList<Competition> obsl = FXCollections.observableArrayList(trans);
        Node[] nodes = new Node[obsl.size()];
        for (int i = 0; i < nodes.length; i++) {
            try {

                FXMLLoader loader = new FXMLLoader();

                Pane root = loader.load(getClass().getResource("/Gui/singleCompetition.fxml").openStream());
                SingleCompetitionController single = (SingleCompetitionController) loader.getController();
                single.getInfo(trans.get(i));
                int id1 = single.getCurrentId();
                Competition m = trans.get(i);

                JFXButton button = single.getButton();
                button.setText("Consult");
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
        if (diffDays > 7) {
            cancel.setVisible(true);
            errorcancel.setVisible(false);
        } else {
            cancel.setVisible(false);
                    errorcancel.setVisible(true);
        }

                    SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
                    selectionModel.select(2); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow1.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addParticipation(ActionEvent event) throws UnsupportedEncodingException {
        MyConnection mc = MyConnection.getInstance();
        ParticipationService ps = new ParticipationService();
        System.out.println(id);
        Participation c = new Participation(currentUser.getId(), id);
        if (ps.addParticipation(c)) {
            participant.setText(Integer.toString(Integer.parseInt(participant.getText()) - 1));
            ps.decrementerParticipants(id);
            String maill = currentUser.getEmail();
            Mailing ma = new Mailing();
            ma.envoyer(currentUser, cm);
            System.out.println(maill);
            flow.getChildren().clear();
            displayCompetition();
            flow1.getChildren().clear();
            displayMyCompetitions();
        }

    }

    @FXML
    void cancelParticipation(ActionEvent event) throws UnsupportedEncodingException {
        ParticipationService ps = new ParticipationService();
        System.out.println(id);
        Participation c = new Participation(currentUser.getId(), id);
        if (ps.annulerParticipation(c)) {
            participant.setText(Integer.toString(Integer.parseInt(participant.getText()) + 1));
            ps.incrementerParticipants(id);
            String maill = currentUser.getEmail();
            Mailing ma = new Mailing();
            ma.envoyer(currentUser, cm);
            System.out.println(maill);
            flow.getChildren().clear();
            displayCompetition();
            flow1.getChildren().clear();
            displayMyCompetitions();
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
