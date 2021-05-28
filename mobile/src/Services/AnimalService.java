/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Entities.Entrainement;
import static Utils.Statics.BASE_URL;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
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
 * @author tibh
 */
public class AnimalService {
      public ArrayList<Animal> animals;
     
    public static AnimalService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private AnimalService() {
         req = new ConnectionRequest();
    }

    public static AnimalService getInstance() {
        if (instance == null) {
            instance = new AnimalService();
        }
        return instance;
    }
    
    public ArrayList<Animal> parseAnimalList(String jsonText){
        try {
            animals=new ArrayList<>();
            
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Animal a = new Animal();
                
                float ds = Float.parseFloat(obj.get("debutSaison").toString());
                a.setDebutSaison((int)ds);
                float fs = Float.parseFloat(obj.get("finSaison").toString());
                a.setFinSaison((int)fs);
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int)id);
                
                a.setCategorie(obj.get("categorie").toString());
                a.setNom(obj.get("nom").toString());
                a.setDescription(obj.get("description").toString());
                a.setImage_animal(obj.get("imageAnimal").toString());
                
                animals.add(a);
            }
            
            
        } catch (IOException ex) {
            
        }
        return animals;
    }
    
     
    
    public ArrayList<Animal> getAllAnimals(){
        
        String url = BASE_URL+"animalsList";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                animals = parseAnimalList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return animals;
    }
    public ArrayList<Animal> getAllAnimalsHunting(){
        
        String url = BASE_URL+"animalsListHunting";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                animals = parseAnimalList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return animals;
    }
    public ArrayList<Animal> getAllAnimalsFishing(){
        
        String url = BASE_URL+"animalsListFishing";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                animals = parseAnimalList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return animals;
    }
    public ArrayList<Animal> getAllAnimalsT(String nom){
        
        String url = BASE_URL+"animalsTraining/"+nom;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                animals = parseAnimalList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return animals;
    }
    public ArrayList<Animal> getAllAnimalsTr(int id){
        
        String url = BASE_URL+"animalsTrainingId/"+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                animals = parseAnimalList(new String(req.getResponseData()));
                req.removeResponseListener(this);
                //System.out.println("AAAAA"+animals);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return animals;
       
        
    }

    
    public Animal add(Animal animal) {

        ConnectionRequest r = new ConnectionRequest();
        r.setPost(true);
        r.setHttpMethod("GET");
        r.setUrl(BASE_URL+"add_animal");
        r.addArgument("categorie", animal.getCategorie());
        r.addArgument("nom", animal.getNom());
        r.addArgument("description", animal.getDescription());
        r.addArgument("debutSaison", Integer.toString(animal.getDebutSaison()));
        r.addArgument("finSaison", Integer.toString(animal.getFinSaison()));
        r.addArgument("image_animal", animal.getImage_animal());
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(r);
         Animal a = new Animal();
        try {
            Map<String, Object> obj = (Map<String, Object>) new JSONParser().parseJSON(
                    new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

         
                
                
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int)id);
                
                a.setCategorie(obj.get("categorie").toString());
                a.setNom(obj.get("nom").toString());
                a.setDescription(obj.get("description").toString());
                a.setImage_animal(obj.get("imageAnimal").toString());
                float ds = Float.parseFloat(obj.get("debutSaison").toString());
                a.setDebutSaison((int)ds);
                float fs = Float.parseFloat(obj.get("finSaison").toString());
                a.setFinSaison((int)fs);
           
            
        } catch (IOException ex) {
           
        }
return a;
    }
//     public Animal AddPhoto(int id, String filePath) {
//        Animal a=new Animal();
//        try {
//           
//            MultipartRequest r = new MultipartRequest();
//            r.setUrl(BASE_URL+"addImage/" + id);
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
//        return a ;
//    }
     
     public boolean modifyAnimal(Animal a)
     {
       String url = BASE_URL+"modify_animal/"+a.getId()+"/"+a.getCategorie()+"/"+a.getNom()+"/"+a.getDescription()+"/"+a.getDebutSaison()+"/"+a.getFinSaison();
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
}
