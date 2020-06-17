/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.util.Date;
import Entities.Location;
import Entities.MoyenDeTransport;
import Entities.Reservation;
import Entities.User;
import Services.LocationService;
import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
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
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class LocationForm extends BaseForm {

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    String LastDay="1";
    public LocationForm(Resources res, MoyenDeTransport M, Image img) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        super(new BorderLayout());
        List<Location> ls = LocationService.getInstance().getAllLocations(M.getId());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Image imgs = res.getImage("bg1.jpg");
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        getAllStyles().setBgImage(imgs);
        ImageViewer iv = new ImageViewer(img);
        Label start = new Label("Arrival Date");
        Picker tfArrival = new Picker();
        tfArrival.setText("Arrival Date");
//        tfArrival.setType(Display.PICKER_TYPE_DATE);
        tfArrival.setDate(new Date());
//        tfArrival.setFormatter(new SimpleDateFormat("yyyy-MM-dd"));
        Label lMark = new Label("Make Your Rent");
        Label lDays = new Label("Days");
        Label lCategory = new Label("Category:");
        Label lPricePerDay = new Label("Price Per Day:");
        Label lFinalPrice = new Label("Total Price");
        TextField Mark = new TextField(M.getMarque(), "Brand", 20, TextField.UNEDITABLE);
        TextField Category = new TextField(M.getCategorie(), "Category", 20, TextField.UNEDITABLE);
        TextField PricePerDay = new TextField(Float.toString(M.getPrixParJour()) + "Dt", "Price", 20, TextField.UNEDITABLE);
        TextField FinalPrice = new TextField(Float.toString(M.getPrixParJour()) + "Dt", "Price", 10, TextField.UNEDITABLE);
        TextField Days = new TextField("1", "Days", 20, TextField.ANY);
        Mark.setSingleLineTextArea(false);
        Category.setSingleLineTextArea(false);
        PricePerDay.setSingleLineTextArea(false);
        FinalPrice.setSingleLineTextArea(false);
        Days.setSingleLineTextArea(false);
        Mark.setEditable(false);
        Category.setEditable(false);
        PricePerDay.setEditable(false);
        FinalPrice.setEditable(false);
        start.getAllStyles().setFgColor(0xFFFFFF, true);
        lDays.getAllStyles().setFgColor(0xFFFFFF, true);
        lMark.getAllStyles().setFgColor(0xFFFFFF, true);
        lCategory.getAllStyles().setFgColor(0xFFFFFF, true);
        lPricePerDay.getAllStyles().setFgColor(0xFFFFFF, true);
        lFinalPrice.getAllStyles().setFgColor(0xFFFFFF, true);
        Mark.getAllStyles().setFgColor(0xFFFFFF, true);
        Category.getAllStyles().setFgColor(0xFFFFFF, true);
        PricePerDay.getAllStyles().setFgColor(0xFFFFFF, true);
        FinalPrice.getAllStyles().setFgColor(0xFFFFFF, true);
        Days.getAllStyles().setFgColor(0xFFFFFF, true);
        tfArrival.getAllStyles().setFgColor(0xFFFFFF, true);
        Category.getAllStyles().setAlignment(TextField.CENTER);
        PricePerDay.getAllStyles().setAlignment(TextField.CENTER);
        FinalPrice.getAllStyles().setAlignment(TextField.CENTER);
        Days.getAllStyles().setAlignment(TextField.CENTER);
        tfArrival.getAllStyles().setAlignment(TextField.CENTER);
        Category.getAllStyles().setBorder(Border.createEmpty());
        PricePerDay.getAllStyles().setBorder(Border.createEmpty());
        FinalPrice.getAllStyles().setBorder(Border.createEmpty());
//        Days.getAllStyles().setBorder(Border.createEmpty());
//        tfArrival.getAllStyles().setBorder(Border.createEmpty());
        Button next = new Button("Confirm");
        Button signIn = new Button("See Rents");
        signIn.getAllStyles().setBorder(Border.createEmpty());
        signIn.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        signIn.addActionListener(e -> new CalendarForm(res,new ArrayList<Reservation>(), ls).show());
        Days.addDataChangedListener((i1,i2) -> {
            if (Days.getText().length() != 0) {
                try {
            FinalPrice.setText(Float.toString((float) (Integer.parseInt(Days.getText())*M.getPrixParJour())));
            LastDay=Days.getText();
        } catch (NumberFormatException nfe) {
            Days.stopEditing();
            Days.setText(LastDay);
            Days.startEditingAsync();
        }
            }
            System.out.println(Days.getText());
        });
        Container content = BoxLayout.encloseYCenter(
                createLineSeparator(),
                iv,
                BoxLayout.encloseXCenter(lCategory), Category,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lPricePerDay), PricePerDay,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lMark),
                //                new SpanLabel("Make your Reservation "),
                BoxLayout.encloseXCenter(lDays), Days,
                createLineSeparator(),
                BoxLayout.encloseXCenter(start), tfArrival,
                createLineSeparator(),
                BoxLayout.encloseXCenter(lFinalPrice),FinalPrice,
                createLineSeparator()
        );
        content.getAllStyles().setFgColor(0xFFFFFF, true);
        content.setScrollableY(true);
        add(BorderLayout.NORTH, BoxLayout.encloseXCenter(new Label(M.getMarque())));
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next, signIn
        ));
        next.requestFocus();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (validateFields(Days, tfArrival, ls)) {
                    try {

                        Location t = new Location(Integer.parseInt(Days.getText()), Float.parseFloat(Days.getText()) * M.getPrixParJour(), tfArrival.getDate(), User.getInstace(0,"","","","",0).getId(), M.getId());
//                        System.out.println(t);
                        if (LocationService.getInstance().addLocation(t)) {
                            Dialog.show("Success", "Rent successfully Made", new Command("OK"));
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

    private boolean validateFields(TextField Days, Picker Arrival, List<Location> ls) {
        System.out.println(Days.getText());
        if (Float.parseFloat(Days.getText()) < 1) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        } else {
            for (Location l : ls) {
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
                        Dialog.show("Invalid date", "Oups !!\nPlease check the list of Rent for available date !", "OK", "Cancel");
                        return false;
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
//    public Date currentDate() {
//        Calendar c = Calendar.getInstance();
//        c.setTime(this.date);
//        Date date = c.getTime();
//
//        java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
//
//        String date1 = format1.format(date);
//
//        Date inActiveDate = null;
//
//        try {
//
//            inActiveDate = format1.parse(date1);
//
//        } catch (ParseException e1) {
//
//        }
//        return inActiveDate;
//    }
}
