/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Competition;
import Entities.Participation;
import Services.CompetitionService;
import Services.ParticipationService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.SimpleDateFormat;
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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public class AddCompetitionForm extends BaseForm{
    String path = null;
    public AddCompetitionForm(Resources res){
        setTitle("Add a new Competition");
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
        List<Participation> lh = ParticipationService.getInstance().getAllParticipations(8);
        Label nom = new Label("Name");
        TextField tNom= new TextField("", "Name..");
        Label categorie = new Label("Category");
        ComboBox<String> Categorie = new ComboBox<> ("Hunting","Fishing");
        Label address = new Label("Address");
        TextField tLieu= new TextField("", "address..");
        Label participant = new Label("Participant");
        TextField tParticipant= new TextField("", "participant..");
        Label likes = new Label(" From: ");
        TextField start = new TextField("", "Debute date", 20, TextField.ANY);
        start.setEditable(false);
        Picker tfArrival = new Picker();
        tfArrival.setText("Debute date");
        tfArrival.setType(Display.PICKER_TYPE_DATE);
        tfArrival.setDate(new Date());
        tfArrival.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        Label comments = new Label(" To: " );
        TextField end = new TextField("", "End date", 20, TextField.ANY);
        end.setEditable(false);
        Picker tEnd = new Picker();
        tEnd.setText("End date");
        tEnd.setType(Display.PICKER_TYPE_DATE);
        tEnd.setDate(new Date());
        tEnd.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        Button btnValider = new Button("Confirm");
          Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
          val.addConstraint(tNom,
                        new RegexConstraint("^([a-zA-Z])[a-zA-Z_-]*[\\w_-]*[\\S]$|^([a-zA-Z])[0-9_-]*[\\S]$|^[a-zA-Z]*[\\S]$", "Example: name|name123|name_123|name-123|name123_type")).
       
        addConstraint(tLieu,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field"))).
       
        addConstraint(tParticipant,
                        new RegexConstraint("^\\d{1,2}$", "Numeric Field"));
         
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                if (validateFields(tNom, tParticipant, tLieu, tfArrival, tEnd,val))
                {
                    try {
                        Competition p;
                        p = new Competition(Categorie.getSelectedItem(),tNom.getText(),tLieu.getText(),Integer.parseInt(tParticipant.getText()),tfArrival.getDate(),tEnd.getDate());
                        System.out.println(p);
                        if( CompetitionService.getInstance().addCompetition(p))
                            Dialog.show("Success","Competition successfully added",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
            }
        });
                addAll(categorie,Categorie,nom,tNom,address,tLieu,participant,tParticipant,likes,tfArrival,comments,tEnd,btnValider);     
              
    }
        private boolean validateFields(TextField nomm,TextField participants, TextField lieu, Picker debutePicker,Picker endPicker,Validator val) {
        if (nomm.getText().isEmpty() || lieu.getText().isEmpty() || participants.getText().isEmpty() || debutePicker.getDate() == null || endPicker.getDate() == null) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        }
        if (!val.isValid()) {
            Dialog.show("Error", "Please check all the fields !", "OK", "Cancel");
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
    
