/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Publicity;
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
public class PubliciteService {
    public ArrayList<Publicity> Pubs;

    public static PubliciteService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private PubliciteService() {
        req = new ConnectionRequest();
    }

    public static PubliciteService getInstance() {
        if (instance == null) {
            instance = new PubliciteService();
        }
        return instance;
    }


        public boolean addPublicity(Publicity p) {
        SimpleDateFormat formater = null;

        formater = new SimpleDateFormat("yyyy-MM-dd");    
        String url = BASE_URL + "addPublicity/" + p.getCompagnie() + "/" + p.getTitre() + "/" + p.getPrix() + "/" + p.getDescription() + "/" + formater.format(p.getDateDebut())+ "/" + formater.format(p.getDateFin());
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addArgument("image", p.getImage());
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

    public ArrayList<Publicity> parsePublicity(String jsonText) {
        try {
            Pubs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Publicity h = new Publicity();
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
                h.setCompagnie(obj.get("compagnie").toString());
                h.setPrix((Float.parseFloat(obj.get("prix").toString())));
                h.setTitre(obj.get("titre").toString());
                h.setImage(obj.get("image").toString());
                h.setDescription(obj.get("description").toString());
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
                Pubs.add(h);
            }

        } catch (IOException ex) {

        }
        return Pubs;
    }

    public ArrayList<Publicity> getAllPublicities() {
        String url = BASE_URL + "affichage_pub";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Pubs = parsePublicity(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Pubs;
    }
     public boolean modifyPublicity(Publicity p) {
        
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("yyyy-MM-dd"); 
        String url = BASE_URL + "modifPublicity/" + p.getId() + "/"  + p.getCompagnie() + "/" + p.getTitre() + "/" + p.getPrix() + "/" + p.getDescription() + "/" + formater.format(p.getDateDebut())+ "/" + formater.format(p.getDateFin());
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addArgument("image", p.getImage());
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
       public boolean deletePublicity(int id) {
        String url = BASE_URL + "deletePublicity/" + id; //création de l'URL
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
