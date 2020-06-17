/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Publicity;
import Services.PubliciteService;
import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.Log;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author User
 */
public class AddPublicityForm extends Form{
    String path = null;
    public AddPublicityForm(Resources res) {
        
             /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Add a new Publicity");
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
        Label prix = new Label("Price");
        TextField tPrice= new TextField("", "price..");
        Label descript = new Label("Description");
        TextField tDescription= new TextField("", "description");
        Label company = new Label("Company");
        TextField tCompagnie= new TextField("", "Company");
        Label title = new Label("Title");
        TextField tTitre= new TextField("", "Title..");
        Label likes = new Label(" From: ");
        TextField start = new TextField("", "Debute date", 20, TextField.ANY);
        start.setEditable(false);
        Picker tfArrival = new Picker();
        tfArrival.setText("Debute date");
        tfArrival.setType(Display.PICKER_TYPE_DATE);
        tfArrival.setDate(new Date());
        tfArrival.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        Label comments = new Label(" To: ");
        TextField end = new TextField("", "End date", 20, TextField.ANY);
        end.setEditable(false);
        Picker tEnd = new Picker();
        tEnd.setText("End date");
        tEnd.setType(Display.PICKER_TYPE_DATE);
        tEnd.setDate(new Date());
        tEnd.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        
        Label tImage = new Label("image : ");
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
                if (validateFields(tTitre, tPrice, tCompagnie,tDescription, tfArrival, tEnd))
                {
                    try {
                        Publicity p;
                        p = new Publicity(tfArrival.getDate(),tEnd.getDate(),tCompagnie.getText(),tTitre.getText(),Float.parseFloat(tPrice.getText()),tDescription.getText(),path);
                        System.out.println(p);
                        if( PubliciteService.getInstance().addPublicity(p))
                            Dialog.show("Success","Publicity successfully added",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(likes,tfArrival,comments,tEnd,company,tCompagnie,title,tTitre,prix,tPrice,descript,tDescription,tImage,photo,btnValider,new Label()); 
         Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
          val.addConstraint(tTitre,
                        new RegexConstraint("^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$", "Example: name|name123|name_123|name-123|name123_type")).
       
        addConstraint(tCompagnie,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field"))).
       
        addConstraint(tDescription,
                
                        new LengthConstraint(10, "Minimum 10 caracters")).
                  
        addConstraint(tPrice,
                        new RegexConstraint("^[0-9]+([\\,|\\.]{0,1}[0-9]{1,2}){0,1}$", "Numeric Field"));
          
    }
     private boolean validateFields(TextField titlee,TextField prixx, TextField companyy, TextField descript, Picker debutePicker,Picker endPicker) {
        if (titlee.getText().isEmpty() || prixx.getText().isEmpty() || companyy.getText().isEmpty() || descript.getText().isEmpty() || debutePicker.getDate() == null || endPicker.getDate() == null) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        }
        Date d1 = new Date();
       Date d2 = debutePicker.getDate();
       Date d3 = endPicker.getDate();
                    Calendar debut = Calendar.getInstance();
                    Calendar fin = Calendar.getInstance();
                    Calendar now=Calendar.getInstance();
                    now.setTime(d1);
                    debut.setTime(d2);
                    fin.setTime(d3);
                    if (debut.before(now)||debut.equals(now)) {
                        Dialog.show("Invalid date", "\nPlease check the debute date\n(must be after the current date)!", "OK", "Cancel");
                        return false;
                     }
                    if(fin.before(debut)||fin.equals(debut)){
                             Dialog.show("Invalid date", "\nPlease check the end date\n(must be after the debute date)!", "OK", "Cancel");
                        return false;
                            
                        }
                        
                    
                    
//        try {
//            int p = Integer.parseInt(participants.getText());
//        } catch (NumberFormatException nfe) {
//            Dialog.show("Error", " must be numeric !", "OK", "Cancel");
//            return false;
//        }
        return true;
    }

    
    
    
}
