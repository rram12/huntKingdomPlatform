/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Ramzi
 */
public class EmailBody {
    
    

    public EmailBody() {
    }
    
    
    public String createEmailBody(String text1,String text2,String text3,String text4){
        return "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>\n" +
"<html style='width:100%;font-family:arial, helvetica neue, helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;'>\n" +
" <head> \n" +
"  <meta charset='UTF-8'> \n" +
"  <meta content='width=device-width, initial-scale=1' name='viewport'> \n" +
"  <meta name='x-apple-disable-message-reformatting'> \n" +
"  <meta http-equiv='X-UA-Compatible' content='IE=edge'> \n" +
"  <meta content='telephone=no' name='format-detection'> \n" +
"  <title>New email</title> \n" +
"  <!--[if (mso 16)]>\n" +
"    <style type='text/css'>\n" +
"    a {text-decoration: none;}\n" +
"    </style>\n" +
"    <![endif]--> \n" +
"  <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> \n" +
"  <style type='text/css'>\n" +
"@media only screen and (max-width:600px) {p, ul li, ol li, a { font-size:16px!important; line-height:150%!important } h1 { font-size:30px!important; text-align:left; line-height:120% } h2 { font-size:26px!important; text-align:left; line-height:120% } h3 { font-size:20px!important; text-align:left; line-height:120% } h1 a { font-size:30px!important; text-align:left } h2 a { font-size:26px!important; text-align:left } h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:16px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:16px!important } td .es-infoblock p, td .es-infoblock ul li, td .es-infoblock ol li, td .es-infoblock a { font-size:12px } *[class='gmail-fix'] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button { font-size:20px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } .es-desk-menu-hidden { display:table-cell!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }\n" +
"#outlook a {\n" +
"	padding:0;\n" +
"}\n" +
".ExternalClass {\n" +
"	width:100%;\n" +
"}\n" +
".ExternalClass,\n" +
".ExternalClass p,\n" +
".ExternalClass span,\n" +
".ExternalClass font,\n" +
".ExternalClass td,\n" +
".ExternalClass div {\n" +
"	line-height:100%;\n" +
"}\n" +
".es-button {\n" +
"	mso-style-priority:100!important;\n" +
"	text-decoration:none!important;\n" +
"}\n" +
"a[x-apple-data-detectors] {\n" +
"	color:inherit!important;\n" +
"	text-decoration:none!important;\n" +
"	font-size:inherit!important;\n" +
"	font-family:inherit!important;\n" +
"	font-weight:inherit!important;\n" +
"	line-height:inherit!important;\n" +
"}\n" +
".es-desk-hidden {\n" +
"	display:none;\n" +
"	float:left;\n" +
"	overflow:hidden;\n" +
"	width:0;\n" +
"	max-height:0;\n" +
"	line-height:0;\n" +
"	mso-hide:all;\n" +
"}\n" +
"</style> \n" +
" </head> \n" +
" <body style='width:100%;font-family:arial, helvetica neue, helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0;'> \n" +
"  <div class='es-wrapper-color' style='background-color:#F0F4F2;'> \n" +
"   <!--[if gte mso 9]>\n" +
"			<v:background xmlns:v='urn:schemas-microsoft-com:vml' fill='t'>\n" +
"				<v:fill type='tile' color='#f0f4f2'></v:fill>\n" +
"			</v:background>\n" +
"		<![endif]--> \n" +
"   <table class='es-wrapper' width='100%' cellspacing='0' cellpadding='0' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;'> \n" +
"     <tr style='border-collapse:collapse;'> \n" +
"      <td valign='top' style='padding:0;Margin:0;'> \n" +
"       <table cellpadding='0' cellspacing='0' class='es-header' align='center' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;'> \n" +
"         <tr style='border-collapse:collapse;'> \n" +
"          <td align='center' style='padding:0;Margin:0;'> \n" +
"           <table class='es-header-body' width='600' cellspacing='0' cellpadding='0' bgcolor='#ffffff' align='center' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;'> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' bgcolor='#c44d39' style='Margin:0;padding-top:10px;padding-bottom:10px;padding-left:20px;padding-right:20px;background-color:#C44D39;'> \n" +
"               <!--[if mso]><table dir='rtl' width='560' cellpadding='0' cellspacing='0'><tr><td dir='ltr' width='270' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' class='es-right' align='right' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='270' align='left' class='es-m-p20b' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;display:none;'></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td><td dir='ltr' width='20'></td><td dir='ltr' width='270' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' align='left' class='es-left' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='270' align='left' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;display:none;'></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td></tr></table><![endif]--></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='padding:0;Margin:0;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='600' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0;'> \n" +
"                       <table border='0' width='100%' height='100%' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td style='padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;'> \n" +
"               <!--[if mso]><table width='560' cellpadding='0' cellspacing='0'><tr><td width='120' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' align='left' class='es-left' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='120' class='es-m-p20b' align='center' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0px;'><img class='adapt-img' src='https://iihrky.stripocdn.email/content/guids/CABINET_cbf6e95853cd5ebe41cc296200147222/images/57151590523435738.png' alt style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;' width='120'></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td><td width='20'></td><td width='420' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' class='es-right' align='right' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='420' align='left' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td style='padding:0;Margin:0;'> \n" +
"                       <table cellpadding='0' cellspacing='0' width='100%' class='es-menu' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr class='links' style='border-collapse:collapse;'> \n" +
"                          <td align='center' valign='top' width='33.33%' style='Margin:0;padding-left:5px;padding-right:5px;padding-top:20px;padding-bottom:0px;border:0;'><a target='_blank' href='https://#' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:trebuchet ms, lucida grande, lucida sans unicode, lucida sans, tahoma, sans-serif;font-size:16px;text-decoration:none;display:block;color:#6AA38B;font-weight:bold;'>ACCOMODATIONS</a></td> \n" +
"                          <td align='center' valign='top' width='33.33%' style='Margin:0;padding-left:5px;padding-right:5px;padding-top:20px;padding-bottom:0px;border:0;'><a target='_blank' href='https://viewstripo.email/' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:trebuchet ms, lucida grande, lucida sans unicode, lucida sans, tahoma, sans-serif;font-size:16px;text-decoration:none;display:block;color:#6AA38B;font-weight:bold;'>SHOP</a></td> \n" +
"                          <td align='center' valign='top' width='33.33%' style='Margin:0;padding-left:5px;padding-right:5px;padding-top:20px;padding-bottom:0px;border:0;'><a target='_blank' href='https://viewstripo.email/' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:trebuchet ms, lucida grande, lucida sans unicode, lucida sans, tahoma, sans-serif;font-size:16px;text-decoration:none;display:block;color:#6AA38B;font-weight:bold;'>CONTACT</a></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td></tr></table><![endif]--></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='padding:0;Margin:0;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='600' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0;'> \n" +
"                       <table border='0' width='100%' height='100%' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td style='padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"           </table></td> \n" +
"         </tr> \n" +
"       </table> \n" +
"       <table cellpadding='0' cellspacing='0' class='es-content' align='center' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;'> \n" +
"         <tr style='border-collapse:collapse;'> \n" +
"          <td align='center' style='padding:0;Margin:0;'> \n" +
"           <table class='es-content-body' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;' width='600' cellspacing='0' cellpadding='0' bgcolor='#ffffff' align='center'> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px;'> \n" +
"               <table width='100%' cellspacing='0' cellpadding='0' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='560' valign='top' align='center' style='padding:0;Margin:0;'> \n" +
"                   <table width='100%' cellspacing='0' cellpadding='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td class='es-m-txt-c' align='left' style='padding:0;Margin:0;padding-bottom:20px;'><h1 style='Margin:0;line-height:36px;mso-line-height-rule:exactly;font-family:helvetica, helvetica neue, arial, verdana, sans-serif;font-size:30px;font-style:normal;font-weight:normal;color:#C44D39;text-align:center;'>"+text1+"</h1></td> \n" +
"                     </tr> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' class='es-m-txt-c' style='padding:0;Margin:0;'><h3 style='Margin:0;line-height:22px;mso-line-height-rule:exactly;font-family:helvetica, helvetica neue, arial, verdana, sans-serif;font-size:18px;font-style:normal;font-weight:normal;color:#6AA38B;text-align:center;'>"+text2+".</h3></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' bgcolor='#fafafa' style='Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;padding-bottom:40px;background-color:#FAFAFA;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='560' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' style='padding:0;Margin:0;padding-bottom:20px;'><h2 style='Margin:0;line-height:29px;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue, arial, verdana, sans-serif;font-size:24px;font-style:normal;font-weight:normal;color:#6AA38B;>"+text3+"</h2></td> \n" +
"                     </tr> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' style='padding:0;Margin:0;padding-bottom:20px;'><p style='Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, helvetica neue, helvetica, sans-serif;line-height:21px;color:#333333;'><strong>"+text4+". </strong></p></td> \n" +
"                     </tr> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' style='padding:0;Margin:0;padding-bottom:20px;'><p style='Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, helvetica neue, helvetica, sans-serif;line-height:21px;color:#333333;'> If you have any question, remark or observation please contact us on our social media pages or send us an email on: <b>huntkingdomteam@gmail.com</b></p></td> \n" +
"                     </tr> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' style='padding:0;Margin:0;'><p style='Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, helvetica neue, helvetica, sans-serif;line-height:21px;color:#333333;'>Yours sincerely,</p></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"           </table></td> \n" +
"         </tr> \n" +
"       </table> \n" +
"       <table cellpadding='0' cellspacing='0' class='es-footer' align='center' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top;'> \n" +
"         <tr style='border-collapse:collapse;'> \n" +
"          <td align='center' style='padding:0;Margin:0;'> \n" +
"           <table class='es-footer-body' width='600' cellspacing='0' cellpadding='0' bgcolor='#ffffff' align='center' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;'> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='padding:0;Margin:0;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='600' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0;'> \n" +
"                       <table border='0' width='100%' height='100%' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td style='padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;'> \n" +
"               <!--[if mso]><table width='560' cellpadding='0' cellspacing='0'><tr><td width='270' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' align='left' class='es-left' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='270' class='es-m-p20b' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0px;'><img class='adapt-img' src='https://iihrky.stripocdn.email/content/guids/CABINET_cbf6e95853cd5ebe41cc296200147222/images/57151590523435738.png' alt style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;' width='270'></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td><td width='20'></td><td width='270' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' class='es-right' align='right' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='270' align='left' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='right' class='es-m-txt-c' style='padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0;'> \n" +
"                       <table cellpadding='0' cellspacing='0' class='es-table-not-adapt es-social' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td align='center' valign='top' style='padding:0;Margin:0;padding-right:10px;'> <a href='https://www.facebook.com/pg/NERDS.H.K/posts/?ref=page_internal' target='_blank'> <img title='Facebook' src='https://iihrky.stripocdn.email/content/assets/img/social-icons/square-colored/facebook-square-colored.png' alt='Fb' width='32' height='32' style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;'></td> \n" +
"                          <td align='center' valign='top' style='padding:0;Margin:0;padding-right:10px;'><a href='https://twitter.com/huntkingdomTn' target='_blank'> <img title='Twitter' src='https://iihrky.stripocdn.email/content/assets/img/social-icons/square-colored/twitter-square-colored.png' alt='Tw' width='32' height='32' style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;'></td> \n" +
"                          <td align='center' valign='top' style='padding:0;Margin:0;padding-right:10px;'><a href='https://twitter.com/huntkingdomTn' target='_blank'><img title='Instagram' src='https://iihrky.stripocdn.email/content/assets/img/social-icons/square-colored/instagram-square-colored.png' alt='Inst' width='32' height='32' style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;'></td> \n" +
"                          <td align='center' valign='top' style='padding:0;Margin:0;'><a href='https://www.youtube.com/channel/UCI7bNyuDVBUnacJb85WlUSg' target='_blank'><img title='Youtube' src='https://iihrky.stripocdn.email/content/assets/img/social-icons/square-colored/youtube-square-colored.png' alt='Yt' width='32' height='32' style='display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td></tr></table><![endif]--></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='padding:0;Margin:0;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='600' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0;'> \n" +
"                       <table border='0' width='100%' height='100%' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td style='padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;'> \n" +
"               <!--[if mso]><table width='560' cellpadding='0' cellspacing='0'><tr><td width='390' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' align='left' class='es-left' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='390' class='es-m-p20b' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='left' class='es-m-txt-l' style='padding:0;Margin:0;'><p style='Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, helvetica neue, helvetica, sans-serif;line-height:21px;color:#6AA38B;'><a target='_blank' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, helvetica neue, helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39;' href=''>6 Chat El Jarid Street</a><br><a target='_blank' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, helvetica neue, helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39;' href=''>El Ghazela 2083</a>&nbsp;<br><a target='_blank' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, helvetica neue, helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39;' href=''>Ariana Tunisia</a></p></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td><td width='20'></td><td width='150' valign='top'><![endif]--> \n" +
"               <table cellpadding='0' cellspacing='0' class='es-right' align='right' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='150' align='left' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='right' class='es-m-txt-l' style='padding:0;Margin:0;'> \n" +
"                      	<span class='msohide es-button-border' style='border-style:solid;border-color:#C44D39;background:#FFFFFF;border-width:3px;display:inline-block;border-radius:0px;width:auto;mso-hide:all;'><a href='https://viewstripo.email/' class='es-button' target='_blank' style='mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, helvetica neue, arial, verdana, sans-serif;font-size:14px;color:#C44D39;border-style:solid;border-color:#FFFFFF;border-width:10px 20px 10px 20px;display:inline-block;background:#FFFFFF;border-radius:0px;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center;'> Contact Us</a></span> \n" +
"                       <!--<![endif]--></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table> \n" +
"               <!--[if mso]></td></tr></table><![endif]--></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='padding:0;Margin:0;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='600' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;font-size:0;'> \n" +
"                       <table border='0' width='100%' height='100%' cellpadding='0' cellspacing='0' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                         <tr style='border-collapse:collapse;'> \n" +
"                          <td style='padding:0;Margin:0px 0px 0px 0px;border-bottom:1px solid #CCCCCC;background:none;height:1px;width:100%;margin:0px;'></td> \n" +
"                         </tr> \n" +
"                       </table></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"             <tr style='border-collapse:collapse;'> \n" +
"              <td align='left' style='Margin:0;padding-top:20px;padding-bottom:20px;padding-left:20px;padding-right:20px;'> \n" +
"               <table cellpadding='0' cellspacing='0' width='100%' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                 <tr style='border-collapse:collapse;'> \n" +
"                  <td width='560' align='center' valign='top' style='padding:0;Margin:0;'> \n" +
"                   <table cellpadding='0' cellspacing='0' width='100%' role='presentation' style='mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;'> \n" +
"                     <tr style='border-collapse:collapse;'> \n" +
"                      <td align='center' style='padding:0;Margin:0;'><p style='Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-size:14px;font-family:arial, helvetica neue, helvetica, sans-serif;line-height:21px;color:#6AA38B;'>© HuntKingdomCo All Rights Reserved. <a target='_blank' \n" +
"                      	href='https://viewstripo.email/' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, helvetica neue, helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39;'>Terms & Conditions</a> | <a target='_blank' class='unsubscribe' href=' style='-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, helvetica neue, helvetica, sans-serif;font-size:14px;text-decoration:none;color:#C44D39;>Unsubscribe</a></p></td> \n" +
"                     </tr> \n" +
"                   </table></td> \n" +
"                 </tr> \n" +
"               </table></td> \n" +
"             </tr> \n" +
"           </table></td> \n" +
"         </tr> \n" +
"       </table> \n" +
"       </td> \n" +
"     </tr> \n" +
"   </table> \n" +
"  </div>  \n" +
" </body>\n" +
"</html>";
    }
    
}
