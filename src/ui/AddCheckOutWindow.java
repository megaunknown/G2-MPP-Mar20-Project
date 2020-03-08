package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.*; 
import org.controlsfx.control.CheckListView;
import java.util.Map;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckOutEntry;
import business.Librarian;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.searchHelper;
import dataaccess.DataAccessFacade.StorageType;
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

	/*************************************** UI ************************************/
	private Label lblLibMemberID,lblBookNumber,lblBookISBN;
	private TextField txtFieldISBN,txtFieldCopyNumber;
	
	/*******************************************************************************/
	private CheckOutEntry COE;
	private BookCopy bookcopy;
	private Book book;
	
	/****************************************************************************/
	private ComboBox<String> cboMemberID;
	private GridPane grid;
	
	public void clear() {
		txtFieldCopyNumber.setText("");
		cboMemberID.setValue("");
		txtFieldISBN.setText("");
		cboMemberID.setValue("");
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



	private void SaveChanges()
	{
		DataAccess da = new DataAccessFacade();
		//First Part ===> Save The Changes to Book Copy
		HashMap<Integer,BookCopy> hasMapBookCopies = da.readBookCopiesMap();
		// Using for-each loop 
        for (Map.Entry mapElement : hasMapBookCopies.entrySet()) { 
            String bookCopy = (String)mapElement.getKey(); 
            bookcopy = (BookCopy)mapElement.getValue();
            book = bookcopy.getBook();
            if(bookCopy.equals(txtFieldCopyNumber.getText()) && 
               book.getIsbn().equals(txtFieldISBN.getText()))
            {
            	bookcopy.changeAvailability();
            	
            	//Write the HashMap Again...
            	List<BookCopy> bcList = HashMapToList(hasMapBookCopies);
            	DataAccessFacade.loadBookCopiesMap(bcList);
            	break;
            }
        }
        
        //Second Part ==> Save New Entry to Checkout
        HashMap<String, CheckOutEntry> readCheckOutEntries = DataAccessFacade.readCheckOutEntriesMap();
        readCheckOutEntries.put(COE.getEntryID()+"", COE);
        DataAccessFacade.saveToStorage(StorageType.CHECKOUTENTRIES, readCheckOutEntries);
        
        
        
	}
	
	private List<BookCopy> HashMapToList(HashMap<Integer,BookCopy> hasMapBookCopies)
	{
		List<BookCopy> mList= new ArrayList<BookCopy>();
		
		 for (Map.Entry mapElement : hasMapBookCopies.entrySet()) { 
	            String bookCopy = (String)mapElement.getKey(); 
	            BookCopy bookcopy = (BookCopy)mapElement.getValue();
	            mList.add(bookcopy);
	        } 
	
		return mList;
	}
	
	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Checkout Book & Search Books");
		grid = new GridPane();  
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Checkout a Book");
        scenetitle.setId("welcome-text");

        grid.add(scenetitle, 0, 0);

        //Library Member
        lblLibMemberID  = new Label("Library Member ID : ");
        grid.add(lblLibMemberID,0,1);
    	cboMemberID = new ComboBox<String>(FXCollections .observableArrayList(searchHelper.getLibraryMembersIDs())); 
    	cboMemberID.setPrefWidth(180);
    	grid.add(cboMemberID,1,1);
    	
    	//*************************************************************************
    	lblBookISBN = new Label("Book ISBN : ");
        grid.add(lblBookISBN,0,2);
        txtFieldISBN = new TextField();
        grid.add(txtFieldISBN,1,2);
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
				
				COE = searchHelper.createCheckOutEntry(cboMemberID.getValue(),
						LoginWindow.INSTANCE.getUserID(),
						txtFieldISBN.getText(),
						Integer.parseInt(txtFieldCopyNumber.getText()) ,
						strMessage);
				
				if(strMessage == null)
				{
					
					UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
					SaveChanges();
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
				COE = searchHelper.createCheckOutEntry(cboMemberID.getValue(),
						LoginWindow.INSTANCE.getUserID(),
						txtFieldISBN.getText(),
						Integer.parseInt(txtFieldCopyNumber.getText()) ,
						strMessage);
				
				if(strMessage == null)
				{
					UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
					SaveChanges();
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
