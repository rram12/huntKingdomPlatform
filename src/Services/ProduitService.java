/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Entities.Produit;
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
public class ProduitService {
    public ArrayList<Produit> produits;
     
    public static ProduitService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ProduitService() {
         req = new ConnectionRequest();
    }

    public static ProduitService getInstance() {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }
    public ArrayList<Produit> parseProductList(String jsonText){
        try {
            produits=new ArrayList<>();
            
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> p : list){
                Produit pr = new Produit();
                
               float idP = Float.parseFloat(p.get("id").toString());
                pr.setId((int)idP);
                pr.setLib_prod(p.get("libProd").toString());
                Double lo = (Double) p.get("prix");
                pr.setPrix(lo);
                pr.setDescription(p.get("description").toString());
                float qte = Float.parseFloat(p.get("qteProd").toString());
                pr.setQte_prod((int)qte);
                pr.setImage(p.get("image").toString());
                pr.setType(p.get("type").toString());
                //a.setImage_animal(obj.get("image_animal").toString());
                
                
                
               
                
                
                produits.add(pr);
            }
            
            
        } catch (IOException ex) {
            
        }
        return produits;
    }
    public ArrayList<Produit> getAllProducts(){
        
        String url = BASE_URL+"productsList";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProductList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return produits;
    }
    
    public ArrayList<Produit> getAllProductsT(String nom){
        
        String url = BASE_URL+"productsTraining/"+nom;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProductList(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return produits;
    }
    
//     public Produit getProduitT(String nom){
//
//      Produit pr = new Produit();
//        try {
//           
//           
//            String url = BASE_URL+"productsTraining"+nom;
//            req.setUrl(url);
//
//           
//
//            Map<String, Object> p= (Map<String, Object>) new JSONParser().parseJSON(
//                    new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
//
//            float idP = Float.parseFloat(p.get("id").toString());
//                pr.setId((int)idP);
//                pr.setLib_prod(p.get("libProd").toString());
//                Double lo = (Double) p.get("prix");
//                pr.setPrix(lo);
//                pr.setDescription(p.get("description").toString());
//                float qte = Float.parseFloat(p.get("qteProd").toString());
//                pr.setQte_prod((int)qte);
//                pr.setImage(p.get("image").toString());
//                pr.setType(p.get("type").toString());
//        } catch (IOException err) {
//            Log.e(err);
//            return null;
//        }
//
//        return pr;
//    }
}
