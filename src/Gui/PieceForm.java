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
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class PieceForm extends BaseForm {
private String absolute_path;

    public PieceForm(Resources res) {
        super("AddPiece", BoxLayout.y());
      
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
                                new Label("Add your defective piece"))
                    )
                )
        ));

        TextField nom = new TextField("");
        nom.setUIID("TextFieldBlack");
        addStringValue("Name", nom);
        
            ComboBox<String> combo = new ComboBox<> ("hunting","fishing"
            );
        combo.setWidth(10);
        addStringValue("Category", combo);
    
        TextField description = new TextField("");
        description.setUIID("TextFieldBlack");
        addStringValue("Decription", description);
        Button ChooseBtn = new Button("Choose Image");
        ChooseBtn.addActionListener(e->{
        Display.getInstance().openGallery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (ev != null && ev.getSource() != null) {
                     absolute_path = (String) ev.getSource();
                    int fileNameIndex = absolute_path.lastIndexOf("/") + 1;
                    String fileName = absolute_path.substring(fileNameIndex);
                    System.out.println("absolute_path : "+absolute_path);
                    Image img = null;
                    try {
                        img = Image.createImage(FileSystemStorage.getInstance().openInputStream(absolute_path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Display.GALLERY_IMAGE);
        
        });
        
        
      /* TextField image = new TextField("image");
        image.setUIID("TextFieldBlack");
        addStringValue("Image", image);*/
        
       Button bt = new Button("add");
       
         bt.addActionListener(e->{
             if(validateFields(nom,description)){
            PiecesDefectueuses p = new PiecesDefectueuses(nom.getText(), combo.getSelectedItem(), description.getText(), absolute_path,3);
           if( PieceService.getInstance().addPiece(p)){
               Dialog.show("ok", "Piece added !", "OK", "Cancel");
               System.out.println("Piece added ! ");
           }
           }
              
             
        });
       addStringValue("Image :",ChooseBtn);
        addStringValue("",bt);
        
        
        
    }
    private boolean validateFields(TextField nom,TextField description){
    if(nom.getText().isEmpty()||description.getText().isEmpty()||absolute_path==null){
    Dialog.show("Error", "Please fill all the fields !", "OK", "Cancel");
    return false;
    }
    return true;}
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
    
}
