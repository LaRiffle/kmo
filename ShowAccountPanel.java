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
public class ShowAccountPanel extends JPanel {
    
    private JButton jbConf = new JButton("Configurer LDAP");
    //private JLabel jlInfo = new JLabel("<html><table><tr><td>Nom</td><td>Jacques</td><td>Suppr.</td></tr><tr><td>Nom</td><td>Modifier</td><td>Suppr.</td></tr></table></html>", 0);
    
    private String labelLogin = "Login";
    private String labelPass = "Mot de passe";

    private JTextField jtfLogin = new JTextField(labelLogin);
    private JTextField jtfPass = new JTextField(labelPass);
    private JButton jbAdd = new JButton("Ajouter");
    private JLabel jlLabel = new JLabel("Le compte a été supprimé");
    
    private JLabel[] jlLogin;
    private JLabel[] jlPassword;
    private JButton[] jbMod;
    private JButton[] jbDel;
    private JPanel panel = new JPanel();
        
    public ShowAccountPanel(String[][] data) {
        Font police = new Font("Arial", Font.PLAIN, 30);
        Color labelColor = new Color (128, 128, 128);
	jtfLogin.setFont(police);
        jtfLogin.setForeground(labelColor);
        jtfPass.setFont(police);
        jtfPass.setForeground(labelColor);
        jbAdd.setFont(police);
        jbConf.setFont(police);
        jtfLogin.setPreferredSize(new Dimension(150, 50));
        jtfPass.setPreferredSize(new Dimension(150, 50));
        jbAdd.setPreferredSize(new Dimension(150, 50));
        jbConf.setPreferredSize(new Dimension(450, 50));
        
        
        this.setPreferredSize(new Dimension(500, 500));
        //On définit le layout manager
        panel.setLayout(new GridBagLayout());		
        //L'objet servant à positionner les composants
        GridBagConstraints gbc = new GridBagConstraints();

        //Espace au dessus et au dessous
        gbc.ipady = 10;
        //On positionne la case de départ du composant
        gbc.gridx = 0;
        gbc.gridy = 0;
        //La taille en hauteur et en largeur
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.gridx = 0;	
        panel.add(jtfLogin, gbc);
        
        gbc.gridx = 1;
        panel.add(jtfPass, gbc);
        
        //Cette instruction informe le layout que c'est une fin de ligne
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 2;
        panel.add(jbAdd, gbc);
        
        JPanel[] loginPanel;
        loginPanel = new JPanel[data.length];
        JPanel[] passwordPanel;
        passwordPanel = new JPanel[data.length];
        jlLogin = new JLabel[data.length];
        jlPassword = new JLabel[data.length];
        jbMod = new JButton[data.length];
        jbDel = new JButton[data.length];
        //loginPanel.setPreferredSize(new Dimension(150, 50));
        //passwordPanel.setPreferredSize(new Dimension(150, 50));
        //loginPanel.add(jlLogin);
        //passwordPanel.add(jlPassword);
        //delPanel.add(jbDel);
        int i;
        for(i = 0; i<data.length; i++){
            
            jlLogin[i] = new JLabel();
            jlPassword[i] = new JLabel();
            jbMod[i] = new JButton("Modifier");
            jbDel[i] = new JButton("Supprimer");
            jlLogin[i].setPreferredSize(new Dimension(150, 50));
            jlPassword[i].setPreferredSize(new Dimension(150, 50));
            jbMod[i].setPreferredSize(new Dimension(150, 50));
            jbDel[i].setPreferredSize(new Dimension(150, 50));
            jlLogin[i].setFont(police);
            jlPassword[i].setFont(police);
            jbMod[i].setFont(police);
            jbDel[i].setFont(police);
            
            
            loginPanel[i] = new JPanel();
            passwordPanel[i] = new JPanel();
            loginPanel[i].setPreferredSize(new Dimension(150, 50));
            passwordPanel[i].setPreferredSize(new Dimension(150, 50));
            loginPanel[i].add(jlLogin[i]);
            passwordPanel[i].add(jlPassword[i]);
            System.out.println("printing "+data[i][0]+" "+data[i][1]);
            gbc.gridwidth = 1;
            gbc.gridy = i+1;
            gbc.gridx = 0;
            jlLogin[i].setText(data[i][0]);
            panel.add(loginPanel[i], gbc);

            gbc.gridx = 1;
            jlPassword[i].setText(data[i][1]);
            panel.add(passwordPanel[i], gbc);

            //Cette instruction informe le layout que c'est une fin de ligne
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 2;
            panel.add(jbDel[i], gbc);
            
            jbDel[i].addActionListener(new RemoveUserListener(i, data[i][0]));
        }
        
        panel.setPreferredSize(new Dimension(500, (60*(i+1))));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        //scrollPane.setBounds(50, 30, 300, 50);
        this.add(scrollPane);
        gbc.gridy = i+2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;	
        this.add(jbConf, gbc);
       
        
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
        jtfPass.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent ke)
            {
                if(!(ke.getKeyChar()==27||ke.getKeyChar()==65535))//this section will execute only when user is editing the JTextField
                {
                    if(jtfPass.getText().equals(labelPass)){
                        jtfPass.setText("");
                        jtfPass.setForeground(Color.BLACK);
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
                    }
                }
            }
        });
    }
    class RemoveUserListener implements ActionListener{
        private int id;
        private String user;
        
        public RemoveUserListener(int id, String user){
            this.id = id;      
            this.user = user;
        }
        
        public void actionPerformed(ActionEvent e) {
            System.out.print("Deleting "+this.user+"...");
            //jlLogin[id].setVisible(false);
            jlPassword[id].setText("Supprimé");
            jbDel[id].setVisible(false);
            
            /*jlLabel.setText("whala dhkdshdklnillhhl");
            JPanel jpLabel = new JPanel();
            jpLabel.setPreferredSize(new Dimension(150, 50));
            jpLabel.add(jlLabel);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = id+1;
            gbc.gridx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            panel.add(jpLabel, gbc);*/
            try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Ecole";
            String bdd_user = "postgres";
            String passwd = "namibia";

            Connection conn = DriverManager.getConnection(url, bdd_user, passwd);
            Statement state = conn.createStatement();
            state.executeUpdate("DELETE FROM userkmo WHERE name='"+user+"'");
            System.out.println(" Done");

            } catch (Exception err) {
              System.out.println("ERREUR de connexion à la bdd");
             err.printStackTrace();
           }
        //System.out.println("Delete...");
            /*String user = showAccountPanel.getJtfLogin().getText();
            String password = showAccountPanel.getJtfPass().getText();
            register(user, password);
            // on rafraichit la page
            String[][] data;
            data = showUser();
            showAccountPanel = new ShowAccountPanel(data);
            showAccountPanel.getJbAdd().addActionListener(new RegisterListener());
            getContentPane().add(showAccountPanel, BorderLayout.CENTER);*/
        }
   }
    public JButton getJbConf(){
        return jbConf;
    }
    public JButton getJbAdd(){
        return jbAdd;
    }
    public JTextField getJtfLogin(){
        return jtfLogin;
    }
    public JTextField getJtfPass(){
        return jtfPass;
    }
}