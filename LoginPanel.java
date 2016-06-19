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

/**
 *
 * @author Tleffyr
 */
public class LoginPanel extends JPanel {
    // Les éléments de pages
    private JTextField jtfLogin; // Champ de saisie texte
    private JPasswordField jtfPass; // Champ de saisie mot de passe
    private JButton jbLogin; // Bouton
    private int returnStatus = 0;
    private JLabel info = new JLabel(""); // servira pour les éventuels messsage d'erreur
    private static Translator trans;
   
    
    public LoginPanel(boolean temoin) {
        trans = new Translator();// on charge le dictionnaire
        trans.setContext("text.login");// on précise l'environnement (la section du dico qu'on utilise)
        // On créé un panel pour chaque élément du formulaire pour harmoniser la mise en page
        JPanel loginPanel = new JPanel(); 
        JPanel passPanel = new JPanel();
        JPanel connexionPanel = new JPanel();
        // On définit les polices
        Font police = new Font("Arial", Font.PLAIN, 30);
        Font littlePolice = new Font("Arial", Font.PLAIN, 18);
        // ... et les couleurs
        Color labelColor = new Color (128, 128, 128);
        Color errorColor = new Color (128, 0, 0);
        // On les affecte aux éléments du formulaire, que l'on dimensionne également
        String labelLogin = trans.get("login");
        jtfLogin = new JTextField(labelLogin);// on accède à un champ du dico via trans.get()
        jtfLogin.setFont(police);
        jtfLogin.setForeground(labelColor);
        jtfLogin.setPreferredSize(new Dimension(300, 60));
        String labelPass = trans.get("password");
        jtfPass = new JPasswordField(labelPass);
        jtfPass.setFont(police);
        jtfPass.setForeground(labelColor);
        jtfPass.setPreferredSize(new Dimension(300, 60));
        char hiddenChar = jtfPass.getEchoChar(); // 
        jtfPass.setEchoChar((char)0);
        jbLogin = new JButton(trans.get("connexion"));
        jbLogin.setFont(police);
        jbLogin.setPreferredSize(new Dimension(300, 60));
        if(temoin){ // On vérifier si on est dans un passage de témoin où une connexion normale, "info" le précisera
            info = new JLabel(trans.get("temoin"));
            info.setVisible(true);
        } else {
            info.setVisible(false);
        }
        info.setFont(littlePolice);
        info.setForeground(errorColor);

        // On affecte chaque élément de formulaire à son panel respectif
        loginPanel.add(jtfLogin);
        passPanel.add(jtfPass);
        connexionPanel.add(jbLogin);	
    
		
        //Le conteneur principal est this
        this.setPreferredSize(new Dimension(450, 500));
        //On définit le layout manager qui gère la mise en page, on pourra se référer à la doc pour plus d'infos
        this.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();

        //Espace au dessus et au dessous (en pixels)
        gbc.ipady = 20;
        //On positionne la case de départ du composant (en indice sur un quadrillage d'origine haut-gauche)
        gbc.gridx = 0;
        gbc.gridy = 0;
        //La taille en hauteur et en largeur (en unité de quadrillage)
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        //Cette instruction informe le layout que c'est une fin de ligne
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(loginPanel, gbc); // On ajoute sur la première ligne le premier élément du formulaire

        gbc.gridy = 1; // et ainsi de suite
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(passPanel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(connexionPanel, gbc);
        
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(info, gbc);
        
        // La truc qui suit permet de simuler les placeholder (en html), ie une suggestion de texte par défaut en clair quand un champ n'est pas saisi
        jtfLogin.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent ke)
            {
                if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
                {
                    if(jtfLogin.getText().equals(labelLogin)){
                        jtfLogin.setText("");
                        jtfLogin.setForeground(Color.BLACK);
                    }
                }
            }
            public void keyReleased(KeyEvent ke)
            {
                if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
                {
                    if(jtfLogin.getText().equals("")) {
                        jtfLogin.setText(labelLogin);
                        jtfLogin.setForeground(labelColor);
                    }
                }
            }
        });
        // On fait ça pour le mot de passe aussi
        jtfPass.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent ke)
            {
                if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
                {
                    if(jtfPass.getText().equals(labelPass)){
                        jtfPass.setText("");
                        jtfPass.setForeground(Color.BLACK);
                        jtfPass.setEchoChar(hiddenChar);
                    }
                }
            }
            public void keyReleased(KeyEvent ke)
            {
                if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
                {
                    if(jtfPass.getText().equals("")) {
                        jtfPass.setText(labelPass);
                        jtfPass.setForeground(labelColor);
                        jtfPass.setEchoChar((char)0); 
                    }
                }
            }
        });
    }
    // Les getters et setters qui suivent permettent d'accéder aux objets de ce panneau et 
    // de faire des actions basiques depuis le controleur principal qui est Fenetre.java
    public JTextField getJtfLogin(){
        return jtfLogin;
    }
    public JTextField getJtfPass(){
        return jtfPass;
    }
    public JButton getJbLogin(){
        return jbLogin;
    }
    public int getReturnStatus() {
        return returnStatus;
    }
    public void setInfo(String message) {
        info.setText(message);
        info.setVisible(true);
    }
    public void removeInfo() {
        info.setText("");
        info.setVisible(false);
    }
}
