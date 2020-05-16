/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Hebergement;
import Entities.Location;
import Entities.MoyenDeTransport;
import Services.LocationService;
import Entities.Reservation;
import Services.HebergementService;
import Services.ReservationService;
import Services.MoyenDeTransportService;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS1
 */
public class ServicesForm extends BaseForm {

//    EncodedImage enc;
    public ServicesForm(Resources res) {
        super("Services", BoxLayout.y());
        List<Reservation> rs = ReservationService.getInstance().getMyReservations(3);
        List<Location> ls = LocationService.getInstance().getMyLocations(3);
        List<MoyenDeTransport> lM = MoyenDeTransportService.getInstance().getAllTransports();
        List<Hebergement> lh = HebergementService.getInstance().getAllAccommodations();
        Calendar now = Calendar.getInstance();
        Calendar finald = Calendar.getInstance();
        now.setTime(new Date());
        MoyenDeTransport mt = lM.get(lM.size() - 1);
        Hebergement heb = lh.get(lh.size() - 1);
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Services");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, heb, spacer1);
        addTab1(swipe, mt, spacer2);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton accommodations = RadioButton.createToggle("Accommodation", barGroup);
        accommodations.setUIID("SelectBar");
        RadioButton Transports = RadioButton.createToggle("Transportation", barGroup);
        Transports.setUIID("SelectBar");
        RadioButton my = RadioButton.createToggle("My Reservations", barGroup);
        my.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, accommodations, Transports, my),
                FlowLayout.encloseBottom(arrow)
        ));

        accommodations.setSelected(true);
        arrow.setVisible(false);
        Container List = new Container();
        if (lh != null) {
            for (Hebergement h : lh) {
                addButton(res, List, h);
            }
        } else {
            System.out.println("No Accommodations available\nPlease Try later !! ");

        }

        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(accommodations, arrow);

        });
        accommodations.addActionListener(e -> {
            if (accommodations.isSelected()) {
                updateArrowPosition(accommodations, arrow);
                List.removeAll();

                if (lh != null) {
                    for (Hebergement h : lh) {
                        addButton(res, List, h);
                        System.out.println(h);
                    }
                } else {
                    System.out.println("No Accommodations available\nPlease Try later !! ");

                }
                refreshTheme();
            }
        });
        Transports.addActionListener(e -> {
            if (Transports.isSelected()) {
                updateArrowPosition(Transports, arrow);
                List.removeAll();
                if (lM != null) {
                    for (MoyenDeTransport m : lM) {
                        addButton1(res, List, m);
                        System.out.println("zid t");
                    }
                } else {
                    System.out.println("No Means of Transport available\nPlease Try later !! ");

                }
                refreshTheme();
            }
        });
        my.addActionListener(e -> {
            if (my.isSelected()) {
                updateArrowPosition(my, arrow);
                List.removeAll();
                Container NewReserv = new Container();
                Container OldReserv = new Container();
                Container NewLocat = new Container();
                Container OldLocat = new Container();
                List.add(BoxLayout.encloseY(new Label("\n                                          !!Recently Made!!\n")));
                if (rs != null || ls != null) {
                    if (rs != null) {
                        for (int j = 0; j < rs.size(); j++) {
                            for (int i = 0; i < lh.size(); i++) {
                                if (lh.get(i).getId() == rs.get(j).getHebergementId()) {
                                    
                                    finald.setTime(rs.get(j).finaldate());
                                    if (now.before(finald)) {
                                        addButton2(res, List, rs.get(j), lh.get(i));
                                    }
                                }
                            }

                        }
                    }
                    

                    if (ls != null) {
                        for (int j = 0; j < ls.size(); j++) {
                            for (int i = 0; i < lM.size(); i++) {
                                if (lM.get(i).getId() == ls.get(j).getMoyenDeTransportId()) {
                                    finald.setTime(ls.get(j).finaldate());
                                    if (now.before(finald)) {
                                        addButton3(res, List, ls.get(j), lM.get(i));
                                    }
                                }
                            }
                        }
                    }
                                    List.add(BoxLayout.encloseY(new Label("\n                                          !!Once Made!!\n")));
                    if (rs != null) {
                        for (int j = 0; j < rs.size(); j++) {
                            for (int i = 0; i < lh.size(); i++) {
                                if (lh.get(i).getId() == rs.get(j).getHebergementId()) {
                                    
                                    finald.setTime(rs.get(j).finaldate());
                                    if (now.after(finald)||now.equals(finald)) {
                                        addButton2(res, List, rs.get(j), lh.get(i));
                                    }
                                }
                            }

                        }
                    }
                    if (ls != null) {
                        for (int j = 0; j < ls.size(); j++) {
                            for (int i = 0; i < lM.size(); i++) {
                                if (lM.get(i).getId() == ls.get(j).getMoyenDeTransportId()) {
                                    finald.setTime(ls.get(j).finaldate());
                                    if (now.after(finald)||now.equals(finald)) {
                                        addButton3(res, List, ls.get(j), lM.get(i));
                                    }
                                }
                            }
                        }
                    }

                } else {
                    List.add("\n\n\tNo Reservations made\n\tPlease feel free to check our \nAccommodations and Means of transport  !! ");
                    System.out.println("No Means of Transport available\nPlease Try later !! ");

                }
//                List.add(BoxLayout.encloseY(new Label("\t\t!!Newest!!")));
//                List.add(BoxLayout.encloseY(NewReserv));
//                List.add(BoxLayout.encloseY(NewLocat));
//                List.add(BoxLayout.encloseY(new Label("\t\t!!Recent!!")));
//                List.add(BoxLayout.encloseY(OldReserv));
//                List.add(BoxLayout.encloseY(OldLocat));
                refreshTheme();
            }
        });
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        add(LayeredLayout.encloseIn(List));
    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
