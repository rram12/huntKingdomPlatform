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
import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tibh
 */
public class AddTrainingForm extends BaseForm{
     ComboBox<String>categorie = new ComboBox<> ("Hunting","Fishing");
     
     TextField nbHeures = new TextField();
     Picker dateEnt = new Picker();
      TextField prix = new TextField();
       ComboBox<String>lieu = new ComboBox<> ("Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba", "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan");
       ComboBox<String>animal = new ComboBox<>();
       ComboBox<String>produit= new ComboBox<>();
       String TrainingDate="";
    
     public AddTrainingForm(Resources res) {
        super("AddTraining", BoxLayout.y());
        
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);

        this.setScrollableY(true);
            
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

        
        
        
        
        
        
        
         List<Animal> lb = AnimalService.getInstance().getAllAnimals();
         for (Animal a : lb) {
             animal.addItem(a.getNom());
         }
          List<Produit> l = ProduitService.getInstance().getAllProducts();
         for (Produit pr : l) {
             produit.addItem(pr.getLib_prod());
             
         }
         
        Label l1 = new Label("Categorie :");
        Label l2 = new Label("Hours Number :");
        Label l3 = new Label("Training Date:");
        Label l4 = new Label("Price :");
        Label l5 = new Label("Place : ");
        Label l6 = new Label("Animals : ");
        Label l7 = new Label("Products : ");
         // content.setScrollableY(true);

        Button submit = new Button("Add");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
      
          
        add(l1).add(categorie);
        add(l2).add(nbHeures);
        add(l3).add(dateEnt);
        add(l4).add(prix);
        add(l5).add(lieu);
        add(l6).add(animal);
        add(l7).add(produit);
        add(submit);

        dateEnt.setType(Display.PICKER_TYPE_DATE);
        dateEnt.setDate(new Date());
        dateEnt.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
         dateEnt.addActionListener(e -> {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            String d = s.format(dateEnt.getDate());
            dateEnt.setText(d);
           TrainingDate = dateEnt.getText() ;
            System.out.println(TrainingDate);
        });
        
         ArrayList<Animal> la = AnimalService.getInstance().getAllAnimalsT(animal.getSelectedItem());
         Animal a = la.get(0);
         System.out.println(a);
        ArrayList<Produit> lp= ProduitService.getInstance().getAllProductsT(produit.getSelectedItem());
        Produit p = lp.get(0);
        System.out.println(p);
         int user = RecupereUser();
         
                submit.addActionListener(e -> {
                   
             if (validateFields(prix, nbHeures, dateEnt)) {
                
                Entrainement b = new Entrainement(categorie.getSelectedItem(),Integer.parseInt(nbHeures.getText()),TrainingDate,Double.valueOf(prix.getText()),lieu.getSelectedItem(),user,a.getId(),p.getId(),"encours");
                Entrainement EntrainementAjoutee = TrainingService.getInstance().add(b);
                if(EntrainementAjoutee!=null)
                {
               Dialog.show("ok", "Training added !", "OK", "Cancel");
                new ListTrainingForm(res).show();
                }
             }
            
        });
       // add(content);
        
     


  
}
      private boolean validateFields(TextField prix, TextField nh, Picker datePicker) {
        if (prix.getText().isEmpty() || nh.getText().isEmpty() || datePicker.getDate() == null) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        }
        try {
            int n = Integer.parseInt(nh.getText());
        } catch (NumberFormatException nfe) {
            Dialog.show("Error", "HoursNumber must be numeric !", "OK", "Cancel");
            return false;
        }
        try {
            double d = Double.parseDouble(prix.getText());
        } catch (NumberFormatException nfe) {
            Dialog.show("Error", "Price must be numeric !", "OK", "Cancel");
            return false;
        }
        
       Date d1 = new Date();
       Date d2 = datePicker.getDate();
                    Calendar debut = Calendar.getInstance();
                    Calendar now=Calendar.getInstance();
                    now.setTime(d1);
                    debut.setTime(d2);
                    if (debut.before(now)||debut.equals(now)) {
                        Dialog.show("Invalid date", "Oups !!\nPlease check Training Date \n(must be after the current date)!", "OK", "Cancel");
                        return false;
         }
                    
        
        return true;
    }
      private int RecupereUser()
      {
       return User.getInstace(0,"","","","",0).getId();
      }
    
}
