package ui;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.ControllerInterface;
import business.SystemController;
import business.ValidationException;
import business.ValidationHelper;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class CheckoutRecords {
	public static final CheckoutRecords INSTANCE = new CheckoutRecords();

	private GridPane grid;

	
	public ObservableList<checkedOutView> getCheckOutRecords()
	{
		ObservableList<checkedOutView> obserList = FXCollections.observableArrayList();
		HashMap<String,CheckOutEntry> authMap = DataAccessFacade.readCheckOutEntriesMap();
		
	    for (Entry<String, CheckOutEntry> mapElement : authMap.entrySet()) { 
	    	CheckOutEntry coe = mapElement.getValue();
	    	
	    	checkedOutView checkedOutViewInstance = new checkedOutView();
	    	checkedOutViewInstance.setBookTile(coe.getBookCopy().getBook().getTitle());
	    	checkedOutViewInstance.setCheckedOutDate(coe.getCheckoutDate());
	    	checkedOutViewInstance.setDueDate(coe.getDueDate());
	    	checkedOutViewInstance.setEntryID(coe.getEntryID());
	    	checkedOutViewInstance.setISBN(coe.getBookCopy().getBook().getIsbn());
	    	checkedOutViewInstance.setMemberId(coe.getMember().getMemberId());
	    	checkedOutViewInstance.setMemberName(coe.getMember().getLastName() + " " + coe.getMember().getFirstName());
	    	checkedOutViewInstance.setReturnDate(coe.getReturnDate());
	    	obserList.add(checkedOutViewInstance);
        } 
		return obserList;
	}

	/* This class is a singleton */
	private CheckoutRecords() {}

	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Checkout Records");

		Text scenetitle;
		grid = new GridPane();
		scenetitle = new Text("Checkout Records");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0);
        
        grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);	
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setAlignment(Pos.CENTER);
		
		final TableView<checkedOutView> tableView = new TableView<checkedOutView>();
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.prefWidthProperty().bind(split.widthProperty());
		tableView.prefHeightProperty().bind(split.heightProperty().subtract(100));
		tableView.setEditable(true);
		
	    TableColumn<checkedOutView, Integer> EntryCol = new TableColumn<>("Entry ID");
	    EntryCol.setCellValueFactory(new PropertyValueFactory<>("entryID"));

	    TableColumn<checkedOutView, LocalDate> CheckedDate = new TableColumn<>("Checked Date");
	    CheckedDate.setCellValueFactory(new PropertyValueFactory<>("CheckedOutDate"));

	    TableColumn<checkedOutView, LocalDate> DueDate = new TableColumn<>("Due Date");
	    DueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
	    
	    TableColumn<checkedOutView, LocalDate> ReturnDate = new TableColumn<>("Return Date");
	    ReturnDate.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
	    
	    TableColumn<checkedOutView, String> MemberName = new TableColumn<>("Member Name");
	    MemberName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
	    
	    TableColumn<checkedOutView, String> MemberId = new TableColumn<>("Member Id");
	    MemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));
	    
	    TableColumn<checkedOutView, String> ISBN = new TableColumn<>("ISBN");
	    ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
	    
	    TableColumn<checkedOutView, String> BookTile = new TableColumn<>("Book Title");
	    ISBN.setCellValueFactory(new PropertyValueFactory<>("BookTile"));
	    
	    tableView.getColumns().addAll(EntryCol,ISBN,BookTile,CheckedDate,DueDate,ReturnDate,MemberId,MemberName);//, titleCol, maxCheckoutLengthCol, AvailableBookCopyNum);

	    tableView.setItems(getCheckOutRecords());
	    tableView.setMaxWidth(10000);
	    tableView.autosize();
	    grid.add(tableView, 0, 1);
	    
	    split.getItems().set(1,grid);
	    split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
	}
	
}
