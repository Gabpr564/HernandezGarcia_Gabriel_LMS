/**
 * File Name: LMSGui.java
 * Name: Gabriel Hernandez Garcia
 * Description: This program displays 5 different buttons
 *              with the following related operations:
 *              Option:                 Operation:
 *              Add Books to database   Takes the user to the add-book scene which
 *                                      has them enter 2 string, 1 for the author and
 *                                      one for the title of the book. It also has a submit button
 *                                      that adds the entered information to the database.
 *
 *              Remove book from
 *              database                Takes the user to the remove-book scene where the user
 *                                      enters either the ID or Title of the book they would like
 *                                      to remove.
 *
 *              List all Books          Takes the user to the list-book scene where the user needs to
 *                                      click refresh to see all the books in the database.
 *
 *              Check out a book        Takes the user to the checkout scene where the user enters
 *                                      the title of the book they would like to check out. It then
 *                                      changes the status of the book in the database and adds a due date.
 *
 *              Check in a book         Takes the user to the check in scene where the user can check in a book
 *                                      only if it's been checked out.
 *
 *              Close                   Closes the application
 *
 * Date: 11/28/2023
 * */

// Preprocessor Directives
package com.example.database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import javafx.collections.FXCollections;

/**
 * Name:          LMSGui
 * Processes:     This class contains the main which is where the application runs.
 * Return Value:  An integer representing an error code; if the program ends without error, zero will be returned to the
 *                calling program or operating system.
 */
public class LMSGui extends Application {
    Stage LMSWindow;
    Scene signInScene,mainMenu, addScene, removeScene, listScene, checkOutScene, checkInScene;

    /**
     * Name: Main
     * @param args
     * Processes: Runs the program and launches the window in which the user had the options.
     */
    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Name: Start
     * @param primaryStage
     * @throws Exception
     * Processes: Creates the Windows and the scenes with the buttons.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Carry for Library Array
        var carry = new Library();
        var library = carry.catalogue;

        // Window
        LMSWindow = primaryStage;

        // Labels and Buttons
        Label welcome = new Label("Welcome!");
        Label menu = new Label("Please Select From the Following Choices:");
        Button add = new Button("Add book to database");
        add.setOnAction(e -> LMSWindow.setScene(addScene));
        Button remove = new Button("Remove book from database");
        remove.setOnAction(e -> LMSWindow.setScene(removeScene));
        Button list = new Button("List all Books");
        list.setOnAction(e -> { LMSWindow.setScene(listScene);});
        Button checkOut = new Button("Check out a book");
        checkOut.setOnAction(e -> LMSWindow.setScene(checkOutScene));
        Button checkIn = new Button("Check in a book");
        checkIn.setOnAction(e -> LMSWindow.setScene(checkInScene));
        Button addBack = new Button("Back");
        addBack.setOnAction(e -> LMSWindow.setScene(mainMenu));
        Button removeBack = new Button("Back");
        removeBack.setOnAction(e -> LMSWindow.setScene(mainMenu));
        Button listBack = new Button("Back");
        listBack.setOnAction(e -> LMSWindow.setScene(mainMenu));
        Button checkOutBack = new Button("Back");
        checkOutBack.setOnAction(e -> LMSWindow.setScene(mainMenu));
        Button checkInBack = new Button("Back");
        checkInBack.setOnAction(e -> LMSWindow.setScene(mainMenu));
        Button exit = new Button("Close");
        exit.setOnAction(e -> Platform.exit());

        // Layouts for all the Scenes are created
        VBox mainMenuLayout = new VBox(5);
        mainMenuLayout.getChildren().addAll(welcome,menu,add,remove,list,checkOut,checkIn,exit);
        mainMenu = new Scene(mainMenuLayout, 800, 600);

        VBox addLayout = new VBox(5);
        Label addTitleInstructions = new Label("Enter book Title:");
        TextField addBookInput = new TextField();
        Label addAuthorInstructions = new Label("Enter book author:");
        TextField addAuthorInput = new TextField();
        Label thankYou = new Label();
        // Add Submit button adds the book to the database using input from the user
        Button addSubmitButton = new Button("Submit");
        addSubmitButton.setOnAction(e -> {
            String bookTitle = addBookInput.getText();
            String bookAuthor = addAuthorInput.getText();
            boolean found = false;
            for(Books book :library){
                if(book.getBookTitle().equalsIgnoreCase(bookTitle)){
                    found = true;
                }
            }
            if(!found){
                thankYou.setText("Book Added!");
                Library.addBook(bookTitle, bookAuthor);
                library.add(new Books(library.size() + 1, bookTitle, bookAuthor, 0));
            } else {
                thankYou.setText("This book is already in the library!");
            }

        });
        addLayout.getChildren().addAll(addTitleInstructions,addBookInput, addAuthorInstructions, addAuthorInput,
                addSubmitButton, thankYou, addBack);
        addScene = new Scene(addLayout,800,600);

