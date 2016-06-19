/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	
    public static void envoyerMail(String dest, String object, String text) {

    try {
        //on récupère le mail admin comme expéditeur
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/Ecole";
        String bdd_user = "postgres";
        String passwd = "namibia";

        Connection conn = DriverManager.getConnection(url, bdd_user, passwd);

        //Création d'un objet Statement, lecture dans les deux sens + mod ok
        Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet result = state.executeQuery("SELECT * FROM parameters WHERE name = 'admin_email'");
        result.next();
        final String username = result.getObject(3).toString();
        result = state.executeQuery("SELECT * FROM parameters WHERE name = 'admin_password'");
        result.next();
        final String password = result.getObject(3).toString();
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
		}
	});

	try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(dest));
            message.setSubject(object);
            message.setText(text);
                        
            Transport.send(message);
            System.out.println("Email sent to "+dest);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    } catch (Exception e) {
       System.out.println("ERREUR de connexion à la bdd");
      e.printStackTrace();
    }
		

		
    }
}
