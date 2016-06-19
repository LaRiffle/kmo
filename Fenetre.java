
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.text.DateFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;
import kmo.LoginPanel;
import kmo.OpenPanel;
import kmo.OpenHeader;
import kmo.AdminBoardPanel;
import kmo.SettingsPanel;
import kmo.LogsPanel;
import kmo.ShowAccountPanel;
import kmo.ShowAccountHeader;
import kmo.ShowDevicePanel;
import kmo.NewDevicePanel;
import kmo.ConfigureLDAPPanel;
import kmo.RunPanel;
import kmo.InfoHeader;
import kmo.ClosePanel;
import kmo.SignalPanel;
import kmo.ByePanel;

import kmo.Translator;
import kmo.SendMailTLS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import javax.swing.Timer;


 

public class Fenetre extends JFrame {

  /* Les commentaires détaillés sur chaque classe sont présents dans le header Panneau et loginPanel,
    les autres Panel ayant des structures similaires */
  private String user;
  private JPanel loginHeader;
  private LoginPanel loginPanel;
  private OpenHeader openHeader;
  private OpenPanel openPanel;
  private AdminBoardPanel adminBoardPanel;
  private SettingsPanel settingsPanel;
  private ShowAccountPanel showAccountPanel;
  private ShowDevicePanel showDevicePanel;
  private NewDevicePanel newDevicePanel;
  private ShowAccountHeader showAccountHeader;
  private ConfigureLDAPPanel configureLDAPPanel;
  private InfoHeader runHeader;
  private RunPanel runPanel;
  private InfoHeader infoHeader;
  private ClosePanel closePanel;
  private ByePanel  byePanel;
  private InfoHeader byeHeader;
  private SignalPanel signalPanel;
  private LogsPanel logsPanel;
  
  static Translator trans;
  
