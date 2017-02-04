package pl.dev.davids.Remover;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Deleter {
	enum direction {
		deleteJPG, deleteRAW
	}
	private int counter=0;
	private String trash;
	private Pather pathJPG, pathRAW;
	private ArrayList<File> fileRemove;

	public Deleter(Pather pathJPG, Pather pathRAW, direction type) {
		if (type.equals(direction.deleteRAW)) {
			this.pathJPG = pathJPG;
			this.pathRAW = pathRAW;
			this.trash = "TrashRAW";
		}
		if (type.equals(direction.deleteJPG)) {
			this.pathJPG = pathRAW;
			this.pathRAW = pathJPG;
			this.trash = "TrashJPG";
		}
		this.fileRemove = new ArrayList<>();
	}

	public void searchFileToDelete() {
		int size = pathRAW.getPathList().size();
		int controlSize = pathJPG.getPathList().size();
		if (size > 0 && controlSize>0) {
			String[] keys = pathRAW.getPathList().keySet().toArray(new String[size]);
			fileRemove.clear();
			for (int i = 0; i < size; ++i) {
				if (pathJPG.getPathList().get(keys[i]) == null) {
					fileRemove.add(pathRAW.getPathList().get(keys[i]));
				}
			}
			counter=fileRemove.size();
		}
		else {
			counter = 0;
		}
	}
	
	public void runFileDelete() {
		moveFilesToTrash();
		//deleteTrash(moveFilesToTrash());
	}

	private File moveFilesToTrash() {
		if (!fileRemove.isEmpty()) {
			boolean check = false;
			int size = fileRemove.size();
			File bin = new File(fileRemove.get(0).getParent() + fileRemove.get(0).separator + trash);
			if (!bin.exists()) {
				if (bin.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			if (bin.exists()) {
				for (int i = 0; i < size; ++i) {
					if (fileRemove.get(i)
							.renameTo(new File(bin.getAbsoluteFile() + bin.separator + fileRemove.get(i).getName())))
						check = true;
				}
				if (check) {
					JOptionPane.showMessageDialog(null, "Move files to Trash");
				}
				return bin;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nothing to delete");
		}
		return null;
	}

	private void deleteTrash(File trash) {
		Object[] options = { "YES", "NO" };
		ArrayList<String> listFiles = new ArrayList<>();
		int count = 1;
		for (File file : trash.listFiles()) {
			if (count % 5 == 0){
				listFiles.add(file.getName() + "\n");
				count=0;
			}
			else
				listFiles.add(file.getName());
			count++;

		}
		int response = JOptionPane.showOptionDialog(null, "Do you want to empty trash?\n" + listFiles.toString(), "Delete trash",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if (response == JOptionPane.YES_OPTION) {
			if (!listFiles.isEmpty()) {
				for (File file : trash.listFiles()) {
					System.out.println(file.getAbsolutePath());
					file.delete();
				}
			}
			if (trash.delete()) {
				System.out.println("delete trash");
			}
		}
	}
	
	public int getCounter() {
		return counter;
	}
}
