package ui;

import java.util.List;

import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.SystemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BookCopiesWindow {
	public static final BookCopiesWindow INSTANCE = new BookCopiesWindow();

	private GridPane grid;

	public ObservableList<BookCopy> getBookCopiesList(Book selectedBook)
	{
		ObservableList<BookCopy> strings = FXCollections.observableArrayList();
		ControllerInterface con = new SystemController();
		List<BookCopy> authMap = con.allBookCopies(selectedBook);
		authMap.forEach(a -> strings.add(a));
		return strings;
	}

	/* This class is a singleton */
	private BookCopiesWindow() {}

	@SuppressWarnings("unchecked")
	public void init(Stage primaryStage, SplitPane split, Book selectedBook) {
		primaryStage.setTitle("Book Copies Page");
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		final TableView<BookCopy> tableView = new TableView<BookCopy>();
		tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		tableView.prefWidthProperty().bind(split.widthProperty());
		tableView.setEditable(true);
	    tableView.setItems(getBookCopiesList(selectedBook));
	    TableColumn<BookCopy, String> copyIdCol = new TableColumn<>("Copy Number");
	    copyIdCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));

	    TableColumn<BookCopy, Boolean> availableCol = new TableColumn<>("Available");
	    availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
	    availableCol.setCellFactory(col -> new TableCell<BookCopy, Boolean>() {
	        private final ImageView imageView = new ImageView();

	        {
	            // initialize ImageView + set as graphic
	            imageView.setFitWidth(20);
	            imageView.setFitHeight(20);
	            setGraphic(imageView);
	        }

	        @Override
	        protected void updateItem(Boolean item, boolean empty) {
	            if (empty || item == null) {
	                // no image for empty cells
	                imageView.setImage(null);
	            } else {
	                // set image for non-empty cell
	                imageView.setImage(item ? new Image("ui/yes.png") : new Image("ui/no.png"));
	            }
	        }

	    });

	    tableView.getColumns().addAll(copyIdCol, availableCol);

	    tableView.setMaxWidth(10000);
	    tableView.autosize();
	    grid.add(tableView, 0, 0);

        /***********************************************/
		Button backBtn = new Button("Back");

		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				BooksWindow.INSTANCE.init(primaryStage, split);
			}
		});
		        	    
        HBox hBack = new HBox(10);
        hBack.setAlignment(Pos.CENTER);
        hBack.getChildren().add(backBtn);
        grid.add(hBack, 0, 2);
        split.getItems().set(1, grid);
		split.lookupAll(".split-pane-divider").stream()
        .forEach(div ->  {
        	div.setMouseTransparent(true);
        	div.setStyle("-fx-padding: 0 1 0 1");
        } );

	}
}
