package pacman;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private GameMenu menu;
	private GameStage gstage;
	
	@Override
	public void start(Stage stage) {
		try {
			this.menu = new GameMenu();
			Scene scene = new Scene(this.menu.createContent(stage));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("PAC-MAN");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
