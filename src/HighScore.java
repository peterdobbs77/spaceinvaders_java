
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class HighScore implements ActionListener {

	String playerName;
	JFrame nameWindow = new JFrame();
	JPanel panel = new JPanel();
	JLabel instructions = new JLabel("Enter Name");
	JTextField nameField = new JTextField();
	JButton addButton = new JButton("Add Score");
	public ArrayList<Score> scoreLogBig2Small = new ArrayList<Score>();
	
	public void createNameWindow(){
		//comment comment comment comment
		addButton.addActionListener(this);
		nameWindow.setTitle("Score Logging");
		nameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nameWindow.setPreferredSize(new Dimension(500, 300));
		panel.setBackground(Color.CYAN);
		panel.setBounds(0, 0, 500, 300);
        panel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        panel.setLayout(new GridLayout(3,1));
		panel.setOpaque(true);
		panel.add(instructions);
		panel.add(nameField);
		panel.add(addButton);
		nameWindow.add(panel);
		
		nameWindow.pack();
		nameWindow.setVisible(true);
	}
	
	//comment comment comment comment
	public String[] getHighScores() throws IOException{
		scoreLogBig2Small.add(0, new Score(Integer.MAX_VALUE + " filler"));
		Scanner scan = new Scanner(new File("scorelog.txt"));
		while(scan.hasNext()){
			Score score = new Score(scan.nextLine());
			boolean added = false;
			for(int i = 0; i<scoreLogBig2Small.size(); i++){
				if(score.points>scoreLogBig2Small.get(i).points){
					scoreLogBig2Small.add(i, score);
					added = true;
					break;
				}
			}
			if(!added) scoreLogBig2Small.add(score);
		}
		scoreLogBig2Small.remove(0);
		
		String[] highestFive = new String[5];
		for(int i = 0; i< highestFive.length; i++){
			highestFive[i] = scoreLogBig2Small.get(i).score2String();
		}		//comment comment comment comment
		scan.close();
		return highestFive;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playerName = nameField.getText();
		try {
			addScoreToLog();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		nameWindow.dispose();
	}
	
	public void addScoreToLog() throws IOException{
		ArrayList<String> logContents = new ArrayList<String>();
		String logFilePath = "C:\\Users\\Peter\\Documents\\EECE1610\\Final Project\\scorelog.txt";
		Scanner scan = new Scanner(new File(logFilePath));
		
		while(scan.hasNextLine()){
			logContents.add(scan.nextLine());
		}
		logContents.add(FlyingObjects.score + " " + playerName);
		scan.close();
		//comment comment comment comment
		PrintWriter writer = new PrintWriter("scorelog.txt");
		for(int i = 0; i<logContents.size(); i++){
			writer.println(logContents.get(i));
		}
		
		//comment comment comment comment
		writer.close();
	}
}

