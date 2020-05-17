/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Services.AnimalService;
import Services.TrainingService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.codename1.util.regex.RE;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author tibh
 */
public class AddAnimalForm extends BaseForm{
    String path = null;
    ComboBox<String>categorie = new ComboBox<> ("Hunting","Fishing");
    ComboBox<String>Debut = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
    ComboBox<String>Fin = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
    TextField nom = new TextField();
    TextField description = new TextField();
    MultiButton photo = new MultiButton("");
       

     public AddAnimalForm(Resources res) {
         
        super("Ajout Animal", new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);

        this.setScrollableY(true);
        this.setLayout(new BorderLayout());

        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label l1 = new Label("Categorie :");
        Label l2 = new Label("Nom :");
        Label l3 = new Label("Description :");
        Label l4 = new Label("Debut Saison : ");
        Label l5 = new Label("Fin Saison : ");
        Label l6 = new Label("image : ");
       
       
       
       String url ="http://localhost/HuntKingdom/web/uploads/photos/choose.png";
       EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/5, this.getHeight()/9 , 0xFFFFFFFF), true);
                Image img = URLImage.createToStorage(placeholder, url, url , URLImage.RESIZE_SCALE_TO_FILL);
        ImageViewer iv = new ImageViewer(img);
        photo.addComponent(BorderLayout.WEST,iv);
        photo.addActionListener(e -> {
            path = Capture.capturePhoto();
            try {
                Image imgA = Image.createImage(path);
                iv.setImage(imgA);
                iv.refreshTheme();
                photo.refreshTheme();
                iv.setUIID("PreviewPhoto");
                photo.setUIID("PreviewPhoto");
                this.revalidate();
            } catch (IOException ex) {
                
            }

        });
        content.setScrollableY(true);

        Button submit = new Button("Ajouter");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
      
          
        content.add(l1).add(categorie);
        content.add(l2).add(nom);
        content.add(l3).add(description);
        content.add(l4).add(Debut);
        content.add(l5).add(Fin);
        content.add(l6).add(photo);
        String nomA="";
       
      
                      submit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                   if ((nom.getText().length() == 0) || (description.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } 
              else {
                       int DS = 0;
                       int FS=0;
                       switch (Debut.getSelectedItem()) {
  case "January":
    DS=1;
    break;
  case "February":
    DS=2;
    break;
  case "March":
    DS=3;
    break;
  case "April":
   DS=4;
    break;
  case "May":
    DS=5;
    break;
  case "June":
   DS=6;
    break;
  case "July":
    DS=7;
    break;
    case "August":
    DS=8;
    break;
    case "September":
    DS=9;
    break;
    case "October":
    DS=10;
    break;
    case "November":
    DS=11;
    break;
    case "December":
    DS=12;
    break;
}
                       switch (Fin.getSelectedItem()) {
  case "January":
    FS=1;
    break;
  case "February":
    FS=2;
    break;
  case "March":
    FS=3;
    break;
  case "April":
   FS=4;
    break;
  case "May":
    FS=5;
    break;
  case "June":
   FS=6;
    break;
  case "July":
    FS=7;
    break;
    case "August":
    FS=8;
    break;
    case "September":
    FS=9;
    break;
    case "October":
    FS=10;
    break;
    case "November":
    FS=11;
    break;
    case "December":
    FS=12;
    break;
}
                 Animal a = new Animal(DS,FS,categorie.getSelectedItem(),nom.getText(),description.getText(),path);
                 
                 Animal AnimalAjoutee = AnimalService.getInstance().add(a);
                  Dialog.show("ok", "Animal added !", "OK", "Cancel");
                 new ListAnimalForm(res).show();
             }
            

                }

            });
             
       

        this.add(CENTER, content);
        this.add(SOUTH, submit);
        
        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
          val.addConstraint(nom,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field"))).
       
        addConstraint(description,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field")));
   
}
      
}