  public Fenetre(){
    // Petits réglages de la page
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle(trans.get("text.login.heading"));
    this.setSize(600, 600);

    this.loginHeader = new Panneau(); // On charge le header classique (class Panneau)
    this.getContentPane().add(loginHeader, BorderLayout.NORTH); // On l'incorpore au visuel
    this.loginPanel = new LoginPanel(false);  // On charge le corps de la page de login
    loginPanel.getJbLogin().addActionListener(new LoginListener()); // On ajoute à listener au bouton login
    this.getContentPane().add(loginPanel, BorderLayout.CENTER); // On l'incorpore
    this.setVisible(true);   // On affiche

  }
  public static void main(String[] args){
    // on charge le nouveau dictionnaire  
    trans = new Translator();
    // Il faut bien un main pour commencer...
    Fenetre fen = new Fenetre();
    fen.setVisible(true);
    
  }
  
    
  /*** Les listeners ***/
  
  
  class LoginListener implements ActionListener{ // Premier listener, les autres fonctionnent de même
        public void actionPerformed(ActionEvent e) {
            // On récupère les infos rentrés par l'utilisateur
            String userGiven = loginPanel.getJtfLogin().getText();
            String password = loginPanel.getJtfPass().getText();
            if(userGiven.equals("admin") && isAdmin(password)){ // si c'est l'administrateur
                println("admin OK"); // petite sortie console pour suivre le déroulement et on enregistre les sorties consoles dans un fichiers logs.txt pour que le superviseur puisse garder une trace
                loginPanel.removeInfo(); // on enlève le texte saisi (pour la prochaine connexion)
                loginPanel.setVisible(false); // On cache ce panneau
                loginHeader.setVisible(false); // et son header
                openHeader = new OpenHeader(trans.get("text.admin.header")); // On charge le nouvel header
                openHeader.getJbLogout().addActionListener(new LogoutListener()); // et le listener associé à la déconnexion
                getContentPane().add(openHeader, BorderLayout.NORTH); // On l'affiche
                adminBoardPanel = new AdminBoardPanel(); // Charge le nouveau panneau
                adminBoardPanel.getJbAccount().addActionListener(new ShowAccountListener()); // listener admin pour gérer les comptes
                adminBoardPanel.getJbDevice().addActionListener(new ShowDeviceListener()); // listener admin pour gérer les appareils
                adminBoardPanel.getJbSettings().addActionListener(new ShowSettingsListener()); // listener admin pour gérer les réglages globaux
                adminBoardPanel.getJbLogs().addActionListener(new ShowLogsListener()); // listener admin pour gérer les réglages globaux
                getContentPane().add(adminBoardPanel, BorderLayout.CENTER); // on l'affiche
                // On enregistre l'utilisateur : c'est l'admin
                user = "ADMIN";
            } else {
                if(connect(userGiven, password) == 1){ // connect() va regarder si l'utilisateur est enregistré sur la BDD ou le LDAP
                     println("User "+userGiven+" found");// info console plaisir
                     loginPanel.removeInfo();
                     loginPanel.setVisible(false);
                     loginHeader.setVisible(false);
                     openHeader = new OpenHeader(trans.get("text.open.heading"));
                     openHeader.getJbLogout().addActionListener(new LogoutListener());
                     getContentPane().add(openHeader, BorderLayout.NORTH);
                     String[][] data; // On va récupérer les infos correspondant aux appareils manquants pour prévenir l'utilisateur
                     data = missingDevices(); // via cette fonction
                     openPanel = new OpenPanel(data);
                     openPanel.getJbOpen().addActionListener(new OpenListener());
                     getContentPane().add(openPanel, BorderLayout.CENTER);
                     user = userGiven; // on enregistre le nouvelle utilisateur
                } else {
                     loginPanel.setInfo(trans.get("text.login.wrongID")); // Sinon on affiche un message d'erreur : on ne conait pas cet utilisateur
                     println("User "+ userGiven +" not found");
                }
            }
        }
    }
  class LoginTemoinListener implements ActionListener{ // pour le passage de témoin, on clôt la session 
        // précédente et on reprend la nouvelle là où l'ancienne a été laissée, cet donc un listener proche
        // du précédent
        public void actionPerformed(ActionEvent e) {
            String user = loginPanel.getJtfLogin().getText();
            String password = loginPanel.getJtfPass().getText();
            if(user.equals("admin") && password.equals("admin")){
                println("admin OK");
                loginPanel.removeInfo();
                loginPanel.setVisible(false);
                loginHeader.setVisible(false);
                openHeader = new OpenHeader(trans.get("text.admin.header"));
                openHeader.getJbLogout().addActionListener(new LogoutListener());
                getContentPane().add(openHeader, BorderLayout.NORTH);
                adminBoardPanel = new AdminBoardPanel();
                adminBoardPanel.getJbAccount().addActionListener(new ShowAccountListener());
                adminBoardPanel.getJbDevice().addActionListener(new ShowDeviceListener());
                adminBoardPanel.getJbSettings().addActionListener(new ShowSettingsListener());
                getContentPane().add(adminBoardPanel, BorderLayout.CENTER);
            } else {
                if(connect(user, password) == 1){
                     println("User found (temoin)");
                     loginPanel.removeInfo();
                     loginPanel.setVisible(false);
                     runPanel.setVisible(true); // On retourne directement à la page "En cours de réunion" ie runPanel
                     loginHeader.setVisible(false);
                     runHeader.setVisible(true);
                     if(byeHeader != null)
                         byeHeader.setVisible(false);
                } else {
                     loginPanel.setInfo(trans.get("text.login.wrongID"));
                     println("User not found");
                }
            }
        }
    }
  class OpenListener implements ActionListener{ // Lorsqu'on demande à dévérouiller le Top Access
        public void actionPerformed(ActionEvent e) {
            println("Opening...");
            openPanel.setVisible(false);
            openHeader.setVisible(false);
            runHeader = new InfoHeader(trans.get("text.run.header"));
            String[][] data; // On récupère l'état de tous les accessoires pour les afficher durant la réunion
            data = showDevice("nomarker"); // on précise qu'on ne veut pas l'ID RFID des matériels
            int delay = 1000; //milliseconds
            ActionListener taskPerformer = new ActionListener() { 
                public void actionPerformed(ActionEvent evt) {
                    runPanel.setData(data);
                }
            };// on règle le timer pour appeler le rafraichissement des données d'état de matériel au bout de int delay ms
            Timer timer = new Timer(delay, taskPerformer);
            timer.setRepeats(true);
            timer.start();
            ActionListener removeTimer = new ActionListener() { // il faut enlever le timer si l'utilisteur quitte cette page
                public void actionPerformed(ActionEvent evt) {
                    timer.stop();
                    println("End of refreshing data of device...");
                }
            };
            runPanel = new RunPanel(data);
            getContentPane().add(runHeader, BorderLayout.NORTH);
            getContentPane().add(runPanel, BorderLayout.CENTER);
            runPanel.getJbTemoin().addActionListener(removeTimer); // On enlève le timer en quittant la page
            runPanel.getJbTemoin().addActionListener(new TemoinListener());
            runPanel.getJbClose().addActionListener(removeTimer);
            runPanel.getJbClose().addActionListener(new CloseListener());
        }
   }
  class TemoinListener implements ActionListener{ // Simple retour à la pseudo page de connexion pour le passage de témoin
        public void actionPerformed(ActionEvent e) {
            println("Passage de témoin...");
            runPanel.setVisible(false);
            loginPanel = new LoginPanel(true);
            getContentPane().add(loginPanel, BorderLayout.CENTER);
            runHeader.setVisible(false);
            loginHeader.setVisible(true);
            loginPanel.getJbLogin().addActionListener(new LoginTemoinListener());
        }
   }
  class CloseListener implements ActionListener{ // Fin de la réunion, avant de quitter on s'assure que rien ne manque
        public void actionPerformed(ActionEvent e) {
            println("Closing session...");
            runPanel.setVisible(false);
            runHeader.setVisible(false);
            infoHeader = new InfoHeader(trans.get("text.close.header"));
            String[][] data;
            data = missingDevices(); // On récupère les objets manquants
            if(closePanel != null) { // closePanel a déjà été chargé, signifiant qu'il a déjà tenté de fermer
                closePanel.setVisible(false);
                println("Retrying to ranger...");
            }
            closePanel = new ClosePanel(data); // on transmet ce qui manque pour en informer l'utilisateur
            getContentPane().add(infoHeader, BorderLayout.NORTH);
            getContentPane().add(closePanel, BorderLayout.CENTER);
            closePanel.getJbRetry().addActionListener(new CloseListener());
            closePanel.getJbClose().addActionListener(new ByeListener());
        }
   }
  class ByeListener implements ActionListener{ // Affiche la dernière page, où l'on peut déclarer si un matériel 
       // est présent mais défectueux, qui se ferme automatiquement au bout de 20 sec si inaction
        public void actionPerformed(ActionEvent e) {
            println("Disconnecting...");
            closePanel.setVisible(false);
            infoHeader.setVisible(false);
            
            String[][] data;
            data = missingDevices(); // On récupère les objets manquants
            if(data.length != 0){ // on vérifie si des objets manquent, et alors on check si il faut envoyer un rapport de mail
                String mail = sendMailAuthorized();
                if(!mail.equals("false")){
                    println("Missing devices, sending an email to "+ mail +"...");
                    String message = trans.get("text.bye.mail1")+ " " + user;
                    for(int i=0; i<data.length; i++){
                        message += "\n- "+ data[i][0];
                    }
                    message += "\n\n "+trans.get("text.bye.mail2");
                    SendMailTLS.envoyerMail(mail, trans.get("text.bye.mail3"), message);
                    
                } else
                    println("Missing devices, no report.");
            }
            
            byeHeader = new InfoHeader(trans.get("text.bye.header"));
            byePanel = new ByePanel();
            getContentPane().add(byeHeader, BorderLayout.NORTH);
            getContentPane().add(byePanel, BorderLayout.CENTER);
            byePanel.getJbSignal().addActionListener(new SignalListener());
            byePanel.getJbLogout().addActionListener(new LogoutListener());
            int delay = 20000; //milliseconds
            ActionListener taskPerformer = new LogoutListener();// on règle le timer pour appeler le Logout au bout de int delay ms
            Timer timer = new Timer(delay, taskPerformer);
            timer.setRepeats(false);
            timer.start();
            ActionListener removeTimer = new ActionListener() { // Naturellement il faut enlever le timer si l'utilisteur clique sur le bouton Logout ou tout autre d'ailleurs
                public void actionPerformed(ActionEvent evt) {
                    timer.stop();
                    println("Log out pressed...");
                }
            };
            byePanel.getJbLogout().addActionListener(removeTimer); 
        }
   }
  class LogoutListener implements ActionListener{ 
        public void actionPerformed(ActionEvent e) {
            println(user + " logs out...");
            // On vérifie tous les panneaux afin que d'où l'on vienne le dernier soit bien caché, et on affiche celui du login
            loginPanel.getJtfPass().setText("");
            if(byeHeader != null)
                byeHeader.setVisible(false);
            if(byePanel != null)
                byePanel.setVisible(false);
            if(openPanel != null)
                openPanel.setVisible(false);
            if(adminBoardPanel != null)
                adminBoardPanel.setVisible(false);
            loginPanel.setVisible(true);
            if(openHeader != null)
                openHeader.setVisible(false);
            if(infoHeader != null)
                infoHeader.setVisible(false);
            loginHeader.setVisible(true);
            user = "none";
            // on enlève tous les listeners si jamais il ya celui qui a été ajouté avec le passage de témoin qui interfère
            for( ActionListener al : loginPanel.getJbLogin().getActionListeners() ) {
                loginPanel.getJbLogin().removeActionListener( al );
            } // puis on remet le bon
            loginPanel.getJbLogin().addActionListener(new LoginListener());
        }
   }
  class SignalListener implements ActionListener{ // Affiche la page où l'on peut envoyer un rapport de souci particulier à l'admin
        public void actionPerformed(ActionEvent e) {
            println("Show signal page...");
            byeHeader.setVisible(false);
            byeHeader = new InfoHeader(trans.get("text.signal.header"));
            getContentPane().add(byeHeader, BorderLayout.NORTH);
            
            byePanel.setVisible(false);
            signalPanel = new SignalPanel();
            
            getContentPane().add(signalPanel, BorderLayout.CENTER);
            signalPanel.getJbSave().addActionListener(new SaveSignalListener());
        }
    } 
  class SaveSignalListener implements ActionListener{ // Affiche la page où l'on peut envoyer un rapport de souci particulier à l'admin
        public void actionPerformed(ActionEvent e) {
            String message = signalPanel.getTextArea().getText();
            String mail = getAdminMail();
            SendMailTLS.envoyerMail(mail, trans.get("text.signal.mail"), message);
            println("Mail sent to admin");
            println(user + " logs out...");
            byeHeader.setVisible(false);
            loginHeader.setVisible(true);
            signalPanel.setVisible(false);
            loginHeader.setVisible(true);
            user = "none";
            // on enlève tous les listeners si jamais il ya celui qui a été ajouté avec le passage de témoin qui interfère
            for( ActionListener al : loginPanel.getJbLogin().getActionListeners() ) {
                loginPanel.getJbLogin().removeActionListener( al );
            } // puis on remet le bon
            loginPanel.getJbLogin().addActionListener(new LoginListener());
        }
    }
  class ShowSettingsListener implements ActionListener{ // Affiche la page de gestion des paramètres globaux (comme pour les mails)
        public void actionPerformed(ActionEvent e) {
            println("Show settings page...");
            showAccountHeader = new ShowAccountHeader(trans.get("text.admin.header"));
            openHeader.setVisible(false);
            getContentPane().add(showAccountHeader, BorderLayout.NORTH);
            String[][] data;
            data = showSettings(); // récupère les paramètres LDAP dans la BDD
            adminBoardPanel.setVisible(false);
            settingsPanel = new SettingsPanel(data);
            getContentPane().add(settingsPanel, BorderLayout.CENTER);
            showAccountHeader.getJbRetour().addActionListener(new RetourListener());
        }
    }
  class ShowLogsListener implements ActionListener{ // Affiche les logs de l'appli ie l'historique d'activité
        public void actionPerformed(ActionEvent e) {
            println("Show settings page...");
            showAccountHeader = new ShowAccountHeader(trans.get("text.admin.header"));
            openHeader.setVisible(false);
            getContentPane().add(showAccountHeader, BorderLayout.NORTH);
            adminBoardPanel.setVisible(false);
            logsPanel = new LogsPanel();
            getContentPane().add(logsPanel, BorderLayout.CENTER);
            showAccountHeader.getJbRetour().addActionListener(new RetourListener());
        }
    }
  class ShowDeviceListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            println("Show device...");
            adminBoardPanel.setVisible(false);
            showAccountHeader = new ShowAccountHeader(trans.get("text.admin.header"));
            openHeader.setVisible(false);
            getContentPane().add(showAccountHeader, BorderLayout.NORTH);
            String[][] data; // On charge les infos sur les matériels (dont les marker RFID)
            data = showDevice("marker");
            showDevicePanel = new ShowDevicePanel(data);
            getContentPane().add(showDevicePanel, BorderLayout.CENTER);
            showAccountHeader.getJbRetour().addActionListener(new RetourListener());
            showDevicePanel.getJbAdd().addActionListener(new NewDeviceListener());
        }
   }
  class NewDeviceListener implements ActionListener{ // Affiche la page pour ajouter un matériel
        public void actionPerformed(ActionEvent e) {
            showDevicePanel.setVisible(false);
            if(newDevicePanel != null)
                newDevicePanel.setVisible(false);
            newDevicePanel = new NewDevicePanel();
            getContentPane().add(newDevicePanel, BorderLayout.CENTER);
            newDevicePanel.getJbRefresh().addActionListener(new NewDeviceListener());
            newDevicePanel.getJbSave().addActionListener(new AddDeviceListener());
        }
    }
  class AddDeviceListener implements ActionListener{ // On gère l'ajout de ce matériel
        public void actionPerformed(ActionEvent e) {
            String name = newDevicePanel.getJtfName().getText();
            String marker = newDevicePanel.getJlMarker().getText();
            println("Saving device "+marker+":"+name+"...");
            addDevice(marker, name); // l'appel à la focntion d'enregistrement en BDD
            // on rafraichit la page en rechargeant les infos sur les matériels
            println("Show device...");
            newDevicePanel.setVisible(false);
            String[][] data;
            data = showDevice("marker");
            showDevicePanel = new ShowDevicePanel(data);
            getContentPane().add(showDevicePanel, BorderLayout.CENTER);
            showDevicePanel.getJbAdd().addActionListener(new NewDeviceListener());
        }
    }
  class ShowAccountListener implements ActionListener{ // Affiche tous les comptes enregistrés en BDD
        public void actionPerformed(ActionEvent e) {
            println("Show Account...");
            adminBoardPanel.setVisible(false);
            showAccountHeader = new ShowAccountHeader(trans.get("text.admin.header"));
            openHeader.setVisible(false);
            getContentPane().add(showAccountHeader, BorderLayout.NORTH);
            String[][] data;
            data = showUser(); // on charge les infos sur les comptes enregistrés en BDD
            showAccountPanel = new ShowAccountPanel(data);
            getContentPane().add(showAccountPanel, BorderLayout.CENTER);
            showAccountHeader.getJbRetour().addActionListener(new RetourListener());
            showAccountPanel.getJbAdd().addActionListener(new RegisterListener());
            showAccountPanel.getJbConf().addActionListener(new ConfigureLDAPListener());
        }
   }
  class ConfigureLDAPListener implements ActionListener{ // Affiche la page de gestion des paramètres rlatifs au LDAP
        public void actionPerformed(ActionEvent e) {
            String[][] data;
            data = showParamLDAP(); // récupère les paramètres LDAP dans la BDD
            showAccountPanel.setVisible(false);
            configureLDAPPanel = new ConfigureLDAPPanel(data);
            getContentPane().add(configureLDAPPanel, BorderLayout.CENTER);
            showAccountHeader.setVisible(true);
        }
    }
  class RetourListener implements ActionListener{ // Le retour vers une page précédente : on regarde ce qui a été chargé qui témoigne de la progression entre les panneaux et donc du panneau vers lequel rediriger
        public void actionPerformed(ActionEvent e) {
            showAccountHeader.setVisible(false);
            openHeader.setVisible(true);
            if(logsPanel != null)
                logsPanel.setVisible(false);
            if(settingsPanel != null)
                settingsPanel.setVisible(false);
            if(configureLDAPPanel != null)
                configureLDAPPanel.setVisible(false);
            if(showAccountPanel != null)
                showAccountPanel.setVisible(false);
            if(showDevicePanel != null){
                showDevicePanel.setVisible(false);
                println("Retour depuis showDevicePanel");
            }
            if(newDevicePanel != null){
                newDevicePanel.setVisible(false);
                println("Retour depuis newDevicePanel");
            }
            adminBoardPanel.setVisible(true);
        }
    }
  class RegisterListener implements ActionListener{ // Gère l'ajout d'un compte dans la BDD
        public void actionPerformed(ActionEvent e) {
            String user = showAccountPanel.getJtfLogin().getText();
            String password = showAccountPanel.getJtfPass().getText();
            register(user, password); // enregistrement dans la BDD
            // on rafraichit la page
            String[][] data;
            data = showUser();
            showAccountPanel.setVisible(false);
            showAccountPanel = new ShowAccountPanel(data);
            showAccountPanel.getJbAdd().addActionListener(new RegisterListener());
            getContentPane().add(showAccountPanel, BorderLayout.CENTER);
        }
    }
  
  /** Fonction de log **/
  public void println(String text){
    System.out.println(text);
    try{
        //File ff=new File("test/logs.txt"); // définir l'arborescence
        //ff.createNewFile();
        FileWriter ffw=new FileWriter("test/logs.txt", true);
        
        Date aujourdhui = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        ffw.write(shortDateFormat.format(aujourdhui)+ " : "+user+" : "+text);  // écrire une ligne dans le fichier resultat.txt
        ffw.write("\n"); // forcer le passage à la ligne
        ffw.close(); // fermer le fichier à la fin des traitements
    } catch (Exception e) {}
      
  }
  
  /*** Les fonctions d'appel à la BDD et au LDAP ***/
  
  public Statement connectSQL() { // Inscription dans la BDD d'un nouveau compte (géré en SQL)
    try {
        Class.forName("org.postgresql.Driver");
        String url = trans.get("sql.url");
        String bdd_user = trans.get("sql.user");
        String passwd = trans.get("sql.password");
        Connection conn = DriverManager.getConnection(url, bdd_user, passwd);
        //Création et renvoi d'un objet Statement
        return conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);       
    } catch (Exception e) {
       println("ERREUR de connexion à la bdd");
      e.printStackTrace();
    }
    return null;
  }
  public int register(String user, String password) { // Inscription dans la BDD d'un nouveau compte (géré en SQL)
    try {
    Statement state = connectSQL();
    state.executeUpdate("INSERT INTO userkmo (name, password) VALUES ('"+ user +"', '"+ password +"')");
    println("Registered "+ user +" "+password);
    }catch (Exception e) {}
    return 0;
  }
  public int addDevice(String marker, String name) { // ajout d'un nouvel matériel dans la BDD
    try {
    Statement state = connectSQL();
    state.executeUpdate("INSERT INTO device (name, marker, status) VALUES ('"+ name +"', '"+ marker +"', 'Tout juste ajouté')");
    System.out.print("Saved device "+ marker +":"+name);     
    } catch (Exception e) {}
    return 0;
  }
  public String[][] missingDevices() { // Renvoie les matériels avec un statut MISSING
    String[][] data;
    int i = 0;
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM device WHERE status = 'MISSING'");
        //lecture de la longueur des row
        i = 0;
        while(result.next()){i++;}
        data = new String[i][3];
        int j = 0;
        while(result.previous() && j <= i){
           data[j][0] = result.getObject(2).toString();
           data[j][1] = result.getObject(3).toString();
           data[j][2] = result.getObject(4).toString();
           j++;
        }
        result.close();
        return data;
    } catch (Exception e) {}
    return null;
  }
   public String[][] showUser() { // Renvoie les données des utilisateurs
    String[][] data;
    int i = 0;
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM userkmo");
        //lecture de la longueur des row
        i = 0;
        while(result.next()){i++;}
        data = new String[i][2];
        int j = 0;
        while(result.previous() && j <= i){
           data[j][0] = result.getObject(2).toString();
           data[j][1] = result.getObject(3).toString();
           j++;
        }
        result.close();
        return data;
    } catch (Exception e) {}
    return null;
  }
  public String[][] showDevice(String marker) { // Renvoie les données des matériels, avec l'option 'marker' ie l'id RFID de la puce
    String[][] data;
    int i = 0;
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM device");
        //lecture de la longueur des row
        i = 0;
        while(result.next()){i++;}
        if(marker == "marker"){ // on renvoie dans ce cas seulement le marqueur
            data = new String[i][3];
            int j = 0;
            while(result.previous() && j <= i){
               data[j][0] = result.getObject(2).toString();
               data[j][1] = result.getObject(3).toString();
               data[j][2] = result.getObject(4).toString();
               j++;
            }
        } else {
            data = new String[i][2];
            int j = 0;
            while(result.previous() && j <= i){
               data[j][0] = result.getObject(2).toString();
               data[j][1] = result.getObject(4).toString();
               j++;
            }
        }
        result.close();
        return data;
    } catch (Exception e) {}
    return null;
  }
  // PARAMETRES PAR DEFAUT POUR LA CONNEXION AU LDAP POLYTECHNIQUE
  // ou=Etudiants,ou=Utilisateurs,dc=id,dc=polytechnique,dc=edu
  // 389
  // ldap-ens.polytechnique.fr
  public String[][] showParamLDAP() { // Renvoie les paramètres (en particulier LDAP)
    String[][] data;
    int i = 0;
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'serveur' OR name = 'base' OR name = 'port'");
        //lecture de la longueur des row
        i = 0;
        while(result.next()){i++;}
        data = new String[i][2];
        while(result.previous()){
           i--;
           data[i][0] = result.getObject(2).toString();
           data[i][1] = result.getObject(3).toString();
        }
        result.close();
        return data;
    } catch (Exception e) {} 
    return null;
  }
   public String[][] showSettings() { // Renvoie les paramètres (en particulier LDAP)
    String[][] data;
    int i = 0;
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'admin_email' OR name = 'admin_email_password' OR name = 'sendMail' OR name='admin_password'");
        //lecture de la longueur des row
        i = 0;
        while(result.next()){i++;}
        data = new String[i][2];
        while(result.previous()){
           i--;
           data[i][0] = result.getObject(2).toString();
           data[i][1] = result.getObject(3).toString();
        }
        result.close();
        return data;
    } catch (Exception e) {}
    return null;
  }  
           
