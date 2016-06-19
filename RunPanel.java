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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Tleffyr
 */
public class RunPanel extends JPanel {
    
    private JButton jbOpen;
    private JButton jbClose;
    private String  title[] = {"Matériel", "Etat"};
    private JTable tableau;
    
    private JLabel[] jlName;
    private JLabel[] jlStatus;
    private JPanel panel = new JPanel();
    private static Translator trans;
    
public RunPanel(String[][] data) {
        trans = new Translator();
        trans.setContext("text.run");
        JPanel temoinPanel = new JPanel();
        JPanel closePanel = new JPanel();
        Font police = new Font("Arial", Font.PLAIN, 30);
        jbOpen = new JButton(trans.get("change"));
        jbOpen.setFont(police);
        jbOpen.setPreferredSize(new Dimension(440, 60));
        temoinPanel.add(jbOpen);
        jbClose = new JButton(trans.get("close"));
        jbClose.setFont(police);
        jbClose.setPreferredSize(new Dimension(440, 60));
        closePanel.add(jbClose);
        title[0] = trans.get("device"); // traduction a posteriori
        title[1] = trans.get("state");
        
        this.setPreferredSize(new Dimension(500, 440));
        /*
        //On définit le layout manager
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel[] namePanel;
        namePanel = new JPanel[data.length];
        JPanel[] statusPanel;
        statusPanel = new JPanel[data.length];
        jlName = new JLabel[data.length];
        jlStatus = new JLabel[data.length];
        
        int i;
        for(i = 0; i<data.length; i++){
            
            jlName[i] = new JLabel();
            jlStatus[i] = new JLabel();
            jlName[i].setPreferredSize(new Dimension(150, 50));
            jlStatus[i].setPreferredSize(new Dimension(150, 50));
            jlName[i].setFont(police);
            jlStatus[i].setFont(police);
            
            
            namePanel[i] = new JPanel();
            statusPanel[i] = new JPanel();
            namePanel[i].setPreferredSize(new Dimension(230, 50));
            statusPanel[i].setPreferredSize(new Dimension(230, 50));
            namePanel[i].add(jlName[i]);
            statusPanel[i].add(jlStatus[i]);
            System.out.println("printing device "+data[i][0]+" ("+data[i][1]+")");
            gbc.gridwidth = 1;
            gbc.gridy = i+1;
            gbc.gridx = 0;
            jlName[i].setText(data[i][0]);
            panel.add(namePanel[i], gbc);

            gbc.gridx++;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            jlStatus[i].setText(data[i][1]);
            panel.add(statusPanel[i], gbc);
        }
        
        panel.setPreferredSize(new Dimension(500, (60*(i+1))));
        */
        
        this.tableau = new JTable(data, title);
        tableau.setFont(police);
        for(int i = 0; i < tableau.getRowCount(); i++){
            tableau.setRowHeight(i, 40);
        }
        
        JScrollPane scrollPane = new JScrollPane(tableau);//new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 310));
        //scrollPane.setBounds(50, 30, 300, 50);
        this.add(scrollPane);
       
        this.add(temoinPanel);
        this.add(closePanel);
        
    }
    public JButton getJbTemoin(){
        return jbOpen;
    }
    public JButton getJbClose(){
        return jbClose;
    }
    public void setData(String[][] data) { // Met à jour les informations sur l'état des matériels
        System.out.println("Refreshed data of device on runPanel...");
        this.tableau = new JTable(data, title);
    }
}
