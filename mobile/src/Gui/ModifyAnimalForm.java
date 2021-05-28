/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Services.AnimalService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 *
 * @author tibh
 */
public class ModifyAnimalForm extends BaseForm{
    public ModifyAnimalForm(Animal a,Resources res)
    {
                super("Modify Animal", BoxLayout.y());

        setLayout(BoxLayout.y());
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
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

       
         ComboBox<String>categorie = new ComboBox<> ("Hunting","Fishing");
     TextField nom = new TextField();
     TextField description = new TextField();
    
     ComboBox<String>Debut = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
    ComboBox<String>Fin = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
         nom.setText(a.getNom());
         description.setText(a.getDescription());
         
         categorie.setSelectedItem(a.getCategorie());
        Button btnValider = new Button("Modify");
switch (a.getDebutSaison()) {
                       
  case 1:
    Debut.setSelectedItem("January");
    break;
  case 2:
    Debut.setSelectedItem("February");
    break;
  case 3:
    Debut.setSelectedItem("March");
    break;
  case 4:
   Debut.setSelectedItem("April");
    break;
  case 5:
    Debut.setSelectedItem("May");
    break;
  case 6:
   Debut.setSelectedItem("June");
    break;
  case 7:
   Debut.setSelectedItem("July");
    break;
    case 8:
    Debut.setSelectedItem("August");
    break;
    case 9:
    Debut.setSelectedItem("September");
    break;
    case 10:
    Debut.setSelectedItem("October");
    break;
    case 11:
    Debut.setSelectedItem("November");
    break;
    case 12:
    Debut.setSelectedItem("December");
    break;
}
switch (a.getFinSaison()) {
                       
  case 1:
    Fin.setSelectedItem("January");
    break;
  case 2:
    Fin.setSelectedItem("February");
    break;
  case 3:
    Fin.setSelectedItem("March");
    break;
  case 4:
   Fin.setSelectedItem("April");
    break;
  case 5:
    Fin.setSelectedItem("May");
    break;
  case 6:
   Fin.setSelectedItem("June");
    break;
  case 7:
   Fin.setSelectedItem("July");
    break;
    case 8:
    Fin.setSelectedItem("August");
    break;
    case 9:
    Fin.setSelectedItem("September");
    break;
    case 10:
    Fin.setSelectedItem("October");
    break;
    case 11:
    Fin.setSelectedItem("November");
    break;
    case 12:
    Fin.setSelectedItem("December");
    break;
}
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0) || (description.getText().length() == 0) ) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
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
                     
                        Animal an=new Animal(DS,FS,a.getId() ,categorie.getSelectedItem(),nom.getText(), description.getText() );
                        if (!AnimalService.getInstance().modifyAnimal(an)) {
                             Dialog.show("Error", "", new Command("OK"));
                           
                            
                        } else {
                           
                             Dialog.show("Success", "Animal Modifided", new Command("OK"));
                            new ListAnimalForm(res).show();
                        }

                    
                }
                
            }

        });

        addAll(categorie,nom, description,Debut,Fin, btnValider);
    
    }
    
}
