/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.MoyenDeTransport;
import Services.MoyenDeTransportService;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
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
 * @author ASUS1
 */
public class ModifyTransport extends Form{
    public ModifyTransport(MoyenDeTransport M,Image img,Resources res) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        super("", BoxLayout.y());
        setTitle("Update Mean Of Transport");
        setLayout(BoxLayout.y());
         Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
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
        
        TextField tfType = new TextField(M.getType(),"Type");
        TextField tfPricePerDay= new TextField(Float.toString(M.getPrixParJour()),"pricePerDay");
        TextField tfMark= new TextField(M.getMarque(), "Mark");
        TextField tfCategory= new TextField(M.getCategorie(), "Category");
        TextField tfImage= new TextField(M.getImage(), "Image");
        Button btnValider = new Button("Confirm");
        ImageViewer iv = new ImageViewer(img);
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfType.getText().length()==0)||(tfPricePerDay.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        MoyenDeTransport t = new MoyenDeTransport(M.getId(),tfType.getText(), Float.parseFloat(tfPricePerDay.getText()),tfImage.getText(),tfMark.getText(),tfCategory.getText());
                        if( MoyenDeTransportService.getInstance().modifyTransport(t))
                            Dialog.show("Success","Mean of Transport successfully Modified",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(iv,tfType,tfPricePerDay,tfMark,tfCategory,tfImage,btnValider);   
    }
}
