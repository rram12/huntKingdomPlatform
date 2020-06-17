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

import Entities.Products;
import Services.ProduitService;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
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
import com.codename1.ui.Form;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class Cart extends BaseForm {
Resources res;
 EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
         List<Products> Mesproduits = new ArrayList<>();
String Lastqtt;
     Double prixTotale;
int quantite = 1;
    public Cart(Resources res1,List<Products> produits) {
        super("your Cart", BoxLayout.y());
        Mesproduits = produits;
        prixTotale = 0.0;
        res = res1;
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        Image img = res.getImage("bg-2.jpg");
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
                                new Label(""))
                    )
                )
        ));

         System.out.println("produits : "+Mesproduits);
         if(Mesproduits.isEmpty()){
         SpanLabel lNom = new SpanLabel("your cart is empty!.");
        lNom.getStyle().setFgColor(0x1e3642);
        Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        lNom.getStyle().setFont(smallPlainSystemFont);
        add(lNom);
         }else{
         System.out.println("cart : "+Mesproduits); 
         
        for (Products single : Mesproduits) {
            add(addItem(single));
        }
         Button CheckBtn = new Button("Confirm");
        //repareBtn.getStyle().setBgColor(0xC89527);
        CheckBtn.addActionListener(e->{
            System.out.println("prixTotale : "+prixTotale);
           if(ProduitService.getInstance().confirm(prixTotale).contains("mail sent")) {
            Dialog.show("ok : ","bill sent successfully \n check your email", "OK", "Cancel");
            new ListProducts(res1).showBack();
           
           }
            
        });
        add(CheckBtn);
        
        }
   
    }
    public Container addItem(Products c) {
 
        
        ImageViewer imgV = null;
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        c1.getStyle().setFgColor(0xbbc2c6);
        c1.setSize(new Dimension(20, 20));
  
     
         String url = "http://localhost/HuntKingdom/web/uploads/"+c.getImage();
     EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth()/2, this.getHeight() / 5, 0xFFFFFFFF), true);
        imgs = URLImage.createToStorage(placeholder,url,url,URLImage.RESIZE_SCALE );
        if(imgs.getHeight() > Display.getInstance().getDisplayHeight() / 5) {
            imgs = imgs.scaledHeight(Display.getInstance().getDisplayHeight() / 5);
        }
        ScaleImageLabel sl = new ScaleImageLabel(imgs);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        sl.addPointerPressedListener((ActionListener)(ActionEvent evt)->{
                Dialog.show("Product : ","Libelle : "+c.getLibProd()+"\ncategorie :"+c.getType()+"\nPrice : "+Double.toString(c.getPrix())+"\nFinal Price : "+Double.toString(c.getPrixFinale()), "OK", "Cancel");
        });
        c1.add(sl);
        
        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label lNom = new Label(c.getLibProd());
        lNom.getStyle().setFgColor(0x1e3642);
        Font smallPlainSystemFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        lNom.getStyle().setFont(smallPlainSystemFont);
        Label prix = new Label(Double.toString(c.getPrixFinale()));
        ///
        TextField qtt = new TextField("");
        qtt.setHint("Quantity");
        qtt.setUIID("TextFieldBlack");
        qtt.addDataChangedListener((i1,i2) -> {
            if (qtt.getText().length() != 0) {
                try {
            if(Integer.parseInt(qtt.getText())<c.getQteProd()&&Integer.parseInt(qtt.getText())>0){        
            prix.setText(Float.toString((float) (Integer.parseInt(qtt.getText())*c.getPrixFinale())));
            quantite = Integer.parseInt(qtt.getText());
            prixTotale+=c.getPrixFinale()*(quantite-1);
            Lastqtt = qtt.getText();
            }else{
            qtt.stopEditing();
            qtt.setText(Lastqtt);
            qtt.startEditingAsync();
            
            }
        } catch (NumberFormatException nfe) {
            qtt.stopEditing();
            qtt.setText(Lastqtt);
            qtt.startEditingAsync();
        }
            }
        });
        
        Button RemoveBtn = new Button("Remove");
        //repareBtn.getStyle().setBgColor(0xC89527);
        Label lid= new Label(Integer.toString(c.getId()));
        lid.setHidden(true);
        RemoveBtn.addActionListener(e->{
            prixTotale-=c.getPrixFinale()*quantite;
            Mesproduits.remove(c);
            ProduitService.getInstance().deleteProduitFromPanier(c.getId());
            new Cart(res, Mesproduits).show();
        });
        prixTotale+=c.getPrixFinale()*1;
        c2.add(lNom);
        c2.add(prix);
        c2.add(qtt);
        c2.add(RemoveBtn);
        c2.add(lid);
        //c1.add(img);
        c1.add(c2);
        refreshTheme();
        
        return c1;
 
    }

     
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
}
