
import java.awt.Color;
import java.awt.Graphics;

//class for creating missiles
class Missile {	
	
	private int xCoord, yCoord;
	public int missileX;
	public int missileY;
	public double missileSpeed;
	private final static int MISSILE_SIZE=20;
	//----------------------
	public Missile(){
		xCoord = -100;
		yCoord = -100;
		missileX=-100;
		missileY=-100;
		missileSpeed = .15;
	}
	//----------------------
	public Missile(int x, int y, int baseX){
		
		xCoord = x;
		yCoord = y;
		missileX=baseX;
        missileY=625;
        missileSpeed = .15;
	}
	//----------------------
	public void magentaMissile(Graphics g){
		
		double missileDX = (missileX-xCoord)*(missileSpeed);
        double missileDY = (missileY-yCoord)*(missileSpeed);
        g.setColor(Color.MAGENTA);
        g.fillOval(missileX, missileY, MISSILE_SIZE, MISSILE_SIZE);
        missileX-=missileDX;
        missileY-=missileDY;
	}
	//-----------------------
	public int getXCoord(){
		return xCoord;
	}
	//----------------------
	public int getYCoord(){
		return yCoord;
	}
}
