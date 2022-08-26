package ru.someboy.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Slipets Artem
 */
public class Book {
    private int book_id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 50, message = "Title should be between 2 and 50 characters")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 30, message = "Author should be between 2 and 30 characters")
    private String author;

    @Min(value = 0, message = "year of birth should be greater than 0")
    private int yearOfPublication;

    public Book(int book_id, String title, String author, int yearOfPublication) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
