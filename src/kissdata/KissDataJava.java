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
		FileManager fileMan = new FileManager();
		
		
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
			dataFile = fileMan.getDataSetFile();
			
			if(dataFile != null) {
				dataFileLabel.setText(dataFile.getAbsolutePath());
				frame.pack();
			}else {
				dataFileLabel.setText("None");
				frame.pack();
			}
			
			
		});
		
		configFileButton.addActionListener(e -> {
			configFile = fileMan.getConfigSetFile();
			if(configFile != null) {
				configFileLabel.setText(configFile.getAbsolutePath());
				frame.pack();
			}else {
				configFileLabel.setText("None");
				frame.pack();
			}
		});
		
		selectSaveFileButton.addActionListener(e -> {
			saveFile = fileMan.saveData();
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
				fileMan.saveFile(saveFile, dataFile, configFile);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error Reading File");
			}
		});
	}
	
}