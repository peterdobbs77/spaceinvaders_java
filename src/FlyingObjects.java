
import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
class FlyingObjects extends JComponent{
	
	public int lastX1=0;
	public int lastY =0;
	public int lastX2=0;
	public int startX = 10;
	public int startY = 10;
	public int baseX = 150;
	public int baseY = 625;
	//
	public int bombX = 0;
	public int bombY = 0;
	public double bombSpeed;
	//
	public int baseNumber;
	//
	public int xCoord, yCoord;
	//
	public boolean base1Exists;
	public boolean base2Exists;
	public boolean base3Exists;
	//
	public String base1Message = "Base Operational";
	public String base2Message = "Base Operational";
	public String base3Message = "Base Operational";
	//
	public int base1Missiles=9;
	public int base2Missiles=6;
	public int base3Missiles=9;
	//
	public Missile missile;
	public Missile missile2;
	public PinkSpaceShip pinkSS;
	public OrangeSpaceShip orangeSS;
	//
	public MListener mouseListener;
	//
	public Thread animationThread;
	//
	public Color color1 = Color.BLUE;
	public Color color2 = Color.BLUE;
	public Color color3 = Color.BLUE;
	public Color scoreColor = new Color(20,57,96);
	// Fonts
	public Font font;
	public FontMetrics fontMetrics;
	//
	public int bases = 3;
	//
	public int missileCount;
	public static int score=0;
	public int lvl;	// (level)
//	public int lol=0; // (loss of life)
	//
	public double spaceship1Speed=2, spaceship2Speed=3;
	//
	private final static int BOMB_SIZE=20;
	//-----------------------------------------
	public FlyingObjects(){
		
		setBounds(0,0,700,700);
		setOpaque(false);
		
		base1Exists=true;
		base2Exists=true;
		base3Exists=true;
		
		missileCount=base1Missiles+base2Missiles+base3Missiles;
		
		animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    repaint();
                    try {Thread.sleep(150);} catch (Exception ex) {}
                }
            }
        });
        animationThread.start();
        
        bombSpeed = .005;
        missile = new Missile(xCoord, yCoord, baseX);
        
        pinkSS = new PinkSpaceShip();
        orangeSS = new OrangeSpaceShip();
        
        font = new Font("Gautami", Font.BOLD, 16);
		fontMetrics = getFontMetrics(font);
		
        mouseListener = new MListener();
        addMouseListener(mouseListener);
        
	}
	//------------------------------------
	public void paintComponent(Graphics g){
		
		createScoreBoard(g);
		
		// bases
		g.setColor(color1);
		g.fillRect(125, 625, 50, 75);
		g.setColor(color2);
		g.fillRect(325, 625, 50, 75);
		g.setColor(color3);
		g.fillRect(525, 625, 50, 75);
		
		// create gray bomb
        greyBomb(g);
        
        // magenta missile
        missile.magentaMissile(g);
		
        //create orange spaceship
        if (lvl>=2){
        	orangeSS.orangeSpaceShip(g);
        	if(missile.missileX>orangeSS.xCoord && missile.missileX<orangeSS.xCoord+orangeSS.orangeWidth){
        		if(missile.missileY<orangeSS.yCoord+2*orangeSS.orangeHeight&&missile.missileY>orangeSS.yCoord-orangeSS.orangeHeight){
        			missileOrangeCollision(g, 30);
        		}
        	}
        	if (lvl>=3) orangeSS.greyBomb(g);
        }
        
        //create pink spaceship
        if (lvl>=3){
        	pinkSS.pinkSpaceship(g);
        	if(missile.missileX+20>=pinkSS.xCoord && missile.missileX<=pinkSS.xCoord+pinkSS.pinkW){
        		if(missile.missileY<pinkSS.yCoord+2*pinkSS.pinkH&&missile.missileY>pinkSS.yCoord-pinkSS.pinkH){
        			missilePinkCollision(g, 30);
        		}
        	}
        }
        
        // missile collision with bomb
        int r = 40; // explosion radius
    	// if the distance between missile and bomb is less that the explosion radius...
		if(Math.sqrt(Math.pow(missile.missileX-bombX,2)+Math.pow(missile.missileY-bombY,2))<=r)
			missileBombCollision(g, r);
       
		// bomb collision with base
        if (bombY>=625){
        	baseCollision(g);
        }
        
        // bomb collision with ground
        if (bombY>=700) groundCollision(g);
        
        // check game progress
        gameProgress(g);
	}
	//---------------------------------------
	public void createScoreBoard(Graphics g){
		
		g.setColor(scoreColor);
		g.setFont(font);
		g.drawString("Score:    "+score, 575, 25);
		g.drawString("Missiles: "+missileCount, 575, 50);
		g.drawString("Level:    "+lvl, 575, 75);
//		g.drawString("Loss of Life: "+lol, 575, 100);
		g.setColor(new Color(200,86,25));
		g.setFont(new Font("Gautami", Font.BOLD, 12));
		g.drawString(base1Message, 175, 650);
		g.drawString("Missiles: "+base1Missiles, 175, 675);
		g.drawString(base2Message, 375, 650);
		g.drawString("Missiles: "+base2Missiles, 375, 675);
		g.drawString(base3Message, 575, 650);
		g.drawString("Missiles: "+base3Missiles, 575, 675);
	}
	//--------------------------------------
	public void createMessage(String message, Graphics g){
		
		g.setColor(scoreColor);
		g.setFont(font);
		g.drawString(message, 325, 325);
	}
	//---------------------------------------
	public void greyBomb(Graphics g){
		
		double bombDX=(baseX-startX)*(bombSpeed);	// bomb change in x
		double bombDY=(baseY-startY)*(bombSpeed);	// bomb change in y
		g.setColor(Color.DARK_GRAY);
		g.fillOval(bombX-10, bombY-10, BOMB_SIZE, BOMB_SIZE);
		bombX += bombDX;	// changing bomb x location
		bombY += bombDY;	// changing bomb y location
		if (bombX>getWidth()-25||bombY>getHeight()-25){
			Random r = new Random();
			startX=r.nextInt(650)+25;
			baseNumber = r.nextInt(bases);
			// fire at base 1 if it exists
			if (baseNumber==0&&base1Exists==true) baseX = 150;
			else if(base2Exists==true) baseX=350;
			else if(base3Exists==true) baseX=550;
			// fire at base 2 if it exists
			if (baseNumber==1&&base2Exists==true) baseX = 350;
			else if(base1Exists==true) baseX=150;
			else if(base3Exists==true) baseX=550;
			// fire at base 3 if it exists
			if (baseNumber==2&&base3Exists==true) baseX = 550;
			else if(base1Exists==true) baseX=150;
			else if(base2Exists==true) baseX=350;
			bombX=startX;
			bombY=0;
		}
	}
	//--------------------------------------
	public void missileBombCollision(Graphics g, int r){
		
		// explode at collision
		g.setColor(Color.RED);
		g.fillOval(missile.missileX-r, missile.missileY-r, r*2, r*2);
		score+=100;
		//
		missile = new Missile();
		//
		// creates new bomb
		Random rand = new Random();
		startX=rand.nextInt(650)+25;
		baseNumber = rand.nextInt(bases);
		// fire at base 1 if it exists
		if (baseNumber==0&&base1Exists==true) baseX = 150;
		else if(base2Exists==true) baseX=350;
		else if(base3Exists==true) baseX=550;
		// fire at base 2 if it exists
		if (baseNumber==1&&base2Exists==true) baseX = 350;
		else if(base1Exists==true) baseX=150;
		else if(base3Exists==true) baseX=550;
		// fire at base 3 if it exists
		if (baseNumber==2&&base3Exists==true) baseX = 550;
		else if(base1Exists==true) baseX=150;
		else if(base2Exists==true) baseX=350;
		//
		bombX=startX;
		bombY=0;
	}
	//----------------------------------------
	public void missileOrangeCollision(Graphics g, int r){
		g.setColor(Color.YELLOW);
		g.fillOval(missile.missileX-r, missile.missileY-r, r*2, r*2);
		score+=200;
		orangeSS.xCoord = -100;	
		//
		missile = new Missile();
	}
	//-----------------------------------------
	public void missilePinkCollision(Graphics g, int r){
		g.setColor(Color.YELLOW);
		g.fillOval(missile.missileX-r, missile.missileY-r, r*2, r*2);
		score+=300;
		pinkSS.xCoord = -100;
		//
		missile = new Missile();
	}
	//----------------------------------------
	public void baseCollision(Graphics g){		
       	if (bombX>125&&bombX<175){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	color1 = Color.WHITE;
           	base1Missiles=0;
           	base1Message = "Base Not Operational";
           	base1Exists=false;
//          lol+=100;
       	}
       	if (bombX>325&&bombX<375){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	color2 = Color.WHITE;
           	base2Missiles=0;
           	base2Message = "Base Not Operational";
           	base2Exists=false;
//          lol+=100;
       	}
       	if (bombX>525&&bombX<575){
       		g.setColor(Color.ORANGE);
           	g.fillOval(bombX-50, bombY-50, 100, 100);
           	color3 = Color.WHITE;
           	base3Missiles=0;
           	base3Message = "Base Not Operational";
           	base3Exists=false;
//          lol+=100;
       	}
	}
	//------------------------------------------
	public void groundCollision(Graphics g){
		g.setColor(Color.ORANGE);
		g.fillOval(bombX-50, bombY-50, 100, 100);
	}
	//--------------------------------
	@SuppressWarnings("deprecation")
	public void gameProgress(Graphics g){
		
		if (base1Missiles<0){
			base1Missiles=0;
			color1 = Color.WHITE;
			base1Message = "Base Not Operational";
			base1Exists=false;
//			lol+=100;
		}
		//
		if (base2Missiles<0){
			base2Missiles=0;
			color2=Color.WHITE;
			base2Message = "Base Not Operational";
			base2Exists=false;
//			lol+=100;
		}
		//
		if (base3Missiles<0){
			base3Missiles=0;
			color3=Color.WHITE;
			base3Message = "Base Not Operational";
			base3Exists = false;
//			lol+=100;
		}
		//
		if (base1Exists==false&&base2Exists==false&&base3Exists==false){
			animationThread.stop();
			createMessage("You Lose", g);
			Main.highScore.createNameWindow();
		}
		//
		if (missileCount<0){
			animationThread.stop();
			createMessage("You Lose", g);
			Main.highScore.createNameWindow();
		}
		//
		if (score==lvl*700&&score<=2100){
			lvl++;
			bombSpeed+=.01;
			if (base1Exists==true) base1Missiles=9;
			if (base2Exists==true) base2Missiles=6;
			if (base3Exists==true) base3Missiles=9;
			missileCount=base1Missiles+base2Missiles+base3Missiles;
			spaceship1Speed++;
			spaceship2Speed++;
//			lol-=100;
		}
		//
		if(score>=2100&&score%800==0){
			if (base1Exists==true) base1Missiles=9;
			if (base2Exists==true) base2Missiles=6;
			if (base3Exists==true) base3Missiles=9;
			missileCount=base1Missiles+base2Missiles+base3Missiles;
			spaceship1Speed+=.5;
			spaceship2Speed+=.5;
//			lol-=100;
		}
	}
	//---------------------------------
	class MListener implements MouseListener{
		
		public void mouseClicked(MouseEvent e){
			
			missile = new Missile(e.getX(), e.getY(), baseX);
			if (baseX==150) base1Missiles--;
			if (baseX==350) base2Missiles--;
			if (baseX==550) base3Missiles--;
			missileCount--;
		}
		@Override
		public void mouseEntered(MouseEvent e){	
		}
		@Override
		public void mouseExited(MouseEvent e){	
		}
		@Override
		public void mousePressed(MouseEvent e){	
		}
		@Override
		public void mouseReleased(MouseEvent e){
		}
	}
}
