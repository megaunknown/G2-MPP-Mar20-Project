package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import business.User;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeWindow {
	private double xOffset = 0; 
	private double yOffset = 0;
	
	public static final HomeWindow INSTANCE = new HomeWindow();

	/* This class is a singleton */
	private HomeWindow() {
	}
	
	public User getCurrentUser() {
		DataAccess da = new DataAccessFacade();
		return da.readUsersMap().get(LoginWindow.INSTANCE.getUserID());
	}

	public void init(Stage primaryStage) {
		int numOfButtons= 6;//Update from 5 to 6 by Mohamed Saleh
		User user = getCurrentUser();
		primaryStage.setTitle("Home Page");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SplitPane split = new SplitPane();
        SplitPane splitStatus = new SplitPane();
        split.setOrientation(Orientation.HORIZONTAL);
        Button homeButton = new Button();
        homeButton.setText("Home");
        homeButton.setPrefWidth(500);
        homeButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        homeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
        		primaryStage.setTitle("Home Page");
            	StackPane stack = new StackPane();
        		stack.setStyle("-fx-background-image: url(\"ui/library_bg.jpg\");\r\n" + 
        				"-fx-background-position: center;\r\n" + 
        				"-fx-background-repeat: no-repeat;\r\n" + 
        				"-fx-background-size: cover, auto;");
        		splitStatus.getItems().set(1, stack);
    			split.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  {
                	div.setMouseTransparent(true);
                	div.setStyle("-fx-padding: 0 1 0 1");
                } );
            }                
        });
        Button checkoutButton = new Button();
        checkoutButton.setText("Checkout/Search Book");
        checkoutButton.setPrefWidth(500);
        checkoutButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        checkoutButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
        		primaryStage.setTitle("Checkout Book Page");
        		AddCheckOutWindow.INSTANCE.init(primaryStage, splitStatus);
    			split.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  {
                	div.setMouseTransparent(true);
                	div.setStyle("-fx-padding: 0 1 0 1");
                } );
            }                
        });
        Button membersButton = new Button();
        membersButton.setText("Library Members");
        membersButton.setPrefWidth(500);
        membersButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        membersButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent event)
            {
        		primaryStage.setTitle("Members Page");
    	        MembersWindow.INSTANCE.init(primaryStage, splitStatus);
    			split.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  {
                	div.setMouseTransparent(true);
                	div.setStyle("-fx-padding: 0 1 0 1");
                } );
            }  
        });
        Button booksButton = new Button();
        booksButton.setText("Books");
        booksButton.setPrefWidth(500);
        booksButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        booksButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
    	        BooksWindow.INSTANCE.init(primaryStage, splitStatus);
    			split.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  {
                	div.setMouseTransparent(true);
                	div.setStyle("-fx-padding: 0 1 0 1");
                } );
            }                
        });
        Button logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setPrefWidth(500);
        logoutButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        logoutButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.initOwner(primaryStage.getScene().getWindow());
				alert.setTitle("Logout");
				alert.setContentText("Do you wanna logout?");
				ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
				ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
				alert.getButtonTypes().setAll(okButton, noButton);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get().getText().equalsIgnoreCase("yes")){
					LoginWindow.INSTANCE.init(primaryStage);
				}
            }                
        });
        
        //Added By Mohamed Saleh
        Button checkoutedButton = new Button();
        checkoutedButton.setText("CheckedOut Records");
        checkoutedButton.setPrefWidth(500);
        checkoutedButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        checkoutedButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
    	        CheckoutRecords.INSTANCE.init(primaryStage, splitStatus);
    			split.lookupAll(".split-pane-divider").stream()
                .forEach(div ->  {
                	div.setMouseTransparent(true);
                	div.setStyle("-fx-padding: 0 1 0 1");
                } );
            }                
        });
        /**********************************/
        
        GridPane  r = new GridPane();
        r.add(homeButton, 0, 0);
        if (user.hasLibrarianRole()) {
            r.add(checkoutButton, 0, 1);
            r.add(checkoutedButton, 0, 3);
        }
        if (user.hasAdminRole()) {
            r.add(membersButton, 0, 2);
        }
        if (user.hasAdminRole()) {
            r.add(booksButton, 0, 4);
        }
        r.add(logoutButton, 0, 5);
        
        //Added by Mohamed Saleh
        
        /*****************************************/
        split.getItems().add(r);
    	StackPane stack = new StackPane();
		stack.setStyle("-fx-background-image: url(\"ui/library_bg.jpg\");\r\n" + 
				"-fx-background-position: center;\r\n" + 
				"-fx-background-repeat: no-repeat;\r\n" + 
				"-fx-background-size: cover, auto;");
        splitStatus.setOrientation(Orientation.VERTICAL);
        split.getItems().add(splitStatus);
        HBox hBox = new HBox(500);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(new Label("User Name: " + LoginWindow.INSTANCE.getUserID()));
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        hBox.getChildren().add(new Label(simpleDateFormat.format(new Date())));
        splitStatus.getItems().add(hBox);
        splitStatus.getItems().add(stack);
        split.setPrefSize(screenSize.width, screenSize.height);

        split.getItems().get(1).setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        split.getItems().get(1).setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	primaryStage.setX(event.getScreenX() - xOffset);
            	primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        Scene scene = new Scene(split, 1000, 600);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ESCAPE) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.initOwner(scene.getWindow());
					alert.setTitle("Logout");
					alert.setContentText("Do you wanna logout?");
					ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
					ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
					alert.getButtonTypes().setAll(okButton, noButton);
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get().getText().equalsIgnoreCase("yes")){
						LoginWindow.INSTANCE.init(primaryStage);
					}
				}
			}
		});
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		
		
		split.setDividerPositions(0.15);
		splitStatus.setDividerPositions(0.02);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
	}

}
