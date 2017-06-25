package application;
	
import Views.HomePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage currentStage;
	
	@Override
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/HomePage.fxml"));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Quick Search 1.0");
			
			HomePageController homePageController = new HomePageController();
			homePageController.setMain(this);
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getCurrentStage(){
		return currentStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
