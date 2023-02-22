package ru.someboy.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.someboy.springcourse.models.Book;
import ru.someboy.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Slipets Artem
 */
@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int bookId) {
        return jdbcTemplate
                .query("SELECT * FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class), bookId)
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES( ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE book_id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", bookId);
    }

    // Join'им таблицы Book и Person и получаем человека, которому принадлежит книга с указанным id
    public Optional<Person> getBookOwner(int bookId) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.person_id " +
                        "WHERE Book.book_id = ?", new BeanPropertyRowMapper<>(Person.class), bookId)
                .stream().findAny();
    }

    // Освбождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    public void release(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", bookId);
    }

    // Назначает книгу человеку (этот метод вызывается, когда человек забирает книгу из библиотеки)
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", selectedPerson.getPersonId(), id);
    }
}
