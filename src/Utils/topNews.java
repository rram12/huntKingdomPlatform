/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Ramzi
 */
public class topNews {
    
    public ArrayList getTopNews() throws MalformedURLException, IOException{
        ArrayList<String> newsList = new ArrayList();
        try{
            URL rssUrl = new URL("http://outdoornewsdaily.com/feed/");
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String line;
            String tmp;
            int i =0;
            while((line=in.readLine())!=null){
                if(line.contains("<title>")){
                    tmp = line.substring(line.indexOf("<title>")); //deletes chars before "<title>"
                    tmp=tmp.replace("<title>", "");
                    tmp = tmp.substring(0,tmp.indexOf("</title>")); //deletes  "</title>" and chars after
                    if(i>0)
                        newsList.add(tmp);
                    i++;
                }
            }
            in.close();
        }
        catch(MalformedURLException ex1){
            System.out.println(ex1.getMessage());
        }
        catch(IOException ex2){
            System.out.println(ex2.getMessage());
        }
        return newsList;
    }
    
    public ArrayList getTopNewsLinks() throws MalformedURLException, IOException{
        ArrayList<String> newsLiksList = new ArrayList();
        try{
            URL rssUrl = new URL("http://outdoornewsdaily.com/feed/");
            BufferedReader in = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String line;
            String tmp;
            int i =0;
            while((line=in.readLine())!=null){
                if(line.contains("<link>")){
                    tmp = line.substring(line.indexOf("<link>")); //deletes chars before "<title>"
                    tmp=tmp.replace("<link>", "");
                    tmp = tmp.substring(0,tmp.indexOf("</link>")); //deletes  "</title>" and chars after
                    if(i>0)
                        newsLiksList.add(tmp);
                    i++;
                }
            }
            in.close();
        }
        catch(MalformedURLException ex1){
            System.out.println(ex1.getMessage());
        }
        catch(IOException ex2){
            System.out.println(ex2.getMessage());
        }
        return newsLiksList;
    }
    
}
