/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Hebergement;
import Services.HebergementService;
import com.codename1.components.MultiButton;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class ListHebergementForm extends Form{
    public ListHebergementForm()  {
        super("Accomodations",new BorderLayout());
              
       
        List<Hebergement> lh = HebergementService.getInstance().getAllAccommodations();
        
        Container Hebergements = new Container(BoxLayout.y());
        Hebergements.setUIID("List");
        Hebergements.setScrollableY(true);
        if (lh != null) {
             for (Hebergement h : lh) {
               
                     MultiButton mb = new MultiButton(h.getType());
                     mb.setUIID("ListItem");
                    
                     mb.setTextLine3(h.getAdresse());
                      //mb.add(LEFT, img);
                     Hebergements.add(FlowLayout.encloseCenter(mb));
                 }
             
            this.add(CENTER, Hebergements);
             
        } else {
            System.out.println("ma7abech yekhdem");
        
        }
        }
}
