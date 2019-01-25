
import java.awt.Color;
import java.awt.Graphics;

public class PinkSpaceShip {

	public int xCoord = 0;
	public int yCoord = 450;
	public int pinkW = 75;
	public int pinkH = 10;
	public double pinkSpeed = 10;
	//--------------------------------
	public void pinkSpaceship(Graphics g){
		
        g.setColor(Color.PINK);
        g.fillOval(xCoord, yCoord, pinkW, pinkH);
        xCoord += pinkSpeed;
	}
}

