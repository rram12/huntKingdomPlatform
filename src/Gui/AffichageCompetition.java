/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Competition;
import Services.CompetitionService;
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
 * @author User
 */
public class AffichageCompetition extends BaseForm{
    public AffichageCompetition(Resources res)  {
        
         super("Competitions", BoxLayout.y());
        
        List<Competition> lh = CompetitionService.getInstance().getAllCompetitions();
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addCommandToOverflowMenu("Add", null,(e) -> new AddCompetitionForm(res).show());
        Image img1 = res.getImage("news-item.jpg");
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
            for (Competition h : lh) {

                addButton1(res, Mts, h);
            }
        } else {
            System.out.println("ma7abech yekhdem");

        }
        add(LayeredLayout.encloseIn(Mts));
        }
        private void addButton1(Resources res, Container List, Competition mdt) {
                Image img;
                if(mdt.getCategorie().toLowerCase().equals("fishing"))
                {img= res.getImage("news-item.jpg");}
                else
                {
                   img= res.getImage("news-item-3.jpg"); 
                }

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        TextField ta = new TextField(mdt.getNom());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" From : " + mdt.getDateDebut());
        Label comments = new Label(" To: " + mdt.getDateFin());
        Button btnDel = new Button("delete");
                Button btnModif = new Button("Modify");
                btnDel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        try {
                            if (CompetitionService.getInstance().deleteCompetition(mdt.getId())) {
                                Dialog.show("Success", "Competition successfully deleted", new Command("OK"));
                                new AffichageCompetition(res).show();
                            } else {
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                            }
                        } catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }
                    }
                });
            btnModif.addActionListener(e -> new ModifyCompetition(mdt,res).show());
                image.addPointerPressedListener((ActionListener) (ActionEvent evt)
                        -> Dialog.show("Competition", "Category: " + mdt.getCategorie()+ "\nLieu: " + mdt.getLieu()+ "\nParticipants: " + mdt.getNbParticipants()+ "\nStarts: " + mdt.getDateDebut()+ "\nEnds: " + mdt.getDateFin()+"\nName: " + mdt.getNom()+  "Dt", new Command("OK")));

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                       likes, comments
                ));
        cnt.add(BorderLayout.EAST,BoxLayout.encloseY(btnDel,btnModif));
        List.add(cnt);
        
    }

    
    
}
