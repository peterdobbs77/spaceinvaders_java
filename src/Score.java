
public class Score {

	public int points;
	public String name;
	
	public Score(String row){
		String[] split = row.split(" ");
		points = Integer.parseInt(split[0]);
		name = split[1];
	}
	
	public String score2String(){
		String output = (this.name + ": " + this.points);
		return output;
	}
}
