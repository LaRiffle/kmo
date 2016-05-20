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
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 *
 * @author Tleffyr
 */
public class ConfigureLDAPPanel extends JPanel {
    
    private JLabel jlTitle = new JLabel("Les paramètres de connexion");
    private JTextField[] jtfValue;
    private JLabel[] jlName;
    private JButton[] jbSave;
    
    public ConfigureLDAPPanel(String[][] data) {
        int height = 35;
        int width = 120;
        int widthValue = 250;
        int widthName = 80;
        Font police = new Font("Arial", Font.PLAIN, 18);
        Font police2 = new Font("Arial", Font.PLAIN, 24);
        jlTitle.setFont(police2);
        JPanel titlePanel =  new JPanel();
        titlePanel.setPreferredSize(new Dimension(450, height));
        titlePanel.add(jlTitle);
        
        JPanel panel =  new JPanel();
        JPanel[] namePanel;
        namePanel = new JPanel[data.length];
        jlName = new JLabel[data.length];
        jtfValue = new JTextField[data.length];
        jbSave = new JButton[data.length];
        
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.ipady = 10; gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1;  gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(titlePanel, gbc);
        
        int i;
        System.out.println("Printing parameters...");
        for(i = 0; i<data.length; i++){
            jlName[i] = new JLabel();
            jtfValue[i] = new JTextField();
            jbSave[i] = new JButton("Enregistrer");
            jlName[i].setPreferredSize(new Dimension(widthName, height));
            jtfValue[i].setPreferredSize(new Dimension(widthValue, height));
            jbSave[i].setPreferredSize(new Dimension(width, height));
            jlName[i].setFont(police);
            jtfValue[i].setFont(police);
            jbSave[i].setFont(police);
            
            namePanel[i] = new JPanel();
            namePanel[i].setPreferredSize(new Dimension(widthName, height));
            namePanel[i].add(jlName[i]);
            System.out.println(""+data[i][0]+": "+data[i][1]);
            gbc.gridwidth = 1;
            gbc.gridy = i+1;
            gbc.gridx = 0;
            jlName[i].setText(data[i][0]);
            namePanel[i].add(jlName[i]);
            panel.add(namePanel[i], gbc);

            gbc.gridx = 1;
            jtfValue[i].setText(data[i][1]);
            panel.add(jtfValue[i], gbc);

            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 2;
            panel.add(jbSave[i], gbc);
            
            jbSave[i].addActionListener(new SaveParamListener(i, data[i][0], jtfValue[i]));
            
        }
        panel.setPreferredSize(new Dimension(500, (60*(i+1))));
        this.add(panel);
        
    }
    
    class SaveParamListener implements ActionListener{
        private int id;
        private String name;
        private String value;
        
        public SaveParamListener(int id, String name, JTextField value){
            this.id = id;      
            this.name = name;
            this.value = jtfValue[id].getText();
        }
        
        public void actionPerformed(ActionEvent e) {
            this.value = jtfValue[id].getText();
            System.out.print("Saving "+this.name+"="+this.value+"...");
            
            jlName[id].setText(jlName[id].getText()+" [Enregistré]");
            
            try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Ecole";
            String bdd_user = "postgres";
            String passwd = "namibia";

            Connection conn = DriverManager.getConnection(url, bdd_user, passwd);
            Statement state = conn.createStatement();
            state.executeUpdate("UPDATE parameters SET value='"+this.value+"' WHERE name='"+this.name+"'");
            System.out.println(" Done");

            } catch (Exception err) {
              System.out.println("ERREUR de connexion à la bdd");
             err.printStackTrace();
            }
        }
   }
    
}
