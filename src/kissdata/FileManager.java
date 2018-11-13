package kissdata;

import java.io.File;
import javax.swing.JFileChooser;
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
}
