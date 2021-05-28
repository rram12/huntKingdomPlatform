/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Competition;
import static Utils.Statics.BASE_URL;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */
public class CompetitionService {

    public ArrayList<Competition> Competitions;

    public static CompetitionService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private CompetitionService() {
        req = new ConnectionRequest();
    }

    public static CompetitionService getInstance() {
        if (instance == null) {
            instance = new CompetitionService();
        }
        return instance;
    }

    public ArrayList<Competition> parseCompetition(String jsonText) {
        try {
            Competitions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Competition h = new Competition();
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
                h.setNom(obj.get("nom").toString());
                h.setNbParticipants(((int) Float.parseFloat(obj.get("nbParticipants").toString())));
                h.setCategorie(obj.get("categorie").toString());
                h.setLieu(obj.get("lieu").toString());
                HashMap<String, Double> hm = (HashMap<String, Double>) obj.get("dateDebut");
                long l = (long) (hm.get("timestamp") * 1000);
                Date d = new Date(l);
                h.setDateDebut(d);
                HashMap<String, Double> hm1 = (HashMap<String, Double>) obj.get("dateFin");
                long l1 = (long) (hm1.get("timestamp") * 1000);
                Date d1 = new Date(l1);
                h.setDateFin(d1);
                Competitions.add(h);
            }

        } catch (IOException ex) {

        }
        return Competitions;
    }

    public ArrayList<Competition> getAllCompetitions() {
        String url = BASE_URL + "affichage_competition";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Competitions = parseCompetition(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Competitions;
    }

    public boolean addCompetition(Competition p) {
        SimpleDateFormat formater = null;

        formater = new SimpleDateFormat("yyyy-MM-dd");
        String url = BASE_URL + "addCompetition/" + p.getNom() + "/" + p.getCategorie() + "/" + p.getLieu() + "/" + p.getNbParticipants() + "/" + formater.format(p.getDateDebut()) + "/" + formater.format(p.getDateFin());
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean modifyCompetition(Competition p) {

        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("yyyy-MM-dd");
        String url = BASE_URL + "modifCompetition/" + p.getId() + "/" + p.getNom() + "/" + p.getCategorie() + "/" + p.getLieu() + "/" + p.getNbParticipants() + "/" + formater.format(p.getDateDebut()) + "/" + formater.format(p.getDateFin());
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteCompetition(int id) {
        String url = BASE_URL + "deleteCompetition/" + id; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Competition> parseMesCompetitions(String jsonText, int id) {
        try {
            Competitions = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            if(list!=null){
            for (Map<String, Object> obj : list) {
                    List<Map<String, Object>> users = (List<Map<String, Object>>) obj.get("users");
                for (Map<String, Object> obj1 : users) {
                    int idUser =(int) Float.parseFloat(obj1.get("id").toString());
                    if(idUser==id){
                Competition h = new Competition();
                float idComp = Float.parseFloat(obj.get("id").toString());
                h.setId((int) idComp);
                h.setNom(obj.get("nom").toString());
                h.setNbParticipants(((int) Float.parseFloat(obj.get("nbParticipants").toString())));
                h.setCategorie(obj.get("categorie").toString());
                h.setLieu(obj.get("lieu").toString());
                HashMap<String, Double> hm = (HashMap<String, Double>) obj.get("dateDebut");
                long l = (long) (hm.get("timestamp") * 1000);
                long t = (long) l * 10000;
                Date d = new Date(l);
                h.setDateDebut(d);
                HashMap<String, Double> hm1 = (HashMap<String, Double>) obj.get("dateFin");
                long l1 = (long) (hm1.get("timestamp") * 1000);
                long t1 = (long) l1 * 10000;
                Date d1 = new Date(l1);
                h.setDateFin(d1);
                Competitions.add(h);
                    }
                }    
            }
            } else {
                System.out.println("empty");
            }

        } catch (IOException ex) {

        }
        return Competitions;
    }
        public ArrayList<Competition> getAllUsers(int id) {
        String url = BASE_URL + "affichage_competition";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Competitions = parseMesCompetitions(new String(req.getResponseData()),id);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(Competitions);
        return Competitions;
    }
        public ArrayList<Competition> getSearched(String search) {
        String url = BASE_URL + "search/" + search;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Competitions = parseCompetition(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Competitions;
    } 

}
