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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 *
 * @author Tleffyr
 */
public class AdminBoardPanel extends JPanel {
     
    private JButton jbAccount;
    private JButton jbDevice;
    private JButton jbSettings;
    private JButton jbLogs;
    private static Translator trans;
    
    public AdminBoardPanel() {
        trans = new Translator();
        trans.setContext("text.adminBoard");
        JPanel accountPanel = new JPanel();
        JPanel devicePanel = new JPanel();
        JPanel settingsPanel = new JPanel();
        JPanel logsPanel = new JPanel();
        Font police = new Font("Arial", Font.PLAIN, 30);
    
        jbDevice = new JButton(trans.get("device"));
        jbDevice.setFont(police);
        jbDevice.setPreferredSize(new Dimension(300, 60));
        jbAccount = new JButton(trans.get("account"));
        jbAccount.setFont(police);
        jbAccount.setPreferredSize(new Dimension(300, 60));
        jbSettings = new JButton(trans.get("settings"));
        jbSettings.setFont(police);
        jbSettings.setPreferredSize(new Dimension(300, 60));
        jbLogs = new JButton(trans.get("logs"));
        jbLogs.setFont(police);
        jbLogs.setPreferredSize(new Dimension(300, 60));

        accountPanel.add(jbAccount);
        devicePanel.add(jbDevice);
        settingsPanel.add(jbSettings);
        logsPanel.add(jbLogs);
		
        //Le conteneur principal
        //JPanel content = new JPanel();
        this.setPreferredSize(new Dimension(450, 500));
        //On définit le layout manager
        this.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();

        //Espace au dessus et au dessous
        gbc.ipady = 40;
        //On positionne la case de départ du composant
        gbc.gridx = 0;
        gbc.gridy = 0;
        //La taille en hauteur et en largeur
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        //Cette instruction informe le layout que c'est une fin de ligne
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(accountPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(devicePanel, gbc);
        
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(settingsPanel, gbc);
        
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(logsPanel, gbc);

       
    }
    
    public JButton getJbAccount(){
        return jbAccount;
    }
    public JButton getJbDevice(){
        return jbDevice;
    }
    public JButton getJbSettings(){
        return jbSettings;
    }
    public JButton getJbLogs(){
        return jbLogs;
    }
    
}
