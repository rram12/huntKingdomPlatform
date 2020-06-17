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
    private JFXButton confirmBtn1;

    @FXML
    private Label place;

    @FXML
    private Label starts;

    @FXML
    private Label category;

    @FXML
    private FlowPane flow;

    @FXML
    private Label participant;

    @FXML
    private JFXTabPane tabCompetition;
    private int id;
    Competition cm;
    private int idu;
    User currentUser;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Services.UserService SU = new UserService();
        currentUser=SU.getConnectedUser1();
        flow.getChildren().clear();
        displayCompetition();
        
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
                    cm = m;
                    participate.setDisable(false);
                    name.setText(m.getNom());
                    starts.setText(m.getDateDebut().toString());
                    ends.setText((m.getDateFin().toString()));
                    category.setText(m.getCategorie());
                    place.setText(m.getLieu());
                    participant.setText(Integer.toString(m.getNbParticipants()));
                    if (m.getNbParticipants() == 0) {
                        particip.setOnAction(null);
//                       particip.setDisable(true);
                        particip.setStyle("-fx-background-color: red ; -fx-text-fill: white;");
                        particip.setText("No more places");
//                       errorparticipants.setVisible(true);
                    }
                    try {
                        if (checkparticipations(id1, currentUser.getId()) != 0) {
                            particip.setOnAction(null);
                            particip.setStyle("-fx-background-color: red ; -fx-text-fill: white;");
//                       particip.setDisable(true);
                            particip.setText("Already Participated");

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultCompetitionController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SingleSelectionModel<Tab> selectionModel = tabCompetition.getSelectionModel();
                    selectionModel.select(1); //select by index starting with 0
                    this.id = id1;

                });

                nodes[i] = root;
                flow.getChildren().add(nodes[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addParticipation(ActionEvent event) throws UnsupportedEncodingException {
        MyConnection mc = MyConnection.getInstance();
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
        
    }

    public int checkparticipations(int idcompetition, int iduser) throws SQLException {
        MyConnection mc = MyConnection.getInstance();
        ParticipationService pss = new ParticipationService();
        return pss.checkParticipation(idcompetition, iduser);
    }
}
