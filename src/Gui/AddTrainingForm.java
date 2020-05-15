/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Entities.Entrainement;
import Entities.Produit;
import Services.AnimalService;
import Services.ProduitService;
import Services.TrainingService;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.text.ParseException;
import java.util.ArrayList;
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
        super("Add Training", new BorderLayout());
        
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);

        this.setScrollableY(true);
        this.setLayout(new BorderLayout());

        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
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
          content.setScrollableY(true);

        Button submit = new Button("Add");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
      
          
        content.add(l1).add(categorie);
        content.add(l2).add(nbHeures);
        content.add(l3).add(dateEnt);
        content.add(l4).add(prix);
        content.add(l5).add(lieu);
        content.add(l6).add(animal);
        content.add(l7).add(produit);
        
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
                submit.addActionListener(e -> {
                   
             if ((nbHeures.getText().length() == 0) || (prix.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                Entrainement b = new Entrainement(categorie.getSelectedItem(),Integer.parseInt(nbHeures.getText()),TrainingDate,Double.valueOf(prix.getText()),lieu.getSelectedItem(),5,a.getId(),p.getId(),"encours");
                Entrainement EntrainementAjoutee = TrainingService.getInstance().add(b);

                new ListTrainingForm(res).show();
             }
            
        });

        this.add(CENTER, content);
        this.add(SOUTH, submit);
      Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(nbHeures,
                new GroupConstraint(
                      
                        new RegexConstraint("^[0-9]*$", "Check number of hours !")));
       
        val.addConstraint(prix,
                new GroupConstraint(
                      
                        new RegexConstraint("^[0-9]*$", "Check price number !")));


  
}
    
}
