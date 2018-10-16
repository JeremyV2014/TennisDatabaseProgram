/*
 *    Program Name:  Assignment2
 *    Purpose:       This class is the front-end main program for users to interact with a TennisDatabase.
 *                   It allows the user to view players, matches, and matches of player as well as insert/delete players or insert matches.
 *                   Furthermore, it allows the user to import/export database files and reset the database
 *    Input:         Pre-exisiting data can be imported via a text file.
 *                   User can insert new/modify/delete players or insert new matches through a variety of graphical controls.
 *    Output:        Messages/Data displayed through graphical controls and database can be exported as a text file.
 *    Developer:     Jeremy Maxey-Vesperman
 *    Modified:      06/08/2018
 */  
 
// Import all necessary javafx components to implement GUI
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

// Import other necessary classes
import java.io.*;
import java.time.LocalDate;
import TennisDatabase.*;
 
public class Assignment2 extends Application {
   
   private TennisDatabase tdb = new TennisDatabase();
   
   /* Create Graphical Containers */
   private VBox vbox = new VBox();
   private GridPane grid = new GridPane();
   private Scene scene = new Scene(vbox, 900, 600);
   
   /* Create Graphical Controls */
   // Title
   private Text sceneTitle = new Text("Welcome to the CS-102 Tennis Manager:");
   
   // User Message Label
   private Label labelUserMsg = new Label();
   
   // Menu
   private MenuBar menuBar = new MenuBar();
   private Menu menuFile = new Menu("File");
   private MenuItem miReset = new MenuItem("Reset Database");
   private MenuItem miImport = new MenuItem("Import Database");
   private MenuItem miExport = new MenuItem("Export Database");
   private MenuItem miExit = new MenuItem("Exit");
   
   // TableViews
   // Table View UI Control for Players.
   private TableView<TennisPlayer> tableViewPlayers = new TableView<>();
   private ObservableList<TennisPlayer> obsListPlayers;
   private TableColumn< TennisPlayer, String > colUID = new TableColumn<>( "UID" );
   private TableColumn<TennisPlayer, String > colFirst = new TableColumn<>( "First Name" );
   private TableColumn< TennisPlayer, String > colLast = new TableColumn<>( "Last Name" );
   private TableColumn< TennisPlayer, Integer > colYear = new TableColumn<>( "Birth Year" );
   private TableColumn< TennisPlayer, String > colCountry = new TableColumn<>( "Country" );
   private TableColumn< TennisPlayer, String > colWinLoss = new TableColumn<>( "Record" );
   // Table View UI Control for Matches.
   private TableView<TennisMatch> tableViewMatches = new TableView<>();
   private ObservableList<TennisMatch> obsListMatches;
   private TableColumn< TennisMatch, String > colP1 = new TableColumn<>( "Player 1" );
	private TableColumn< TennisMatch, String > colP2 = new TableColumn<>( "Player 2" );
	private TableColumn< TennisMatch, String > colDate = new TableColumn<>( "Match Date" );
	private TableColumn< TennisMatch, String > colTournament = new TableColumn<>( "Tournament" );
   private TableColumn< TennisMatch, String > colScore = new TableColumn<>( "Score" );
	private TableColumn< TennisMatch, Integer > colWinner = new TableColumn<>( "Winner" );
   // Table View UI Control for Matches of a Player.
   private TableView<TennisMatch> tableViewPlayerMatches = new TableView<>();
   private ObservableList<TennisMatch> obsListPlayerMatches;
   private TableColumn< TennisMatch, String > colPlayersP1 = new TableColumn<>( "Player 1" );
	private TableColumn< TennisMatch, String > colPlayersP2 = new TableColumn<>( "Player 2" );
	private TableColumn< TennisMatch, String > colPlayersDate = new TableColumn<>( "Match Date" );
	private TableColumn< TennisMatch, String > colPlayersTournament = new TableColumn<>( "Tournament" );
	private TableColumn< TennisMatch, String > colPlayersScore = new TableColumn<>( "Score" );
	private TableColumn< TennisMatch, Integer > colPlayersWinner = new TableColumn<>( "Winner" );
   
