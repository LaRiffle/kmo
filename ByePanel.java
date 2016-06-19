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
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Tleffyr
 */
public class ByePanel extends JPanel {
    private JButton jbSignal;
    private JButton jbLogout;
    private static Translator trans;
    
    public ByePanel() {
        trans = new Translator();
        trans.setContext("text.bye");
        JPanel signalPanel = new JPanel();
        JPanel logoutPanel = new JPanel();
        Font police = new Font("Arial", Font.PLAIN, 30);
    
        jbLogout = new JButton(trans.get("logout"));
        jbLogout.setFont(police);
        jbLogout.setPreferredSize(new Dimension(550, 60));
        jbSignal = new JButton("<html>"+trans.get("signal")+"</html>");
        jbSignal.setFont(police);
        jbSignal.setPreferredSize(new Dimension(550, 100));

        signalPanel.add(jbSignal);
        logoutPanel.add(jbLogout);
		
        //Le conteneur principal
        //JPanel content = new JPanel();
        this.setPreferredSize(new Dimension(570, 500));
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
        this.add(signalPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(logoutPanel, gbc);

       
    }
    
    public JButton getJbSignal(){
        return jbSignal;
    }
    public JButton getJbLogout(){
        return jbLogout;
    }
    
}
