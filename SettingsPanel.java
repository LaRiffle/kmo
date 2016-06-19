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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;


/**
 *
 * @author Tleffyr
 */
public class SettingsPanel extends JPanel {
    
    private JLabel jlTitle;
    private JTextField[] jtfValue;
    private JLabel[] jlName;
    private JButton[] jbSave;
    private JCheckBox checkMail;
    private static Translator trans;
    
    public SettingsPanel(String[][] data) {
        trans = new Translator();
        trans.setContext("text.settings");
        int height = 35;
        int width = 120;
        int widthValue = 250;
        int widthName = 180;
        Font police = new Font("Arial", Font.PLAIN, 18);
        Font police2 = new Font("Arial", Font.PLAIN, 24);
        jlTitle = new JLabel(trans.get("title"));
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
        
        JPanel checkMailPanel = new JPanel();
        checkMail = new JCheckBox(trans.get("checkMail"));
        checkMail.setFont(police);
        checkMailPanel.setPreferredSize(new Dimension(550, height));
        
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.ipady = 10; gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 1;  gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(titlePanel, gbc);
        
        int i;
        String mailEnabled ="";
        System.out.println("Printing parameters...");
        for(i = 0; i<data.length; i++){
            if(data[i][0].equals("sendMail")){
                mailEnabled = data[i][1];      
            }else {
            jlName[i] = new JLabel();
            jtfValue[i] = new JTextField();
            jbSave[i] = new JButton(trans.get("save"));
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
            jlName[i].setText(trans.get("name."+data[i][0]));
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
            
        }
        gbc.gridy = i+1;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        checkMailPanel.add(checkMail);
        panel.add(checkMailPanel, gbc);
        checkMail.addActionListener(new SaveParamListener("sendMail", checkMail.isSelected()));
        //System.out.print(data[i][1]);
        if(mailEnabled.equals("true")){
            checkMail.setSelected(true);
        }
        panel.setPreferredSize(new Dimension(555, (60*(i+1))));
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
        public SaveParamListener(String name, boolean checked){
            this.id = -1;      
            this.name = name;
            if(checked)
                this.value = "true";
            else
                this.value = "false";
        }
        
        public void actionPerformed(ActionEvent e) {
            if(id != -1)
                this.value = jtfValue[id].getText();
            else{
                if(((JCheckBox)e.getSource()).isSelected())
                    this.value = "true";
                else
                    this.value = "false";
            }
                
            System.out.print("Saving "+this.name+"="+this.value+"...");
            
            if(id != -1)
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
            if(this.name.equals("admin_password")){// si chgt de mdp admin, on envoie un mail de confirmation pour éviter les pb
                System.out.println("Sending conf...");
                state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'sendMail' OR name = 'admin_email'");
                result.next();
                String mail = result.getObject(3).toString();
                String message = trans.get("mail1");
                String objet = trans.get("mail2");
                SendMailTLS.envoyerMail(mail, objet, message);
                System.out.println("Mail of confirmation sent to "+mail);
            }

            } catch (Exception err) {
              System.out.println("ERREUR de connexion à la bdd");
             err.printStackTrace();
            }
        }
   }
}
