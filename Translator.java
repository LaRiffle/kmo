/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmo;


//Tout ça pour le yml parser
import net.sourceforge.yamlbeans.*;
import net.sourceforge.yamlbeans.emitter.*;
import net.sourceforge.yamlbeans.parser.*;
import net.sourceforge.yamlbeans.scalar.*;
import net.sourceforge.yamlbeans.tokenizer.*;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Tleffyr
 */
public class Translator {
    
    //le dictionnaire de mots
private static Map trans;
private String classpath = "";
    
public Translator(){
    try // On initialise le dictionnaire de mots
    { 
        YamlReader reader = new YamlReader(new FileReader("test/parameters.yml"));
        try 
        {
            Object object = reader.read();
            //System.out.println(object);
            trans = (Map)object;
            //System.out.println(object.text);
            //System.out.println(trans.get("text.start.heading"));
            
        } 
        catch(YamlException e)
        {
            e.printStackTrace();
        }
    
    } 
       catch(FileNotFoundException e)
    {
        e.printStackTrace();
    }
    
}
    public void setContext(String context){
        classpath = context;
    }
    public String getContext(){
        return classpath;
    }
    public String get(String req){ // fonction d'agrément
        if(!classpath.equals(""))
            req = classpath+"."+req;
        
        Map yml = trans;
        int i=0;
        //System.out.println(req);
        //System.out.println(req.split("\\.").length);
        for (String retval: req.split("\\.")){
          i++;
          //System.out.println(i+" : "+retval);
          //System.out.println(yml);
          if(i == req.split("\\.").length)
            return (String)yml.get(retval);   
          else
            yml = (Map)yml.get(retval);   
        }
        return "error";
        //return (String) trans.get(req);
        
    }
}