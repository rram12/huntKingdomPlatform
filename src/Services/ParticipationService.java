/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.Participation;
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
public class ParticipationService {
      public ArrayList<Participation> Participations;

    public static ParticipationService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ParticipationService() {
        req = new ConnectionRequest();
    }

    public static ParticipationService getInstance() {
        if (instance == null) {
            instance = new ParticipationService();
        }
        return instance;
    }


    public ArrayList<Participation> parseParticipation(String jsonText,int id) {
        try {
            Participations = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            if(list!=null){
            for (Map<String, Object> obj : list) {
//                Participation h = new Participation();
                float id1 = Float.parseFloat(obj.get("id").toString());
                if(id1==id){
                    List<Map<String, Object>> users = (List<Map<String, Object>>) obj.get("users");
                for (Map<String, Object> obj1 : users) {
                Participation p = new Participation();
                int idUser =(int) Float.parseFloat(obj1.get("id").toString());
                p.setUser_id(idUser);
                p.setCompetition_id((int)id1);
                Participations.add(p);
                }
//                    h.setCompetition_id((int)id1);
//                    Participations.add(p);
                }
                
                
            }
            }
            else System.out.println("empty");

        } catch (IOException ex) {

        }
        return Participations;
    }
    public ArrayList<Participation> getAllParticipations(int id) {
        String url = BASE_URL + "affichage_competition";
        req.setUrl(url);
//        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Participations = parseParticipation(new String(req.getResponseData()),id);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(Participations);
        return Participations;
    }
        public boolean Participation(Participation pn) {
        SimpleDateFormat formater = null;

        formater = new SimpleDateFormat("yyyy-MM-dd");    
        String url = BASE_URL + "addToCompetition/" +pn.getCompetition_id() + "/"  + pn.getUser_id() ;
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

                public boolean annulerParticipations(Participation p) {
        String url = BASE_URL + "annulerParticipation/" + p.getUser_id() + "/" + p.getCompetition_id() ; //création de l'URL
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
