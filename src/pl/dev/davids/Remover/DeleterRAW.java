package pl.dev.davids.Remover;

public class DeleterRAW extends Deleter {
	public DeleterRAW(Pather pathJPG, Pather pathRAW) {
		super("TrashRAW", pathJPG, pathRAW);
	}

	@Override
	public void searchFileToDelete() {
		int size = pathRAW.getPathList().size();
		int controlSize = pathJPG.getPathList().size();
		if (size > 0 && controlSize > 0) {
			String[] keys = pathRAW.getPathList().keySet().toArray(new String[size]);
			fileRemove.clear();
			for (int i = 0; i < size; ++i) {
				if (pathJPG.getPathList().get(keys[i]) == null) {
					fileRemove.add(pathRAW.getPathList().get(keys[i]));
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
