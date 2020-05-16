
/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package Gui;

import Entities.PiecesDefectueuses;
import Entities.Reparation;
import Services.PieceService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class RepareForm extends BaseForm {

    public RepareForm(Resources res,String id) {
        super("AddPiece", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");

        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        Image img = res.getImage("profile-background.jpg");
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
                                        new Label("Add your defective piece"))
                        )
                )
        ));

        TextField prix = new TextField("");
        prix.setUIID("TextFieldBlack");
        addStringValue("Price", prix);

        TextField description = new TextField("");
        description.setUIID("TextFieldBlack");
        addStringValue("Decription", description);
        Picker dateTimePicker = new Picker();
        dateTimePicker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        dateTimePicker.setDate(null);
        addStringValue("Finish date", dateTimePicker);

        Button bt = new Button("Repare");
        /* SimpleDateFormat  formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	String strDate = formatter.format((Date)dateTimePicker.getValue());*/

        bt.addActionListener(e -> {
            if (validateFields(prix, description, dateTimePicker)) {
                Reparation r = new Reparation(new Date(),(Date) dateTimePicker.getValue(), Double.parseDouble(prix.getText()), description.getText(), 16, Integer.parseInt(id));

                if (PieceService.getInstance().repare(r)) {
                    Dialog.show("ok", "Piece repared ! ", "OK", "Cancel");
                     new ListProductInPromotion(res).show();
                    System.out.println("Piece repared ! ");
                }
            }
        });
        addStringValue("", bt);

    }

    private boolean validateFields(TextField prix, TextField description, Picker dateTimePicker) {
        if (prix.getText().isEmpty() || description.getText().isEmpty() || dateTimePicker.getDate() == null) {
            Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
            return false;
        }
        Date d1 = new Date();
       Date d2 = dateTimePicker.getDate();
                    Calendar debut = Calendar.getInstance();
                    Calendar now=Calendar.getInstance();
                    now.setTime(d1);
                    debut.setTime(d2);
                    if (debut.before(now)||debut.equals(now)) {
                        Dialog.show("Invalid date", "Oups !!\nPlease check the End date of reparation\n(must be after the current date)!", "OK", "Cancel");
                        return false;
         }
                    
        try {
            double d = Double.parseDouble(prix.getText());
        } catch (NumberFormatException nfe) {
            Dialog.show("Error", "Price must be numeric !", "OK", "Cancel");
            return false;
        }
        return true;
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    
}
