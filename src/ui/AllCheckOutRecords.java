package ui;

import java.util.HashMap;

import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.LibraryMember;
import business.Person;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AllCheckOutRecords{ //extends Stage implements LibWindow {
	public static final AllCheckOutRecords INSTANCE = new AllCheckOutRecords();
	private GridPane grid;
	private Text scenetitle;
	private TableView<CheckOutEntry> tvCheckOutEntry;
	
		
	private boolean isInitialized = false;
	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private TextArea ta;

	public void setData(String data) {
		ta.setText(data);
	}

	/* This class is a singleton */
	private AllCheckOutRecords() {}

	public void init(Stage primaryStage, SplitPane split) {
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);	
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));       
      	
        scenetitle = new Text("List of Checkout Entries");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0);

        tvCheckOutEntry = new TableView<CheckOutEntry>();
//        tvMembers.setMinWidth(1000);        
        tvCheckOutEntry.prefWidthProperty().bind(split.widthProperty());;
              
        /*
	    TableColumn<LibraryMember, String> col = new TableColumn<>("Checkout Date");	    
	    col.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
	    tvCheckOutEntry.getColumns().add(col);
	    
	    col = new TableColumn<>("Due Date");
	    col.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
	    tvCheckOutEntry.getColumns().add(col);
	    
	    col = new TableColumn<>("Return Date");
	    col.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
	    tvCheckOutEntry.getColumns().add(col);
	    
	    col = new TableColumn<>("Member ID");
	    col.setCellValueFactory(new PropertyValueFactory<>("telephone"));
	    tvCheckOutEntry.getColumns().add(col);
	    
	    col = new TableColumn<>("User ID");
	    col.setCellValueFactory(new PropertyValueFactory<>("user"));
	    tvCheckOutEntry.getColumns().add(col);

	    tvCheckOutEntry.setItems(getMembersList());	 
	    */
	    
	    
	    grid.add(tvCheckOutEntry, 0, 1);	
        
        //*************
        
	    Button addMemberBtn = new Button("Add New Member");
		addMemberBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddMemberWindow.INSTANCE.init(primaryStage, split);
			}
		});
		
		Button editBtn = new Button("Edit Selected Member");
		editBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				/*
				LibraryMember member= tvMembers.getSelectionModel().getSelectedItem();	
				if(member != null)
				{
//					EditMemberWindow.INSTANCE.init(primaryStage, split, member);
				}
				*/
			}
		});		
		
        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER);
        hBack.getChildren().add(editBtn);
        hBack.getChildren().add(addMemberBtn);        
        grid.add(hBack, 0, 3);

        
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
     
	}
	

	public ObservableList<CheckOutEntry> getCheckoutEntries()
	{
		ObservableList<CheckOutEntry> strings = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,CheckOutEntry> checkoutEntry = da.readCheckOutEntry();
		checkoutEntry.values().forEach(a -> strings.add(a));
		return strings;
	}
}
