package Gui;

import Entities.PiecesDefectueuses;
import Services.PieceService;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ListYourPieces extends BaseForm {
Resources res;
EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    public ListYourPieces(Resources res1) {
        super("ListYourPieces", BoxLayout.y());
        res = res1;
        
              
        
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
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
                                new Label("List your pieces"))
                    )
                )
        ));
          
        TextField search = new TextField("");
        search.setUIID("TextFieldBlack");
        addStringValue("search", search);
         Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
         List<PiecesDefectueuses> pieces = PieceService.getInstance().listyourPieces();
        for (PiecesDefectueuses single : pieces) {
            c.add(addItem(single));
            //add(createLineSeparator(0xC89527));
        }
        add(c);
      
         
        search.addDataChangedListener((type, index) -> {
            if (search.getText().length() == 0) {
                removeComponent(c);
                c.removeAll();
               List<PiecesDefectueuses> pieces1 = PieceService.getInstance().listyourPieces();
        for (PiecesDefectueuses single : pieces1) {
            c.add(addItem(single));
            //add(createLineSeparator(0xC89527));
        }
        add(c);

            } else {

                List<PiecesDefectueuses> pieces2 = PieceService.getInstance().getListSearch(search.getText());
                removeComponent(c);
                c.removeAll();
                for (PiecesDefectueuses single : pieces2) {

                    c.add(addItem(single));

                }
                add(c);
            }

            show();
        });
    
      
        
       
    }
    public Container addItem(PiecesDefectueuses c) {
       ImageViewer imgV = null;
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.getStyle().setFgColor(0xbbc2c6);
        c1.setSize(new Dimension(20, 20));
  
     
         String url = "http://localhost/HuntKingdom/web/uploads/photos/"+c.getImage();
     

     EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2, this.getHeight() / 5, 0xFFFFFFFF), true);
        imgs = URLImage.createToStorage(placeholder,url,url,URLImage.RESIZE_SCALE );
        if(imgs.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            imgs = imgs.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
        }
  
       
        ScaleImageLabel sl = new ScaleImageLabel(imgs);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        c1.add(sl);
        
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label lNom = new Label(c.getNom());
        lNom.getStyle().setFgColor(0x1e3642);
        Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        lNom.getStyle().setFont(smallPlainSystemFont);
        
        Label Categorie = new Label("categorie: " +c.getCategorie());
        SpanLabel desc = new SpanLabel("description: " + c.getDescription());
        Label lid= new Label(Integer.toString(c.getId()));
        lid.setHidden(true);
        Button repareBtn;
        if(c.isEtat()){
         repareBtn = new Button("Ready");
         
         
    /*  repareBtn.getUnselectedStyle().setBgColor(0x1A6A12);
        repareBtn.getUnselectedStyle().setBgTransparency(255);
        repareBtn.setShowEvenIfBlank(true);*/
        
        
        
        repareBtn.addActionListener(e->{
            System.out.println("Piece is ready : "+c);
            new Ready(res,lid.getText()).show();
        });
       }else{
         repareBtn = new Button("Show Progress");
        //repareBtn.getStyle().setBgColor(0xC89527);
        repareBtn.addActionListener(e->{
          System.out.println("Piece in progress : "+c);
          new InProgress(res,lid.getText()).show();

        });
        
        }
        
        c2.add(lNom);
        c2.add(Categorie);
        c2.add(desc);
        c2.add(repareBtn);
        c2.add(lid);
        //c1.add(img);
        c1.add(c2);
        c1.setLeadComponent(repareBtn);
        refreshTheme();
        return c1;
 
    }
 private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
    
}
