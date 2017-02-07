package pl.dev.davids.Remover;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public abstract class Deleter {
	protected int counter = 0;
	protected String trash;
	protected Pather pathJPG, pathRAW;
	protected ArrayList<File> fileRemove;

	public Deleter(String trash, Pather pathJPG, Pather pathRAW) {
		this.pathJPG = pathJPG;
		this.pathRAW = pathRAW;
		this.trash = trash;
		this.fileRemove = new ArrayList<>();
	}

	public abstract void searchFileToDelete();

	public abstract void runFileDelete();

	protected File moveFilesToTrash() {
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

	protected void deleteTrash(File trash) {
		Object[] options = { "YES", "NO" };
		ArrayList<String> listFiles = new ArrayList<>();
		int count = 1;
		for (File file : trash.listFiles()) {
			if (count % 5 == 0) {
				listFiles.add(file.getName() + "\n");
				count = 0;
			} else
				listFiles.add(file.getName());
			count++;

		}
		int response = JOptionPane.showOptionDialog(null, "Do you want to empty trash?\n" + listFiles.toString(),
				"Delete trash", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
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
	
	public void show(){
		System.out.println(pathJPG.getPathList().toString());
		System.out.println(pathRAW.getPathList().toString());
		System.out.println(fileRemove.toString());
		System.out.println(counter);
	}
}
