/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Entities.Hebergement;
import Entities.MoyenDeTransport;
import Entities.User;

/**
 *
 * @author User
 */
public class Session {
    public static User current_user=null;
    public static Hebergement current_hebergement=null;
    public static MoyenDeTransport current_moyenDeTransport=null;
}