   // Button Controls
   // Input Buttons
   private Button addPlyrBtn = new Button("Add Player");
   private Button addMtchBtn = new Button("Add Match");
   private Button rmvPlyrBtn = new Button("Remove Selected Player");
   // View Buttons
   private Button dispPlyrMtchBtn = new Button("Display Player's Matches");
   private Button dispPlyrBtn = new Button("Display All Players");
   private Button dispMtchBtn = new Button("Display All Matches");
   
   // Input Boxes
   // Tennis Player
   private TextField textFieldPUID = new TextField();
   private TextField textFieldPFirst = new TextField();
   private TextField textFieldPLast = new TextField();
   private TextField textFieldPYear = new TextField();
   private TextField textFieldPCountry = new TextField();
   // Tennis Match
   private TextField textFieldMatchP1 = new TextField();
   private TextField textFieldMatchP2 = new TextField();
   private TextField textFieldMatchTournament = new TextField();
   private TextField textFieldMatchScores = new TextField();
   private DatePicker datePickerMatch = new DatePicker();

   
   // Desc.:   Start method of application. Creates an application window that provides various functions to the user
   //          to allow them to interact with the TennisDatabase.
   // Input:   Tennis database file to import. If invalid/not provided. TennisDatabase creates an empty database
   //          User input for interacting with database is entered through graphical controls on the GUI.
   // Output:  Relevant prompts, warnings, data, etc. displayed through graphical controls on the GUI.
   public void start( Stage mainStage ) {
      // Set application window title bar text
      mainStage.setTitle("CS-102 Tennis Manager");
      
      // Set the scene graph for this stage
      mainStage.setScene(scene); 
      
      // Configure VBox container for organizing content and menu
      vbox.setSpacing( 10 );
      
      // Add menu and content window to vbox container
      vbox.getChildren().addAll(menuBar, grid);
      
      // Configure the content pane
      grid.setAlignment( Pos.CENTER );
      grid.setHgap( 20 );
      grid.setVgap( 20 );
      grid.setPadding(new Insets(0, 15, 15, 15));
      
      // Configure Menu
      menuFile.getItems().addAll(miReset, miImport, miExport, new SeparatorMenuItem(), miExit);
      menuBar.getMenus().add(menuFile);
      
      // Configure scene title font
      sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
      
      // Configure tennis player tableview
      colUID.setMinWidth( 100 );
      colFirst.setMinWidth( 150 );
      colLast.setMinWidth( 150 );
      colYear.setMinWidth( 100 );
      colCountry.setMinWidth( 150 );
      colWinLoss.setMinWidth( 100 );
      
      colUID.setCellValueFactory( new PropertyValueFactory<>( "id" ) );
      colFirst.setCellValueFactory( new PropertyValueFactory<>( "firstName" ) );
      colLast.setCellValueFactory( new PropertyValueFactory<>( "lastName" ) );
      colYear.setCellValueFactory( new PropertyValueFactory<>( "birthYear" ) );
      colCountry.setCellValueFactory( new PropertyValueFactory<>( "country" ) );
      colWinLoss.setCellValueFactory( new PropertyValueFactory<>( "winLossString" ) );
      
      tableViewPlayers.getColumns().addAll(colUID, colFirst, colLast, colYear, colCountry, colWinLoss);
      
      // Configure tennis match tableview
      colP1.setMinWidth( 150 );
		colP2.setMinWidth( 150 );
		colDate.setMinWidth( 75 );
		colTournament.setMinWidth( 150 );
		colScore.setMinWidth( 150 );
		colWinner.setMinWidth( 50 );
		
      colP1.setCellValueFactory( new PropertyValueFactory<>( "Player1Name" ) );
      colP2.setCellValueFactory( new PropertyValueFactory<>( "Player2Name" ) );
      colDate.setCellValueFactory( new PropertyValueFactory<>( "DateString" ) );
      colTournament.setCellValueFactory( new PropertyValueFactory<>( "Tournament" ) );
      colScore.setCellValueFactory( new PropertyValueFactory<>( "Score" ) );
      colWinner.setCellValueFactory( new PropertyValueFactory<>( "Winner" ) );
      
		tableViewMatches.getColumns().addAll( colP1, colP2, colDate, colTournament, colScore, colWinner );
      
      // Configure tennis player's match tableview
      colPlayersP1.setMinWidth( 150 );
		colPlayersP2.setMinWidth( 150 );
		colPlayersDate.setMinWidth( 75 );
		colPlayersTournament.setMinWidth( 150 );
		colPlayersScore.setMinWidth( 150 );
		colPlayersWinner.setMinWidth( 50 );
		
      colPlayersP1.setCellValueFactory( new PropertyValueFactory<>( "Player1Name" ) );
      colPlayersP2.setCellValueFactory( new PropertyValueFactory<>( "Player2Name" ) );
      colPlayersDate.setCellValueFactory( new PropertyValueFactory<>( "DateString" ) );
      colPlayersTournament.setCellValueFactory( new PropertyValueFactory<>( "Tournament" ) );
      colPlayersScore.setCellValueFactory( new PropertyValueFactory<>( "Score" ) );
      colPlayersWinner.setCellValueFactory( new PropertyValueFactory<>( "Winner" ) );
      
      tableViewPlayerMatches.getColumns().addAll( colPlayersP1, colPlayersP2, colPlayersDate, colPlayersTournament, colPlayersScore, colPlayersWinner );
      
      // Configure player textbox prompt text
      textFieldPUID.setPromptText("Enter Player UID");
      textFieldPFirst.setPromptText("Enter First Name");
      textFieldPLast.setPromptText("Enter Last Name");
      textFieldPYear.setPromptText("Enter Birth Year (YYYY)");
      textFieldPCountry.setPromptText("Enter Country");
      
      // Configure match textbox prompt text
      textFieldMatchP1.setPromptText("Enter Player 1 ID");
      textFieldMatchP2.setPromptText("Enter Player 2 ID");
      textFieldMatchTournament.setPromptText("Enter Tournament");
      textFieldMatchScores.setPromptText("Enter Set Scores");
      datePickerMatch.setPromptText("Pick Match Date");
      
      
      // Add all controls to the content pane
      grid.add(sceneTitle, 0, 0, 5, 1);
      
      grid.add(labelUserMsg, 0, 2, 5, 1);
      
      grid.add(tableViewPlayers, 0, 1, 5, 1);
      
      grid.add(tableViewMatches, 0, 1, 5, 1);
      
      grid.add(tableViewPlayerMatches, 0, 1, 5, 1);
      
      grid.add(textFieldPUID, 0, 3);
      grid.add(textFieldPFirst, 1, 3);
      grid.add(textFieldPLast, 2, 3);
      grid.add(textFieldPYear, 3, 3);
      grid.add(textFieldPCountry, 4, 3);      
      
      grid.add(textFieldMatchP1, 0, 3);
      grid.add(textFieldMatchP2, 1, 3);
      grid.add(textFieldMatchTournament, 2, 3);
      grid.add(textFieldMatchScores, 3, 3);
      grid.add(datePickerMatch, 4, 3);
      
      grid.add(addPlyrBtn, 0, 4);
      grid.add(addMtchBtn, 0, 4);
      grid.add(rmvPlyrBtn, 1, 4);
      
      grid.add(dispPlyrBtn, 3, 4);
      grid.add(dispMtchBtn, 4, 4);
      grid.add(dispPlyrMtchBtn, 3, 4);
      
      // Set initial visibility of controls
      tableViewMatches.setVisible(false);
      
      tableViewPlayerMatches.setVisible(false);
      
      textFieldMatchP1.setVisible(false);
      textFieldMatchP2.setVisible(false);
      textFieldMatchTournament.setVisible(false);
      textFieldMatchScores.setVisible(false);
      datePickerMatch.setVisible(false);

      addMtchBtn.setVisible(false);
      
      dispPlyrBtn.setVisible(false);
      
      // Disable buttons that require a tableview row to be selected
      rmvPlyrBtn.setDisable(true);
      dispPlyrMtchBtn.setDisable(true);
      
      
      /* Setup ActionEvent Handlers */
      
      // Menu Interface
      // Reset Menu Item
      miReset.setOnAction(
         (ActionEvent e) -> {
            tdb.resetDatabase(); // Reset the database
            
            // Update view
            obsListMatches = null; // force tableview update
            tableViewMatches.setItems( obsListMatches );
            obsListPlayers = null; // force tableview update
            tableViewPlayers.setItems(obsListPlayers);
            
            // Disable buttons that can't be used without any tableview row selected
            rmvPlyrBtn.setDisable(true);
            dispPlyrMtchBtn.setDisable(true);
            
            // Update user message window
            labelUserMsg.setText("Database Sucessfully Reset.");
         }
      );
      // Import Menu Item
      miImport.setOnAction(
         (ActionEvent e) -> {
            // Get the user-selected file
            FileChooser fcImport = new FileChooser();
            fcImport.setTitle("Import Database");
            fcImport.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
            File file = fcImport.showOpenDialog(mainStage);
            
            // Check if they actually selected a file
            if (file != null) {
               try {
                  tdb.loadFromFile(file); // attempt to load database from selected file
               } catch (FileNotFoundException ex) {
                  System.out.println("Error! Invalid database file selected.");
                  labelUserMsg.setText("Error! Invalid Database File Selected.\r\nCreating Empty Database...");
               } 
               try {
                  // Update view
                  obsListMatches = tdb.getMatchesList();
                  tableViewMatches.setItems(obsListMatches);
                  obsListPlayers = tdb.getPlayersList();
                  tableViewPlayers.setItems(obsListPlayers);
                  
                  // Update user message window
                  labelUserMsg.setText("Database Successfully Imported.");
               }  catch (TennisDatabaseRuntimeException ex) {
                  System.out.println("Error! Invalid database file selected.");
                  labelUserMsg.setText("Error! Invalid Database File Selected.\r\nCreating Empty Database...");
               }
               
               // Disable buttons that can't be used without any tableview row selected
               rmvPlyrBtn.setDisable(true);
               dispPlyrMtchBtn.setDisable(true);
            }
         }
      );
      // Export Menu Item
      miExport.setOnAction(
         (ActionEvent e) -> {
            // Get user-selected file to export database to
            FileChooser fcExport = new FileChooser();
            fcExport.setTitle("Export Database");
            fcExport.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
            File file = fcExport.showSaveDialog(mainStage);
            
            // Check if they actually selected a file
            if (file != null) {
               try {
                  tdb.exportDatabase(file); // Pass file to TennisDatabase to handle the export
                  labelUserMsg.setText("Database Successfully Exported.\r\nLocation: " + file.getPath());
               } catch (TennisDatabaseRuntimeException ex) {
                  labelUserMsg.setText("Error! Database Export Failed.");
               }
            }
         }
      );
      // Exit Menu Item
      miExit.setOnAction( ( ActionEvent t ) -> { System.exit( 0 ); } ); // Exit application 
      
      // Tennis Player TableView Mouse Click Event
      tableViewPlayers.setOnMouseClicked(
         (MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
               if (!(tableViewPlayers.getSelectionModel().getSelectedItem() == null)) { // make sure that there is a selected item
                  // A row is selected, enable the user controls that have this dependency
                  rmvPlyrBtn.setDisable(false);
                  dispPlyrMtchBtn.setDisable(false);
               }
            }
         }
      );
      
