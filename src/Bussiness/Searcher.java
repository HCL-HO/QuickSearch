package Bussiness;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import Method.FilePathFinder;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Searcher{
	TextArea pane;
	
	public Searcher(TextArea pane){
	this.pane = pane;	
	}
	
	FilePathFinder finder = new FilePathFinder();
	
	public String[] searchFile(String fromDir, String[] keys, String[] exc){
		String[] results = finder.search(fromDir, keys, exc, new SearchJob());
		return results;
		}	
	
	public void clearList(){
		finder.getSearcher().clearAllFileList();
	}
	
	public boolean isJobFinish(){
		return finder.getSearcher().isBatchJobFinish();
	}
	
	public class SearchJob implements Consumer<List<File>>{
		
		Run run = new Run();
		
		public class Run implements Runnable{
			File f;
			@Override
			public void run() {
					pane.appendText("File: "+ f.getAbsolutePath() +"\n");
			}
			public void setFile(File f){
				this.f = f;
			}		
		}
		
		@Override
		public void accept(List<File> list) {	
			if(list.size()>5){
				for(File f : list ){
					run.setFile(f);
					Platform.runLater(run);
					if(isJobFinish()){
						System.out.println("Finish");
					}
				}
				clearList();					
			}
		}
	}
}
