/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Services.AnimalService;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import java.util.List;

/**
 *
 * @author tibh
 */
public class ListAnimalForm extends BaseForm{
    public ListAnimalForm(Resources res)
    {
     
      super(new BorderLayout());
     
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
       
     
     List<Animal> lb = AnimalService.getInstance().getAllAnimals();
     
     Container animals = new Container(BoxLayout.y());
     animals.setScrollableY(true);
     if (lb != null) {
            Form hi = new Form("Animals",new GridLayout(2, 2));
            Button Add = new Button("Add Animal");
     Add.addActionListener(b->new AddAnimalForm(res).show());
     Button Stat = new Button("Stat");
     Stat.addActionListener(b->new StatistiquePieForm().show());
            MultiButton mb = null;
             for (Animal a : lb) {
      String url ="http://localhost/HuntKingdom/web/uploads/photos/Liza_saliens1.jpg";
                  //System.out.println(e.getAnimalId().getId());
                  if(a.getId()==4)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/lieujaune1.jpg";
                  }
                  if(a.getId()==1)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/ours_noir.jpg";
                  }
                  if(a.getId()==3)
                  {
                      url ="http://localhost/HuntKingdom/web/uploads/photos/smallgeme1.jpg";
                  }
                   EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2 , this.getHeight()/5 , 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, url, url , URLImage.RESIZE_SCALE_TO_FILL);
                Container imgC = new Container();
                imgC.add(img);
//                 mb = new MultiButton(a.getCategorie());
//                    
//                     mb.setEmblem(img);
//                    
//                     mb.setTextLine2(a.getDescription());
Label nom =new Label(a.getNom());
Button modify = new Button("");
 FontImage.setMaterialIcon(modify, FontImage.MATERIAL_UPDATE);
nom.getAllStyles().setFgColor(16777215);  
nom.getAllStyles().setBgColor(16691263);                  
 
hi.add(LayeredLayout.encloseIn(imgC,
        FlowLayout.encloseBottom(nom),FlowLayout.encloseRight(modify)));
modify.addActionListener(e->new ModifyAnimalForm(a,res).show());

    }
             animals.add(FlowLayout.encloseRight(Add));
             animals.add(FlowLayout.encloseRight(Stat));
             animals.add(FlowLayout.encloseCenter(hi));
     }
       this.add(CENTER, animals);
     
    }
     
    
}
