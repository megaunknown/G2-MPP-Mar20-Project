package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.controlsfx.control.CheckListView;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.Librarian;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.searchHelper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


public class AddCheckOutWindow {
	public static final AddCheckOutWindow INSTANCE = new AddCheckOutWindow();

	private Label lblLibMemberID, lblBook,lblBookNumber;
	private TextField txtFieldCopyNumber;
	//private final CheckListView<Pair<String, Author>> checkListViewAuthors = new CheckListView<Pair<String,Author>>();
	private final CheckListView<String> checkListViewAuthors = new CheckListView<String>();
	
	private ComboBox<String> cboMemberID;
	private ComboBox<String> cboBookTitle;
	private GridPane grid;
	
	public void clear() {
		txtFieldCopyNumber.setText("");
		cboBookTitle.setValue("");
		cboMemberID.setValue("");
		checkListViewAuthors.getCheckModel().clearChecks();
	}

	public List<String> getLibraryMembersIDs()
	{
		List<String> strings = new ArrayList<String>();
		DataAccess da = new DataAccessFacade();
		HashMap<String,LibraryMember> membersMap = da.readMembersMap();
		membersMap.values().forEach(a -> strings.add(a.getMemberId()));
		
		return strings;
	}

	public ObservableList<String> getBookTitle()
	{
		ObservableList<String> strings = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> membersMap = da.readBooksMap();
		membersMap.values().forEach(a -> strings.add(a.getTitle()));
		
		return strings;
	}
	
	/* This class is a singleton */
	private AddCheckOutWindow() {}

	final int WINDOW_WIDTH= 1024;
	final int WINDOW_HEIGHT = 785;
	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Checkout Book & Search Books");
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
       /*
        grid.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT); // Default width and height
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        */
       
        
        Text scenetitle = new Text("Checkout a Book");
        scenetitle.setId("welcome-text");

        grid.add(scenetitle, 0, 0);

        //Library Member
        
        lblLibMemberID  = new Label("Library Member ID : ");
        grid.add(lblLibMemberID,0,1);
    	cboMemberID = new ComboBox<String>(FXCollections .observableArrayList(getLibraryMembersIDs())); 
    	cboMemberID.setPrefWidth(180);
    	grid.add(cboMemberID,1,1);
    	
    	//*************************************************************************
    	lblBook = new Label("Book Title : ");
        grid.add(lblBook,0,2);
        cboBookTitle = new ComboBox<String>(FXCollections .observableArrayList(getBookTitle())); 
        grid.add(cboBookTitle,1,2);
        //*************************************************************************
        lblBookNumber = new Label("Book Copy : ");
        grid.add(lblBookNumber,0,3);
        //*************************************************************************
        txtFieldCopyNumber = new TextField();
        grid.add(txtFieldCopyNumber,1,3);
        /***************************************************************************************************************/
        Button saveCheckOutEntryBtn = new Button("Save Checkout Entry");
        Button saveAndNewCheckOutEntryBtn = new Button("Save and New");
        

        saveCheckOutEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String strMessage = null;
				
				CheckOutEntry COE = searchHelper.createCheckOutEntry(cboMemberID.getValue(),
																	LoginWindow.INSTANCE.getUserID(),
																	cboBookTitle.getValue(),
																	Integer.parseInt(txtFieldCopyNumber.getText()) ,
																	strMessage);
				
				if(strMessage == null)
				{
					UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
					//Deserialize
					
				//	searchHelper.getBookCopy(, Integer.parseInt(txtFieldCopyNumber.getText()))
					
				//	searchHelper.getBookCopy(ISBN, BookCopy)
					//Serialize
				}
				else
				{
					UI_Helper_Class.showMessageBoxError(strMessage);
				}

			}
		});

        saveAndNewCheckOutEntryBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String strMessage = null;
				CheckOutEntry COE = searchHelper.createCheckOutEntry(cboMemberID.getValue(),
						LoginWindow.INSTANCE.getUserID(),
						cboBookTitle.getValue(),
						Integer.parseInt(txtFieldCopyNumber.getText()) ,
						strMessage);
				
				if(strMessage == null)
				{
					UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
					clear();
				}
				else
				{
					UI_Helper_Class.showMessageBoxError(strMessage);
				}
					
				
			}
		});

       

        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER);
        hBack.getChildren().add(saveCheckOutEntryBtn);
        hBack.getChildren().add(saveAndNewCheckOutEntryBtn);
        
        grid.add(hBack, 0, 4);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
		
		/****************************** Search Book By ISBN **********************/
		 grid.add(new HBox(100), 0, 5);
		 
		Text search = new Text("Search Book Data");
		search.setId("welcome-text");
        grid.add(search, 0, 6);
        
        
        Label lblBookISBN = new Label("Book ISBN :");
		grid.add(lblBookISBN, 0, 7);

		final TextField isbnTextField = new TextField();
		grid.add(isbnTextField, 1, 7);

		
		Button searchBtn = new Button("Search By ISBN");
		grid.add(searchBtn, 0, 8,2,1);
		searchBtn.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
			
				String strISBN = isbnTextField.getText();
				DataAccess ds = new DataAccessFacade();
				HashMap<String,Book> bookList = ds.readBooksMap();
				Book book = bookList.get(strISBN);
				if(book == null)
				{
					UI_Helper_Class.showMessageBoxError("The book is not avaliable");
					return;
				}
				
				BookCopy[] copies = book.getCopies();
				boolean bSearchResult = false;
				for(BookCopy CP:copies)
				{
					if(CP.isAvailable())
					{
						bSearchResult= true;
						UI_Helper_Class.showMessageBoxInfo("The Book Is Avaliable");
						break;
					}
				}
				if(!bSearchResult)
				{
					UI_Helper_Class.showMessageBoxError("There is no available copies");
					
				}
			}
		});

	}
}
