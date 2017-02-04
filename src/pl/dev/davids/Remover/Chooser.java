package pl.dev.davids.Remover;

import javax.swing.JFileChooser;

public class Chooser extends JFileChooser {
	public Chooser() {
		super();
		this.setCurrentDirectory(new java.io.File("user.dir"));
		this.setDialogTitle("File chooser");
		this.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
	}

	public String choosePath() {
		String path = "";
		if (this.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = (this.getSelectedFile().toString());
		} else {
			path = "No Selection";
		}
		return path;
	}
}
