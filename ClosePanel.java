/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmo;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author Tleffyr
 */
public class ClosePanel extends JPanel {
    
    private JButton jbClose = new JButton("<html>Fermer et verrouiller<br>le Top Access</html>");
    private JButton jbRetry = new JButton("<html>J'ai rangé les <br> accessoires manquants<html>");
    private JLabel jlInfo = new JLabel("<html><u>Accessoires en place</u></html>", 0);
    
    private JLabel[] jlName;
    
public ClosePanel(String[][] data) { // data = missingDevices
        JPanel closePanel = new JPanel();
        JPanel infoPanel = new JPanel();
        Font police = new Font("Arial", Font.PLAIN, 30);
        jlInfo.setFont(police);
        jlInfo.setHorizontalAlignment(JLabel.LEFT);
       
        jbClose.setFont(police);
        jbClose.setPreferredSize(new Dimension(440, 100));
        jbRetry.setFont(police);
        jbRetry.setPreferredSize(new Dimension(440, 100));
        
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

        if(data.length != 0) {
            jlInfo.setText("<html><u>Accessoires manquants</u></html>");
        }
        //Cette instruction informe le layout que c'est une fin de ligne
        gbc1.gridwidth = GridBagConstraints.REMAINDER;
        gbc1.gridx = 0;	
        infoPanel.add(jlInfo, gbc1);
        
        System.out.println("Printing devices...");
        int i;
        for(i = 0; i<data.length; i++){
            jlName[i] = new JLabel(data[0][0], 0);
            jlName[i].setFont(police);
            jlName[i].setHorizontalAlignment(JLabel.LEFT);
            gbc1.gridy = i+1;
            gbc1.gridwidth = GridBagConstraints.REMAINDER;
            gbc1.gridx = 0;	
            infoPanel.add(jlName[i], gbc1);
        }
		
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

        if(data.length != 0){
            jbClose.setText("<html>Fermer tout de même<br>Verrouiller le Top Access<html>");
            closePanel.setLayout(new GridLayout(2, 1));
            closePanel.add(jbRetry);
            closePanel.add(jbClose);
            
        } else {
            closePanel.add(jbClose);
        }
        
        //Cette instruction informe le layout que c'est une fin de ligne
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(infoPanel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(closePanel, gbc);
        
        
        
        
        
        //jbLogin.addActionListener(new LoginPanel.LoginListener());
    }
    public JButton getJbRetry(){
        return jbRetry;
    }
    public JButton getJbClose(){
        return jbClose;
    }
}
