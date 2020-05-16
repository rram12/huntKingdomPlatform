/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Hebergement;
import Entities.Location;
import Entities.MoyenDeTransport;
import Entities.Reservation;
import Services.LocationService;
import Services.ReservationService;
import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class MaReservationForm extends BaseForm {

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();

    public MaReservationForm(Resources res, Hebergement M, Image img, Reservation r) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        super(new BorderLayout());
        List<Reservation> ls = ReservationService.getInstance().getAllReservations(M.getId());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Image imgs = res.getImage("bg1.jpg");
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        getAllStyles().setBgImage(imgs);
        ImageViewer iv = new ImageViewer(img);
        Label start = new Label("Arrival Date");
        TextField TfArrival1 = new TextField(formatter.format(r.Arrivaldate()),"Arrival Date", 20, TextField.ANY);
        TfArrival1.setSingleLineTextArea(false);
        TfArrival1.setEditable(false);
        Picker tfArrival = new Picker();
        tfArrival.setText("Arrival Date");
//        tfArrival.setType(Display.PICKER_TYPE_DATE);
        tfArrival.setDate(r.Arrivaldate());
//        tfArrival.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
Label lAddress=new Label("Address");
        lAddress.setTextPosition(LEFT);
        FontImage.setMaterialIcon(lAddress, FontImage.MATERIAL_LOCATION_CITY);
        Label lMark = new Label("Make Your Reservation");
        Label lType=new Label("Type");
        Label lDays = new Label("Days");
        Label lPricePerDay = new Label("Price Per Day");
        TextField Address = new TextField(M.getAdresse(), "Address", 20, TextField.UNEDITABLE);
        TextField Type = new TextField(M.getType(), "Type", 20, TextField.UNEDITABLE);
        TextField PricePerDay = new TextField(Float.toString(M.getPrixParJour()) + "Dt", "Price", 20, TextField.UNEDITABLE);
        TextField Days = new TextField(Integer.toString(r.getNbJours()), "Days", 20, TextField.DECIMAL);
        Address.setSingleLineTextArea(false);
        Type.setSingleLineTextArea(false);
        PricePerDay.setSingleLineTextArea(false);
        Days.setSingleLineTextArea(false);
        Address.setEditable(false);
        Type.setEditable(false);
        PricePerDay.setEditable(false);
        lMark.getAllStyles().setFgColor(0xFFFFFF, true);
        start.getAllStyles().setFgColor(0xFFFFFF, true);
        lDays.getAllStyles().setFgColor(0xFFFFFF, true);
        lType.getAllStyles().setFgColor(0xFFFFFF, true);
        lAddress.getAllStyles().setFgColor(0xFFFFFF, true);
        lPricePerDay.getAllStyles().setFgColor(0xFFFFFF, true);
        Address.getAllStyles().setFgColor(0xFFFFFF, true);
        Type.getAllStyles().setFgColor(0xFFFFFF, true);
        PricePerDay.getAllStyles().setFgColor(0xFFFFFF, true);
        Days.getAllStyles().setFgColor(0xFFFFFF, true);
        tfArrival.getAllStyles().setFgColor(0xFFFFFF, true);
        TfArrival1.getAllStyles().setFgColor(0xFFFFFF, true);
        Address.getAllStyles().setAlignment(TextField.CENTER);
        Type.getAllStyles().setAlignment(TextField.CENTER);
        PricePerDay.getAllStyles().setAlignment(TextField.CENTER);
        Days.getAllStyles().setAlignment(TextField.CENTER);
        tfArrival.getAllStyles().setAlignment(TextField.CENTER);
        TfArrival1.getAllStyles().setAlignment(TextField.CENTER);
        Address.getAllStyles().setBorder(Border.createEmpty());
        Type.getAllStyles().setBorder(Border.createEmpty());
        PricePerDay.getAllStyles().setBorder(Border.createEmpty());
        Button next = new Button("Confirm");
        Button delete = new Button("Cancel");
        Button signIn = new Button("See Reservations");
        signIn.getAllStyles().setBorder(Border.createEmpty());
        signIn.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        signIn.addActionListener(e -> new CalendarForm(res, ls, new ArrayList<Location>()).show());
        Calendar debut1 = Calendar.getInstance();
        Calendar now1 = Calendar.getInstance();
        debut1.setTime(r.Arrivaldate());
        now1.setTime(date);
        long diff = r.Arrivaldate().getTime() - date.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        if (now1.before(debut1)&&diffDays>7)
            {
            Container content = BoxLayout.encloseYCenter(
                new Label(""),
                createLineSeparator(),
                iv,
                BoxLayout.encloseXCenter(lAddress),Address,
                BoxLayout.encloseXCenter(lType),Type,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lPricePerDay),PricePerDay,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lMark),
                BoxLayout.encloseXCenter(lDays),Days,
                createLineSeparator(),
                BoxLayout.encloseXCenter(start), tfArrival,
                createLineSeparator()
        );
        content.getAllStyles().setFgColor(0xFFFFFF, true);
            content.setScrollableY(true);
            add(BorderLayout.NORTH, BoxLayout.encloseXCenter(new Label("Accommodation")));
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                BoxLayout.encloseXCenter(next,delete), signIn
        ));
        }
        else{
            Days.setEditable(false);
            Container content = BoxLayout.encloseYCenter(
                new Label(""),
                createLineSeparator(),
                iv,
                BoxLayout.encloseXCenter(lAddress),Address,
                BoxLayout.encloseXCenter(lType),Type,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lPricePerDay),PricePerDay,
                createLineSeparator(),
                new Label("You cannot change neither delete your Reservation due to delay constraints ", "LogoLabel"),
                BoxLayout.encloseXCenter(lDays),Days,
                createLineSeparator(),
                BoxLayout.encloseXCenter(start), tfArrival,
                createLineSeparator()
        );
            content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
