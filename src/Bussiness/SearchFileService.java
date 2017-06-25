package Bussiness;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SearchFileService extends Service<Void>{
	Searcher task;
	String fromDir;
	String[] keys;
	String[] exc;
	
	public SearchFileService (Searcher task, String fromDir, String[] keys, String[] exc){
		this.task = task;
		this.keys = keys;
		this.exc = exc;
		this.fromDir = fromDir;
	}
	
	@Override
	protected Task<Void> createTask() {
		task.searchFile(fromDir, keys, exc);
		return null;
	}

}
