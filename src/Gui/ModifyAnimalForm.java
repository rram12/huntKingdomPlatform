/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Services.AnimalService;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author tibh
 */
public class ModifyAnimalForm extends BaseForm{
    public ModifyAnimalForm(Animal a,Resources res)
    {
         setTitle("Modify Animal");
        
        setLayout(BoxLayout.y());
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
       
         ComboBox<String>categorie = new ComboBox<> ("Hunting","Fishing");
     TextField nom = new TextField();
     TextField description = new TextField();
     TextField DebutSaison = new TextField();
     TextField FinSaison = new TextField();
         nom.setText(a.getNom());
         description.setText(a.getDescription());
         DebutSaison.setText(Integer.toString(a.getDebutSaison()));
         FinSaison.setText(Integer.toString(a.getFinSaison()));
         categorie.setSelectedItem(a.getCategorie());
        Button btnValider = new Button("Modify");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0) || (description.getText().length() == 0) || (DebutSaison.getText().length() == 0)|| (FinSaison.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                             
                       
                        Animal an=new Animal( Integer.parseInt(DebutSaison.getText()),Integer.parseInt(FinSaison.getText()),a.getId() ,categorie.getSelectedItem(),nom.getText(), description.getText() );
                        if (!AnimalService.getInstance().modifyAnimal(an)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            new ListAnimalForm(res).show();
                        } else {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            new ListAnimalForm(res).show();
                        }

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "DebutSaison and FinSaison must be a number", new Command("OK"));
                    }

                }
            }

        });

        addAll(categorie,nom, description,DebutSaison,FinSaison, btnValider);
    
    }
    
}
