/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Animal;
import Entities.User;
import static Utils.Statics.BASE_URL;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tibh
 */
public class UserService {
    public ArrayList<User> users;
     
    public static UserService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public  ArrayList parseUser(String json) {
        try {
            users=new ArrayList<>();
            
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            if(list.size()>0){
            for(Map<String,Object> obj : list){
                User a = new User();
               
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int)id);
                
                a.setUsername(obj.get("username").toString());
                a.setFirstName(obj.get("firstName").toString());
                a.setEmail(obj.get("email").toString());
                a.setRoles(obj.get("roles").toString());
                a.setAddress(obj.get("address").toString());
                float num = Float.parseFloat(obj.get("phoneNumber").toString());
                a.setPhoneNumber((long)num);
                users.add(a);
            }
            }
            
        } catch (IOException ex) {
            
        }
        return users;
    }
     public ArrayList<User> getUserConnected(String name){
        
        String url = BASE_URL+"UserConnected/"+name;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users= parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return users;
    }
}
