import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
 
public class Panneau extends JPanel {
  
  private JLabel textLeft = new JLabel(); // Les zones de textes quis eront affichées
  private JLabel textRight = new JLabel();
  private JPanel headingLeft = new JPanel(); // Les cadres pour harmoniser la mise en page
  private JPanel headingRight = new JPanel();
  
  public Panneau(){
      this.setLayout(new BorderLayout()); // On définit un style de mise en page simple (plus que GridBagLayout())
      Font police = new Font("Arial", Font.PLAIN, 50); // La police est précisée
      textLeft = new JLabel("kMo"); // On remplit la zone de texte
      textLeft.setFont(police); // avec la bonne police
      textLeft.setHorizontalAlignment(JLabel.RIGHT); // On la fige à droite
      textLeft.setPreferredSize(new Dimension(100, 100)); // dans un cadre de bonne taille
      textLeft.setForeground(Color.white); // et avec la bonne couleur
      textRight = new JLabel("    Connexion"); // Idem (les espaces facilitent la mise en page)
      textRight.setFont(police);
      textRight.setHorizontalAlignment(JLabel.LEFT);
      textRight.setPreferredSize(new Dimension(400, 100));
      textRight.setForeground(Color.white);
      headingLeft.setPreferredSize(new Dimension(150, 100)); // On fixe la taille des cadres et leur couleur de fond
      headingLeft.setBackground(Color.black);
      headingRight.setPreferredSize(new Dimension(437, 100));
      headingRight.setBackground(Color.black);
      headingLeft.add(textLeft); // On met le texte dans les cadres
      headingRight.add(textRight);
      this.add(headingLeft, BorderLayout.WEST); // On ajoute les cadres au header
      this.add(headingRight, BorderLayout.EAST);
      //this.add(ecran2);
  }
  public void paintComponent(Graphics g){
    // On remplit le header d'un fond noir global
    g.setColor(Color.black);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    
  }
 
}