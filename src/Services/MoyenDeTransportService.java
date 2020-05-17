/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.MoyenDeTransport;
import static Utils.Statics.BASE_URL;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
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
public class MoyenDeTransportService {

    public ArrayList<MoyenDeTransport> Mts;

    public static MoyenDeTransportService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private MoyenDeTransportService() {
        req = new ConnectionRequest();
    }

    public static MoyenDeTransportService getInstance() {
        if (instance == null) {
            instance = new MoyenDeTransportService();
        }
        return instance;
    }

    public boolean addTransport(MoyenDeTransport h) {
        String url = BASE_URL + "addTransport/" + h.getType() + "/" + h.getMarque() + "/" + h.getCategorie() + "/" + h.getPrixParJour(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addArgument("image", h.getImage());
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
//        MoyenDeTransport a = new MoyenDeTransport();
//        try {
//            Map<String, Object> obj = (Map<String, Object>) new JSONParser().parseJSON(
//                    new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
//            float id = Float.parseFloat(obj.get("id").toString());
//            a.setId((int) id);
//            a.setType(obj.get("type").toString());
//            a.setPrixParJour((Float.parseFloat(obj.get("prixParJour").toString())));
//            a.setCategorie(obj.get("categorie").toString());
//            a.setMarque(obj.get("marque").toString());
//            a.setImage(obj.get("image").toString());
//            System.out.println(a);
//            //MoyenDeTransport b=AddPhoto(a.getId(), a.getImage());
//
//        } catch (IOException ex) {
//
//        }
        return resultOK;
    }

//    public MoyenDeTransport AddPhoto(int id, String filePath) {
//        MoyenDeTransport a = new MoyenDeTransport();
//        try {
//
//            MultipartRequest r = new MultipartRequest();
//            r.setUrl(BASE_URL + "addImageTransport/" + id);
//            r.setPost(true);
//            r.addData("path", filePath, "image/jpeg");
//
//            InfiniteProgress prog = new InfiniteProgress();
//            Dialog dlg = prog.showInifiniteBlocking();
//            r.setDisposeOnCompletion(dlg);
//            NetworkManager.getInstance().addToQueueAndWait(r);
//
//            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
//                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
//
//        } catch (IOException err) {
//
//        }
//        return a;
//    }

    public ArrayList<MoyenDeTransport> parseTransports(String jsonText) {
        try {
            Mts = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                MoyenDeTransport h = new MoyenDeTransport();
                float id = Float.parseFloat(obj.get("id").toString());
                h.setId((int) id);
                h.setType(obj.get("type").toString());
                h.setPrixParJour((Float.parseFloat(obj.get("prixParJour").toString())));
                h.setCategorie(obj.get("categorie").toString());
                h.setImage(obj.get("image").toString());
                h.setMarque(obj.get("marque").toString());
                Mts.add(h);
            }

        } catch (IOException ex) {

        }
        return Mts;
    }

    public ArrayList<MoyenDeTransport> getAllTransports() {
        String url = BASE_URL + "transport";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Mts = parseTransports(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Mts;
    }

    public boolean deleteTransport(int id) {
        String url = BASE_URL + "deleteTransport/" + id; //création de l'URL
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

    public boolean modifyTransport(MoyenDeTransport h) {
        String url = BASE_URL + "modifyTransport/" + h.getId() + "/" + h.getType() + "/" + h.getMarque() + "/" + h.getCategorie() + "/" + h.getPrixParJour() + "/" + h.getImage(); //création de l'URL
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
    public ArrayList<MoyenDeTransport> getSearched(String search) {
        String url = BASE_URL + "searchTransport/" + search;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Mts = parseTransports(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Mts;
    }
}
