/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Hebergement;
import static Utils.Statics.BASE_URL;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS1
 */
public class HebergementService {

    public ArrayList<Hebergement> Hebergements;

    public static HebergementService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private HebergementService() {
        req = new ConnectionRequest();
    }

    public static HebergementService getInstance() {
        if (instance == null) {
            instance = new HebergementService();
        }
        return instance;
    }

    public boolean addHebergement(Hebergement h) {
        String url = BASE_URL + "addAccommod/" + h.getDescription() + "/" + h.getType() + "/" + h.getAdresse() + "/" + h.getPrixParJour() + "/" + h.getNbChambre()+ "/" + h.getNbLits()+ "/" + h.getCapacite()+ "/" + h.getImage(); //création de l'URL
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

    public ArrayList<Hebergement> parseAccommodations(String jsonText) {
        try {
            Hebergements = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Hebergement h = new Hebergement();
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
                h.setType(obj.get("type").toString());
                h.setNbChambre(((int) Float.parseFloat(obj.get("nbChambre").toString())));
                h.setCapacite(((int) Float.parseFloat(obj.get("capacite").toString())));
                h.setPrixParJour((Float.parseFloat(obj.get("prixParJour").toString())));
                h.setAdresse(obj.get("adresse").toString());
                h.setImage(obj.get("image").toString());
                h.setDescription(obj.get("description").toString());
                Hebergements.add(h);
            }

        } catch (IOException ex) {

        }
        return Hebergements;
    }

    public ArrayList<Hebergement> getAllAccommodations() {
        String url = BASE_URL + "hebergement";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Hebergements = parseAccommodations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Hebergements;
    }
}
