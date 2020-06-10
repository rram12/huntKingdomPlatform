/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Entities.Entrainement;
import Entities.Produit;
import Entities.User;
import Services.AnimalService;
import Services.ProduitService;
import Services.TrainingService;
import Services.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tibh
 */
public class ListTrainer extends BaseForm{
    public ListTrainer(Resources res)
    {
        super("Trainings", BoxLayout.y());
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
            
          Image img = res.getImage("bg-2.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }

        ScaleImageLabel sl = new ScaleImageLabel(img);
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

        
        
        
        
        
         List<Entrainement> lb = TrainingService.getInstance().getAllTraining();
          Container entr = new Container(BoxLayout.y());
       
        entr.setScrollableY(true);
        if (lb != null) {
            for (Entrainement e : lb) {
                System.out.println("At"+e.getAnimalId());
                Container c = new Container();
                //MultiButton mb = new MultiButton(e.getCategorie());
               
                Label categorie = new Label("Categorie: "+e.getCategorie());
                
                Container north = new Container(new FlowLayout(Component.CENTER));
                north.addComponent(categorie);

                //mb.addComponent(BorderLayout.NORTH, north);
                 Container center = new Container(new BorderLayout());
                Container row = new Container(new GridLayout(4, 1));
                row.getStyle().setPaddingTop(15);
                Label l4 = new Label("Place: "+e.getLieu());
               Animal a = getAnimal(e.getAnimalId());
               Produit p = getProduit(e.getProduitId());
               System.out.println(a);
               Label l2 = new Label("Animal: "+a.getNom()+"            Product:"+p.getLib_prod() );
               Label l5 = new Label("Training Date: "+e.getDateEnt());
               row.add(l2);
               row.add(l4);
               row.add(l5);

                Label l6 = new Label("Hours Number: "+Integer.toString(e.getNbHeures()));
                row.add(l6);
                 Button Accept= new Button("Accept");
                 Button Refuse= new Button("Refuse");
                 row.add(Accept);
                 row.add( Refuse);
                center.add(BorderLayout.CENTER, row);

                //mb.addComponent(BorderLayout.CENTER, center);
                Container south = new Container(new BorderLayout());
                 
                
                 int user = RecupereUser();
                 Accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               
                if (!TrainingService.getInstance().modifyTraining(e,user)) {
                       Dialog.show("Error", "", new Command("OK"));
                     
                     } else {
                       Dialog.show("Success", "Training Accepted", new Command("OK"));
                         new ListTrainer(res).show();
            }
            }
        });
                 Refuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                TrainingService.getInstance().DeleteT(e);
                 Dialog.show("Success", "Training Refused", new Command("OK"));
                    new ListTrainer(res).show();
            }
            }
        );
                 
                 
                
                
               
//                mb.setLeadComponent(Accept);
//                mb.setLeadComponent(Refuse);
//
//                mb.addComponent(BorderLayout.SOUTH, south);
//                
entr.add(north);
                entr.add(center);

            }
        }

        add(entr);
     
    
    
    }
    public Animal getAnimal(int id)
    {
     ArrayList<Animal> la = AnimalService.getInstance().getAllAnimalsTr(id);
         Animal a = la.get(0);
      
         return a;
    }
    public Produit getProduit(int id)
    {
     ArrayList<Produit> lp = ProduitService.getInstance().getAllProductsTr(id);
         Produit p= lp.get(0);
      
         return p;
    }
    private int RecupereUser()
      {
       return User.getInstace(0,"","","","",0).getId();
      }
}
