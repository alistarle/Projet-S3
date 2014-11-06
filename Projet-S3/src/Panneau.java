import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
 
public class Panneau extends JPanel { 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
public void paintComponent(Graphics g){
	
    g.setColor(Color.black);
    
    // Abscisse
    g.drawLine(50,650,650,650);
    
    // Ordonnee
    g.drawLine(50,650,50,50);
       

  }               


	public void affSommet (Sommet s, Graphics g) {
		int x = (int) s.x;
		int y = (int) s.y;
		g.drawLine(x, y, x, y);
	}
	
	public void paint(Sommet s, Sommet ss, Graphics g) {
		int x = (int) s.x;
		int y = (int) s.y;
		int x2 = (int) ss.x;
		int y2 = (int) ss.y;
		g.setColor(Color.green);
		
		g.drawLine(x,y,x2,y2);
		System.out.println("affSegment est appelle");
				
	}

}