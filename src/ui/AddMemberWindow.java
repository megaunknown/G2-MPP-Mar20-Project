package ui;

import java.util.UUID;

import business.Address;
import business.LibraryMember;
import business.ValidationException;
import business.ValidationHelper;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddMemberWindow {
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	private GridPane grid;
	private TextField txtFirstName, txtLastName, txtPhone, txtStreet, txtCity, txtState, txtZip;
	
	/* This class is a singleton */
	private AddMemberWindow() {}
	
	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Add new member");
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add New Member");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0);

        grid.add(new Label("First Name (*)"),0,1);
        txtFirstName = new TextField();
        grid.add(txtFirstName,1,1);

        grid.add(new Label("Last Name (*)"),0,2);
        txtLastName = new TextField();
        grid.add(txtLastName,1,2);
        
        grid.add(new Label("Phone"),0,3);
        txtPhone = new TextField();
        grid.add(txtPhone,1,3);
        
        grid.add(new Label("Street"),0,4);
        txtStreet = new TextField();
        grid.add(txtStreet,1,4);
        
        grid.add(new Label("City"),0,5);
        txtCity = new TextField();
        grid.add(txtCity,1,5);
        
        grid.add(new Label("State"),0,6);
        txtState = new TextField();
        grid.add(txtState,1,6);
        
        grid.add(new Label("Zip Code"),0,7);
        txtZip = new TextField();
        grid.add(txtZip,1,7);

        /***********************************************/
        Button saveBookBtn = new Button("Save");
        Button saveNewBookBtn = new Button("Save and New");
        Button backBtn = new Button("Back");

        saveBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {	
				if(isDataValid())
				{
					DataAccess dataAccess = new DataAccessFacade();
					String memberId= UUID.randomUUID().toString();
					dataAccess.saveNewMember(new LibraryMember(memberId, txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText())));
					AllMembersWindow.INSTANCE.init(primaryStage, split);
				}
			}
		});

        saveNewBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isDataValid())
				{
					DataAccess dataAccess = new DataAccessFacade();
					String memberId= UUID.randomUUID().toString();
					dataAccess.saveNewMember(new LibraryMember(memberId, txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText())));
					UI_Helper_Class.showMessageBoxInfo("Saved successfully");
					clear();
				}
			}
		});

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AllMembersWindow.INSTANCE.init(primaryStage, split);
			}
		});

        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER_RIGHT);
        hBack.getChildren().add(saveBookBtn);
        hBack.getChildren().add(saveNewBookBtn);
        hBack.getChildren().add(backBtn);
        grid.add(hBack, 0, 8,2,1);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );

	}
	
	public boolean isDataValid()
	{
		try {				
			ValidationHelper<String> validate= new ValidationHelper<String>();
			validate.mandatoryValidator(txtFirstName.getText());
			validate.mandatoryValidator(txtLastName.getText());
		}
		catch(ValidationException ex)
		{
			UI_Helper_Class.showMessageBoxError("Error! " + ex.getMessage());
			return false;
		}
		return true;
	}
	
	public void clear() {		
		txtFirstName.setText("");
		txtLastName.setText("");
		txtPhone.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtZip.setText("");
		
		
	}
}
