package pl.dev.davids.Remover;

public class DeleterJPG extends Deleter {
	public DeleterJPG(Pather pathJPG, Pather pathRAW) {
		super("TrashJPG", pathJPG, pathRAW);
	}

	@Override
	public void searchFileToDelete() {
		int size = pathJPG.getPathList().size();
		int controlSize = pathRAW.getPathList().size();
		if (size > 0 && controlSize > 0) {
			String[] keys = pathJPG.getPathList().keySet().toArray(new String[size]);
			fileRemove.clear();
			for (int i = 0; i < size; ++i) {
				if (pathRAW.getPathList().get(keys[i]) == null) {
					fileRemove.add(pathJPG.getPathList().get(keys[i]));
				}
			}
			counter = fileRemove.size();
		} else {
			counter = 0;
		}
	}

	@Override
	public void runFileDelete() {
		moveFilesToTrash();
		//deleteTrash(moveFilesToTrash());
	}

}
