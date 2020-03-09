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
		HashMap<String,BookCopy> hasMapBookCopies = da.readBookCopiesMap();
		BookCopy BC = hasMapBookCopies.get(txtFieldISBN.getText()+ "-" + txtFieldCopyNumber.getText());
		da.changeBookCopyVisiblity(BC);
        //Second Part ==> Save New Entry to Checkout  
        DataAccessFacade.saveNewCheckOutEntry(COE);
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
				
				
				
				if(txtFieldCopyNumber.getText().length()==0)
				{
					UI_Helper_Class.showMessageBoxWarning("You Must Type Book Copy Number");
					return;
				}
				
				if(txtFieldISBN.getText().length() == 0)
				{
					UI_Helper_Class.showMessageBoxWarning("You Must Type Book ISBN");
					return ;
				}
				
				if(cboMemberID.getValue().length()==0)
				{
					UI_Helper_Class.showMessageBoxWarning("You Must Select Member ID.");
					return;
				}
				
				COE = searchHelper.createCheckOutEntry(cboMemberID.getValue(),
						LoginWindow.INSTANCE.getUserID(),
						txtFieldISBN.getText(),
						Integer.parseInt(txtFieldCopyNumber.getText()) ,
						strMessage);
				
				if(strMessage == null)
				{
					try
					{
						SaveChanges();
						UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
						
					}
					catch(Exception e)
					{
						UI_Helper_Class.showMessageBoxError("Error during Checkout Process.");
					}
					
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
					try
					{
						SaveChanges();
						UI_Helper_Class.showMessageBoxInfo("Checkout Entry has been Saved Successfully.");
						clear();
					}
					catch(Exception e)
					{
						UI_Helper_Class.showMessageBoxError("Error during Checkout Process.");
					}
					
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
		 
		Text search = new Text("Search Book By ISBN");
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
			
				if(isbnTextField.getText().toString().length() == 0)
				{
					UI_Helper_Class.showMessageBoxWarning("You Must Type Book ISBN");
					return ;
				}
				
				String strISBN = isbnTextField.getText();
				DataAccess ds = new DataAccessFacade();
				HashMap<String,Book> bookList = ds.readBooksMap();
				Book book = bookList.get(strISBN);
				
				if(book == null)
				{
					UI_Helper_Class.showMessageBoxError(book.getTitle() + " book is not avaliable");
					return;
				}
				
				BookCopy[] copies = book.getCopies();
				boolean bSearchResult = false;
				for(BookCopy CP:copies)
				{
					if(CP.isAvailable())
					{
						bSearchResult= true;
						UI_Helper_Class.showMessageBoxInfo(book.getTitle() + " book copy Is Avaliable");
						break;
					}
				}
				
				if(!bSearchResult)
				{
					UI_Helper_Class.showMessageBoxError(book.getTitle() + " book copy is no available copies");
					
				}
			}
		});

	}
}