      // Input Buttons
      // Add Player Button      
      addPlyrBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            public void handle( ActionEvent e ) {
               // Read all input fields and enforce proper data entry
               String id = textFieldPUID.getText();
               String first = textFieldPFirst.getText();
               String last = textFieldPLast.getText();
               if (id.equals("") || first.equals("") || last.equals("")) {
                  String msg = (id.equals("")) ? "Unique ID." : (first.equals("") ? "First Name." : "Last Name.");
                  labelUserMsg.setText("Please Enter a " + msg);
               } else { 
                  try {
                     int year = Integer.parseInt(textFieldPYear.getText());
                     if (year < 0 || year > 9999) {
                        labelUserMsg.setText("Please Enter a Valid Birth Year (YYYY).");
                     } else {
                        String country = textFieldPCountry.getText();
                        if (country.equals("")) { labelUserMsg.setText("Please Enter a Country"); }
                        else {
                           try {
                              // Attempt to insert the player
                              tdb.insertPlayer(id, first, last, year, country);
                              
                              // Update View for player tableview
                              obsListPlayers = tdb.getPlayersList();
                              tableViewPlayers.setItems(obsListPlayers);
                              
                              // Update user message window
                              labelUserMsg.setText("Player Successfully Added.");
                           } catch (TennisDatabaseRuntimeException ex) {
                              // If failed... player already exists... update exisiting player
                              tdb.updatePlayer(id, first, last, year, country);
                              
                              // Update View for player tableview
                              tableViewPlayers.getColumns().clear(); // Force update
                              tableViewPlayers.getColumns().addAll(colUID, colFirst, colLast, colYear, colCountry, colWinLoss);
                              obsListPlayers = tdb.getPlayersList();
                              tableViewPlayers.setItems(obsListPlayers);
                              
                              // Update user message window
                              labelUserMsg.setText("Updated Existing Player.");
                           }
                           // Update view for match tableview
                           tableViewMatches.getColumns().clear(); // Force update
                           tableViewMatches.getColumns().addAll(colP1, colP2, colDate, colTournament, colScore, colWinner);
                           obsListMatches = tdb.getMatchesList();
                           tableViewMatches.setItems( obsListMatches );
                        }
                     }
                  } catch (NumberFormatException ex) {
                     labelUserMsg.setText("Please Enter a Valid Birth Year (YYYY).");
                  }
               }
            }
         }
      );
      // Add Match Button
      addMtchBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            //...
            @Override
            public void handle( ActionEvent e) {
            // Read all input fields and enforce proper data entry
               String p1 = textFieldMatchP1.getText();
               String p2 = textFieldMatchP2.getText();
               if (!p1.equals(p2)) {
                  String tournament = textFieldMatchTournament.getText();
                  if (!tournament.equals("")) {
                     String score = textFieldMatchScores.getText();
                     try {
                        LocalDate date = datePickerMatch.getValue();
                        int year = date.getYear();
                        int month = date.getMonthValue();
                        int day = date.getDayOfMonth();
                        
                        // Attempt to insert new match
                        tdb.insertMatch(p1, p2, year, month, day, tournament, score);
                        
                        // Update view
                        obsListMatches = tdb.getMatchesList();
                        tableViewMatches.setItems( obsListMatches );
                        tableViewPlayers.getColumns().clear(); // Force update
                        tableViewPlayers.getColumns().addAll(colUID, colFirst, colLast, colYear, colCountry, colWinLoss);
                        obsListPlayers = tdb.getPlayersList();
                        tableViewPlayers.setItems(obsListPlayers);
                        
                        // Update user meesage window
                        labelUserMsg.setText("Match Successfully Added.");                           
                     } catch (NullPointerException ex) {
                        System.out.println("Error! No/Invalid Date selected!");
                        labelUserMsg.setText("Please Enter a Valid Date.");
                     } catch (TennisDatabaseRuntimeException ex) {
                        System.out.println(ex.getMessage());
                        labelUserMsg.setText(ex.getMessage());
                     }
                  } else {
                     labelUserMsg.setText("Please Enter a Tournament Name.");
                  }
               } else {
                  labelUserMsg.setText("Please Make Sure Player IDs Aren't Identical.");
               }
            }
         }
      );
      // Remove Player Button
      rmvPlyrBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            //...
            @Override
            public void handle( ActionEvent e ) {
               // Disable buttons that require a row to be selected as this operation will remove the selected row
               rmvPlyrBtn.setDisable(true);
               dispPlyrMtchBtn.setDisable(true);
               
               // Get uid of the player of the selected row
               String id = tableViewPlayers.getSelectionModel().getSelectedItem().getId();
               
               // Delete the player with matching id
               tdb.deletePlayer(id);
               try {
                  // Update view
                  obsListPlayers = tdb.getPlayersList();
                  tableViewPlayers.setItems(obsListPlayers);
                  
                  // Update user message window
                  labelUserMsg.setText("Player " + id + " Successfully Removed.");
               } catch (TennisDatabaseRuntimeException ex) { // This is the last node in the tree
                  // Update view
                  tableViewPlayers.getColumns().clear(); // Force update
                  tableViewPlayers.getColumns().addAll(colUID, colFirst, colLast, colYear, colCountry, colWinLoss);
                  obsListPlayers = FXCollections.emptyObservableList();
                  tableViewPlayers.setItems(obsListPlayers);
                  
                  // Update user message window
                  labelUserMsg.setText("All Players Have Been Removed.");
               }
            }
         }
      );
      
      // Display Buttons
      // Display All Players button press
      dispPlyrBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent e ) {
               // Show/Hide appropriate tableviews
               tableViewPlayers.setVisible(true);
               tableViewMatches.setVisible(false);
               tableViewPlayerMatches.setVisible(false);
                              
               // Show/Hide appropriate input controls
               showPlayerInputControls();
               hideMatchInputControls();
               
               // Clear anything currently in the textboxes
               clearMatchInputControls();
               
               // Print all players (to fulfill assignment requirements)
               printAllPlayers();
               
               // Clear user message window
               labelUserMsg.setText("");
            }
         }
      );
      // Display All Matches Button
      dispMtchBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent e ) {
               // Show/Hide appropriate tableviews
               tableViewMatches.setVisible(true);
               tableViewPlayers.setVisible(false);
               tableViewPlayerMatches.setVisible(false);
               
               // Show/Hide appropriate input controls
               showMatchInputControls();
               hidePlayerInputControls();
               
               // Clear anything currently in the textboxes
               clearPlayerInputControls();
               
               // Print all matches (to fulfill assignment requirements)
               printAllMatches();
               
               // Clear user message window
               labelUserMsg.setText("");
            }
         }
      );
      // Display Player's Matches Button
      dispPlyrMtchBtn.setOnAction(
         new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent e ) {
               // Show/Hide appropriate tableviews
               tableViewPlayerMatches.setVisible(true);
               tableViewPlayers.setVisible(false);
               tableViewMatches.setVisible(false);
               
               // Show/Hide appropriate input controls
               hidePlayerInputControls();
               hideMatchInputControls();
               dispPlyrBtn.setVisible(true);
               dispMtchBtn.setVisible(true);
               
               // Clear anything currently in the textboxes
               clearPlayerInputControls();
               
               // Clear user message window
               labelUserMsg.setText("");
               
               // Get the uid of the selected player in the selected row
               String id = tableViewPlayers.getSelectionModel().getSelectedItem().getId();
               try {
                  tdb.printMatchesOfPlayer(id); // Try to print all of the player's matches
                  obsListPlayerMatches = tdb.getMatchesOfPlayer(id); // Try getting the list of player's matches
               } catch (TennisDatabaseRuntimeException ex) {
                  // Update observableList with empty list to clear previous player matches
                  obsListPlayerMatches = FXCollections.emptyObservableList();
                  
                  // Output error message to console and user message window
                  System.out.println(ex.getMessage());
                  labelUserMsg.setText(ex.getMessage());
               }
               // Update View
               tableViewPlayerMatches.setItems(obsListPlayerMatches);
            }
         }
      );
      
      // Application window configured... show it
      mainStage.show();
   }
   
   // Desc.:   Method to show all input controls for the player view screen
   private void showPlayerInputControls() {
      textFieldPUID.setVisible(true);
      textFieldPFirst.setVisible(true);
      textFieldPLast.setVisible(true);
      textFieldPYear.setVisible(true);
      textFieldPCountry.setVisible(true);
      
      addPlyrBtn.setVisible(true);
      rmvPlyrBtn.setVisible(true);
      dispMtchBtn.setVisible(true);
      dispPlyrMtchBtn.setVisible(true);
   }
   
   // Desc.:   Method to show all input controls for the match view screen
   private void showMatchInputControls() {
      textFieldMatchP1.setVisible(true);
      textFieldMatchP2.setVisible(true);
      textFieldMatchTournament.setVisible(true);
      textFieldMatchScores.setVisible(true);
      datePickerMatch.setVisible(true);
      
      addMtchBtn.setVisible(true);
      dispPlyrBtn.setVisible(true);
   }
   
   // Desc.:   Method to hide all input controls for the player view screen
   private void hidePlayerInputControls() {
      textFieldPUID.setVisible(false);
      textFieldPFirst.setVisible(false);
      textFieldPLast.setVisible(false);
      textFieldPYear.setVisible(false);
      textFieldPCountry.setVisible(false);
      
      addPlyrBtn.setVisible(false);
      rmvPlyrBtn.setVisible(false);
      dispMtchBtn.setVisible(false);
      dispPlyrMtchBtn.setVisible(false);
   }
   
   // Desc.:   Method to hide all input controls for the matche view screen
   private void hideMatchInputControls() {
      textFieldMatchP1.setVisible(false);
      textFieldMatchP2.setVisible(false);
      textFieldMatchTournament.setVisible(false);
      textFieldMatchScores.setVisible(false);
      datePickerMatch.setVisible(false);
      
      addMtchBtn.setVisible(false);
      dispPlyrBtn.setVisible(false);
   }
   
   // Desc.:   Method to clear any text in player input controls
   private void clearPlayerInputControls() {
      textFieldPUID.setText("");
      textFieldPFirst.setText("");
      textFieldPLast.setText("");
      textFieldPYear.setText("");
      textFieldPCountry.setText("");
   }
   
   // Desc.:   Method to clear any text in match input controls
   private void clearMatchInputControls() {
      textFieldMatchP1.setText("");
      textFieldMatchP2.setText("");
      textFieldMatchTournament.setText("");
      textFieldMatchScores.setText("");
      datePickerMatch.setValue(null); 
   }

   // Desc.:   Method to print all players in database
   // Output:  Prints all players to text-based console OR prints an error if there are no players to print
   private void printAllPlayers() {
      try { // Try to print all tennis players
         tdb.printAllPlayers();
      } catch (TennisDatabaseRuntimeException e) { // If database throws an exception... there are no players to print
         System.out.println(e.getMessage()); // Print error message to console
      }
   }
   
   // Desc.:   Method to print all matches in database
   // Output:  Prints all matches to text-based console OR prints an error message if there are no matches to print
   private void printAllMatches() {
      try { // Try to print all tennis matches
         tdb.printAllMatches();
      } catch (TennisDatabaseRuntimeException e) { // If database throws an exception... there are no matches to print
         System.out.println(e.getMessage()); // Print error message to console
      }
   }
}
// © 2018 Jeremy Maxey-Vesperman