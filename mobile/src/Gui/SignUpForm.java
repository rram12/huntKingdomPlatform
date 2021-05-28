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

import com.codename1.capture.Capture;
import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {
EncodedImage ei;
        ImageViewer imgv = null;
    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        //setUIID("SignIn");
        Image imgs = res.getImage("signUp (2).jpg");
        
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CAMERA_ALT, 4, (ev) -> {
            String path = Capture.capturePhoto();
            if(path == null) {
                showToast("User canceled Camera");
                return;
            }
            setImage(path, imgv);
        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_PHOTO, 4, (ev) -> {
            Display.getInstance().openGallery(e -> {
                if(e == null || e.getSource() == null) {
                    showToast("User canceled Gallery");
                    return;
                }
                String filePath = (String)e.getSource();
                System.out.println(filePath);
                setImage(filePath, imgv);
            }, Display.GALLERY_IMAGE);
        });
        
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        getAllStyles().setBgImage(imgs);
        TextField fName = new TextField("", "First Name", 20, TextField.ANY);
        TextField lName = new TextField("", "Last Name", 20, TextField.ANY);
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        //Image img = new Image();
        
        try {
            ei = EncodedImage.create("/uploadImage.png");
            imgv = new ImageViewer(ei);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        imgv.setPreferredH(500);
        imgv.setPreferredW(500);
        Container c = new Container(BoxLayout.y());
        c.add(new Label("Picture: ", "LogoLabel"));
        c.add(imgv);
        TextField address = new TextField("", "Address", 20, TextField.ANY);
        TextField phone = new TextField("", "Phone Number", 20, TextField.NUMERIC);
        Button btn = new Button("Validate");
        Label lFName = new Label(" ", "RedLabel");
        Label lLName = new Label(" ", "RedLabel");
        Label lUName = new Label(" ", "RedLabel");
        Label lEmail = new Label(" ", "RedLabel");
        Label lPw = new Label(" ", "RedLabel");
        Label lPw2 = new Label(" ", "RedLabel");
        Label lAd = new Label(" ", "RedLabel");
        Label lPhone = new Label(" ", "RedLabel");
//        username.setSingleLineTextArea(false);
//        email.setSingleLineTextArea(false);
//        password.setSingleLineTextArea(false);
//        confirmPassword.setSingleLineTextArea(false);
//        Button next = new Button("Next");
//        Button signIn = new Button("Sign In");
//        signIn.addActionListener(e -> previous.showBack());
//        signIn.setUIID("Link");
//        Label alreadHaveAnAccount = new Label("Already have an account?");
//        
        Container content = BoxLayout.encloseY(
                new Label(" "),
                new Label(" "),
                new Label(" "),
                new FloatingHint(fName),
                lFName,
                createLineSeparator(),
                new FloatingHint(lName),
                lLName,
                createLineSeparator(),
                new FloatingHint(username),
                lUName,
                createLineSeparator(),
                new FloatingHint(email),
                lEmail,
                createLineSeparator(),
                new FloatingHint(password),
                lPw,
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                lPw2,
                createLineSeparator(),
                
                c,
                new FloatingHint(address),
                lAd,
                createLineSeparator(),
                new FloatingHint(phone),
                lPhone,
                createLineSeparator(),
                btn
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
//        add(BorderLayout.SOUTH, BoxLayout.encloseY(
//                next,
//                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
//        ));
//        next.requestFocus();
//        next.addActionListener(e -> new ActivateForm(res).show());
    }
    private void showToast(String text) {
        Image errorImage = FontImage.createMaterial(FontImage.MATERIAL_ERROR, UIManager.getInstance().getComponentStyle("Title"), 4);
        ToastBar.Status status = ToastBar.getInstance().createStatus();
        status.setMessage(text);
        status.setIcon(errorImage);
        status.setExpires(2000);
        status.show();
    }
    
    private void setImage(String filePath, ImageViewer iv) {
            try {
                Image i1 = Image.createImage(filePath);
                iv.setImage(i1);
                iv.getParent().revalidate();
            } catch (Exception ex) {
                Log.e(ex);
                Dialog.show("Error", "Error during image loading: " + ex, "OK", null);
            }
    }
}
