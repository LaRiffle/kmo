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
 
public class InfoHeader extends JPanel {
  
  private JLabel text = new JLabel();
  private JPanel heading = new JPanel();
  
  public InfoHeader(String title){
      this.setLayout(new BorderLayout());
      Font police = new Font("Arial", Font.PLAIN, 50);
      text = new JLabel(title);
      text.setFont(police);
      text.setPreferredSize(new Dimension(490, 100));
      text.setForeground(Color.white);     
 
      heading.setPreferredSize(new Dimension(495, 100));
      heading.setBackground(Color.black);

      heading.add(text);
      this.add(heading);
      //this.add(headingRight, BorderLayout.EAST);
  }
 
  public void paintComponent(Graphics g){
      
    
    g.setColor(Color.black);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    
  }
 
}