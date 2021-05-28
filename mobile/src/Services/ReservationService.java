/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Location;
import Entities.Reservation;
import Entities.User;
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
 * @author ASUS1
 */
public class ReservationService {
    public ArrayList<Reservation> Mts;

    public static ReservationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ReservationService() {
        req = new ConnectionRequest();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }
    public boolean addReservation(Reservation h) {
        SimpleDateFormat formater = null;

    formater = new SimpleDateFormat("yyyy-MM-dd");
//    System.out.println(formater.format(aujourdhui));
        String url = BASE_URL + "addReservation/" + h.getUserId()+ "/" + h.getHebergementId()+ "/" + h.getNbJours()+"/" + h.getPrixTot()+ "/"+ formater.format(h.getDateArrivee()); //création de l'URL
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

    public ArrayList<Reservation> parseReservations(String jsonText) {
        try {
            Mts = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reservation h = new Reservation();
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
                h.setPrixTot((Float.parseFloat(obj.get("prixTot").toString())));
                h.setNbJours(((int)Float.parseFloat(obj.get("nbJours").toString())));
                HashMap<String, Double> hm = (HashMap<String, Double>) obj.get("dateArrivee");
                long l = (long) (hm.get("timestamp") * 1000);
//                long t = (long) l * 10000;
                Date d = new Date(l);
                h.setDateArrivee(d);
                
                Map<String, Object> userMap = (Map<String, Object>) obj.get("user");
                int idu = ((int) Float.parseFloat(userMap.get("id").toString()));
                h.setUserId(idu);
                
                Map<String, Object> hebergementMap = (Map<String, Object>) obj.get("Hebergement");
                int idh = ((int) Float.parseFloat(hebergementMap.get("id").toString()));
                h.setHebergementId(idh);
                Mts.add(h);
            }

        } catch (IOException ex) {

        }
        return Mts;
    }

    public ArrayList<Reservation> getAllReservations(int idh) {
        String url = BASE_URL + "listRes/"+idh;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Mts = parseReservations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Mts;
    }
    public ArrayList<Reservation> getMyReservations(int idu) {
        String url = BASE_URL + "myRes/"+idu;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Mts = parseReservations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Mts;
    }
    public boolean deleteReservation(int id) {
        String url = BASE_URL + "deleteReservation/" +id; //création de l'URL
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
    public boolean modifyReservation(Reservation h) {
        SimpleDateFormat formater = null;

    formater = new SimpleDateFormat("yyyy-MM-dd");
        String url = BASE_URL + "modifyReservation/" + h.getId()+"/" + h.getNbJours()+"/" + h.getPrixTot()+ "/"+ formater.format(h.getDateArrivee()); //création de l'URL
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
}
