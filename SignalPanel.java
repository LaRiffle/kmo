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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

/**
 *
 * @author Tleffyr
 */
public class SignalPanel extends JPanel {
    // Les textes à afficher
    private JLabel labelTextArea = new JLabel();
    private JTextArea textArea = new JTextArea(5, 20);
    private JButton jbSave; // Bouton
    private static Translator trans;
    
    public SignalPanel() {
        trans = new Translator();
        trans.setContext("text.signal");
        // On créé un panel pour chaque élément du formulaire pour harmoniser la mise en page
        JPanel labelPanel = new JPanel(); 
        JPanel textAreaPanel = new JPanel();
        JPanel savePanel = new JPanel();
        // On définit les polices
        Font police = new Font("Arial", Font.PLAIN, 30);
        Font littlePolice = new Font("Arial", Font.PLAIN, 18);
        // ... et les couleurs
        Color labelColor = new Color (128, 128, 128);
   
        labelTextArea.setText(trans.get("text"));
        labelTextArea.setFont(police);
        //labelTextArea.setForeground(labelColor);
        labelTextArea.setPreferredSize(new Dimension(500, 60));
        textArea.setFont(police);
        //textArea.setForeground(labelColor);
        textArea.setPreferredSize(new Dimension(300, 60));
        //textArea.setEditable(false);
        jbSave = new JButton(trans.get("send"));
        jbSave.setFont(police);
        jbSave.setPreferredSize(new Dimension(300, 60));

        // On affecte chaque élément de formulaire à son panel respectif
        labelPanel.add(labelTextArea);
        textAreaPanel.add(textArea);
        savePanel.add(jbSave);	
    
	this.setPreferredSize(new Dimension(450, 500));
        this.setLayout(new GridBagLayout());		
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(labelPanel, gbc); 

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(textAreaPanel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(savePanel, gbc);

        
    }
    // Les getters et setters qui suivent permettent d'accéder aux objets de ce panneau et 
    // de faire des actions basiques depuis le controleur principal qui est Fenetre.java
    public JButton getJbSave(){
        return jbSave;
    }
    public JTextArea getTextArea(){
        return textArea;
    }
}
