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
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 *
 * @author Tleffyr
 */
public class OpenPanel extends JPanel {
    
    private JButton jbOpen = new JButton("Déverrouiller le Top Access");
    private JLabel jlInfo = new JLabel("<html><u>Etat du matériel</u></html>", 0);
    private JLabel jlInfo1 = new JLabel("Manquants : ", 0);
    
    private JLabel[] jlName;
    
public OpenPanel(String[][] data) { // data = missingDevices
        JPanel openPanel = new JPanel();
        JPanel infoPanel = new JPanel();
        Font police = new Font("Arial", Font.PLAIN, 30);
        jlInfo.setFont(police);
        jlInfo.setHorizontalAlignment(JLabel.LEFT);
        jlInfo1.setFont(police);
        jlInfo1.setHorizontalAlignment(JLabel.LEFT);
       
        jbOpen.setFont(police);
        jbOpen.setPreferredSize(new Dimension(440, 60));

        openPanel.add(jbOpen);
        //infoPanel.add(jlInfo);
        //infoPanel.add(jlInfo1);
        
        jlName = new JLabel[data.length];
        
        //Le conteneur principal
        //JPanel content = new JPanel();
        infoPanel.setPreferredSize(new Dimension(450, 150));
        //On définit le layout manager
        infoPanel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.anchor = GridBagConstraints.WEST;
        //Espace au dessus et au dessous
        gbc1.ipady = 10;
        //On positionne la case de départ du composant
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        //La taille en hauteur et en largeur
        gbc1.gridheight = 1;
        gbc1.gridwidth = 1;

        //Cette instruction informe le layout que c'est une fin de ligne
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridx = 0;	
        infoPanel.add(jlInfo, gbc1);

        if(data.length == 0) {
            jlInfo1.setText("Aucun accessoire manquant");
        }
        gbc1.gridy = 1;
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridx = 0;	
        infoPanel.add(jlInfo1, gbc1);
        
        System.out.println("Printing devices...");
        int i;
        for(i = 0; i<data.length; i++){
            jlName[i] = new JLabel(data[0][0], 0);
            jlName[i].setFont(police);
            jlName[i].setHorizontalAlignment(JLabel.LEFT);
            gbc1.gridy = i+2;
            gbc1.gridwidth = GridBagConstraints.REMAINDER;
            gbc1.gridx = 0;	
            infoPanel.add(jlName[i], gbc1);
        }
		
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
        this.add(openPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(infoPanel, gbc);
        
        
        //jbLogin.addActionListener(new LoginPanel.LoginListener());
    }
    public JButton getJbOpen(){
        return jbOpen;
    }
}
