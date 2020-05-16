/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Hebergement;
import Services.HebergementService;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author ASUS1
 */
public class AddHebergementForm extends Form{

    public AddHebergementForm() {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Add a new Accommodation");
        setLayout(BoxLayout.y());
        
        TextField tfType = new TextField("","Type");
        TextField tfPricePerDay= new TextField("", "pricePerDay");
        TextField tfDescription= new TextField("", "Description");
        TextField tfAddress= new TextField("", "Address");
        TextField tfNbRooms= new TextField("", "Rooms");
        TextField tfCapacity= new TextField("", "Capacity");
        TextField tfImage= new TextField("", "Image");
        Button btnValider = new Button("Add Accommodation");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfType.getText().length()==0)||(tfPricePerDay.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Hebergement t = new Hebergement(tfType.getText(), Float.parseFloat(tfPricePerDay.getText()),tfImage.getText(), 
                                tfAddress.getText(), Integer.parseInt(tfNbRooms.getText()), Integer.parseInt(tfCapacity.getText()), Integer.parseInt(tfCapacity.getText()),tfDescription.getText());
                        if( HebergementService.getInstance().addHebergement(t))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfType,tfPricePerDay,tfDescription,tfAddress,tfNbRooms,tfCapacity,tfImage,btnValider);   
    }
    
    
}
