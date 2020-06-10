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
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
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
        if (lh != null) {
             for (Competition h : lh) {
                 
                     Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));               
                     MultiButton mb = new MultiButton(h.getNom());
                     mb.setUIID("ListItem");
                     Button btnConsult = new Button("Consult");
                   
                     mb.setTextLine3(h.getCategorie());
                      //mb.add(LEFT, img);
                     Competitions.add(FlowLayout.encloseCenter(mb));
                     btnConsult.addActionListener(e -> new ParticipateForm(h,res).show());
                     C1.add(btnConsult);
                     Competitions.add(FlowLayout.encloseCenter(C1));

                 }
             
            this.add( Competitions);
             
        } else {
            System.out.println("ma7abech yekhdem");
        
        }
     }
    
}
