package ui;

import java.util.UUID;

import business.Address;
import business.LibraryMember;
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

public class EditMemberWindow {
	public static final EditMemberWindow INSTANCE = new EditMemberWindow();
	private GridPane grid;
	private Label lblMemberId;
	private TextField txtFirstName, txtLastName, txtPhone, txtStreet, txtCity, txtState, txtZip, txtMemberID;
	private LibraryMember member;
	
	/* This class is a singleton */
	private EditMemberWindow() {}
	
	public void init(Stage primaryStage, SplitPane split, LibraryMember member) {
		this.member= member;
		
		primaryStage.setTitle("Edit Member Information");
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Edit Member Information");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0);
        
        lblMemberId= new Label(this.member.getMemberId());
        grid.add(new Label("Member ID"),0,1);
        grid.add(lblMemberId,1,1);

        grid.add(new Label("First Name"),0,2);
        txtFirstName = new TextField();
        txtFirstName.setText(member.getFirstName());
        txtFirstName.setPrefWidth(400);
        grid.add(txtFirstName,1,2);

        grid.add(new Label("Last Name"),0,3);
        txtLastName = new TextField();
        txtLastName.setText(member.getLastName());
        grid.add(txtLastName,1,3);
        
        grid.add(new Label("Phone"),0,4);
        txtPhone = new TextField();
        txtPhone.setText(member.getTelephone());
        grid.add(txtPhone,1,4);
        
        grid.add(new Label("Street"),0,5);
        txtStreet = new TextField();
        txtStreet.setText(member.getAddress().getStreet());
        grid.add(txtStreet,1,5);
        
        grid.add(new Label("City"),0,6);
        txtCity = new TextField();
        txtCity.setText(member.getAddress().getCity());
        grid.add(txtCity,1,6);
        
        grid.add(new Label("State"),0,7);
        txtState = new TextField();
        txtState.setText(member.getAddress().getState());
        grid.add(txtState,1,7);
        
        grid.add(new Label("Zip Code"),0,8);
        txtZip = new TextField();
        txtZip.setText(member.getAddress().getZip());
        grid.add(txtZip,1,8);

        /***********************************************/
        Button saveBookBtn = new Button("Save");
        Button backBtn = new Button("Back");

        saveBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				DataAccess dataAccess = new DataAccessFacade();				
				dataAccess.editMember(new LibraryMember(lblMemberId.getText(), txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText())));
				AllMembersWindow.INSTANCE.init(primaryStage, split);
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
        hBack.getChildren().add(backBtn);
        grid.add(hBack, 0, 9,2,1);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );

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