//        System.out.println(arrow.getParent());
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Hebergement h, Label spacer) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        String url = "http://localhost/HuntKingdom/web/uploads/" + h.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 3, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
//        if (img.getHeight() < size) {
//            System.out.println("height asgher");
//            img = img.scaledHeight(size);
//        }
        Label likes = new Label(h.getNbChambre() + " Rooms ");
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_NEW_RELEASES, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(h.getCapacite() + " People Max");
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
//        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            System.out.println("akber ");
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(h.getAdresse(), "LargeWhiteText"),
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addTab1(Tabs swipe, MoyenDeTransport mdt, Label spacer) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        String url = "http://localhost/HuntKingdom/web/uploads/" + mdt.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 3, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
//        if (img.getHeight() < size) {
//            img = img.scaledHeight(size);
//        }
        Label likes = new Label(mdt.getType() + " ");
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_RECOMMEND, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(mdt.getCategorie());
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
//        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
//        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(mdt.getMarque(), "LargeWhiteText"),
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Resources res, Container List, Hebergement h) {
        String url = "http://localhost/HuntKingdom/web/uploads/" + h.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextField ta = new TextField(h.getAdresse());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" Rooms: " + h.getNbChambre());
        Label comments = new Label(" Capacity: " + h.getCapacite());
        Label price = new Label(" Price/Day: " + h.getPrixParJour() + "Dt");
//        price.setTextPosition(LEFT);
//        FontImage.setMaterialIcon(price, FontImage.MATERIAL_MONEY);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments), price
                ));
        List.add(cnt);
        image.addActionListener(e -> new ReservationForm(res, h, img).show());
    }

    private void addButton1(Resources res, Container List, MoyenDeTransport mdt) {
        System.out.println(mdt.getImage());
        String url = "http://localhost/HuntKingdom/web/uploads/" + mdt.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextField ta = new TextField(mdt.getMarque());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" Category: " + mdt.getCategorie());
        Label comments = new Label(" Type: " + mdt.getType());
        Label price = new Label(" Price/Day: " + mdt.getPrixParJour() + "Dt");
//        price.setTextPosition(LEFT);
//        FontImage.setMaterialIcon(price, FontImage.MATERIAL_MONEY);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments), price
                ));
        List.add(cnt);
        image.addActionListener(e -> new LocationForm(res, mdt, img).show());
    }

    private void addButton2(Resources res, Container a, Reservation r, Hebergement h) {
        String url = "http://localhost/HuntKingdom/web/uploads/" + h.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextField ta = new TextField(h.getAdresse());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" From : " + r.Arrivaldate());
        Label comments = new Label(" To: " + r.finaldate());
        Label price = new Label(" For: " + r.getPrixTot() + "Dt");
//        price.setTextPosition(LEFT);
//        FontImage.setMaterialIcon(price, FontImage.MATERIAL_MONEY);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,likes, comments, price
                ));
        a.add(cnt);
        image.addActionListener(e -> new MaReservationForm(res, h, img,r).show());
    }

    private void addButton3(Resources res, Container a, Location l, MoyenDeTransport mdt) {
        System.out.println(mdt.getImage());
        String url = "http://localhost/HuntKingdom/web/uploads/" + mdt.getImage();
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth() / 2, this.getHeight() / 5, 0xFFFFFFFF), true);
        Image img = URLImage.createToStorage(placeholder, url, url, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextField ta = new TextField(mdt.getMarque());
        ta.setUIID("NewsTitle");
        ta.setEditable(false);

        Label likes = new Label(" From : " + l.Arrivaldate());
        Label comments = new Label(" To: " + l.finaldate());
        Label price = new Label(" For: " + l.getPrixTot() + "Dt");
//        price.setTextPosition(LEFT);
//        FontImage.setMaterialIcon(price, FontImage.MATERIAL_MONEY);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,likes, comments, price
                ));
        a.add(cnt);
        image.addActionListener(e -> new MaLocationForm(res, mdt, img,l).show());
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
