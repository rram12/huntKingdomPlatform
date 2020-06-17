/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author syrine
 */
public class JavaMail {
    public static void sendMail(String recepient,int choice) throws Exception{
        System.out.println("in progress");
        
    Properties properties = new Properties();
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable","true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    String myAccount="mohamedsayed.tourabi@esprit.tn";
    String password="182JMT0297";
    
    Session session=Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
           return new PasswordAuthentication(myAccount, password);
        }      
});
   Message message=prepareMessage(session,myAccount,recepient,choice);
        Transport.send(message);
        System.out.println("sent successfully");
        
    
  
    }
    private static Message prepareMessage(Session session,String myAccount,String recepient,int choice)
    {
        try {
            Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount,"HuntKingDom"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Admin message");
            String htmlCode = "";
            if(choice==1){
             htmlCode = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
"    <!--[if gte mso 9]><xml>\n" +
"        <o:OfficeDocumentSettings>\n" +
"            <o:AllowPNG/>\n" +
"            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"        </o:OfficeDocumentSettings>\n" +
"    </xml><![endif]-->\n" +
"    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
"    <meta name=\"format-detection\" content=\"date=no\">\n" +
"    <meta name=\"format-detection\" content=\"address=no\">\n" +
"    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
"    <title>Email Template</title>\n" +
"\n" +
"\n" +
"    <style type=\"text/css\" media=\"screen\">\n" +
"        /* Linked Styles */\n" +
"        body { padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none }\n" +
"        a { color:#a88123; text-decoration:none }\n" +
"        p { padding:0 !important; margin:0 !important }\n" +
"\n" +
"        /* Mobile styles */\n" +
"    </style>\n" +
"    <style media=\"only screen and (max-device-width: 480px), only screen and (max-width: 480px)\" type=\"text/css\">\n" +
"        @media only screen and (max-device-width: 480px), only screen and (max-width: 480px) {\n" +
"            div[class='mobile-br-5'] { height: 5px !important; }\n" +
"            div[class='mobile-br-10'] { height: 10px !important; }\n" +
"            div[class='mobile-br-15'] { height: 15px !important; }\n" +
"            div[class='mobile-br-20'] { height: 20px !important; }\n" +
"            div[class='mobile-br-25'] { height: 25px !important; }\n" +
"            div[class='mobile-br-30'] { height: 30px !important; }\n" +
"\n" +
"            th[class='m-td'],\n" +
"            td[class='m-td'],\n" +
"            div[class='hide-for-mobile'],\n" +
"            span[class='hide-for-mobile'] { display: none !important; width: 0 !important; height: 0 !important; font-size: 0 !important; line-height: 0 !important; min-height: 0 !important; }\n" +
"\n" +
"            span[class='mobile-block'] { display: block !important; }\n" +
"\n" +
"            div[class='wgmail'] img { min-width: 320px !important; width: 320px !important; }\n" +
"\n" +
"            div[class='img-m-center'] { text-align: center !important; }\n" +
"\n" +
"            div[class='fluid-img'] img,\n" +
"            td[class='fluid-img'] img { width: 100% !important; max-width: 100% !important; height: auto !important; }\n" +
"\n" +
"            table[class='mobile-shell'] { width: 100% !important; min-width: 100% !important; }\n" +
"            td[class='td'] { width: 100% !important; min-width: 100% !important; }\n" +
"\n" +
"            table[class='center'] { margin: 0 auto; }\n" +
"\n" +
"            td[class='column-top'],\n" +
"            th[class='column-top'],\n" +
"            td[class='column'],\n" +
"            th[class='column'] { float: left !important; width: 100% !important; display: block !important; }\n" +
"\n" +
"            td[class='content-spacing'] { width: 15px !important; }\n" +
"\n" +
"            div[class='h2'] { font-size: 44px !important; line-height: 48px !important; }\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body class=\"body\" style=\"padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none\">\n" +
"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#1e1e1e\">\n" +
"    <tbody><tr>\n" +
"        <td align=\"center\" valign=\"top\">\n" +
"            <!-- Top -->\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"            <!-- END Top -->\n" +
"\n" +
"            <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mobile-shell\">\n" +
"                <tbody><tr>\n" +
"                    <td class=\"td\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; width:600px; min-width:600px; Margin:0\" width=\"600\">\n" +
"                        <!-- Header -->\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                        <!-- END Header -->\n" +
"\n" +
"                        <!-- Main -->\n" +
"                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                            <tbody><tr>\n" +
"                                <td>\n" +
"                                    <!-- Head -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/JJxrFRyVRr20CJD3pOx9_top_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/SNcoUN5kSfCDagqSBEZ4_top_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h2\" style=\"color:#ffffff; font-family:Georgia, serif; min-width:auto !important; font-size:60px; line-height:64px; text-align:center\">\n" +
"                                                                <em>Thank you</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <div class=\"h3-2-center\" style=\"color:#1e1e1e; font-family:Arial, sans-serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center; letter-spacing:5px\">FOR YOUR REGISTRATION!</div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Head -->\n" +
"\n" +
"                                    <!-- Body -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">we are delighted to inform you  that you were accepted in HUNTKINGDOM</div>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"20\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"40\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                            </td>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Body -->\n" +
"\n" +
"                                    <!-- Foot -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"30\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">\n" +
"                                                                <em>Follow Us</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <!-- Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td align=\"center\">\n" +
"                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                            <tbody><tr>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.facebook.com/NERDS.H.K/?modal=admin_todo_tour\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/PZeWpIm2TkSqtS6i07xE_ico_facebook.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://twitter.com/hunt_kingdom\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/hAIPhWl2SB2cL0Atc4lB_ico_twitter.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.instagram.com/huntkingdom2020/?hl=fr\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/NrXUpqcRQwKnJKzLkqS1_ico_instagram.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.pinterest.fr/huntkingdom/\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/VaewiS8gT5ClCCR9vAO1_ico_pinterest.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                            </tr>\n" +
"                                                                            </tbody></table>\n" +
"                                                                    </td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <!-- END Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/nK8bYazcQWGAQt8sAH2g_bot_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/v9RanaDRM2FzjQNT9PwV_bot_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Foot -->\n" +
"                                </td>\n" +
"                            </tr>\n" +
"                            </tbody></table>\n" +
"                        <!-- END Main -->\n" +
"\n" +
"                        <!-- Footer -->\n" +
"\n" +
"                        <!-- END Footer -->\n" +
"                    </td>\n" +
"                </tr>\n" +
"                </tbody></table>\n" +
"            <div class=\"wgmail\" style=\"font-size:0pt; line-height:0pt; text-align:center\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/oD2XPM6QQiajFKLdePkw_gmail_fix.gif\" width=\"600\" height=\"1\" style=\"min-width:600px\" alt=\"\" border=\"0\"></div>\n" +
"        </td>\n" +
"    </tr>\n" +
"    </tbody></table>\n" +
"\n" +
"</body></html>";
            }
            else if(choice==2)
            {
                htmlCode ="<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
"    <!--[if gte mso 9]><xml>\n" +
"        <o:OfficeDocumentSettings>\n" +
"            <o:AllowPNG/>\n" +
"            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"        </o:OfficeDocumentSettings>\n" +
"    </xml><![endif]-->\n" +
"    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
"    <meta name=\"format-detection\" content=\"date=no\">\n" +
"    <meta name=\"format-detection\" content=\"address=no\">\n" +
"    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
"    <title>Email Template</title>\n" +
"\n" +
"\n" +
"    <style type=\"text/css\" media=\"screen\">\n" +
"        /* Linked Styles */\n" +
"        body { padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none }\n" +
"        a { color:#a88123; text-decoration:none }\n" +
"        p { padding:0 !important; margin:0 !important }\n" +
"\n" +
"        /* Mobile styles */\n" +
"    </style>\n" +
"    <style media=\"only screen and (max-device-width: 480px), only screen and (max-width: 480px)\" type=\"text/css\">\n" +
"        @media only screen and (max-device-width: 480px), only screen and (max-width: 480px) {\n" +
"            div[class='mobile-br-5'] { height: 5px !important; }\n" +
"            div[class='mobile-br-10'] { height: 10px !important; }\n" +
"            div[class='mobile-br-15'] { height: 15px !important; }\n" +
"            div[class='mobile-br-20'] { height: 20px !important; }\n" +
"            div[class='mobile-br-25'] { height: 25px !important; }\n" +
"            div[class='mobile-br-30'] { height: 30px !important; }\n" +
"\n" +
"            th[class='m-td'],\n" +
"            td[class='m-td'],\n" +
"            div[class='hide-for-mobile'],\n" +
"            span[class='hide-for-mobile'] { display: none !important; width: 0 !important; height: 0 !important; font-size: 0 !important; line-height: 0 !important; min-height: 0 !important; }\n" +
"\n" +
"            span[class='mobile-block'] { display: block !important; }\n" +
"\n" +
"            div[class='wgmail'] img { min-width: 320px !important; width: 320px !important; }\n" +
"\n" +
"            div[class='img-m-center'] { text-align: center !important; }\n" +
"\n" +
"            div[class='fluid-img'] img,\n" +
"            td[class='fluid-img'] img { width: 100% !important; max-width: 100% !important; height: auto !important; }\n" +
"\n" +
"            table[class='mobile-shell'] { width: 100% !important; min-width: 100% !important; }\n" +
"            td[class='td'] { width: 100% !important; min-width: 100% !important; }\n" +
"\n" +
"            table[class='center'] { margin: 0 auto; }\n" +
"\n" +
"            td[class='column-top'],\n" +
"            th[class='column-top'],\n" +
"            td[class='column'],\n" +
"            th[class='column'] { float: left !important; width: 100% !important; display: block !important; }\n" +
"\n" +
"            td[class='content-spacing'] { width: 15px !important; }\n" +
"\n" +
"            div[class='h2'] { font-size: 44px !important; line-height: 48px !important; }\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body class=\"body\" style=\"padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none\">\n" +
"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#1e1e1e\">\n" +
"    <tbody><tr>\n" +
"        <td align=\"center\" valign=\"top\">\n" +
"            <!-- Top -->\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"            <!-- END Top -->\n" +
"\n" +
"            <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mobile-shell\">\n" +
"                <tbody><tr>\n" +
"                    <td class=\"td\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; width:600px; min-width:600px; Margin:0\" width=\"600\">\n" +
"                        <!-- Header -->\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                        <!-- END Header -->\n" +
"\n" +
"                        <!-- Main -->\n" +
"                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                            <tbody><tr>\n" +
"                                <td>\n" +
"                                    <!-- Head -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/JJxrFRyVRr20CJD3pOx9_top_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/SNcoUN5kSfCDagqSBEZ4_top_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h2\" style=\"color:#ffffff; font-family:Georgia, serif; min-width:auto !important; font-size:60px; line-height:64px; text-align:center\">\n" +
"                                                                <em>sorry</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Head -->\n" +
"\n" +
"                                    <!-- Body -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">Thank you very much for your interest in HUNTKINGDOM .We appreciate you taking the time to register but we are sorry to inform you that your inscription has been denied.\n" +
"                                                </div>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"20\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"40\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                            </td>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Body -->\n" +
"\n" +
"                                    <!-- Foot -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"30\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">\n" +
"                                                                <em>Follow Us</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <!-- Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td align=\"center\">\n" +
"                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                            <tbody><tr>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.facebook.com/NERDS.H.K/?modal=admin_todo_tour\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/PZeWpIm2TkSqtS6i07xE_ico_facebook.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://twitter.com/hunt_kingdom\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/hAIPhWl2SB2cL0Atc4lB_ico_twitter.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.instagram.com/huntkingdom2020/?hl=fr\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/NrXUpqcRQwKnJKzLkqS1_ico_instagram.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.pinterest.fr/huntkingdom/\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/VaewiS8gT5ClCCR9vAO1_ico_pinterest.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                            </tr>\n" +
"                                                                            </tbody></table>\n" +
"                                                                    </td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <!-- END Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/nK8bYazcQWGAQt8sAH2g_bot_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/v9RanaDRM2FzjQNT9PwV_bot_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Foot -->\n" +
"                                </td>\n" +
"                            </tr>\n" +
"                            </tbody></table>\n" +
"                        <!-- END Main -->\n" +
"\n" +
"                        <!-- Footer -->\n" +
"\n" +
"                        <!-- END Footer -->\n" +
"                    </td>\n" +
"                </tr>\n" +
"                </tbody></table>\n" +
"            <div class=\"wgmail\" style=\"font-size:0pt; line-height:0pt; text-align:center\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/oD2XPM6QQiajFKLdePkw_gmail_fix.gif\" width=\"600\" height=\"1\" style=\"min-width:600px\" alt=\"\" border=\"0\"></div>\n" +
"        </td>\n" +
"    </tr>\n" +
"    </tbody></table>\n" +
"\n" +
"</body></html>" ;
            }
            else{
            htmlCode = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>\n" +
"    <!--[if gte mso 9]><xml>\n" +
"        <o:OfficeDocumentSettings>\n" +
"            <o:AllowPNG/>\n" +
"            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
"        </o:OfficeDocumentSettings>\n" +
"    </xml><![endif]-->\n" +
"    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">\n" +
"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
"    <meta name=\"format-detection\" content=\"date=no\">\n" +
"    <meta name=\"format-detection\" content=\"address=no\">\n" +
"    <meta name=\"format-detection\" content=\"telephone=no\">\n" +
"    <title>Email Template</title>\n" +
"\n" +
"\n" +
"    <style type=\"text/css\" media=\"screen\">\n" +
"        /* Linked Styles */\n" +
"        body { padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none }\n" +
"        a { color:#a88123; text-decoration:none }\n" +
"        p { padding:0 !important; margin:0 !important }\n" +
"\n" +
"        /* Mobile styles */\n" +
"    </style>\n" +
"    <style media=\"only screen and (max-device-width: 480px), only screen and (max-width: 480px)\" type=\"text/css\">\n" +
"        @media only screen and (max-device-width: 480px), only screen and (max-width: 480px) {\n" +
"            div[class='mobile-br-5'] { height: 5px !important; }\n" +
"            div[class='mobile-br-10'] { height: 10px !important; }\n" +
"            div[class='mobile-br-15'] { height: 15px !important; }\n" +
"            div[class='mobile-br-20'] { height: 20px !important; }\n" +
"            div[class='mobile-br-25'] { height: 25px !important; }\n" +
"            div[class='mobile-br-30'] { height: 30px !important; }\n" +
"\n" +
"            th[class='m-td'],\n" +
"            td[class='m-td'],\n" +
"            div[class='hide-for-mobile'],\n" +
"            span[class='hide-for-mobile'] { display: none !important; width: 0 !important; height: 0 !important; font-size: 0 !important; line-height: 0 !important; min-height: 0 !important; }\n" +
"\n" +
"            span[class='mobile-block'] { display: block !important; }\n" +
"\n" +
"            div[class='wgmail'] img { min-width: 320px !important; width: 320px !important; }\n" +
"\n" +
"            div[class='img-m-center'] { text-align: center !important; }\n" +
"\n" +
"            div[class='fluid-img'] img,\n" +
"            td[class='fluid-img'] img { width: 100% !important; max-width: 100% !important; height: auto !important; }\n" +
"\n" +
"            table[class='mobile-shell'] { width: 100% !important; min-width: 100% !important; }\n" +
"            td[class='td'] { width: 100% !important; min-width: 100% !important; }\n" +
"\n" +
"            table[class='center'] { margin: 0 auto; }\n" +
"\n" +
"            td[class='column-top'],\n" +
"            th[class='column-top'],\n" +
"            td[class='column'],\n" +
"            th[class='column'] { float: left !important; width: 100% !important; display: block !important; }\n" +
"\n" +
"            td[class='content-spacing'] { width: 15px !important; }\n" +
"\n" +
"            div[class='h2'] { font-size: 44px !important; line-height: 48px !important; }\n" +
"        }\n" +
"    </style>\n" +
"</head>\n" +
"<body class=\"body\" style=\"padding:0 !important; margin:0 !important; display:block !important; background:#1e1e1e; -webkit-text-size-adjust:none\">\n" +
"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#1e1e1e\">\n" +
"    <tbody><tr>\n" +
"        <td align=\"center\" valign=\"top\">\n" +
"\n" +
"\n" +
"            <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"mobile-shell\">\n" +
"                <tbody><tr>\n" +
"                    <td class=\"td\" style=\"font-size:0pt; line-height:0pt; padding:0; margin:0; font-weight:normal; width:600px; min-width:600px; Margin:0\" width=\"600\">\n" +
"                        <!-- Header -->\n" +
"\n" +
"\n" +
"                        <!-- END Header -->\n" +
"\n" +
"                        <!-- Main -->\n" +
"                        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                            <tbody><tr>\n" +
"                                <td>\n" +
"                                    <!-- Head -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/JJxrFRyVRr20CJD3pOx9_top_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/SNcoUN5kSfCDagqSBEZ4_top_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h2\" style=\"color:#ffffff; font-family:Georgia, serif; min-width:auto !important; font-size:60px; line-height:64px; text-align:center\">\n" +
"                                                                <em>Thank you</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"10\"></td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Head -->\n" +
"\n" +
"                                    <!-- Body -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"35\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">Thank you for your final payment on your HuntKingdom application. We appreciate the prompt manner in which you have made these payments.We hope that you are satisfied by our services.\n" +
"                                                </div>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"20\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"40\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                                &nbsp;\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"                                            </td>\n" +
"                                            <td class=\"content-spacing\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"20\"></td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Body -->\n" +
"\n" +
"                                    <!-- Foot -->\n" +
"                                    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#d2973b\">\n" +
"                                        <tbody><tr>\n" +
"                                            <td>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"30\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <div class=\"h3-1-center\" style=\"color:#1e1e1e; font-family:Georgia, serif; min-width:auto !important; font-size:20px; line-height:26px; text-align:center\">\n" +
"                                                                <em>Follow Us</em>\n" +
"                                                            </div>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"\n" +
"                                                            <!-- Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td align=\"center\">\n" +
"                                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                            <tbody><tr>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.facebook.com/NERDS.H.K/?modal=admin_todo_tour\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/PZeWpIm2TkSqtS6i07xE_ico_facebook.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://twitter.com/hunt_kingdom\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/hAIPhWl2SB2cL0Atc4lB_ico_twitter.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.instagram.com/huntkingdom2020/?hl=fr\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/NrXUpqcRQwKnJKzLkqS1_ico_instagram.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                                <td class=\"img-center\" style=\"font-size:0pt; line-height:0pt; text-align:center\" width=\"38\"><a href=\"https://www.pinterest.fr/huntkingdom/\" target=\"_blank\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/VaewiS8gT5ClCCR9vAO1_ico_pinterest.jpg\" border=\"0\" width=\"28\" height=\"28\" alt=\"\"></a></td>\n" +
"                                                                            </tr>\n" +
"                                                                            </tbody></table>\n" +
"                                                                    </td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                            <!-- END Socials -->\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"15\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"3\" bgcolor=\"#e6ae57\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                    <tbody><tr>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/nK8bYazcQWGAQt8sAH2g_bot_left.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                        <td>\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\"><tbody><tr><td height=\"24\" class=\"spacer\" style=\"font-size:0pt; line-height:0pt; text-align:center; width:100%; min-width:100%\">&nbsp;</td></tr></tbody></table>\n" +
"\n" +
"                                                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                                                                <tbody><tr>\n" +
"                                                                    <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" height=\"3\" bgcolor=\"#e6ae57\">&nbsp;</td>\n" +
"                                                                </tr>\n" +
"                                                                </tbody></table>\n" +
"                                                        </td>\n" +
"                                                        <td class=\"img\" style=\"font-size:0pt; line-height:0pt; text-align:left\" width=\"27\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/v9RanaDRM2FzjQNT9PwV_bot_right.jpg\" border=\"0\" width=\"27\" height=\"27\" alt=\"\"></td>\n" +
"                                                    </tr>\n" +
"                                                    </tbody></table>\n" +
"                                            </td>\n" +
"                                        </tr>\n" +
"                                        </tbody></table>\n" +
"                                    <!-- END Foot -->\n" +
"                                </td>\n" +
"                            </tr>\n" +
"                            </tbody></table>\n" +
"                        <!-- END Main -->\n" +
"\n" +
"                        <!-- Footer -->\n" +
"\n" +
"                        <!-- END Footer -->\n" +
"                    </td>\n" +
"                </tr>\n" +
"                </tbody></table>\n" +
"            <div class=\"wgmail\" style=\"font-size:0pt; line-height:0pt; text-align:center\"><img src=\"https://d1pgqke3goo8l6.cloudfront.net/oD2XPM6QQiajFKLdePkw_gmail_fix.gif\" width=\"600\" height=\"1\" style=\"min-width:600px\" alt=\"\" border=\"0\"></div>\n" +
"        </td>\n" +
"    </tr>\n" +
"    </tbody></table>\n" +
"\n" +
"</body></html>";
            
            }
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
        }
          return null;
    }
    
}
