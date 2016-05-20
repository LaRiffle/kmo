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
public class ShowDevicePanel extends JPanel {
    
    private JButton jbAdd = new JButton("Ajouter un matériel");
   
    private JLabel jlTitle = new JLabel("Modifier le matériel");
    private JLabel jlLabel = new JLabel("Le matériel a été supprimé");
    
    private JLabel[] jlMarker;
    private JTextField[] jtfName;
    private JButton[] jbMod;
    private JButton[] jbDel;
    private JPanel panel = new JPanel();
        
    public ShowDevicePanel(String[][] data) {
        int height = 35;
        int widthMarker = 120;
        int widthName = 170;
        int widthMod = 80;
        int widthDel = 80;
        Font police = new Font("Arial", Font.PLAIN, 24);
        Font policeTitle = new Font("Arial", Font.PLAIN, 30);
	jlTitle.setFont(policeTitle);
        JPanel titlePanel =  new JPanel();
        titlePanel.setPreferredSize(new Dimension(450, height));
        titlePanel.add(jlTitle);
        
        JPanel panel =  new JPanel();
        JPanel[] markerPanel;
        markerPanel = new JPanel[data.length];
        jtfName = new JTextField[data.length];
        jlMarker = new JLabel[data.length];
        jbMod = new JButton[data.length];
        jbDel = new JButton[data.length];
        
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.ipady = 10; gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1;  gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(titlePanel, gbc);
        
        int i;
        System.out.println("Printing devices...");
        for(i = 0; i<data.length; i++){
            jlMarker[i] = new JLabel();
            jtfName[i] = new JTextField();
            jbMod[i] = new JButton("Enregistrer");
            jbDel[i] = new JButton("Suppr.");
            jlMarker[i].setPreferredSize(new Dimension(widthMarker, height));
            jtfName[i].setPreferredSize(new Dimension(widthName, height));
            jbMod[i].setPreferredSize(new Dimension(widthMod, height));
            jbDel[i].setPreferredSize(new Dimension(widthDel, height));
            jlMarker[i].setFont(police);
            jtfName[i].setFont(police);
            jbMod[i].setFont(police);
            jbDel[i].setFont(police);
            
            markerPanel[i] = new JPanel();
            markerPanel[i].setPreferredSize(new Dimension(widthMarker, height));
            markerPanel[i].add(jlMarker[i]);
            System.out.println(""+data[i][1]+": "+data[i][0]+" [Status:"+data[i][2]+"]");
            gbc.gridwidth = 1;
            gbc.gridy = i+1;
            gbc.gridx = 0;
            jlMarker[i].setText(data[i][1]);
            markerPanel[i].add(jlMarker[i]);
            panel.add(markerPanel[i], gbc);

            gbc.gridx++;
            jtfName[i].setText(data[i][0]);
            panel.add(jtfName[i], gbc);

            gbc.gridx++;
            panel.add(jbMod[i], gbc);
            
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx++;
            panel.add(jbDel[i], gbc);
            
            jbMod[i].addActionListener(new SaveDeviceListener(i, data[i][1]));
            jbDel[i].addActionListener(new RemoveDeviceListener(i, data[i][1]));
            
        }
        panel.setPreferredSize(new Dimension(500, ((height+10)*(i+1))));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        //scrollPane.setBounds(50, 30, 300, 50);
        this.add(scrollPane);
        
        
        
        jbAdd.setFont(police);
        jbAdd.setPreferredSize(new Dimension(450, 50));


        gbc.gridy = i+2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(jbAdd, gbc);
    }
    class SaveDeviceListener implements ActionListener{
        private int id;
        private String marker;
        private String name;
        
        public SaveDeviceListener(int id, String marker){
            this.id = id;      
            this.marker = marker;
        }
        
        public void actionPerformed(ActionEvent e) {
            this.name = jtfName[id].getText();
            System.out.print("Saving device "+this.marker+"="+this.name+"...");
            
            jlMarker[id].setText(jlMarker[id].getText()+" [Enregistré]");
            
            try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Ecole";
            String bdd_user = "postgres";
            String passwd = "namibia";

            Connection conn = DriverManager.getConnection(url, bdd_user, passwd);
            Statement state = conn.createStatement();
            state.executeUpdate("UPDATE device SET name='"+this.name+"' WHERE marker='"+this.marker+"'");
            System.out.println(" Done");

            } catch (Exception err) {
              System.out.println("ERREUR de connexion à la bdd");
             err.printStackTrace();
            }
        }
    }
    
    class RemoveDeviceListener implements ActionListener{
        private int id;
        private String marker;
        private String name;
        
        public RemoveDeviceListener(int id, String marker){
            this.id = id;      
            this.marker = marker;
        }
        
        public void actionPerformed(ActionEvent e) {
            this.name = jtfName[id].getText();
            System.out.print("Deleting "+this.marker+":"+this.name+"...");
            //jlLogin[id].setVisible(false);
            jlMarker[id].setText("Supprimé");
            jtfName[id].setVisible(false);
            jbMod[id].setVisible(false);
            jbDel[id].setVisible(false);
            
            try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Ecole";
            String bdd_user = "postgres";
            String passwd = "namibia";

            Connection conn = DriverManager.getConnection(url, bdd_user, passwd);
            Statement state = conn.createStatement();
            state.executeUpdate("DELETE FROM device WHERE marker='"+this.marker+"'");
            System.out.println(" Done");

            } catch (Exception err) {
              System.out.println("ERREUR de connexion à la bdd");
             err.printStackTrace();
           }
        
        }
   }
    /*
    public JButton getJbConf(){
        return jbConf;
    }*/
    public JButton getJbAdd(){
        return jbAdd;
    }
    /*
    public JTextField getJtfLogin(){
        return jtfLogin;
    }
    public JTextField getJtfPass(){
        return jtfPass;
    }*/
}