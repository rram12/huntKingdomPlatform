/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Hebergement;
import Entities.MoyenDeTransport;
import Services.HebergementService;
import Services.MoyenDeTransportService;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class ListTransportForm extends Form {

    public ListTransportForm(Resources res) {
        super("Accomodations", new BorderLayout());

        List<MoyenDeTransport> lh = MoyenDeTransportService.getInstance().getAllTransports();

        Container Mts = new Container(BoxLayout.y());
        Mts.setUIID("List");
        Mts.setScrollableY(true);
        if (lh != null) {
            for (MoyenDeTransport h : lh) {

                Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Label lbMarque = new Label(h.getMarque());
                Label lbPricePerDay = new Label(Float.toString(h.getPrixParJour()));
                Button btnDel = new Button("delete");
                Button btnModif = new Button("Modify");
                Button btnRent = new Button("Rent");
                btnDel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (MoyenDeTransportService.getInstance().deleteTransport(h.getId())) {
                                Dialog.show("Success", "Mean of Transport successfully deleted", new Command("OK"));
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }
                    }
                });
                btnModif.addActionListener(e -> new ModifyTransport(h).show());
//                btnRent.addActionListener(e -> new LocationForm(h).show());
                lbMarque.addPointerPressedListener((ActionListener) (ActionEvent evt)
                        -> Dialog.show("Transport", "Mark: " + h.getMarque() + "\nCategory: " + h.getCategorie() + "\nType: " + h.getType()+ "\nPricePerDay: " + h.getPrixParJour() + "Dt", new Command("OK")));
                C1.add(lbMarque);
                C1.add(lbPricePerDay);
                C1.add(btnDel);
                C1.add(btnModif);
                C1.add(btnRent);
//                C1.setLeadComponent(lbMarque);
                //mb.add(LEFT, img);
                Mts.add(FlowLayout.encloseCenter(C1));
            }

            this.add(CENTER, Mts);

        } else {
            System.out.println("ma7abech yekhdem");

        }
    }
}
