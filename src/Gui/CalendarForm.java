/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Location;
import Entities.Reservation;
import com.codename1.components.FloatingHint;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class CalendarForm extends BaseForm{
    public CalendarForm(Resources res,List<Reservation> rs,List<Location> ls) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        
        add(BorderLayout.NORTH, 
                BoxLayout.encloseXCenter(new Label("!Reservations/Rents Calendar!"))
//                BoxLayout.encloseY(
//                        new Label(res.getImage("smily.png")),
//                        new Label("    !Reservations/Rents Calendar!")
//                )
        );
        
//        TextField code = new TextField("", "Enter Code", 20, TextField.PASSWORD);
//        
//        Button signUp = new Button("Sign Up");
//        Button resend = new Button("Resend");
//        Label alreadHaveAnAccount = new Label("Already have an account?");
//        Button signIn = new Button("Sign In");
//Date dateObject1=new Date();
//        Date dateObject2=new Date();
//                Date dateObject3=new Date();
        ArrayList<Date> selectedA = new ArrayList<Date>();
        if(rs!=null || ls!=null)
        {
            if(rs!=null){
                for (Reservation r : rs) {
                for (int i = 0; i < r.getNbJours(); i++) {
//                    Date d = datePlusOne(i, r.Arrivaldate());
                    selectedA.add(datePlusOne(i, r.Arrivaldate()));
                }
            }
            }
            if(ls!=null)
            {
                for (Location l : ls) {
                for (int j = 0; j < l.getNbJours(); j++) {
//                    Date d = datePlusOne(i, l.Arrivaldate());
                    selectedA.add(datePlusOne(j, l.Arrivaldate()));
                }
            }
            }
        }
//        selectedA.add(rs.get(0).Arrivaldate());
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            dateObject1 = sdf.parse("2020-05-17");
//        } catch (ParseException ex) {
//        }
//        try {
//            dateObject2 = sdf.parse("2020-05-18");
//        } catch (ParseException ex) {
//        }
//        try {
//            dateObject3 = sdf.parse("2020-05-19");
//        } catch (ParseException ex) {
//        }
////        Date a=java.text.SimpleDateFormat("yyyy-MM-dd").parse("2020-05-17");
//        selectedA.add(dateObject1);
//        selectedA.add(dateObject2);
//        selectedA.add(dateObject3);
        Collection<Date> selected = selectedA;
        System.out.println(ls);
        System.out.println(rs);
        System.out.println(selectedA);
        System.out.println(selected);
        Calendar c=new Calendar();
        Image imgs = res.getImage("bg1.jpg");
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        getAllStyles().setBgImage(imgs);
        c.setMultipleSelectionEnabled(true);
        c.setSelectedDays(selected,"CalendarSelectedDay");
        c.setChangesSelectedDateEnabled(false);
//        c.highlightDates(selected);
        Container content = BoxLayout.encloseY(
                new SpanLabel(""),
                new SpanLabel(""),
                new SpanLabel(""),
                new SpanLabel(""),
                c,
//                new FloatingHint(code),
//                createLineSeparator(),
                new SpanLabel("We've sent the confirmation code to your email. Please check your inbox", "CenterLabel")
//                ,resend,
//                signUp,
//                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
    }
    public Date datePlusOne(int i, Date d) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(d);
        //Incrementing the date by i days
        c.add(java.util.Calendar.DAY_OF_MONTH, i);
        Date date = c.getTime();
        return date;
    }
}
