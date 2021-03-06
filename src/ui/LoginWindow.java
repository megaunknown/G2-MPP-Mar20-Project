package ui;

import java.util.Optional;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindow {
	private String userID;
	private double xOffset = 0;
	private double yOffset = 0;
	public static final LoginWindow INSTANCE = new LoginWindow();

	/* This class is a singleton */
	private LoginWindow() {
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void init(Stage primaryStage) {
		primaryStage.setTitle("Login Page");
		StackPane stack = new StackPane();
		ImageView image = new ImageView("ui/login-background.png");
		image.fitWidthProperty().bind(stack.widthProperty());
		image.fitHeightProperty().bind(stack.heightProperty());
		stack.getChildren().add(image);
		GridPane grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Login");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		grid.setGridLinesVisible(false);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button loginBtn = new Button("Log in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(loginBtn);
		userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					loginBtn.fire();
				}
			}
		});
		pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					loginBtn.fire();
				}
			}
		});

		grid.add(hbBtn, 1, 4);

		HBox messageBox = new HBox(10);
		messageBox.setAlignment(Pos.BOTTOM_RIGHT);
		Text messageBar = new Text();
		messageBox.getChildren().add(messageBar);
		grid.add(messageBox, 1, 6);

		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					ControllerInterface c = new SystemController();
					c.login(userTextField.getText().trim(), pwBox.getText().trim());
					INSTANCE.setUserID(userTextField.getText());
					HomeWindow.INSTANCE.init(primaryStage);
				} catch (LoginException ex) {
					UI_Helper_Class.showMessageBoxError("Error! " + ex.getMessage());
				}

			}
		});
		grid.setMaxSize(400, 200);
		grid.setStyle("-fx-background-color: #FFFFFF");
		stack.getChildren().add(grid);
		stack.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		stack.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
		Scene scene = new Scene(stack, 1000, 600);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ESCAPE) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.initOwner(scene.getWindow());
					alert.setTitle("Exit Application");
					alert.setContentText("Do you wanna leave the application?");
					ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
					ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
					alert.getButtonTypes().setAll(okButton, noButton);
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get().getText().equalsIgnoreCase("yes")){
						primaryStage.close();
					}
				}
			}
		});
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.setScene(scene);
	}

}
