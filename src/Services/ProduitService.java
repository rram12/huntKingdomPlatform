/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Entities.Products;
import Entities.Produit;
import Entities.User;
import Utils.Statics;
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
    ArrayList<Products> listProduits = new ArrayList<>();
             String res="";


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
    public ArrayList<Produit> getAllProductsTr(int id){
        
        String url = BASE_URL+"productsTrainingId/"+id;
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
     public ArrayList<Products> listProducts() {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "product/api/allProducts";
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            ProduitService ser = new ProduitService();
            listProduits = ser.parseListProductsAllJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listProduits;
    }
     public ArrayList<Products> parseListProductsAllJson(String json) {

        ArrayList<Products> produits = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
                
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
            for (Map<String, Object> obj : list) {
                Products p = new Products();
                p.setDescription(obj.get("description").toString());
                p.setId((int)Float.parseFloat(obj.get("id").toString()));
                p.setImage(obj.get("image").toString());
                p.setMarque(obj.get("marque").toString());
                p.setPrix(Double.parseDouble(obj.get("prix").toString()));
                p.setPrixFinale(Double.parseDouble(obj.get("prixFinale").toString()));
                p.setQteProd((int)Float.parseFloat(obj.get("qteProd").toString()));
                p.setType(obj.get("type").toString());
                p.setLibProd(obj.get("libProd").toString());
                produits.add(p);

            }

        } catch (IOException ex) {
        }

        return produits;

    }
     public void addToCart(int id) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "product/api/addPanier/"+id;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
     
      public void deleteProduitFromPanier(int id) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "product/api/deleteprod/"+id;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println("json response : \n"+new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
      
     public String confirm(Double prixTotale) {
        ConnectionRequest con = new ConnectionRequest();
        User u = User.getInstace(0,"","","","",0);
        String url = Statics.BASE_URL1 + "product/api/confirmPanier/"+prixTotale+"/"+u.getEmail()+"/"+u.getUsername()+"/"+u.getAddress()+"/"+u.getPhoneNumber();
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println("json response : \n"+new String(con.getResponseData()));
            res = new String(con.getResponseData()); 
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return res;
    }
    
}
