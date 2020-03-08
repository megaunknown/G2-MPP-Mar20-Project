package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeWindow {
	
	public static final HomeWindow INSTANCE = new HomeWindow();

	/* This class is a singleton */
	private HomeWindow() {
	}

	public void init(Stage primaryStage) {
		int numOfButtons= 5;
		
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
//        				"-fx-opacity: 0.2;\r\n" + 
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
//            @Override
//            public void handle(ActionEvent event)
//            {
//        		primaryStage.setTitle("Members Page");            	
//            	AllMembersWindow.INSTANCE.init(primaryStage, splitStatus);
//        		splitStatus.getItems().set(1, new StackPane(new Label("Members")));
//    			split.lookupAll(".split-pane-divider").stream()
//                .forEach(div ->  {
//                	div.setMouseTransparent(true);
//                	div.setStyle("-fx-padding: 0 1 0 1");
//                } );
//            }     
        	@Override
            public void handle(ActionEvent event)
            {
        		primaryStage.setTitle("Members Page");
    	        AllMembersWindow.INSTANCE.init(primaryStage, splitStatus);
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
//        Button usersButton = new Button();
//        usersButton.setText("Users");
//        usersButton.setPrefWidth(500);
//        usersButton.setPrefHeight(screenSize.getHeight()/6);
//        usersButton.setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent event)
//            {
//        		primaryStage.setTitle("Users Page");
//        		splitStatus.getItems().set(1, new StackPane(new Label("Users")));
//    			split.lookupAll(".split-pane-divider").stream()
//                .forEach(div ->  {
//                	div.setMouseTransparent(true);
//                	div.setStyle("-fx-padding: 0 1 0 1");
//                } );
//            }                
//        });
        Button logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setPrefWidth(500);
        logoutButton.setPrefHeight(screenSize.getHeight()/numOfButtons);
        logoutButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
            	LoginWindow.INSTANCE.init(primaryStage);
            }                
        });
        GridPane  r = new GridPane();
        r.add(homeButton, 0, 0);
        r.add(checkoutButton, 0, 1);
        r.add(membersButton, 0, 2);
        r.add(booksButton, 0, 3);
//        r.add(usersButton, 0, 4);
        r.add(logoutButton, 0, 4);
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
	
        Scene scene = new Scene(split, 1000, 600);
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
