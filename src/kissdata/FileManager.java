package kissdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {
	
	
	public File getDataSetFile() {
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Dataset File");
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
		
	}
	
	public File getConfigSetFile() {
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Config File");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
		chooser.setFileFilter(filter);
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
		
	}
	
	
	public File saveData() {
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv", "csv", "comma seperated values");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Choose CSV Location");
		
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			
			File file = chooser.getSelectedFile();
			if (file.getName().contains(".csv")) {
			    // filename is OK as-is
			} else {
			    file = new File(file.toString() + ".csv");
			}
			
			
			return file;
		}
		
		return null;
		
	}
	
	public void saveFile(File fileToSave, File fileWithData, File fileWithConfig) throws FileNotFoundException {
		
		if(fileWithData == null || fileToSave == null || fileWithConfig == null) {
			JOptionPane.showMessageDialog(null, "Missing Files");
			return;
		}
		
		
		ArrayList<String> rules = new ArrayList<String>();
		ArrayList<int[]> locations = new ArrayList<int[]>();
		ArrayList<ArrayList<String[]>> modifiers = new ArrayList<ArrayList<String[]>>();
		
		// Read Config File
		Scanner conf = new Scanner(fileWithConfig);
		
		
		
		
		
		while(conf.hasNextLine()) {
			String curLine = conf.nextLine();
			
			String[] lineRules = curLine.split(" ");
			
			rules.add(lineRules[0]);
			
			String[] ruleLocation = lineRules[1].split(":");
			int[] formLocation = {Integer.parseInt(ruleLocation[0]), Integer.parseInt(ruleLocation[1])};
			locations.add(formLocation);
			
			
			if(lineRules.length >= 3) {
			
				String[] curMods = lineRules[2].split(",");
				
				ArrayList<String[]> modValues = new ArrayList<String[]>();
				
				for(int i = 0; i < curMods.length; i++) {
					
					
					String[] parts = curMods[i].split("=");
					
					modValues.add(parts);
					
				}
				
				modifiers.add(modValues);
				
			}else {
				modifiers.add(null);
			}
			
		}
		
		PrintWriter pw = new PrintWriter(fileToSave);
		StringBuilder sb = new StringBuilder();
		sb.append("id");
		sb.append(',');
		for(int i = 0; i < rules.size(); i++) {
			sb.append(rules.get(i));
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append('\n');
		
		Scanner in = new Scanner(fileWithData);
		int id = 0;
		while(in.hasNextLine()) {
			String readLine = in.nextLine();
			
			// Process Data
			String cause = readLine.substring(144, 148);
			if(cause.contains("X40") || cause.contains("X41") || cause.contains("X42") || cause.contains("X43") || cause.contains("X44")) {
				
				sb.append(id);
				sb.append(',');
				
				for(int i = 0; i < locations.size(); i++) {
					
					String information = readLine.substring(locations.get(i)[0]-1, locations.get(i)[1]-1);
					
					
					if(modifiers.get(i) != null) {
						
						for(String[] mod: modifiers.get(i)) {
							if(information.contains(mod[0])) {
								information = mod[1];
							}
						}
						
						
					}
					
					
					sb.append(information);
					sb.append(',');
					
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append('\n');
				id++;					
			}
			
		}
		sb.deleteCharAt(sb.length()-1);
		pw.write(sb.toString());
		conf.close();
		in.close();
		pw.close();
		JOptionPane.showMessageDialog(null, "Done");
	}
}
