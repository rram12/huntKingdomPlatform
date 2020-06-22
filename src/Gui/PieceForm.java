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
import Entities.User;
import Services.PieceService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class PieceForm extends BaseForm {
private String absolute_path;
 String GlobalPath = "";
    String GlobalExtension = "";
    public PieceForm(Resources res) {
       super("", BoxLayout.y());
      
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        
        
       
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
        setTitle("AddPiece");
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
       /* Label limport = new Label("no file selected");
        Button upload = new Button("Choose Image..");
        
        upload.addPointerPressedListener((ei)->{
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", e2-> {
                    String file = (String)e2.getSource();
                    if (file == null) {
                        System.out.println("No file was selected");
                    } else {
                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            extension = file.substring(file.lastIndexOf(".")+1);
                        }
                        if ("txt".equals(extension)) {
                            FileSystemStorage fs = FileSystemStorage.getInstance();
                            try {
                                InputStream fis = fs.openInputStream(file);
                                System.out.println(Util.readToString(fis));
                            } catch (Exception ex) {
                                Log.e(ex);
                            }
                        } else {
                            //moveFile(file,)
                            String path = file.substring(7);
                            System.out.println("Selected file :"+file.substring(40)+"\n"+"path :"+path);
                            limport.setText("file imported");
                            limport.getAllStyles().setFgColor(0x69E781);
                            
                            GlobalPath=path;
                            GlobalExtension=file.substring(file.lastIndexOf(".")+1);
                        }
                    }
                });
            }
        });
        */
        
        Button ChooseBtn = new Button("Choose Image");
        ChooseBtn.addActionListener(e->{
        Display.getInstance().openGallery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (ev != null && ev.getSource() != null) {
                     absolute_path = (String) ev.getSource();
                    int fileNameIndex = absolute_path.lastIndexOf("/") + 1;
                    String fileName = absolute_path.substring(fileNameIndex);
                    System.out.println("fileName : "+fileName);
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
        
        
     
        
       Button bt = new Button("add");
       
         bt.addActionListener(e->{
             if(validateFields(nom,description)){
           /*  int subname = 18;
            Random rand = new Random();
            int upperbound = 7483647;
            int int_random = rand.nextInt(upperbound);
            String Fullname = "MobileGenerated_"+subname+"_"+int_random+"."+GlobalExtension;
            System.out.println(Fullname);*/
          // boolean moving = moveFile(GlobalPath,"C:\\wamp\\www\\HuntKingdom\\web\\uploads\\photos\\"+Fullname);
          //  System.out.println("moved? :"+moving);
          String logoPath = "32ee29789ebac5d68c11228b3e6e6889.png";
            PiecesDefectueuses p = new PiecesDefectueuses(nom.getText(), combo.getSelectedItem(), description.getText(), logoPath,User.getInstace(0,"","","","",0).getId());
          if(!PieceService.getInstance().exists(p)){
            if( PieceService.getInstance().addPiece(p)){
               Dialog.show("OK", "Piece added !", new Command("OK"));
           }
            }else{
              Dialog.show("Error", "Piece exists !", new Command("OK"));
                }
           }
        });
     //  addStringValue("Image :",ChooseBtn);
       // addStringValue("",limport);
       // addStringValue("",upload);
        addStringValue("",ChooseBtn);
        addStringValue("",bt);
        
        
        
    }
    private boolean validateFields(TextField nom,TextField description){
    if(nom.getText().isEmpty()||description.getText().isEmpty()){
    Dialog.show("Error", "Please fill all the fields !",new Command("OK"));
    return false;
    }
    return true;}
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
   /*   public boolean moveFile(String sourcePath, String targetPath) {
        //File fileToMove = new File(sourcePath);
        File fileToMove=new File(sourcePath);
        boolean ftm=fileToMove.renameTo(new File(targetPath));
         System.out.println("source: "+sourcePath+" Target: "+targetPath);
        return ftm;
    }*/
    
}