        VBox removeLayout = new VBox(5);
        Label removeInstructions = new Label("Enter the ID or Title of Book to be removed:");
        Label removeResult = new Label();
        TextField removeInput = new TextField();
        Button removeSubmitButton = new Button("Submit");
        // Remove Submit Button removes book from database using input from the user
        removeSubmitButton.setOnAction(e -> {
            String textTest = removeInput.getText();
            if(isInteger(textTest)){
                int searchNumber = Integer.parseInt(textTest);
                boolean removed = false;
                Library.removeBookByID(searchNumber);
                for(Books book :library){
                    if(book.getBookID() == searchNumber){
                        library.remove(book);
                        removed = true;
                        removeResult.setText("Book Removed!");
                    }
                }
            } else {
                boolean removed = false;
                for(Books book :library){
                    if(book.getBookTitle().equalsIgnoreCase(textTest)){
                        library.remove(book);
                        removeResult.setText("Book Removed");
                        Library.removeBookByTitle(textTest);
                        removed = true;
                    }
                }
                if(!removed){
                    removeResult.setText("Book not found in the library.");
                }

            }
        });
        removeLayout.getChildren().addAll(removeInstructions,removeInput,removeSubmitButton,removeResult,removeBack);
        removeScene = new Scene(removeLayout,800,600);

        VBox listLayout = new VBox(5);
        Label allBooks = new Label("All Book in the Library:");
        Button refresh = new Button("Refresh");
        ListView<Books> listView = new ListView<>(carry.catalogue);
        // Refresh Button refreshes the books in the Library ArrayList
        refresh.setOnAction(e->Library.updateArrayList(carry.catalogue));
        listLayout.getChildren().addAll(allBooks,listView,refresh, listBack);
        listScene = new Scene(listLayout,800,600);

        VBox checkoutLayout = new VBox(5);
        Label checkoutInstructions = new Label("Enter title of book you would like to check out:");
        TextField checkoutInput = new TextField();
        Label checkoutResult = new Label();
        Label returnInstruction = new Label();
        Button checkoutSubmit = new Button("Submit");
        // Checkout Submit Button changes status of book to checked out in database
        checkoutSubmit.setOnAction(e -> {
            String checkoutTitle = checkoutInput.getText();
            boolean checkedOut = false;
            for(Books book:library){
                if(book.getBookTitle().equalsIgnoreCase(checkoutTitle)){
                    if(!book.isCheckedout()){
                        book.setCheckedout(true);
                        checkoutResult.setText("You have checked out:"  + book);
                        checkedOut = true;
                        book.setDueDate(30);
                        Library.checkoutBook(checkoutTitle);
                        returnInstruction.setText("You have 30 days to return the book.");
                    } else {
                        checkoutResult.setText("This book has already been checked out.");
                    }
                    break;
                }
            }
            if(!checkedOut){
                checkoutResult.setText("Book was not found or already checked out");
            }
        });
        checkoutLayout.getChildren().addAll(checkoutInstructions,checkoutInput,checkoutSubmit,checkoutResult,
                returnInstruction,checkOutBack);
        checkOutScene = new Scene(checkoutLayout,800,600);

        VBox checkInLayout = new VBox(5);
        Label checkInInstructions = new Label("Enter title of book you would like to check in:");
        TextField checkInInput = new TextField();
        Label checkInResult = new Label();
        Button checkInSubmit = new Button("Submit");
        // Check In Submit button changes status of book checked in the database
        checkInSubmit.setOnAction(e -> {
            String checkInTitle = checkInInput.getText();
            boolean checkedIn = false;
            for(Books book:library){
                if(book.getBookTitle().equalsIgnoreCase(checkInTitle)){
                    if(book.isCheckedout()){
                        book.setCheckedout(false);
                        checkInResult.setText("You have checked in:"  + book);
                        checkedIn = true;
                        book.setDueDate(0);
                        Library.checkinBook(checkInTitle);
                    } else {
                        checkInResult.setText("This book is still in the Library and has not been checked out");
                    }
                    break;
                }
            }
            if(!checkedIn){
                System.out.println("This book is still in the Library and has not been checked out");
            }
        });
        checkInLayout.getChildren().addAll(checkInInstructions, checkInInput, checkInSubmit, checkInResult,
                checkInBack);
        checkInScene = new Scene(checkInLayout,800,600);

        // Launches Window
        LMSWindow.setScene(mainMenu);
        LMSWindow.setTitle("Library Management System");
        LMSWindow.show();
    }

    /**
     * Name: isInteger
      * @param input
     * @return boolean
     * Processes: Determines whether number passed in is an Integer or not.
     */
    private boolean isInteger(String input){
        try{
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
