/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Competition;
import Services.CompetitionService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.List;

/**
 *
 * @author User
 */
public class ConsultandParticipate extends BaseForm{
     public ConsultandParticipate(Resources res) {
         
       super("Competitions", BoxLayout.y());              
       
        List<Competition> lh = CompetitionService.getInstance().getAllCompetitions();
        
        Container Competitions = new Container(BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addCommandToOverflowMenu("My Competitions", null,(e) -> new AffichageCompetitionParticipes(res).show());
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
        Competitions.setScrollableY(true);
         Container List = new Container();
        if (lh != null) {
             for (Competition h : lh) {
                 
//                     Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));               
//                     MultiButton mb = new MultiButton(h.getNom());
//                     mb.setUIID("ListItem");
//                     Button btnConsult = new Button("Consult");
//                   
//                     mb.setTextLine3(h.getCategorie());
//                      //mb.add(LEFT, img);
//                     Competitions.add(FlowLayout.encloseCenter(mb));
//                     btnConsult.addActionListener(e -> new ParticipateForm(h,res).show());
//                     C1.add(btnConsult);
//                     Competitions.add(FlowLayout.encloseCenter(C1));
              addButton1(res,List, h);

                 }
             
        } else {
            System.out.println("ma7abech yekhdem");
        
        }
                tb.addSearchCommand(e -> {
            String text = (String) e.getSource();
                if (text == null || text.length() == 0) {
                    // clear search
                    List.removeAll();

                if (lh != null) {
                    for (Competition h : lh) {
                        addButton1(res, List, h);
                        System.out.println(h);
                    }
                } else {
                    System.out.println("No Accommodations available\nPlease Try later !! ");
                    List.add("\n\n\tNo Accommodations available\n\tPlease feel free to check later \n     for new Accommodations   !! ");

                }
                refreshTheme();
                } else {
                    List.removeAll();
                    text = text.toLowerCase();
        List<Competition> sh = CompetitionService.getInstance().getSearched(text);
                if (sh != null) {
                    for (Competition h : sh) {
                        addButton1(res, List, h);
                        System.out.println(h);
                    }
                } else {
                    System.out.println("No Accommodations available\nPlease Try later !! ");
                    List.add("\n\n\tNo Accommodations available\n\tPlease feel free to check later \n     for new Accommodations   !! ");

                }
                refreshTheme();
                }

        });
         add(LayeredLayout.encloseIn(List));
     }
             private void addButton1(Resources res, Container List, Competition mdt) {
                Image img;
                if(mdt.getCategorie().toLowerCase().equals("fishing"))
                {img= res.getImage("c700x420.jpg");}
                else
                {
                   img= res.getImage("images.jpg"); 
                }

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        TextField ta = new TextField(mdt.getNom());
        ta.setUIID("PopupDialogTitle");
        ta.setEditable(false);
        Button btnConsult = new Button("Consult");
       
        btnConsult.addActionListener(e -> new ParticipateForm(mdt,res).show());
                image.addPointerPressedListener((ActionListener) (ActionEvent evt)
                        -> Dialog.show("Competition", "Category: " + mdt.getCategorie()+ "\nLieu: " + mdt.getLieu()+ "\nParticipants: " + mdt.getNbParticipants()+ "\nStarts: " + mdt.getDateDebut()+ "\nEnds: " + mdt.getDateFin()+"\nName: " + mdt.getNom()+  "Dt", new Command("OK")));

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta
                ));
        cnt.add(BorderLayout.EAST,BoxLayout.encloseY(btnConsult));
        List.add(cnt);
        
    }
    
}
