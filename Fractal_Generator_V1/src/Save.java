import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Save {

	int numberOfUnamedFractal;
	String textFilePath = ".\\SavedFractal.txt";

	String[] SavedFractal;
	SettingsPanel Spanel;
	
	private void addStringToTextFile(String s) {
		try {
			String test = textFileToString();
			FileWriter writer = new FileWriter(textFilePath);
			writer.write(test);
			writer.append(s);
			writer.close();
		} catch (Exception e) {}
	}
	
	private String textFileToString() {
		String s = "";
		try {
			FileReader reader = new FileReader(textFilePath);
			int data = reader.read();
			while(data != -1) {
				s+=(char)data;
				data = reader.read();
			}
			reader.close();
		} catch (Exception e) {}
		return s;
	}
	
	public String[] getSavedFractal() {
		SavedFractal = textFileToString().split("\n");
		ArrayList<String> temp = new ArrayList<>();
		for(int i = 0 ; i < SavedFractal.length;i++) {
			if(SavedFractal[i].length()>5 && Pattern.matches("|Koch |TreeS|TreeA|LSyst|", SavedFractal[i].substring(0,5))) {
				if(SavedFractal[i].substring(0,5).equals("LSyst")) {
					String s = "" + SavedFractal[i];
					while(SavedFractal[i].indexOf(")") == -1) {
						s+=SavedFractal[i+1];
						System.out.println("String added" + SavedFractal[i+1]);
						i++;
					}
					temp.add(s);
				}else temp.add(SavedFractal[i]);
			}
		}
		SavedFractal = (String[]) temp.toArray(new String[0]);
		return SavedFractal;
	}
	
	public void SaveFractal(Fractal f, String s) {
		numberOfUnamedFractal = getNumberOfUnamedFractal();
		String text = "";
		text += s + " : " + "MyFractal" + numberOfUnamedFractal + " (" + (int)f.numberOfIteration+ ",";
		if(s.equals("Koch ")) {
			text += String.format("%d,%d,%d,%d,%b);\n", (int)f.rotationAngle,(int)f.skewAngle,f.numberOfSide,f.numberOfSegment,f.mirror);
		}
		if(s.equals("TreeSimple ")) {
			text += (int)f.rotationAngle+","+f.numberOfBranch+","+(int)f.lengthOfBranch+","+f.leaves+","+f.trunk+");\n";
		}
		if(s.equals("TreeAdvanced ")) {
			text += f.leavesA+","+f.trunkA+",";
			for(Branch b : f.branches) {
				if(b == null)break;
				text+= "{" + b.angle +","+ b.length +","+ b.position + "}";
			}
			text += ");\n";
		}
		if(s.equals("LSystem ")) {
			text += String.format("%d,%s,%s);\n", (int)f.rotationAngle,Spanel.LSystemRules.getText(),Spanel.LSystemStart.getText());
		}
		addStringToTextFile(text);
	}
	

	public void SetValue(int index) {
		SetValue(getSavedFractal()[index]);
	}
	
	private void SetValue(String str) {
		int start = str.indexOf("(");
		int end = str.indexOf(")");
		String data = str.substring(start+1,end);
		String[] d = data.split(",");
		
		if(str.substring(0, 5).equals("Koch ")){
			Spanel.setValueKoch(Integer.parseInt(d[0]), Integer.parseInt(d[1]) , Integer.parseInt(d[2]), Integer.parseInt(d[3]) ,Integer.parseInt(d[4]) , Boolean.parseBoolean(d[5]));
		}
		if(str.substring(0, 5).equals("TreeS")){
			Spanel.setValueTreeSimple(Integer.parseInt(d[0]), Integer.parseInt(d[1]) , Integer.parseInt(d[2]), Integer.parseInt(d[3]) ,Boolean.parseBoolean(d[4]) , Boolean.parseBoolean(d[5]));
		}
		if(str.substring(0, 5).equals("TreeA")){
			data = str.substring(str.indexOf("{"),str.indexOf(")"));
			Spanel.setValueTreeAdvanced(Integer.parseInt(d[0]), Boolean.parseBoolean(d[1]) , Boolean.parseBoolean(d[2]), getBranchesArr(data));
		}
		if(str.substring(0, 5).equals("LSyst")){
			String[] temp = d[2].split("\r");
			d[2]="";
			for(String s : temp) {
				d[2] += s + "\r\n";
			}
			Spanel.setValueLSystem(Integer.parseInt(d[0]), Integer.parseInt(d[1]) , d[2], d[3]);
		}

	}
	
	private int[][] getBranchesArr(String s) {
//		s="{1,2,3}{1,2,3}";
		System.out.println(s);
		String[] split = s.substring(1).split("[{]");
		int[][] arr = new int[split.length][3];
		for(int i = 0; i < arr.length;i++) {
			arr[i] = getBranch(split[i].substring(0,split[i].length()-1));
		}
		return arr;
	}
	private int[] getBranch(String s) {
//		s="1,2,3";
		String[] split = s.split(",");
		return new int[] {Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2])};
	}
	private int getNumberOfUnamedFractal() {
		int count = 1;
		String textFile = textFileToString();
		while(textFile.indexOf("MyFractal"+count) != -1) {
			count++;
		}
		return count;
	}
	
}
