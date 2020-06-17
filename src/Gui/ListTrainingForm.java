/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;


import Entities.Animal;
import Entities.Entrainement;
import Entities.User;
import Services.AnimalService;
import Services.TrainingService;
import Services.UserService;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tibh
 */
public class ListTrainingForm  extends BaseForm {

    public ListTrainingForm(Resources res)   {
               super("Trainings", BoxLayout.y());

         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        Image img1 = res.getImage("bg-2.jpg");
        if (img1.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img1 = img1.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }

        ScaleImageLabel sl = new ScaleImageLabel(img1);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                FlowLayout.encloseCenter(
                                        new Label(""))
                        )
                )
        ));
        
        
        
        
        
        int user = RecupereUser();
        List<Entrainement> lb = TrainingService.getInstance().getAllTrainingUser(user);
     
        Container entrainements = new Container(BoxLayout.y());
     entrainements.setScrollableY(true);
       
        if (lb != null) {
            
             for (Entrainement e : lb) {
                
                  String url ="";
                  
                  System.out.println(e.getAnimalId());
                
                  if(e.getAnimalId()==4)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/lieujaune2.jpg";
                  }
                  if(e.getAnimalId()==1)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/ours_noir2.jpg";
                  }
                  if(e.getAnimalId()==3)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/smallgeme2.jpg";
                  }
                  
                  
                 
                EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2 , this.getHeight()/5 , 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, url, url , URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
                     MultiButton mb = new MultiButton(e.getCategorie());
                     mb.setUIID("ListItem");  
                     mb.setTextLine2(e.getLieu());
                     mb.setTextLine3(e.getDateEnt());
                     mb.setTextLine4("Accept : "+e.getAccepter());
                     
                     
                     Button bouton2 = new Button("Delete");
                      bouton2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    TrainingService.getInstance().DeleteT(e);
                     Dialog.show("OK", "training deleted successfully ! ", new Command("OK"));
                    new ListTrainingForm(res).show();

                }

            });
                     
                      mb.add(LEFT, imgC);
                      
                      entrainements.add(FlowLayout.encloseCenter(mb));
                      entrainements.add(FlowLayout.encloseCenter(bouton2));
             }
            
            this.add(entrainements);
           
             
        } else {
            System.out.println("ma7abech yekhdem");
        
        }
        }
     private int RecupereUser()
      {
       return User.getInstace(0,"","","","",0).getId();
      }
            
}