//        add(BorderLayout.SOUTH, BoxLayout.encloseY(
//                signIn
//        ));
        }
        delete.requestFocus();
        delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (ReservationService.getInstance().deleteReservation(r.getId())) {
                                Dialog.show("Success", "Reservation successfully deleted", new Command("OK"));
                                new ServicesForm(res).show();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }
                    }
                });
        next.requestFocus();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (validateFields(Days, tfArrival, ls,r)) {
                    try {

                        Reservation t = new Reservation(r.getId(),Integer.parseInt(Days.getText()), Float.parseFloat(Days.getText()) * M.getPrixParJour(), tfArrival.getDate());
//                        System.out.println(t);
                        if (ReservationService.getInstance().modifyReservation(t)) {
                            Dialog.show("Success", "Reservation successfully modified", new Command("OK"));
                            previous.showBack();
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }
            }
        });
    }

    private boolean validateFields(TextField Days, Picker Arrival, List<Reservation> ls,Reservation r) {
        System.out.println(Days.getText());
        if (Float.parseFloat(Days.getText()) < 1) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        } else {
            for (Reservation l : ls) {
                if(l.getId()!=r.getId()){
                for (int i = 0; i < Integer.parseInt(Days.getText()); i++) {
                    Date d = datePlusOne(i, Arrival.getDate());
                    Calendar debut = Calendar.getInstance();
                    Calendar fin = Calendar.getInstance();
                    Calendar entre = Calendar.getInstance();
                    Calendar now = Calendar.getInstance();
                    debut.setTime(l.Arrivaldate());
                    fin.setTime(l.finaldate());
                    now.setTime(date);
                    entre.setTime(d);
                    if ((debut.before(entre) && entre.before(fin)) || debut.equals(entre) || entre.before(now) || entre.equals(now)) {
                        Dialog.show("Invalid date", "Oups !!\nPlease check the list of Reservations for available date !", "OK", "Cancel");
                        return false;
                    }
                }
            }
            }
        }
        return true;
    }

    public Date datePlusOne(int i, Date d) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(d);
        //Incrementing the date by 1 day
        c.add(java.util.Calendar.DAY_OF_MONTH, i);
        Date date = c.getTime();

        java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");

        String date1 = format1.format(date);

        Date inActiveDate = null;

        try {

            inActiveDate = format1.parse(date1);

        } catch (ParseException e1) {
        }
        return inActiveDate;
    }
}
