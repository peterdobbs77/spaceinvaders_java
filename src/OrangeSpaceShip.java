
import java.awt.*;
import java.util.Random;

public class OrangeSpaceShip {	// flying object that drops bombs from a much closer distance to ground.
	
	public int xCoord = 0; 
	public int yCoord = 300;
	public double orangeSpeed = 5;
    int orangeWidth = 50;
    int orangeHeight = 20;
    int bombX=xCoord;
    int bombY=300;
    int endX=150;
    int endY=650;
    int baseNumber;
    int bases=3;
    private static final int BOMB_SIZE = 20;
    //------------------------
    public OrangeSpaceShip(){
    	
    }
    public void orangeSpaceShip(Graphics g){
	    g.setColor(Color.ORANGE);
	    g.fillOval(xCoord, yCoord, orangeWidth, orangeHeight);
	    xCoord += orangeSpeed;
	}
    //-------------------------
    public void greyBomb(Graphics g){
    	double bombDX=(xCoord-5)*(orangeSpeed/64);
    	double bombDY=(yCoord-5)*(orangeSpeed/64);
    	g.setColor(Color.DARK_GRAY);
		g.fillOval(bombX-10, bombY-10, BOMB_SIZE, BOMB_SIZE);
		bombX+=bombDX;
		bombY+=bombDY;
		if (bombX>675||bombY>675){
			Random r = new Random();
			baseNumber = r.nextInt(bases);
			FlyingObjects flyingObjects = new FlyingObjects();
			// fire at base 1 if it exists
			if (baseNumber==0&&flyingObjects .base1Exists==true) endX = 150;
			else if(flyingObjects.base2Exists==true) endX=350;
			else if(flyingObjects.base3Exists==true) endX=550;
			// fire at base 2 if it exists
			if (baseNumber==1&&flyingObjects.base2Exists==true) endX = 350;
			else if(flyingObjects.base1Exists==true) endX=150;
			else if(flyingObjects.base3Exists==true) endX=550;
			// fire at base 3 if it exists
			if (baseNumber==2&&flyingObjects.base3Exists==true) endX = 550;
			else if(flyingObjects.base1Exists==true) endX=150;
			else if(flyingObjects.base2Exists==true) endX=350;
			bombX=xCoord;
			bombY=300;
		}
    }
    //----------------------------------
    public void baseCollision(Graphics g){
    	FlyingObjects flyingObjects = new FlyingObjects();
    	if (bombX>125&&bombX<175){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	flyingObjects.color1 = Color.WHITE;
           	flyingObjects.base1Missiles=0;
           	flyingObjects.base1Message = "Base Not Operational";
           	flyingObjects.base1Exists=false;
       	}
       	if (bombX>325&&bombX<375){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	flyingObjects.color2 = Color.WHITE;
           	flyingObjects.base2Missiles=0;
           	flyingObjects.base2Message = "Base Not Operational";
           	flyingObjects.base2Exists=false;
       	}
       	if (bombX>525&&bombX<575){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	flyingObjects.color3 = Color.WHITE;
           	flyingObjects.base3Missiles=0;
           	flyingObjects.base3Message = "Base Not Operational";
           	flyingObjects.base3Exists=false;
       	}
    }
}

