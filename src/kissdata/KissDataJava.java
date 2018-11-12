package kissdata;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class KissDataJava {
	
	
	private static File dataFile;
	private static File saveFile;
	private static File configFile;
	
	public static void main(String[] args) throws IOException {
		
		
		JFrame frame = new JFrame("KissData");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		JButton dataFileButton = new JButton("Select Data File");
		JLabel dataFileLabel = new JLabel("None");
		JButton configFileButton = new JButton("Select Config File");
		JLabel configFileLabel = new JLabel("None");
		JButton selectSaveFileButton = new JButton("Select Save File");
		JLabel saveFileLabel = new JLabel("None");
		JButton saveFileButton = new JButton("Export CSV");
		
		pane.add(dataFileButton);
		pane.add(dataFileLabel);
		pane.add(configFileButton);
		pane.add(configFileLabel);
		pane.add(selectSaveFileButton);
		pane.add(saveFileLabel);
		pane.add(saveFileButton);
		frame.pack();
		frame.setVisible(true);
		
		
		dataFileButton.addActionListener(e -> {
			dataFile = getDataSetFile();
			
			if(dataFile != null) {
				dataFileLabel.setText(dataFile.getAbsolutePath());
				frame.pack();
			}else {
				dataFileLabel.setText("None");
				frame.pack();
			}
			
			
		});
		
		configFileButton.addActionListener(e -> {
			configFile = getConfigSetFile();
			if(configFile != null) {
				configFileLabel.setText(configFile.getAbsolutePath());
				frame.pack();
			}else {
				configFileLabel.setText("None");
				frame.pack();
			}
		});
		
		selectSaveFileButton.addActionListener(e -> {
			saveFile = saveData();
			if(saveFile != null) {
				saveFileLabel.setText(saveFile.getAbsolutePath());
				frame.pack();
			}else {
				saveFileLabel.setText("None");
				frame.pack();
			}
		});
		
		saveFileButton.addActionListener(e -> {
			try {
				saveFile();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error Reading File");
			}
		});
	}
	
	public static void saveFile() throws FileNotFoundException {
		
		if(dataFile == null || saveFile == null || configFile == null) {
			JOptionPane.showMessageDialog(null, "Missing Files");
			return;
		}
		
		
		ArrayList<String> rules = new ArrayList<String>();
		ArrayList<int[]> locations = new ArrayList<int[]>();
		ArrayList<ArrayList<String[]>> modifiers = new ArrayList<ArrayList<String[]>>();
		
		// Read Config File
		Scanner conf = new Scanner(configFile);
		while(conf.hasNextLine()) {
			String curLine = conf.nextLine();
			
			String[] lineRules = curLine.split(" ");
			
			rules.add(lineRules[0]);
			
			String[] ruleLocation = lineRules[1].split(":");
			int[] formLocation = {Integer.parseInt(ruleLocation[0]), Integer.parseInt(ruleLocation[1])};
			System.out.println(lineRules[0] + ": " + formLocation[0] + " " + formLocation[1]);
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
		
		PrintWriter pw = new PrintWriter(saveFile);
		StringBuilder sb = new StringBuilder();
		sb.append("id");
		sb.append(',');
		for(int i = 0; i < rules.size(); i++) {
			sb.append(rules.get(i));
			sb.append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append('\n');
		
		Scanner in = new Scanner(dataFile);
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
	
	public static File getDataSetFile() {
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Dataset File");
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
		
	}
	
	public static File getConfigSetFile() {
		
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose Config File");
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
		
	}
	
	
	public static File saveData() {
		
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Choose CSV Location");
		
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		
		return null;
		
	}
	
	public void buttonClicked(ActionEvent e) {
		System.out.println("Button Works!");
	}
	
}