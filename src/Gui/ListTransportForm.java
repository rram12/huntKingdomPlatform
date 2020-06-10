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
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
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
import com.codename1.ui.util.Resources;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class ListTransportForm extends BaseForm {

    public ListTransportForm(Resources res) {
        super("Means Of Transport", BoxLayout.y());
        
        List<MoyenDeTransport> lh = MoyenDeTransportService.getInstance().getAllTransports();
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        
        tb.addCommandToRightBar("Add", null, (e) -> new AddTransportForm(res).show());
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
        Container Mts = new Container();
        if (lh != null) {
            for (MoyenDeTransport h : lh) {

                addButton1(res, Mts, h);
            }
        } else {
            System.out.println("ma7abech yekhdem");

        }
        add(LayeredLayout.encloseIn(Mts));
    }
    private void addButton1(Resources res, Container List, MoyenDeTransport mdt) {
        System.out.println(mdt.getImage());
        String url = "http://localhost/HuntKingdom/web/uploads/" + mdt.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        TextField ta = new TextField(mdt.getMarque());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" Category: " + mdt.getCategorie());
        Label comments = new Label(" Type: " + mdt.getType());
        Label price = new Label(" Price/Day: " + mdt.getPrixParJour() + "Dt");
        Button btnDel = new Button("delete");
                Button btnModif = new Button("Modify");
                btnDel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (MoyenDeTransportService.getInstance().deleteTransport(mdt.getId())) {
                                Dialog.show("Success", "Mean of Transport successfully deleted", new Command("OK"));
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }
                    }
                });
                btnModif.addActionListener(e -> new ModifyTransport(mdt,img,res).show());
                image.addPointerPressedListener((ActionListener) (ActionEvent evt)
                        -> Dialog.show("Transport", "Mark: " + mdt.getMarque() + "\nCategory: " + mdt.getCategorie() + "\nType: " + mdt.getType()+ "\nPricePerDay: " + mdt.getPrixParJour() + "Dt", new Command("OK")));

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments), price
                ));
        cnt.add(BorderLayout.EAST,BoxLayout.encloseY(btnDel,btnModif));
        List.add(cnt);
        
    }
}
