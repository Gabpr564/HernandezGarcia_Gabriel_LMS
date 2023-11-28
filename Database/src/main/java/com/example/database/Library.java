/**
 * File Name: Library.java
 * Name: Gabriel Hernandez Garcia
 * Description: Creates the ArrayList used by the program to store all the information on the books.
 * Also stores all the methods to connect to the database and update it according to how the user wants to.
 * Date: 11/28/2023
 */

// Preprocess Directives
package com.example.database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Name: Library
 * Processes: Creates the Library "catalogue" and also updates, adds, removes, checks out and checks in books to the
 * database.
 */
public class Library {
    // ArrayList
    ObservableList<Books> catalogue = FXCollections.observableArrayList();

    // Updates Database
    public static void updateArrayList(ObservableList<Books> carry){
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "SELECT LibraryID, Title, Author, dueDate FROM catalogue";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url, uname, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("LibraryID");
                String title = resultSet.getString("Title");
                String author =resultSet.getString("Author");
                int dueDate= resultSet.getInt("dueDate");

                Books refresh = new Books(id,title,author,dueDate);
                carry.add(refresh);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

    // Add books to database
    public static void addBook(String title, String author){
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "INSERT INTO catalogue(title,author,isCheckedout) VALUES(?,?,?)";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url, uname, password);
            PreparedStatement pstate = conn.prepareStatement(query);
            pstate.setString(1,title);
            pstate.setString(2,author);
            pstate.setString(3,"N");
            pstate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

    // Removes books by ID or Title from database
    public static void removeBookByID(int ID){
        // Parameters to Start connection
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "DELETE FROM catalogue WHERE LibraryID = (?)";
        Connection conn = null;

        // Verifies Driver
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        // Starts Connection
        try{

            conn = DriverManager.getConnection(url, uname, password);
            PreparedStatement pstate = conn.prepareStatement(query);
            pstate.setInt(1,ID);
            pstate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            // Closes Connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void removeBookByTitle(String title){
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "DELETE FROM catalogue WHERE Title = (?)";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url, uname, password);
            PreparedStatement pstate = conn.prepareStatement(query);
            pstate.setString(1,title);
            pstate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    // Check out books from database
    public static void checkoutBook(String title){
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "UPDATE catalogue SET isCheckedout = 'Y', dueDate = 30 WHERE title = (?)";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url, uname, password);
            PreparedStatement pstate = conn.prepareStatement(query);
            pstate.setString(1,title);
            pstate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

    // Check in books from Database
    public static void checkinBook(String title){
        String uname = "root";
        String url = "jdbc:mysql://localhost:3306/lmsdatabase";
        String password = "admin";
        String query = "UPDATE catalogue SET isCheckedout = 'N', dueDate = NULL WHERE title = (?)";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            conn = DriverManager.getConnection(url, uname, password);
            PreparedStatement pstate = conn.prepareStatement(query);
            pstate.setString(1,title);
            pstate.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
