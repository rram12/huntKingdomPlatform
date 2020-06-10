/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Competition;
import Entities.Participation;
import Entities.User;
import Services.ParticipationService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
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
import java.util.Calendar;
import java.util.Date;
import static javafx.scene.input.KeyCode.P;

/**
 *
 * @author User
 */
public class AnnulerParticipationForm extends BaseForm {

    int idu = User.getInstace(0, "", "", "", "", 0).getId();

    public AnnulerParticipationForm(Competition P, Resources res) {
        setTitle("Annuler Participation");
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
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        TextField tNom = new TextField(P.getNom(), "Name");
        tNom.setEditable(false);
        TextField tCategorie = new TextField(P.getCategorie(), "Category");
        tCategorie.setEditable(false);
        TextField start = new TextField(formatter.format(P.getDateDebut()), "Debute date", 20, TextField.ANY);
        start.setEditable(false);
        TextField end = new TextField(formatter.format(P.getDateFin()), "End date", 20, TextField.ANY);
        end.setEditable(false);
        Button btnValider = new Button("Cancel Participation");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                try {
                    Participation pp;
                    pp = new Participation(idu, P.getId());
                    System.out.println(pp);

                    if (ParticipationService.getInstance().annulerParticipations(pp)) {
                        Dialog.show("Success", "your participation has been successfully canceled", new Command("OK"));
                        new AffichageCompetitionParticipes(res).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }

        });
        Date date = new Date();
        long diff = P.getDateDebut().getTime() - date.getTime();
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
        if (diffDays > 7) {
            addAll(tCategorie, tNom, start, end, btnValider);
        } else {
            addAll(tCategorie, tNom, start, end, new SpanLabel("You cannot cancel your participation due to delay constraints", "PopupDialogTitle"));
        }
    }
}
