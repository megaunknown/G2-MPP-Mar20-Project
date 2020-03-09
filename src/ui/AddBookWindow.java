package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.controlsfx.control.CheckListView;

import business.Author;
import business.Book;
import business.ValidationException;
import business.ValidationHelper;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


public class AddBookWindow {
	public static final AddBookWindow INSTANCE = new AddBookWindow();

	private Label lblISBN, lblTitle,lblAuthors, lblMaxCheckoutLength;
	RadioButton rbMaxCheckoutLength_7, rbMaxCheckoutLength_21;
	private TextField txtISBN, txtTitle;
	private final CheckListView< Author> checkListViewAuthors = new CheckListView<Author>();

	private GridPane grid;
	
	public void clear() {
		txtISBN.setText("");
		txtTitle.setText("");
		checkListViewAuthors.getCheckModel().clearChecks();
	}

	public ObservableList<Author> getAuthorsList()
	{
		ObservableList<Author> obsList = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String,Author> authMap = da.readAuthorsMap();
		authMap.values().forEach(a -> obsList.add(a));
		return obsList;
	}

	/* This class is a singleton */
	private AddBookWindow() {}

	public void init(Stage primaryStage, SplitPane split) {
		primaryStage.setTitle("Add Book Page");
		grid = new GridPane();
		grid.setId("top-container");
		grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Insert Book Information");
        scenetitle.setId("welcome-text");

        grid.add(scenetitle, 0, 0);

        lblISBN = new Label("ISBN (*)");
        grid.add(lblISBN,0,1);
        txtISBN = new TextField();
        grid.add(txtISBN,1,1);
        
        lblTitle  = new Label("Title (*)");
        grid.add(lblTitle,0,2);
        txtTitle = new TextField();
        grid.add(txtTitle,1,2);

        lblAuthors = new Label("Authors (*)");
        grid.add(lblAuthors,0,3);

        checkListViewAuthors.setItems(getAuthorsList());
        checkListViewAuthors.setPrefWidth(400);
        checkListViewAuthors.setPrefHeight(400);
        grid.add(checkListViewAuthors,1,3);
        checkListViewAuthors.getCheckModel().getCheckedItems().addListener(new ListChangeListener<Author>()
        {
			@Override
			public void onChanged(Change<? extends Author> c) {
				checkListViewAuthors.getCheckModel().getCheckedItems();
			}
        });
        
        lblMaxCheckoutLength = new Label("Max Checkout Length");
        grid.add(lblMaxCheckoutLength,0,4);
        ToggleGroup group = new ToggleGroup();

        rbMaxCheckoutLength_7 = new RadioButton("7 days");
        rbMaxCheckoutLength_7.setToggleGroup(group);
        rbMaxCheckoutLength_7.setSelected(true);
        

        rbMaxCheckoutLength_21 = new RadioButton("21 days");
        rbMaxCheckoutLength_21.setToggleGroup(group);
        
        GridPane gridMax= new GridPane();
        gridMax.add(rbMaxCheckoutLength_7, 0, 0);
        gridMax.add(rbMaxCheckoutLength_21, 1, 0);
        
        grid.add(gridMax,1,4);


        /***********************************************/
        Button saveBookBtn = new Button("Save");
        Button saveNewBookBtn = new Button("Save and New");
        Button backBtn = new Button("Back");

        saveBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isDataValid())
				{	
					ObservableList<Author> strings = checkListViewAuthors.getCheckModel().getCheckedItems();
					List<Author> lst = new ArrayList<Author>();
					strings.forEach(val -> lst.add(val));
					int maxCheckoutLength= rbMaxCheckoutLength_7.isSelected()? 7: 21;
					DataAccess dataAccess = new DataAccessFacade();
					dataAccess.saveNewBook(new Book(txtISBN.getText(), txtTitle.getText(), maxCheckoutLength, lst));
					BooksWindow.INSTANCE.init(primaryStage, split);
				}
			}
		});

        saveNewBookBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isDataValid())
				{
					ObservableList<Author> strings = checkListViewAuthors.getCheckModel().getCheckedItems();
					List<Author> lst = new ArrayList<Author>();
					strings.forEach(val -> lst.add(val));
					int maxCheckoutLength= rbMaxCheckoutLength_7.isSelected()? 7: 21;
					DataAccess dataAccess = new DataAccessFacade();
					dataAccess.saveNewBook(new Book(txtISBN.getText(), txtTitle.getText(), maxCheckoutLength, lst));
					UI_Helper_Class.showMessageBoxInfo("Saved successfully");
					clear();
				}
			}
		});

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				BooksWindow.INSTANCE.init(primaryStage, split);
			}
		});

        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER_RIGHT);
        hBack.getChildren().add(saveBookBtn);
        hBack.getChildren().add(saveNewBookBtn);
        hBack.getChildren().add(backBtn);
        grid.add(hBack, 0, 6, 2, 1);
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
			validate.mandatoryValidator(txtTitle.getText());
			validate.mandatoryValidator(txtISBN.getText());
			
			ValidationHelper<ObservableList> validateList= new ValidationHelper<ObservableList>();
			validateList.mandatoryValidator(checkListViewAuthors.getCheckModel().getCheckedItems());
		}
		catch(ValidationException ex)
		{
			UI_Helper_Class.showMessageBoxError("Error! " + ex.getMessage());
			return false;
		}
		
		
		return true;
	}
}
