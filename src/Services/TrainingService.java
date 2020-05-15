/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Entities.Entrainement;
import Entities.Produit;
import Entities.User;
import static Utils.Statics.BASE_URL;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
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
 * @author tibh
 */
public class TrainingService {
    
     public ArrayList<Entrainement> entrainements;
     
    public static TrainingService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private TrainingService() {
         req = new ConnectionRequest();
    }

    public static TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }
    
    public ArrayList<Entrainement> parseTraining(String jsonText){
        try {
            entrainements=new ArrayList<>();
            
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Entrainement t = new Entrainement();
                Map<String, Object> a = (Map<String, Object>) obj.get("animal");
                Animal an = new Animal();
                float idA = Float.parseFloat(a.get("id").toString());
                an.setId((int)idA);
                
               
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setCategorie(obj.get("categorie").toString());
                t.setNbHeures(((int)Float.parseFloat(obj.get("nbHeures").toString())));
                
                t.setLieu(obj.get("lieu").toString());
                //t.setUserId(5);
                t.setAnimalId(an.getId());
               
                
                t.setAccepter(obj.get("accepter").toString());
                entrainements.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return entrainements;
    }
    

    public ArrayList<Entrainement> getAllTrainingUser(){
        String url = BASE_URL+"training_user/"+5;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                entrainements = parseTraining(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entrainements;
    }
  
    
     public boolean DeleteT(Entrainement e) {
        
       String url = BASE_URL +"delete_training/"+e.getId();               
        System.out.println(url);               
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public Entrainement add(Entrainement e) {

        ConnectionRequest r = new ConnectionRequest();
        r.setPost(true);
        r.setHttpMethod("GET");
        r.setUrl(BASE_URL+"add_training");
        
        r.addArgument("categorie", e.getCategorie());
        r.addArgument("nbHeures", Integer.toString(e.getNbHeures()));
        r.addArgument("dateEnt", e.getDateEnt());
        r.addArgument("prix", Double.toString(e.getPrix()));
        r.addArgument("lieu", e.getLieu());
        r.addArgument("userId", Integer.toString(e.getUserId()));
        r.addArgument("animalId",Integer.toString(e.getAnimalId()));
        r.addArgument("produitId", Integer.toString(e.getProduitId()));
        
        r.addArgument("accepter", e.getAccepter());
        
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(r);
        Entrainement en = new Entrainement();
        try {
            Map<String, Object> obj = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
           Map<String, Object> u = (Map<String, Object>) obj.get("user");
            Map<String, Object> an = (Map<String, Object>) obj.get("animal");
            Map<String, Object> p = (Map<String, Object>) obj.get("produit");
            User us = new User();
            float idU = Float.parseFloat(u.get("id").toString());
                us.setId((int)idU);
      
            Animal a = new Animal();
       
                float idA = Float.parseFloat(an.get("id").toString());
                a.setId((int)idA);
               
           Produit pr = new Produit();
            float idP = Float.parseFloat(p.get("id").toString());
                pr.setId((int)idP);
                
            
                
           
              
                float id = Float.parseFloat(obj.get("id").toString());
                en.setId((int)id);
                en.setCategorie(obj.get("categorie").toString());
                en.setNbHeures(((int)Float.parseFloat(obj.get("nbHeures").toString())));
                en.setDateEnt(obj.get("dateEnt").toString());
                
                en.setLieu(obj.get("lieu").toString());
                en.setAccepter(obj.get("accepter").toString());
                e.setUserId(us.getId());
                en.setAnimalId(a.getId());
                en.setProduitId(pr.getId());
                
          
        } catch (IOException ex) {
           
        }
 return en;
    }
    
    public List<Integer> Stati() {
        List<Integer> li = new ArrayList<Integer>();
        try {
            ConnectionRequest r = new ConnectionRequest();

            r.setUrl(BASE_URL+"Stat");
            r.setPost(false);
            r.setHttpMethod("GET");

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();
            r.setDisposeOnCompletion(dlg);
            NetworkManager.getInstance().addToQueueAndWait(r);

            Map<String, Object> response = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            List<String> content = (List<String>) response.get("root");
            System.out.println("content ====> " + content);
            for (String obj : content) {
                li.add(Integer.parseInt(obj));
                System.out.println("li ====> " + li);
            }
            
        } catch (IOException err) {
            Log.e(err);
            return null;
        }
        return li;
    }
 
}
