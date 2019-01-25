
import javax.swing.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
class Main extends JFrame{
	
	private final static int FRAME_WIDTH=800;
	private final static int FRAME_HEIGHT=800;
	private final static int GAME_WIDTH=700;
	private final static int GAME_HEIGHT=700;
	public JPanel scorePanel, groundPanel, gamePanel;
	public JComponent flyingObjectsComponent;
	public JLabel currentScoreLabel, missileCountLabel, highScoreLabel;
	public JLabel gameVet1, gameVet2, gameVet3, gameVet4, gameVet5;
	public String[] highestFive = new String[5];
	public static HighScore highScore = new HighScore();
	//-----------------------------------------
	public Main() throws IOException{
			
		setTitle("Mission Control 2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);
		
		highestFive = highScore.getHighScores();
		
		// flying objects
		flyingObjectsComponent = new FlyingObjects();
		
		// game
		gamePanel = new JPanel();
		gamePanel.setBackground(Color.BLACK);
		gamePanel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gamePanel.setOpaque(false);
		add(gamePanel);
		
		// ground
		groundPanel = new JPanel();
		groundPanel.setBackground(Color.GREEN);
		groundPanel.setBounds(0, 700, 800, 75);
		groundPanel.setOpaque(true);
		
		// score panel
		scorePanel = new JPanel();
		scorePanel.setBackground(Color.RED);
		scorePanel.setBounds(700,0,100,800);
		scorePanel.setOpaque(true);
		highScoreLabel = new JLabel("\nHigh Scores:");
		gameVet1 = new JLabel(highestFive[0]);
		gameVet2 = new JLabel(highestFive[1]);
		gameVet3 = new JLabel(highestFive[2]);
		gameVet4 = new JLabel(highestFive[3]);
		gameVet5 = new JLabel(highestFive[4]);
		scorePanel.add(highScoreLabel);
		scorePanel.add(gameVet1);
		scorePanel.add(gameVet2);
		scorePanel.add(gameVet3);
		scorePanel.add(gameVet4);
		scorePanel.add(gameVet5);
		// add score
		add(scorePanel);
		
		// add ground
		add(groundPanel);

		// add flying objects
		add(flyingObjectsComponent);
		
		pack();
		setVisible(true);
	}
	//---------------------------------------
	public static void main(String[] args){
		
		try {
			new Main();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

