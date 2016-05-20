package kmo;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
 
public class ShowAccountHeader extends JPanel {
  
  private JLabel textLeft = new JLabel();
  private JButton textRight = new JButton();
  private JPanel headingLeft = new JPanel();
  private JPanel headingRight = new JPanel();
  
  public ShowAccountHeader(String title){
      this.setLayout(new BorderLayout());
      Font police = new Font("Arial", Font.PLAIN, 50);
      Font police2 = new Font("Arial", Font.PLAIN, 24);
      textLeft = new JLabel(title);
      textLeft.setFont(police);
      //textLeft.setHorizontalAlignment(JLabel.RIGHT);
      textLeft.setPreferredSize(new Dimension(320, 100));
      textLeft.setForeground(Color.white);  
      textRight = new JButton("Retour");
      textRight.setFont(police2);
      //textRight.setHorizontalAlignment(JLabel.LEFT);
      textRight.setPreferredSize(new Dimension(160, 50));
      //textRight.setForeground(Color.white);
      headingLeft.setPreferredSize(new Dimension(350, 100));
      headingLeft.setBackground(Color.black);
      headingRight.setPreferredSize(new Dimension(180, 100));
      headingRight.setBackground(Color.black);
      //headingLeft.setBorder(BorderFactory.createLineBorder(Color.red));
      //headingRight.setBorder(BorderFactory.createLineBorder(Color.red));
      headingLeft.add(textLeft);
      headingRight.add(textRight);
      this.add(headingLeft, BorderLayout.WEST);
      this.add(headingRight, BorderLayout.EAST);
      //this.add(ecran2);
  }
  public JButton getJbRetour(){
        return textRight;
    }
  public void paintComponent(Graphics g){
      
    
    g.setColor(Color.black);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    
    /*Font font = new Font("Arial", Font.PLAIN, 36);
    g.setFont(font);
    g.setColor(Color.red);          
    g.drawString("kMo     Connexion", 10, 20);*/
    
  }
 
}