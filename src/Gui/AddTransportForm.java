/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.MoyenDeTransport;
import Services.MoyenDeTransportService;
import com.codename1.capture.Capture;
import com.codename1.components.MultiButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.Log;
import com.codename1.ui.EncodedImage;
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
import java.io.IOException;

/**
 *
 * @author ASUS1
 */
public class AddTransportForm extends Form{
    String path = null;
    public AddTransportForm(Resources res) {
        
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        super("", BoxLayout.y());
        setTitle("Add Mean Of Transport");
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
        
        //TextField tfType = new TextField("","Type");
        ComboBox<String> Type = new ComboBox<> ("diezel","gaz","battery");
        TextField tfPricePerDay= new TextField("", "pricePerDay");
        TextField tfMark= new TextField("", "Mark");
        ComboBox<String> Category = new ComboBox<> ("car","boat","bike");
        Label tfImage = new Label("image : ");
        MultiButton photo = new MultiButton("");
        Button btnValider = new Button("Confirm");
        String url ="http://localhost/HuntKingdom/web/uploads/photos/choose.png";
       EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getHeight()/5 , 0xFFFFFFFF), true);
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
                Log.e(ex);
            }

        });
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((Type.getSelectedItem().length()==0)||(tfPricePerDay.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        MoyenDeTransport t;
                        t = new MoyenDeTransport(Type.getSelectedItem(), Float.parseFloat(tfPricePerDay.getText()),path,tfMark.getText(),Category.getSelectedItem());
                        System.out.println(path);
                        if( MoyenDeTransportService.getInstance().addTransport(t))
                            Dialog.show("Success","Mean of Transport successfully added",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(Type,tfPricePerDay,tfMark,Category,tfImage,photo,btnValider);   
    }
}
