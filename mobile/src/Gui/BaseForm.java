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

import Entities.User;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {

        Toolbar tb = getToolbar();

        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                FlowLayout.encloseCenterBottom(
                        new Label(res.getImage("logosite.png"), "PictureWhiteBackgrond"))
        ));

        tb.addMaterialCommandToSideMenu("Reclamation", FontImage.MATERIAL_WATCH, e -> new ReclamationForm(res).show());
        tb.addMaterialCommandToSideMenu("Services", FontImage.MATERIAL_APARTMENT, e -> new ServicesForm(res).show());

        User u = User.getInstace(0, "", "", "", "", 0);
        if (User.getInstace(0, "", "", "", "", 0).getRoles().contains("ROLE_CLIENT")) {
            tb.addMaterialCommandToSideMenu("Competitions", FontImage.MATERIAL_ADD, e -> new ConsultandParticipate(res).show());

            tb.addMaterialCommandToSideMenu("Add Training", FontImage.MATERIAL_ADD, e -> new AddTrainingForm(res).show());
            tb.addMaterialCommandToSideMenu("Training", FontImage.MATERIAL_BOOK, e -> new ListTrainingForm(res).show());
            tb.addMaterialCommandToSideMenu("ListAnimal", FontImage.MATERIAL_ALBUM, e -> new ListAnimalClient(res).show());
        } else if (User.getInstace(0, "", "", "", "", 0).getRoles().contains("ROLE_TRAINER")) {
            tb.addMaterialCommandToSideMenu("Publicities", FontImage.MATERIAL_ALBUM, e -> new AffichagePublicity(res).show());
            tb.addMaterialCommandToSideMenu("Competitions", FontImage.MATERIAL_ALBUM, e -> new AffichageCompetition(res).show());

            tb.addMaterialCommandToSideMenu("ListAnimalTrainer", FontImage.MATERIAL_ALBUM, e -> new ListAnimalForm(res).show());
            tb.addMaterialCommandToSideMenu("ListTrainer", FontImage.MATERIAL_BOOK, e -> new ListTrainer(res).show());
        }

        if (User.getInstace(0, "", "", "", "", 0).getRoles().contains("ROLE_REPAIRER")) {
            tb.addMaterialCommandToSideMenu("Means Of Transport", FontImage.MATERIAL_ALBUM, e -> new ListTransportForm(res).show());
            tb.addMaterialCommandToSideMenu("Competitions", FontImage.MATERIAL_ADD, e -> new ConsultandParticipate(res).show());
        }
        tb.addMaterialCommandToSideMenu("List defective Parts", FontImage.MATERIAL_ALBUM, e -> new ListDefective(res).show());

        

        tb.addMaterialCommandToSideMenu("List your pieces", FontImage.MATERIAL_ALBUM, e -> new ListYourPieces(res).show());
        tb.addMaterialCommandToSideMenu("Add Piece", FontImage.MATERIAL_ADD, e -> new PieceForm(res).show());
        tb.addMaterialCommandToSideMenu("List All Products", FontImage.MATERIAL_ALBUM, e -> new ListProducts(res).show());
        tb.addMaterialCommandToSideMenu("List Product in Promotion", FontImage.MATERIAL_ALBUM, e -> new ListProductInPromotion(res).show());

        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            System.out.println(User.getInstace(0, "", "", "", "", 0));
            User.getInstace(0, "", "", "", "", 0).cleanUserSession();
            new SignInForm(res).show();
        });

    }
}
