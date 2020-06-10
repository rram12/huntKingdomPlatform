/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Animal;
import Services.AnimalService;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ext.filechooser.FileChooser;


import com.codename1.io.FileSystemStorage;

import com.codename1.io.Log;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
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
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.codename1.util.regex.RE;



import java.io.InputStream;
import java.util.Random;

/**
 *
 * @author tibh
 */
public class AddAnimalForm extends BaseForm{
    String path = null;
    String GlobalPath = "";
    String GlobalExtension = "";
    ComboBox<String>categorie = new ComboBox<> ("Hunting","Fishing");
    ComboBox<String>Debut = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
    ComboBox<String>Fin = new ComboBox<> ("January","February","March","April","May","June","July","August","September","October","November","December");
    TextField nom = new TextField();
    TextField description = new TextField();
    MultiButton photo = new MultiButton("");
    

     public AddAnimalForm(Resources res,int length) {
         
        super("Ajout Animal", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);

        this.setScrollableY(true);
        
        Image img = res.getImage("bg-2.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
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
        
        
        
       // Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label l1 = new Label("Categorie :");
        Label l2 = new Label("Nom :");
        Label l3 = new Label("Description :");
        Label l4 = new Label("Debut Saison : ");
        Label l5 = new Label("Fin Saison : ");
        Label l6 = new Label("image : ");
        Label limport = new Label("no file selected");
       Button upload = new Button("");
       Container photo1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
       photo1.addAll(limport,upload);
       
       
       
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
       // content.setScrollableY(true);

        Button submit = new Button("Ajouter");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
      
         
        add(l1);add(categorie);
        add(l2);add(nom);
        add(l3);add(description);
       add(l4);add(Debut);
        add(l5);add(Fin);
        add(photo1);
        String nomA="";
       
      
                      submit.addActionListener((e)-> {
            int subname = length+1;
            Random rand = new Random();
            int upperbound = 7483647;
            int int_random = rand.nextInt(upperbound);
            String Fullname = "MobileGenerated_"+subname+"_"+int_random+"."+GlobalExtension;
            System.out.println(Fullname);
            //boolean moving = moveFile(GlobalPath,"C:/wamp64/www/HuntKingdom/web/uploads/photos/"+Fullname);
            //System.out.println("moved? :"+moving);
           
                
                   if ((nom.getText().length() == 0) || (description.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } 
              else {
                       int DS = 0;
                       int FS=0;
                       switch (Debut.getSelectedItem()) {
  case "January":
    DS=1;
    break;
  case "February":
    DS=2;
    break;
  case "March":
    DS=3;
    break;
  case "April":
   DS=4;
    break;
  case "May":
    DS=5;
    break;
  case "June":
   DS=6;
    break;
  case "July":
    DS=7;
    break;
    case "August":
    DS=8;
    break;
    case "September":
    DS=9;
    break;
    case "October":
    DS=10;
    break;
    case "November":
    DS=11;
    break;
    case "December":
    DS=12;
    break;
}
                       switch (Fin.getSelectedItem()) {
  case "January":
    FS=1;
    break;
  case "February":
    FS=2;
    break;
  case "March":
    FS=3;
    break;
  case "April":
   FS=4;
    break;
  case "May":
    FS=5;
    break;
  case "June":
   FS=6;
    break;
  case "July":
    FS=7;
    break;
    case "August":
    FS=8;
    break;
    case "September":
    FS=9;
    break;
    case "October":
    FS=10;
    break;
    case "November":
    FS=11;
    break;
    case "December":
    FS=12;
    break;
}
                 Animal a = new Animal(DS,FS,categorie.getSelectedItem(),nom.getText(),description.getText(),Fullname);
                 
                 Animal AnimalAjoutee = AnimalService.getInstance().add(a);
                  Dialog.show("ok", "Animal added !", "OK", "Cancel");
                 new ListAnimalForm(res).show();
             }
            

                

            });
             
       

       // this.add(CENTER, content);
       
        add(submit);
        
        
        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
          val.addConstraint(nom,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field"))).
       
        addConstraint(description,
                new GroupConstraint(
                        new LengthConstraint(5, "Minimum 5 caracters"),
                        new RegexConstraint("^([a-zA-Z ÉéèÈêÊôÔ']*)$", "Alphabetic Field")));
   
}
//     public boolean moveFile(String sourcePath, String targetPath) {
//        //File fileToMove = new File(sourcePath);
//        File fileToMove=new File(sourcePath);
//        boolean ftm=fileToMove.renameTo(new File(targetPath));
//         System.out.println("source: "+sourcePath+" Target: "+targetPath);
//        return ftm;
//        
//    }
      
}

