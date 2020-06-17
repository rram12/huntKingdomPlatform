/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.DateCustom;
import Entities.Repairer;
import Entities.PiecesDefectueuses;
import Entities.Products;
import Entities.Reparation;
import Entities.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class PieceService {

    //public ArrayList<Task> tasks;
    public static PieceService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    ArrayList<PiecesDefectueuses> listPieces = new ArrayList<>();
    Reparation r;
    Repairer reparateur;
    DateCustom dateDiff;
    int count;
    String reponseBool;
    ArrayList<Products> listProduits = new ArrayList<>();

    private PieceService() {
        req = new ConnectionRequest();
    }

    public static PieceService getInstance() {
        if (instance == null) {
            instance = new PieceService();
        }
        return instance;
    }

    public boolean addPiece(PiecesDefectueuses p) {

        String url = Statics.BASE_URL1 + "reparation/api/add?nom=" + p.getNom() + "&categorie=" + p.getCategorie() + "&description=" + p.getDescription() + "&image=" + p.getImage() + "&user=" + Integer.toString(p.getUserId());
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

    public boolean repare(Reparation r) {
        String idPiece = Integer.toString(r.getPiecesdefectueuses_id());
        String idUser = Integer.toString(r.getUserId());
        String prix = Double.toString(r.getPrixRep());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = formatter.format(r.getDateFin());
        String strDate1 = formatter.format(r.getDateDebut());

        String url = Statics.BASE_URL1 + "reparation/api/repare/" + idPiece + "/" + idUser + "?prixRep=" + prix + "&description=" + r.getDescription()+"&dateDebut=" + strDate1 + "&dateFin=" + strDate;
        System.out.println("URL : " + url);
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

    public ArrayList<PiecesDefectueuses> listAll() {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/all";
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            listPieces = ser.parseListPiecesJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPieces;
    }

    public ArrayList<PiecesDefectueuses> parseListPiecesJson(String json) {

        ArrayList<PiecesDefectueuses> pieces = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
             On doit convertir notre réponse texte en CharArray à fin de
             permettre au JSONParser de la lire et la manipuler d'ou vient 
             l'utilité de new CharArrayReader(json.toCharArray())
            
             La méthode parse json retourne une MAP<String,Object> ou String est 
             la clé principale de notre résultat.
             Dans notre cas la clé principale n'est pas définie cela ne veux pas
             dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
             qui est root.
             En fait c'est la clé de l'objet qui englobe la totalité des objets 
             c'est la clé définissant le tableau de tâches.
             */
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste 
             d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                PiecesDefectueuses e = new PiecesDefectueuses();

                float id = Float.parseFloat(obj.get("id").toString());
                Map<String, Object> user = (Map<String, Object>) obj.get("user");
                float idUser = Float.parseFloat(user.get("id").toString());
                boolean etat = obj.get("etat").toString().equals("true");
                boolean reserved = obj.get("reserved").toString().equals("true");
                e.setId((int) id);
                e.setUserId((int) idUser);
                e.setCategorie(obj.get("categorie").toString());
                e.setDescription(obj.get("description").toString());
                e.setImage(obj.get("image").toString());
                e.setNom(obj.get("nom").toString());
                e.setEtat(etat);
                e.setReserved(reserved);
                pieces.add(e);

            }

        } catch (IOException ex) {
        }

        return pieces;

    }

    public ArrayList<PiecesDefectueuses> listyourPieces() {
        ConnectionRequest con = new ConnectionRequest();
        String constId = Integer.toString(User.getInstace(0,"","","","",0).getId());
        String url = Statics.BASE_URL1 + "reparation/api/list_your_defective/" + constId;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            listPieces = ser.parseListPiecesJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPieces;
    }

    public Reparation listyourReparation(String id) {

        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/ready/" + id;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            r = ser.parseReparationJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return r;
    }

    public Reparation parseReparationJson(String json) {

        Reparation r = new Reparation();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            Map<String, Object> reparation = (Map<String, Object>) tasks.get("reparation");
            Map<String, Object> reparateur = (Map<String, Object>) reparation.get("reparateur");
            float idRep = Float.parseFloat(reparateur.get("id").toString());
            r.setUserId((int) idRep);
            Map<String, Object> piecedefectueuse = (Map<String, Object>) reparation.get("piecedefectueuse");
            float idPiece = Float.parseFloat(piecedefectueuse.get("id").toString());
            r.setPiecesdefectueuses_id((int) idPiece);
            float id = Float.parseFloat(reparation.get("id").toString());
            r.setId((int) id);
            Double prix = Double.parseDouble(reparation.get("prixRep").toString());
            r.setPrixRep(prix);
            r.setDescription(reparation.get("description").toString());
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println("dateFinale :  " + tasks.get("dateFinale").toString());
            Date myDate = null;
            try {
                myDate = myFormat.parse(tasks.get("dateFinale").toString());
            } catch (ParseException ex) {
            }
            r.setDateFin(myDate);
        
            System.out.println("dateDebut :  " + tasks.get("dateDebut").toString());
            Date myDate1 = null;
            try {
                myDate1 = myFormat.parse(tasks.get("dateDebut").toString());
            } catch (ParseException ex) {
                
            }

            r.setDateDebut(myDate1);
        } catch (IOException ex) {
        }
        
        
        
        return r;
    }

    public void DeleteReparation(String lidRep, String lidPiece) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/finish_reparation/" + lidRep + "/" + lidPiece;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }
 public void sendMail(String to) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/send/" + to;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    public void deleteFinishedPromotion() {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/delete_finished_promo";
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            System.out.println(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public Repairer getRepairer(int id) {
        String strId = Integer.toString(id);
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/show_repairer_profile/" + strId;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            reparateur = ser.parseGetRepairerJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return reparateur;
    }

    public Repairer parseGetRepairerJson(String json) {

        Repairer r = new Repairer();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            float id = Float.parseFloat(tasks.get("id").toString());
            r.setId((int) id);
            r.setEmail(tasks.get("email").toString());
            r.setFirstName(tasks.get("firstName").toString());
            r.setLastName(tasks.get("lastName").toString());
            float phone = Float.parseFloat(tasks.get("phoneNumber").toString());
            r.setPhone((int) phone);
        } catch (IOException ex) {
        }
        return r;
    }

    public DateCustom ShowProgress(int id) {
        String strId = Integer.toString(id);
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/show_progress/" + strId;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            dateDiff = ser.parseGetDateJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return dateDiff;
    }

    public DateCustom parseGetDateJson(String json) {

        DateCustom d = null;

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            float year = Float.parseFloat(tasks.get("years").toString());
            float month = Float.parseFloat(tasks.get("months").toString());
            float days = Float.parseFloat(tasks.get("days").toString());
            float hours = Float.parseFloat(tasks.get("hours").toString());
            float minutes = Float.parseFloat(tasks.get("minutes").toString());
            d = new DateCustom((int) year, (int) month, (int) days, (int) hours, (int) minutes);

        } catch (IOException ex) {
        }
        return d;
    }

    public int getYourReady(int id) {
        String strId = Integer.toString(id);
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/count_your_ready/" + strId;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            count = ser.parseGetCountJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return count;
    }

    public int parseGetCountJson(String json) {

        int d = 0;

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            float c = Float.parseFloat(tasks.get("count").toString());

            d = (int) c;

        } catch (IOException ex) {
        }
        return d;
    }

    public ArrayList<PiecesDefectueuses> getListSearch(String name) {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/search/" + name;
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            listPieces = ser.parseListPiecesJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listPieces;
    }
     public ArrayList<Products> listProductInPromotion() {
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/product_promo";
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            listProduits = ser.parseListProductsJson(new String(con.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listProduits;
    }
     public ArrayList<Products> parseListProductsJson(String json) {

        ArrayList<Products> produits = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));

            
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");

            for (Map<String, Object> obj : list) {
                Products p = new Products();
               Map<String, Object> promotion = (Map<String, Object>) obj.get("promotion");
                p.setTaux(Double.parseDouble(promotion.get("taux").toString()));
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
       public boolean exists(PiecesDefectueuses p) {
        
        ConnectionRequest con = new ConnectionRequest();
        String url = Statics.BASE_URL1 + "reparation/api/isExists?nom=" + p.getNom() + "&categorie=" + p.getCategorie() + "&description=" + p.getDescription() + "&image=" + p.getImage() + "&user=" + Integer.toString(p.getUserId());
        con.setUrl(url);
        con.addResponseListener((NetworkEvent evt) -> {
            PieceService ser = new PieceService();
            reponseBool = new String(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return reponseBool.contains("exists");
    }
    

}
