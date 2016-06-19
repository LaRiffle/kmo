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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 *
 * @author Tleffyr
 */
public class LogsPanel extends JPanel {
    
    private JButton jbAdd;
   
    private JLabel jlTitle;
    
    private JLabel[] jlMarker;
    private JTextField[] jtfName;
    private JButton[] jbMod;
    private JButton[] jbDel;
    private JPanel panel = new JPanel();
    private static Translator trans;
    
    private JTextArea textArea;
        
    public LogsPanel() {
        trans = new Translator();
        trans.setContext("text.logs");
 
        Font police = new Font("Arial", Font.PLAIN, 24);
        JPanel[] logsPanel;
        JLabel[] jlLogs;
        logsPanel = new JPanel[100];
        jlLogs = new JLabel[100];
        
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipady = 10; gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1;  gbc.gridwidth = 1;
      
        
        BufferedReader br = null;
        int i = 0;
        int j = 0;
        String text= "";
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("test/logs.txt"));
            while ((sCurrentLine = br.readLine()) != null) {
                text = text + sCurrentLine + "\n";
                if(i==100) i=0;
		System.out.println(sCurrentLine);
                /*gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.gridy = j+1;
                gbc.gridx = 0;
                jlLogs[i] = new JLabel();
                jlLogs[i].setFont(police);
                jlLogs[i].setText(sCurrentLine);
                logsPanel[i] =  new JPanel();
                logsPanel[i].add(jlLogs[i]);*/
                //panel.add(logsPanel[i], gbc);
                i++; j++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
		if (br != null)br.close();
            } catch (IOException ex) {
		ex.printStackTrace();
            }
	}
        /*panel.setPreferredSize(new Dimension(500, ((50+10)*(i+1))));

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        this.add(scrollPane);*/
        
        JScrollPane scrollPane = new JScrollPane(panel);
        
        textArea = new JTextArea(text);
        scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setCaretPosition(textArea.getDocument().getLength());
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        this.add(scrollPane);
        
        /*int height = 35;
        int widthMarker = 120;
        int widthName = 170;
        int widthMod = 80;
        int widthDel = 80;
        Font police = new Font("Arial", Font.PLAIN, 24);
        Font policeTitle = new Font("Arial", Font.PLAIN, 30);
        jlTitle = new JLabel(trans.get("modify"));
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
            jbMod[i] = new JButton(trans.get("save"));
            jbDel[i] = new JButton(trans.get("delete"));
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
        
        jbAdd = new JButton(trans.get("add"));
        jbAdd.setFont(police);
        jbAdd.setPreferredSize(new Dimension(450, 50));


        gbc.gridy = i+2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(jbAdd, gbc);*/
    }
    
}