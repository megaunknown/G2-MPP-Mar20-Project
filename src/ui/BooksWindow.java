package ui;

import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;
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

public class BooksWindow {
	public static final BooksWindow INSTANCE = new BooksWindow();

	private GridPane grid;
	TextField copyIDText;

	public ObservableList<Book> getBooksList()
	{
		ControllerInterface con = new SystemController();		
		ObservableList<Book> strings = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> authMap = da.readBooksMap();
		authMap.values().forEach(a -> {
			List<BookCopy> copies = con.allBookCopies(a);
			int available=0;
			for(BookCopy copy: copies)
			{
				if(copy.isAvailable())
					available += 1;
			}
			
			a.setNumAvailableCopies(available);
			strings.add(a);
		});

		return strings;
	}

	/* This class is a singleton */
	private BooksWindow() {}

	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Books Page");
		Text scenetitle;
		grid = new GridPane();
		scenetitle = new Text("List of Books");
        scenetitle.setId("welcome-text");
        grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);	
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));    
        grid.add(scenetitle, 0, 0);
        
		grid.setAlignment(Pos.CENTER);
		final TableView<Book> tableView = new TableView<Book>();
		tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		tableView.prefWidthProperty().bind(split.widthProperty());
		tableView.prefHeightProperty().bind(split.heightProperty().subtract(100));
		tableView.setEditable(true);
	    TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
	    isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

	    TableColumn<Book, String> titleCol = new TableColumn<>("Title");
	    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

	    TableColumn<Book, String> maxCheckoutLengthCol = new TableColumn<>("Max Checkout Length");
	    maxCheckoutLengthCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));
	    
	    TableColumn<Book, String> AvailableBookCopyNum = new TableColumn<>("Num of Available Copies");
	    AvailableBookCopyNum.setCellValueFactory(new PropertyValueFactory<>("numAvailableCopies"));

	    tableView.getColumns().addAll(isbnCol, titleCol, maxCheckoutLengthCol, AvailableBookCopyNum);

	    tableView.setItems(getBooksList());
	    tableView.setMaxWidth(10000);
	    tableView.autosize();
	    grid.add(tableView, 0, 1);

        /***********************************************/
		Button addBookBtn = new Button("Add New Book");

		addBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AddBookWindow.INSTANCE.init(primaryStage, split);
			}
		});
		
		Popup addCopyPopup = new Popup();
		GridPane popupGrid = new GridPane();
		popupGrid.setAlignment(Pos.CENTER);
		popupGrid.setPrefSize(400, 200);
		popupGrid.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;"); 
		Label copyIDLabel = new Label("Book Copy ID *");
		copyIDText = new TextField();	
		
		popupGrid.add(copyIDLabel, 0, 0);
		popupGrid.add(copyIDText, 1, 0);
		Button saveCopyIDBtn = new Button("Save Book Copy");
		saveCopyIDBtn.setDisable(true);
		Button closeBtn = new Button("Close");
		closeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addCopyPopup.hide();
				split.setDisable(false);			
			}
		});
		popupGrid.add(saveCopyIDBtn, 0, 1);
		popupGrid.add(closeBtn, 1, 1);
		addCopyPopup.getContent().add(popupGrid);
        
		Button addBookCopyBtn = new Button("Add New Book Copy");
    	addBookCopyBtn.setDisable(true);
    	
		Button viewBookCopiesBtn = new Button("View Book Copies");
		viewBookCopiesBtn.setDisable(true);

		viewBookCopiesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Book selectedBook = tableView.getSelectionModel().getSelectedItem();
				BookCopiesWindow.INSTANCE.init(primaryStage, split, selectedBook);
			}
		});

		addBookCopyBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addCopyPopup.show(primaryStage);
				split.setDisable(true);
				tableView.setItems(getBooksList());
			}

		});
		
	    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	addBookCopyBtn.setDisable(false);
	        	viewBookCopiesBtn.setDisable(false);
	        }
	    });
	    
	    saveCopyIDBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			Book selectedBook = tableView.getSelectionModel().getSelectedItem();
			BookCopy bookCopy = new BookCopy(selectedBook, Integer.parseInt(copyIDText.getText()), true);
			DataAccess dataAccess = new DataAccessFacade();
			dataAccess.saveNewBookCopy(bookCopy);
			addCopyPopup.hide();
			split.setDisable(false);
			
			//Hanh: refresh window
			init(primaryStage, split);
				
			}

		});
	    
	    copyIDText.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(newValue.isEmpty())
		    	saveCopyIDBtn.setDisable(true);
		    else
		    	saveCopyIDBtn.setDisable(false);
		});

        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER_RIGHT);
        hBack.getChildren().add(addBookBtn);
        hBack.getChildren().add(addBookCopyBtn);
        hBack.getChildren().add(viewBookCopiesBtn);
        grid.add(hBack, 0, 3);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );
	}
	
}
