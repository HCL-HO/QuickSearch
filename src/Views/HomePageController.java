package Views;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import Bussiness.MesPaneChangeListener;
import Bussiness.SearchFileService;
import Bussiness.Searcher;
import Bussiness.ServiceTask;
import application.Main;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.RadioButton;

public class HomePageController {
	@FXML
	private TextArea mesPane;
	@FXML
	private TextField rootDirField;
	@FXML
	private Button rootDirBtn;
	@FXML
	private TextField targetDirField;
	@FXML
	private Button tarDirBtn;
	@FXML
	private Button runBtn;
	@FXML
	private RadioButton searchRadBtn;
	@FXML
	private RadioButton copyRadBtn;
	@FXML
	private TextField keyWordField;
	@FXML 
	private TextField exceptField;
	
	private ToggleGroup g1 = new ToggleGroup();
	private DirectoryChooser chooser = new DirectoryChooser();
	private Main main = new Main();
	private final String searchModeMes = "Search Mode Is On";
	private Searcher searcher;
	
	@FXML
	public void initialize(){
		searchRadBtn.setToggleGroup(g1);
		copyRadBtn.setToggleGroup(g1);
		loadSearchModeSetting();
	}
	


	@FXML
	public void onRootDirBtn(){
		System.out.println("onRootDirBtn");
		File file = chooser.showDialog(main.getCurrentStage());
		String rootPath = file.getAbsolutePath();
		rootDirField.setText(rootPath);
	}
	@FXML
	public void onTarDirBtn(){
		System.out.println("onTarDirBtn");
		File file = chooser.showDialog(main.getCurrentStage());
		String tarPath = file.getAbsolutePath();
		tarDirBtn.setText(tarPath);
	}
	
	@FXML
	public void onSearchRadBtn(){
		loadSearchModeSetting();
	}
	
	@FXML
	public void onCopyRadBtn(){
		tarDirBtn.setDisable(false);
		targetDirField.clear();
		targetDirField.setEditable(true);	
	}

	
	private void loadSearchModeSetting() {
		tarDirBtn.setDisable(true);
		targetDirField.setText(searchModeMes);
		targetDirField.setEditable(false);
	}

	public void setMain(Main main){
		this.main = main;
	}
	
	@FXML
	public void onRunBtn(ActionEvent event) {
		String rootPath;
		String[] keyWords;
		String[] except;

		mesPane.clear();
		if(searchRadBtn.isSelected()){
			System.out.println("STEP1");
			if(rootDirField.getText().isEmpty() || !new File(rootDirField.getText()).exists()){
				mesPane.setText("Please choose a valid root path");
			} else {
				System.out.println("STEP2");
				keyWords = proceessSearchRequirement(keyWordField.getText());
				except = proceessSearchRequirement(exceptField.getText());
				rootPath = rootDirField.getText();
				mesPane.setText(
						"Search folder: " + rootPath +"\n"
						+"Keywords: " + keyWordField.getText() +"\n" 
						+"Excluding: " + exceptField.getText()+"\n"
				);
				searcher = new Searcher(mesPane);
				System.out.println("STEP3");
				mesPane.appendText("Start Searching......"+"\n");
				SearchFileService service = 
						new SearchFileService(searcher, rootPath, keyWords, except);
				service.start();
				mesPane.appendText("End");			
			}
		} else if (copyRadBtn.isSelected()){
			
		} else {
			mesPane.setText("Unexpected Error: No radio button is selected");
		}
	}

	
	private String[] proceessSearchRequirement(String text) {
		String[] results;
		if(text.isEmpty() || text == null){
				results = null;
			} else {
				results = text.split(",");
			}
			return results;
		}	 

	}

	
//	public void appendMesInMainThread(String s){
//		Platform.runLater(()->{
//			mesPane.appendText(s);
//		});
//	}
	
//	public class SearchThread extends Thread{
//	String rootPath;
//	String[] keyWords;
//	String[] except;
//	Searcher searcher;
//	String[] results;
//	
//	public SearchThread(String rootPath, String[] keyWords, String[] except){
//		this.except =except;
//		this.keyWords = keyWords;
//		this.rootPath = rootPath;
//	}
//	
//	@Override
//	public void run() {
//		super.run();
//		searcher = new Searcher();
//		mesPane.appendText("Start Searching......"+"\n");
//		
//		results = searcher.searchFile(rootPath, keyWords, except, new SearchJob());
//			mesPane.appendText("-----------End-------------");
//			if(searcher.isJobFinish()){
//				System.out.println("Finish");
//			}
//		
//	}

//	Task task = new Task<Void>() {
//	    @Override public Void call() {
//			searcher.searchFile(rootPath, keyWords, except, new SearchJob());
//			mesPane.appendText("-----------End-------------");
//			if(searcher.isJobFinish()){
//				updateThread.interrupt();
//			}
//			return null;
//	    }
//	};
//	updateThread = new Thread(task);
//	updateThread.start();
	
//	https://stackoverflow.com/questions/30569709/java-thread-immediately-update-ui

