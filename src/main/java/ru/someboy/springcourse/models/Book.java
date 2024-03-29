package ru.someboy.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Slipets Artem
 */
public class Book {
    private int bookId;

    @NotEmpty(message = "Поле 'Название книги' не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле 'Название книги' должно быть от 2 до 100 символов длиной")
    private String title;

    @NotEmpty(message = "Поле 'ФИО' не должно быть пустым")
    @Size(min = 2, max = 100, message = "Поле 'ФИО' автора должно быть от 2 до 100 символов длиной")
    private String author;

    @Min(value = 1500, message = "Поле 'Дата публикации книги' должно быть не меньше 1500 года")
    private int year;

    //Пустой конструктор нужен для Spring, аннотация ModelAttribute
    public Book() {

    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
