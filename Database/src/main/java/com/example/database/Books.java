/**
 * File Name: Books.Java
 * Name: Gabriel
 * Description: Holds constructor to build the ArrayList in the Library class.
 * Date: 11/28/2023
 */
// Preprocessor Directives
package com.example.database;

public class Books {
    // Variables
    int bookID;
    String bookTitle;
    String bookAuthor;
    int dueDate;
    boolean checkedout;

    // Constructor
    public Books(int ID, String title, String author, int dueDate) {
        this.setBookID(ID);
        this.setBookTitle(title);
        this.setBookAuthor(author);
        this.setDueDate(dueDate);
        this.setCheckedout(false);
    }

    // Setters and Getters
    public int getBookID() {
        return this.bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public String getBookTitle() {
        return this.bookTitle;
    }
    public void setBookTitle(String bookName) {
        this.bookTitle = bookName;
    }
    public String getBookAuthor() {
        return this.bookAuthor;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    public boolean isCheckedout() {
        return this.checkedout;
    }
    public void setCheckedout(boolean checkedout) {
        this.checkedout = checkedout;
    }
    public int getDueDate() {
        return this.dueDate;
    }
    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }
}
