/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;


import Entities.Animal;
import Entities.Entrainement;
import Services.AnimalService;
import Services.TrainingService;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.util.List;

/**
 *
 * @author tibh
 */
public class ListTrainingForm  extends BaseForm {

    public ListTrainingForm(Resources res)   {
       
        super("Trainings",new BorderLayout()); 
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        List<Entrainement> lb = TrainingService.getInstance().getAllTrainingUser();
        
        

        Container entrainements = new Container(BoxLayout.y());
     entrainements.setScrollableY(true);
       
        if (lb != null) {
            
             for (Entrainement e : lb) {
                
                  String url ="";
                  
                  System.out.println(e.getAnimalId());
                
                  if(e.getAnimalId()==4)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/lieujaune1.jpg";
                  }
                  if(e.getAnimalId()==1)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/ours_noir.jpg";
                  }
                  if(e.getAnimalId()==3)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/smallgeme1.jpg";
                  }
                  
                  
                 
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2 , this.getHeight()/5 , 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, url, url , URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
                     MultiButton mb = new MultiButton(e.getCategorie());
                     mb.setUIID("ListItem");  
                     mb.setTextLine2(e.getLieu());
                     
                     
                     Button bouton2 = new Button("Delete");
                      bouton2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    TrainingService.getInstance().DeleteT(e);
                    new ListTrainingForm(res).show();

                }

            });
                     
                      mb.add(LEFT, imgC);
                      
                      entrainements.add(FlowLayout.encloseCenter(mb));
                      entrainements.add(FlowLayout.encloseCenter(bouton2));
             }
            
            this.add(CENTER, entrainements);
           
             
        } else {
            System.out.println("ma7abech yekhdem");
        
        }
        }
            
}
