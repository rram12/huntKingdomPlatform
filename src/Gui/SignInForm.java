/* Copyright (c) 2016, Codename One
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

import Entities.User;
import Services.UserService;
import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import javafx.scene.layout.BackgroundImage;

/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class SignInForm extends BaseForm {
    

    public SignInForm(Resources res) {
        super(new BorderLayout());
        
        if(!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout)getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
       // setUIID("SignIn");
        
        add(BorderLayout.NORTH,BoxLayout.encloseXCenter(new Label(res.getImage("logosite.png"), "LogoLabel")) );
         
        Image imgs = res.getImage("CaptureBG.JPG");
        getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        getAllStyles().setBgImage(imgs);
        
        TextField username = new TextField("", "", 20, TextField.ANY);
        
        TextField password = new TextField("", "", 20, TextField.PASSWORD);
        username.getAllStyles().setFgColor(0xFFFFFF, true);
        password.getAllStyles().setFgColor(0xFFFFFF, true);
        username.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        Label lbUser = new Label("Email");
        Label lbpass = new Label("password");
        lbUser.getAllStyles().setFgColor(0xFFFFFF, true);
        lbpass.getAllStyles().setFgColor(0xFFFFFF, true);
        
        signUp.getAllStyles().setBorder(Border.createEmpty());
        signUp.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        
        
        Label doneHaveAnAccount = new Label("Don't have an account?");
        doneHaveAnAccount.getAllStyles().setFgColor(0xFFFFFF, true);
        Container content = BoxLayout.encloseY(
                lbUser,username,
                createLineSeparator(),
                lbpass,password,
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        
        
        signIn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    if(validateFields(username.getText(),password.getText())){
                   if(UserService.getInstance().getUserConnected(username.getText()).size()>0){
                    User u =UserService.getInstance().getUserConnected(username.getText()).get(0);
                    User.getInstace(u.getId(),u.getUsername(),u.getEmail(),u.getRoles(),u.getAddress(),u.getPhoneNumber());
                     System.out.println("UserSession : "+User.getInstace(0,"","","","",0));
                    new NewsfeedForm(res).show();
                    }else{
                    Dialog.show("Alert", "User doesnt exist ! ", new Command("OK"));
                    }
                }
              }
            });
    }
    public boolean validateFields(String username,String pass){
    if(username.isEmpty()||pass.isEmpty()){
                        Dialog.show("Alert", "please fill all the fields ! ", new Command("OK"));
    return false;}
    return true;}
    
}
