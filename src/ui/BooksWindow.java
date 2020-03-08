package ui;

import java.util.HashMap;

import org.controlsfx.control.CheckListView;

import business.Author;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Pair;


public class BooksWindow {
	public static final BooksWindow INSTANCE = new BooksWindow();
	private Label lblISBN, lblTitle,lblAuthors;
	private TextField txtISBN, txtTitle;
	
	private final CheckListView<Pair<String, Author>> checkListViewAuthors = new CheckListView<Pair<String,Author>>();

	private GridPane grid;

	private boolean isInitialized = false;
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	public ObservableList<Book> getBooksList()
	{
		ObservableList<Book> strings = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,Book> authMap = da.readBooksMap();
		authMap.values().forEach(a -> strings.add(a));
		return strings;
	}

	/* This class is a singleton */
	private BooksWindow() {}

	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Books Page");
		DataAccess dataAccess = new DataAccessFacade();
		dataAccess.readBooksMap();
		grid = new GridPane();
//		grid.setId("top-container");
		grid.setAlignment(Pos.CENTER);
//        grid.setHgap(5);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
		final TableView<Book> tableView = new TableView<Book>();
		tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		tableView.prefWidthProperty().bind(split.widthProperty());
		tableView.setEditable(true);
	    TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
	    isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

	    TableColumn<Book, String> titleCol = new TableColumn<>("Title");
	    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

	    TableColumn<Book, String> maxCheckoutLengthCol = new TableColumn<>("Max Checkout Length");
	    maxCheckoutLengthCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));

	    tableView.getColumns().addAll(isbnCol, titleCol, maxCheckoutLengthCol);

	    tableView.setItems(getBooksList());
	    tableView.setMaxWidth(10000);
	    tableView.autosize();
//	    tableView.setFillWidth(tableView.getColumns(), true);
	    grid.add(tableView, 0, 0);

        /*********************************************************************/
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
		Label copyIDLabel = new Label("Book Copy ID");
		TextField copyIDText = new TextField();
		popupGrid.add(copyIDLabel, 0, 0);
		popupGrid.add(copyIDText, 1, 0);
		Button saveCopyIDBtn = new Button("Save Book Copy");
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

		addBookCopyBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Book selectedBook = tableView.getSelectionModel().getSelectedItem();
				ControllerInterface con = new SystemController();
				con.allBookCopies(selectedBook);
				Popup viewCopiesPopup = new Popup();
				GridPane popupCopiesGrid = new GridPane();
				popupGrid.setAlignment(Pos.CENTER);
				popupGrid.setPrefSize(400, 200);
				popupGrid.setStyle("-fx-background-color:white;-fx-border-color: black;-fx-border-width:2;-fx-border-radius:3;-fx-hgap:3;-fx-vgap:5;"); 
				Label copyIDLabel = new Label("Book Copy ID");
				TextField copyIDText = new TextField();
				popupGrid.add(copyIDLabel, 0, 0);
				popupGrid.add(copyIDText, 1, 0);
				Button saveCopyIDBtn = new Button("Save Book Copy");
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
			}

		});

		viewBookCopiesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addCopyPopup.show(primaryStage);
				split.setDisable(true);
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
				split.setDisable(false);
			}

		});

        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER);
        hBack.getChildren().add(addBookBtn);
        hBack.getChildren().add(addBookCopyBtn);
        hBack.getChildren().add(viewBookCopiesBtn);
        grid.add(hBack, 0, 2);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );

	}
}