public String sendMailAuthorized() { // Renvoie si oui ou non on a le droit d'envoyer des mails au factotum
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'sendMail' OR name = 'admin_email'");
        result.next();
        String mail = result.getObject(3).toString();
        result.next();
        if(result.getObject(3).toString().equals("true"))
            return mail;
        return "false";
    } catch (Exception e) {}
    return "false";
  }

public boolean isAdmin(String password) { // Renvoie si oui ou non on a le mot de passe Admin est correct
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'admin_password'");
        result.next();
        String passbdd = result.getObject(3).toString();
        return passbdd.equals(password);
    } catch (Exception e) {}
    return false;
  }
           
public String getAdminMail() { // Renvoie si oui ou non on a le droit d'envoyer des mails au factotum
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'admin_email'");
        result.next();
        String mail = result.getObject(3).toString();
        return mail;
    } catch (Exception e) {}
    return "undefined_error";
  }
  // ou=Etudiants,ou=Utilisateurs,dc=id,dc=polytechnique,dc=edu
  // 389
  // ldap-ens.polytechnique.fr
  public int connect(String user, String password) { // Vérifie si le login et le mdp sont corrects
    try {
        Statement state = connectSQL();
        ResultSet result = state.executeQuery("SELECT * FROM userkmo");
        while(result.next()){         
            if(result.getObject(2).toString().equals(user) && result.getObject(3).toString().equals(password))
                 return 1;
        }
        result.close();
        //Si personne n'a été trougé sur la bdd interne on va chercher sur le LDAP
        // On récupère les paramètres LDAP dans la BDD
        result = state.executeQuery("SELECT * FROM parameters");
        String serverIP = "";
        String base = "";
        String serverPort = "";
        while(result.next()){         
          switch(result.getString("name")){
              case "serveur":
                  //Adresse du serveur sur lequel se trouve l'annuaire LDAP
                  serverIP = result.getString("value");
              break;
              case "base":
                  base = result.getString("value");
              break;
              case "port":
                  //Port du serveur sur lequel se trouve l'annuaire LDAP
                  serverPort = result.getString("value");
              break;       
          }
        }

        result.close();
        //Login de connexion à l'annuaire LDAP : Le login dois être sous forme de "distinguished name"
        //ce qui signifie qu'il doit être affiché sous la forme de son arborescence LDAP
        //String serverLogin = "CN=CARRUESCO Valentin,OU=UTILISATEURS,DC=idleman,DC=fr";
        //Mot de passe de connexion à l'annuaire LDAP
        //String serverPass = "motDePasseSecret";
        //On remplit un tableau avec les parametres d'environement et de connexion au LDAP
        Hashtable environnement = new Hashtable();
        environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environnement.put(Context.PROVIDER_URL, "ldap://"+serverIP+":"+serverPort+"/");
        environnement.put(Context.SECURITY_AUTHENTICATION, "none");
        //environnement.put(Context.SECURITY_PRINCIPAL, serverLogin);
        //environnement.put(Context.SECURITY_CREDENTIALS, serverPass);
        println("Connecting...");
        try {
            //On appelle le contexte à partir de l'environnement
            DirContext contexte = new InitialDirContext(environnement);
            
            LdapContext ctx = null;
            ctx = new InitialLdapContext(environnement, null);
            println("Connection Successful.");
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attrIDs = {"sn",
                            "givenname",
                            "mail"};
            constraints.setReturningAttributes(attrIDs);
            NamingEnumeration answer = ctx.search(base, "sn="+user, constraints);
            if (answer.hasMore()) {
                    Attributes attrs = ((SearchResult) answer.next()).getAttributes();
                    println("Found "+ attrs.get("sn"));
                    return 1;
            }else{
                    println("No data found for "+user);
            }

            state.close();  
            
        } catch (NamingException e) {
            println("Connexion au serveur : ECHEC");
            e.printStackTrace();
        }
         
    } catch (Exception e) {
       println("ERREUR de connexion à la bdd");
      e.printStackTrace();
    }
    
    return 0;
  }
}
