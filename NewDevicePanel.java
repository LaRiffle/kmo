/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Tleffyr
 */
public class NewDevicePanel extends JPanel {
    
    private JButton jbAdd = new JButton("Valider le matériel");
   
    private JLabel jlTitle = new JLabel("Ajout de matériel");
    
    private JLabel jlInfo = new JLabel("Approchez la puce du capteur");
    private JLabel jlInfo2 = new JLabel("situé dans le Core");
    private JLabel jlState = new JLabel("Pas de puce détectée");
    private JButton jbRefresh = new JButton("Rafraîchir");
    private JPanel panel = new JPanel();
    private JPanel statePanel =  new JPanel();
    
    private JPanel markerPanel = new JPanel();
    private JTextField jtfName = new JTextField();
    private JLabel jlMarker;
    private JButton jbSave = new JButton("Enregistrer");
        
    public NewDevicePanel() {
        int height = 40;
        int widthMarker = 120;
        int widthName = 170;
        int widthMod = 80;
        int widthDel = 80;
        Font police = new Font("Arial", Font.PLAIN, 24);
        Font policeTitle = new Font("Arial", Font.PLAIN, 30);
	jlTitle.setFont(policeTitle);
        Font policeLittle = new Font("Arial", Font.PLAIN, 18);
        //Color linkColor = new Color (0, 0, 255);
        jlInfo.setFont(police);
        jlInfo2.setFont(police);
        jlState.setFont(police);
        jbRefresh.setFont(policeLittle);
        //jlRefresh.setForeground(linkColor);
        JPanel titlePanel =  new JPanel();
        titlePanel.setPreferredSize(new Dimension(450, height));
        titlePanel.add(jlTitle);
        JPanel infoPanel =  new JPanel();
        infoPanel.setPreferredSize(new Dimension(450, height*2));
        infoPanel.add(jlInfo);
        infoPanel.add(jlInfo2);
        statePanel.setPreferredSize(new Dimension(450, height*3));
        jbRefresh.setPreferredSize(new Dimension(150, height));
        if(true){
            System.out.println("New device found...");
            jlMarker = new JLabel(String.valueOf(Math.random()).substring(0,6));
            jlMarker.setFont(police);
            jtfName.setFont(police);
            jbSave.setFont(police);
            markerPanel.add(jlMarker);
            jtfName.setPreferredSize(new Dimension(150, height));
            statePanel.setLayout(new GridBagLayout());		
            //L'objet servant à positionner les composants
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.ipady = 10; gbc.gridheight = 1;  gbc.gridwidth = 1;

            gbc.gridwidth = 1;
            gbc.gridy = 0;
            gbc.gridx = 0;
            statePanel.add(markerPanel, gbc);
            gbc.gridx++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            statePanel.add(jtfName, gbc);
            /*gbc.gridx++;
            statePanel.add(jbSave, gbc);*/
            
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            statePanel.add(jbRefresh, gbc);

            jbAdd.setFont(police);
            jbAdd.setPreferredSize(new Dimension(450, 50));


            this.setPreferredSize(new Dimension(450, 500));
            //On définit le layout manager
            this.setLayout(new GridBagLayout());		
            //L'objet servant à positionner les composants
            //GridBagConstraints gbc = new GridBagConstraints();

            //Espace au dessus et au dessous
            gbc.ipady = 20;
            //On positionne la case de départ du composant
            gbc.gridx = 0;
            gbc.gridy = 0;
            //La taille en hauteur et en largeur
            gbc.gridheight = 1;
            gbc.gridwidth = 1;

            //Cette instruction informe le layout que c'est une fin de ligne
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(titlePanel, gbc);

            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(infoPanel, gbc);

            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(statePanel, gbc);

            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(jbAdd, gbc);
        
        } else {
            GridBagConstraints gbc = new GridBagConstraints();
            System.out.println("No new device found...");
            statePanel.add(jlState);
            statePanel.add(jbRefresh);
            //Espace au dessus et au dessous
            gbc.ipady = 20;
            //On positionne la case de départ du composant
            gbc.gridx = 0;
            gbc.gridy = 0;
            //La taille en hauteur et en largeur
            gbc.gridheight = 1;
            gbc.gridwidth = 1;

            //Cette instruction informe le layout que c'est une fin de ligne
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(titlePanel, gbc);

            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(infoPanel, gbc);

            gbc.gridy++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;	
            this.add(statePanel, gbc);
 
       }
        
        
    }
    /*
    public JButton getJbConf(){
        return jbConf;
    }*/
    public JButton getJbRefresh(){
        return jbRefresh;
    }
    public JButton getJbSave(){
        return jbAdd;
    }
    public JTextField getJtfName(){
        return jtfName;
    }
    public JLabel getJlMarker(){
        return jlMarker;
    }
}