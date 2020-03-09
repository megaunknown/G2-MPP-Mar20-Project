package ui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Start extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}

	public static void hideAllWindows() {
		primStage.hide();
	}

	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		primStage.getIcons().add(new Image("ui/icon.png"));
		primStage.initStyle(StageStyle.UNDECORATED);
		//Commented By Mohamed Saleh
		//LoginWindow.INSTANCE.init(primaryStage);
		//Added By Mohamed Saleh
		LoginWindow.INSTANCE.init(primaryStage);
		
		//*****************************************/
		primaryStage.show();
	}

}
