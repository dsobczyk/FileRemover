package pl.dev.davids.Remover;

import java.io.File;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Pather {
	private String path;
	private HashMap<String, File> pathList;
	private int counter = 0;
	private Chooser chooser;

	public Pather(Chooser chooser) {
		path = "";
		pathList = new HashMap<String, File>();
		this.chooser = chooser;
	}

	public String choosePath() {
		String path = "";
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			path = (chooser.getSelectedFile().toString());
		} else {
			path = "No Selection";
		}
		return path;
	}

	public DefaultListModel<String> generateFileList(String ext) {
		try {
			boolean check = false;
			File folder = new File(path);
			DefaultListModel<String> listModel = null;
			String extension = ext;
			if (folder.isDirectory() && !extension.equals("")) {
				listModel = new DefaultListModel<String>();
				String temp = "";
				for (final File fileEntry : folder.listFiles()) {
					if (fileEntry.isFile()) {
						temp = fileEntry.getName();
						if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase())
								.equals(extension)) {
							listModel.addElement(fileEntry.getName());
							pathList.put(selectNumberSubpath(fileEntry.getName()), fileEntry);
							check = true;
						}
					}
				}
			} else
				JOptionPane.showMessageDialog(null, "Wrong path or no extension");
			if (!check)
				listModel.addElement("No Files");
			counter = listModel.getSize();
			return listModel;
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Empty path");
			return null;
		}
	}

	private String selectNumberSubpath(String path) {
		String subpath = path.substring(0, path.lastIndexOf('.'));
		return subpath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		pathList.clear();
	}

	public HashMap<String, File> getPathList() {
		return pathList;
	}

	public void setPathList(HashMap<String, File> pathList) {
		this.pathList = pathList;
	}

	public Chooser getChooser() {
		return chooser;
	}

	public void setChooser(Chooser chooser) {
		this.chooser = chooser;
	}
	
	public int getCounter() {
		return counter;
	}

}
