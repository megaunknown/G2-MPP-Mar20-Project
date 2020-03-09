package ui;

import java.util.HashMap;

import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MembersWindow{ //extends Stage implements LibWindow {
	public static final MembersWindow INSTANCE = new MembersWindow();
	private GridPane grid;
	private Text scenetitle;
	private TableView<LibraryMember> tvMembers;

	private TextArea ta;

	public void setData(String data) {
		ta.setText(data);
	}

	/* This class is a singleton */
	private MembersWindow() {}

	public void init(Stage primaryStage, SplitPane split) {
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);	
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));       
      	
        scenetitle = new Text("List of members");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0);

        tvMembers = new TableView<LibraryMember>();
        tvMembers.prefHeightProperty().bind(split.heightProperty().subtract(100));    
        tvMembers.prefWidthProperty().bind(split.widthProperty());;

	    TableColumn<LibraryMember, String> col = new TableColumn<>("First Name");
	    col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
	    tvMembers.getColumns().add(col);
	    
	    col = new TableColumn<>("Last Name");
	    col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
	    tvMembers.getColumns().add(col);
	    
	    col = new TableColumn<>("Telephone");
	    col.setCellValueFactory(new PropertyValueFactory<>("telephone"));
	    tvMembers.getColumns().add(col);
	    
	    col = new TableColumn<>("Address");
	    col.setCellValueFactory(new PropertyValueFactory<>("address"));
	    tvMembers.getColumns().add(col);

	    tvMembers.setItems(getMembersList());	    
	    
	    
	    grid.add(tvMembers, 0, 1);	
        
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
				EditMemberWindow.INSTANCE.init(primaryStage, split, tvMembers.getSelectionModel().getSelectedItem());
			}
		});		
		editBtn.setDisable(true);
		
        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER_RIGHT);
        hBack.getChildren().add(editBtn);
        hBack.getChildren().add(addMemberBtn);        
        grid.add(hBack, 0, 3);

        
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
		
		tvMembers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	editBtn.setDisable(false);
	        }
	    });
     
	}
	
	public ObservableList<LibraryMember> getMembersList()
	{
		ObservableList<LibraryMember> strings = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,LibraryMember> memberMap = da.readMembersMap();
		memberMap.values().forEach(a -> strings.add(a));
		return strings;
	}
}
